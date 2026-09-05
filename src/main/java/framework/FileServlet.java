package framework;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

/** 处理 /static/* 和 /favicon.ico，不参与 MVC 模板渲染。 */
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Request URI 包含 contextPath，资源路径必须先将它去掉。
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String mime = getServletContext().getMimeType(path);
        if (mime == null) {
            mime = fallbackMimeType(path);
        }
        if (mime == null) {
            mime = URLConnection.guessContentTypeFromName(path);
        }
        response.setContentType(mime == null ? "application/octet-stream" : mime);

        // ServletContext 只会在当前 Web 应用根目录中查找资源。
        try (InputStream input = getServletContext().getResourceAsStream(path)) {
            if (input == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            OutputStream output = response.getOutputStream();
            input.transferTo(output);
            output.flush();
        }
    }

    /** 某些 Windows 环境没有注册常见 Web 文件的 MIME 类型，这里提供稳定的回退值。 */
    private String fallbackMimeType(String path) {
        String lowerPath = path.toLowerCase();
        if (lowerPath.endsWith(".css")) {
            return "text/css";
        }
        if (lowerPath.endsWith(".js")) {
            return "text/javascript";
        }
        if (lowerPath.endsWith(".svg")) {
            return "image/svg+xml";
        }
        if (lowerPath.endsWith(".ico")) {
            return "image/x-icon";
        }
        return null;
    }
}
