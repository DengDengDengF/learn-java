import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String redirectUrl = request.getContextPath() + "/hello";
        if (name != null) {
            redirectUrl += "?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8);
        }
        response.sendRedirect(redirectUrl);
    }
}
