package controller;

import framework.GetMapping;
import framework.ModelAndView;
import framework.PostMapping;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.SignInBean;
import model.User;

import java.io.IOException;
import java.util.Map;

/** 演示 JSON 登录、Session、权限判断和退出流程。 */
public class UserController {
    @GetMapping("/signin")
    public ModelAndView signin() {
        return new ModelAndView("signin.html");
    }

    @PostMapping("/signin")
    public ModelAndView doSignin(SignInBean bean, HttpSession session) {
        // SignInBean 来自 JSON Body，HttpSession 则由 DispatcherServlet 按类型注入。
        if (!"password".equals(bean.getPassword()) || bean.getUsername().isBlank()) {
            return new ModelAndView("signin.html", Map.of("error", "用户名不能为空，密码应为 password"));
        }
        // Demo 用固定规则模拟身份和权限，不连接真实数据库。
        User user = new User(bean.getUsername(), "admin".equalsIgnoreCase(bean.getUsername()));
        session.setAttribute("user", user);
        return new ModelAndView("redirect:/user/profile");
    }

    @GetMapping("/user/profile")
    public ModelAndView profile(HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        // 未登录时不直接输出页面，而是让浏览器重新请求登录地址。
        if (user == null) {
            return new ModelAndView("redirect:/signin");
        }
        if (!user.isManager()) {
            // Controller 已写入 403，返回 null 告诉框架不要继续渲染。
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only admin can view this page");
            return null;
        }
        return new ModelAndView("profile.html", Map.of("user", user));
    }

    @GetMapping("/signout")
    public ModelAndView signout(HttpSession session) {
        // invalidate() 会清除当前会话及其中保存的 user。
        session.invalidate();
        return new ModelAndView("redirect:/signin");
    }
}
