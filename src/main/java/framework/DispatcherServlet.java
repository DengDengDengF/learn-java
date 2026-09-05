package framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * MVC 的统一请求入口。
 *
 * <p>负责建立路由表、绑定方法参数、调用 Controller，并处理 Controller 返回的
 * ModelAndView。具体业务逻辑仍然留在 Controller 中。</p>
 */
public class DispatcherServlet extends HttpServlet {
    /** 由 Server 显式注册、等待扫描的 Controller 实例。 */
    private final Object[] controllers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    // GET 和 POST 使用独立路由表，所以相同路径可以分别处理两种 HTTP 方法。
    private Map<String, HandlerMethod> getMappings = Map.of();
    private Map<String, HandlerMethod> postMappings = Map.of();
    private ViewEngine viewEngine;

    public DispatcherServlet(Object... controllers) {
        this.controllers = controllers.clone();
    }

    @Override
    public void init() throws ServletException {
        // 初始化时只扫描一次，后续请求直接查 Map，不需要重复使用反射寻找路由。
        Map<String, HandlerMethod> gets = new HashMap<>();
        Map<String, HandlerMethod> posts = new HashMap<>();
        for (Object controller : controllers) {
            scanController(controller, gets, posts);
        }
        this.getMappings = Map.copyOf(gets);
        this.postMappings = Map.copyOf(posts);
        this.viewEngine = new ViewEngine(getServletContext());
    }

    private void scanController(Object controller, Map<String, HandlerMethod> gets,
                                Map<String, HandlerMethod> posts) throws ServletException {
        for (Method method : controller.getClass().getDeclaredMethods()) {
            GetMapping get = method.getAnnotation(GetMapping.class);
            PostMapping post = method.getAnnotation(PostMapping.class);
            // 同时处理 GET 和 POST 会让参数来源含糊，因此在启动阶段拒绝。
            if (get != null && post != null) {
                throw new ServletException(method + " cannot have both GET and POST mappings");
            }
            if (get != null) {
                register(gets, get.value(), new HandlerMethod(controller, method, false));
            }
            if (post != null) {
                register(posts, post.value(), new HandlerMethod(controller, method, true));
            }
        }
    }

    private void register(Map<String, HandlerMethod> mappings, String path, HandlerMethod handler)
            throws ServletException {
        if (!path.startsWith("/")) {
            throw new ServletException("Mapping path must start with '/': " + path);
        }
        // 重复路由属于配置错误，应在启动时暴露，而不是随机选择一个 Controller。
        if (mappings.putIfAbsent(path, handler) != null) {
            throw new ServletException("Duplicate mapping: " + path);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dispatch(request, response, getMappings);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 为突出 JSON -> JavaBean 的过程，本 Demo 不处理表单编码的 POST 请求。
        String contentType = request.getContentType();
        if (contentType == null || !contentType.toLowerCase().startsWith("application/json")) {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "POST requests must use application/json");
            return;
        }
        dispatch(request, response, postMappings);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response,
                          Map<String, HandlerMethod> mappings) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        // 使用去掉应用上下文后的路径作为路由表的 key。
        String path = request.getRequestURI().substring(request.getContextPath().length());
        HandlerMethod handler = mappings.get(path);
        if (handler == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ModelAndView modelAndView = handler.invoke(request, response, objectMapper);
        // null 表示 Controller 已经通过 HttpServletResponse 自行完成响应。
        if (modelAndView == null) {
            return;
        }
        // redirect: 是框架和 Controller 约定的特殊 View 名称，不进入模板引擎。
        if (modelAndView.view().startsWith("redirect:")) {
            String target = modelAndView.view().substring("redirect:".length());
            response.sendRedirect(request.getContextPath() + target);
            return;
        }
        viewEngine.render(modelAndView, response.getWriter());
    }
    /**
     * 封装一次 Controller 调用所需的信息和参数绑定规则。
     * 文档中的 GetDispatcher、PostDispatcher 在这里被合并成了一个类。
     */
    private static final class HandlerMethod {
        private final Object controller;
        private final Method method;
        /** true 表示普通业务参数从 JSON Body 读取，否则从 Query String 读取。 */
        private final boolean jsonBody;

        private HandlerMethod(Object controller, Method method, boolean jsonBody) throws ServletException {
            this.controller = controller;
            this.method = method;
            this.jsonBody = jsonBody;
            validateMethod();
        }

        private void validateMethod() throws ServletException {
            if (!ModelAndView.class.isAssignableFrom(method.getReturnType())) {
                throw new ServletException("Controller method must return ModelAndView: " + method);
            }
            // HTTP Body 只能读取一次，因此每个 POST 方法只允许一个 JavaBean 参数。
            long bodyParameters = java.util.Arrays.stream(method.getParameterTypes())
                    .filter(type -> !isServletType(type))
                    .count();
            if (jsonBody && bodyParameters > 1) {
                throw new ServletException("POST handler supports only one JSON body: " + method);
            }
        }

        private ModelAndView invoke(HttpServletRequest request, HttpServletResponse response,
                                    ObjectMapper objectMapper) throws ServletException, IOException {
            Object[] arguments = new Object[method.getParameterCount()];
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Class<?> type = parameter.getType();
                // Servlet 基础对象不需要业务代码手动获取，由框架按类型注入。
                if (type == HttpServletRequest.class) {
                    arguments[i] = request;
                } else if (type == HttpServletResponse.class) {
                    arguments[i] = response;
                } else if (type == HttpSession.class) {
                    arguments[i] = request.getSession();
                } else if (jsonBody) {
                    // Jackson 根据目标参数类型，把整个 JSON Body 反序列化成 JavaBean。
                    arguments[i] = objectMapper.readValue(request.getReader(), type);
                } else {
                    arguments[i] = bindQueryParameter(request, parameter);
                }
            }

            try {
                // 参数数组准备完成后，才真正进入 Controller 业务方法。
                return (ModelAndView) method.invoke(controller, arguments);
            } catch (IllegalAccessException e) {
                throw new ServletException("Cannot invoke controller method: " + method, e);
            } catch (InvocationTargetException e) {
                // 反射会包装 Controller 抛出的异常，这里恢复原始异常语义。
                Throwable cause = e.getCause();
                if (cause instanceof IOException ioException) {
                    throw ioException;
                }
                if (cause instanceof ServletException servletException) {
                    throw servletException;
                }
                if (cause instanceof RuntimeException runtimeException) {
                    throw runtimeException;
                }
                throw new ServletException("Controller method failed: " + method, cause);
            }
        }

        private static Object bindQueryParameter(HttpServletRequest request, Parameter parameter)
                throws ServletException {
            // parameter.getName() 依赖 pom.xml 中编译器的 <parameters>true</parameters>。
            String value = request.getParameter(parameter.getName());
            Class<?> type = parameter.getType();
            try {
                if (type == String.class) {
                    return value == null ? "" : value;
                }
                if (type == int.class) {
                    return value == null ? 0 : Integer.parseInt(value);
                }
                if (type == long.class) {
                    return value == null ? 0L : Long.parseLong(value);
                }
                if (type == boolean.class) {
                    return value != null && Boolean.parseBoolean(value);
                }
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid value for parameter '" + parameter.getName() + "'", e);
            }
            throw new ServletException("Unsupported GET parameter type: " + type.getName());
        }

        private static boolean isServletType(Class<?> type) {
            return type == HttpServletRequest.class
                    || type == HttpServletResponse.class
                    || type == HttpSession.class;
        }
    }
}
