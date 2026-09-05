import controller.HelloController;
import controller.UserController;
import filter.AuthFilter;
import filter.EncodingFilter;
import filter.LogFilter;
import framework.DispatcherServlet;
import framework.FileServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.nio.file.Path;

/**
 * Demo 的启动入口。
 *
 * <p>这里使用内嵌 Tomcat，因此不需要单独安装和配置 Tomcat。</p>
 */
public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) throws LifecycleException {
        // 创建 HTTP 连接器。调用 getConnector() 可确保默认连接器在 start() 前完成初始化。
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.getConnector();

        // 模板和静态文件都从 webapp 目录中读取。
        String webapp = Path.of("src", "main", "webapp").toAbsolutePath().toString();
        Context context = tomcat.addContext("", webapp);

        // Filter 按注册顺序组成链：编码 -> 日志 -> 登录校验 -> Servlet。
        addFilter(context, "encoding", new EncodingFilter(), "/*");
        addFilter(context, "log", new LogFilter(), "/*");
        addFilter(context, "auth", new AuthFilter(), "/user/*");

        // 静态资源使用专门的 Servlet，避免被下面的 MVC 总入口接管。
        Tomcat.addServlet(context, "files", new FileServlet());
        context.addServletMappingDecoded("/static/*", "files");
        context.addServletMappingDecoded("/favicon.ico", "files");

        // 显式注册 Controller，DispatcherServlet 会扫描这些对象的方法注解。
        DispatcherServlet dispatcher = new DispatcherServlet(
                new HelloController(),
                new UserController()
        );
        // 映射到 /，让所有未被更精确规则匹配的动态请求经过 MVC 调度器。
        Tomcat.addServlet(context, "dispatcher", dispatcher).setLoadOnStartup(1);
        context.addServletMappingDecoded("/", "dispatcher");

        tomcat.start();
        System.out.printf("MVC demo is running at http://localhost:%d/hello?name=Bob%n", PORT);
        tomcat.getServer().await();
    }

    private static void addFilter(Context context, String name, jakarta.servlet.Filter filter,
                                  String urlPattern) {
        FilterDef definition = new FilterDef();
        definition.setFilterName(name);
        definition.setFilter(filter);
        context.addFilterDef(definition);

        FilterMap mapping = new FilterMap();
        mapping.setFilterName(name);
        mapping.addURLPatternDecoded(urlPattern);
        mapping.setDispatcher("REQUEST");
        context.addFilterMap(mapping);
    }
}
