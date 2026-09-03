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
        context.addServletMappingDecoded("/", "helloServlet");

        tomcat.start();
        System.out.printf("Server is running at http://localhost:%d/%n", PORT);
        tomcat.getServer().await();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/html");
        response.getWriter().write("<html><body><h1>Hello, world!</h1></body></html>");
    }
}
