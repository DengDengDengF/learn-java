import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

public class SignInServlet extends HttpServlet {
    // 模拟一个数据库:
    private final Map<String, String> users = Map.of("bob", "bob123", "alice", "alice123", "tom", "tomcat");

    // GET请求时显示登录页:
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Sign In</h1>");
        pw.write("<form action=\"" + req.getContextPath() + "/signin\" method=\"post\">");
        pw.write("<p>Username: <input name=\"username\"></p>");
        pw.write("<p>Password: <input name=\"password\" type=\"password\"></p>");
        pw.write("<p><button type=\"submit\">Sign In</button> <a href=\"/\">Cancel</a></p>");
        pw.write("</form>");
        pw.flush();
    }

    // POST请求时处理用户登录:
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("====== Request Info ======");
        System.out.println("Method: " + req.getMethod());
        System.out.println("URL: " + req.getRequestURL());
        System.out.println("Query String: " + req.getQueryString());

        // 1. 打印 Headers
        System.out.println("--- Headers ---");
        java.util.Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + req.getHeader(headerName));
        }

        // 2. 规范化打印 ParameterMap (解决 String[] 打印出地址值的问题)
        System.out.println("--- Parameters ---");
        req.getParameterMap().forEach((key, values) -> {
            System.out.println(key + " = " + java.util.Arrays.toString(values));
        });
        System.out.println("==========================");
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String expectedPassword = name == null ? null : users.get(name.toLowerCase(Locale.ROOT));
        if (expectedPassword != null && expectedPassword.equals(password)) {
            // 登录成功:
            req.getSession().setAttribute("user", name);
            resp.sendRedirect(req.getContextPath() + "/index");
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
