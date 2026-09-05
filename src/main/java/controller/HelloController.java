package controller;

import framework.GetMapping;
import framework.ModelAndView;

import java.util.Map;

/** 演示重定向、GET Query 参数绑定和模板渲染。 */
public class HelloController {
    @GetMapping("/")
    public ModelAndView index() {
        // redirect: 前缀由 DispatcherServlet 转换成 HTTP 302。
        return new ModelAndView("redirect:/hello");
    }

    @GetMapping("/hello")
    public ModelAndView hello(String name, int age, boolean vip) {
        // name、age、vip 由框架按方法参数名和类型自动绑定。
        String displayName = name.isBlank() ? "World" : name;
        // Map 中的键可以在 Pebble 模板中通过 {{ name }} 等表达式访问。
        return new ModelAndView("hello.html", Map.of(
                "name", displayName,
                "age", age,
                "vip", vip
        ));
    }
}
