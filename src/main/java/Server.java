import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Server extends HttpServlet {
    private static final int PORT = 8080;

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.getConnector();

        Context context = tomcat.addContext("", System.getProperty("java.io.tmpdir"));
        Tomcat.addServlet(context, "helloServlet", new Server());
        context.addServletMappingDecoded("/hello", "helloServlet");
        Tomcat.addServlet(context, "redirectServlet", new RedirectServlet());
        context.addServletMappingDecoded("/hi", "redirectServlet");
        Tomcat.addServlet(context, "forwardServlet", new ForwardServlet());
        context.addServletMappingDecoded("/morning", "forwardServlet");
        Tomcat.addServlet(context, "signInServlet", new SignInServlet());
        context.addServletMappingDecoded("/signin", "signInServlet");
        Tomcat.addServlet(context, "signOutServlet", new SignOutServlet());
        context.addServletMappingDecoded("/signout", "signOutServlet");
        Tomcat.addServlet(context, "indexServlet", new IndexServlet());
        context.addServletMappingDecoded("/", "indexServlet");
        context.addServletMappingDecoded("/index", "indexServlet");
        tomcat.start();
        System.out.printf("Server is running at http://localhost:%d/%n", PORT);
        tomcat.getServer().await();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/plain");

        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            name = "world";
        }
        response.getWriter().write("Hello " + name);
    }
}
