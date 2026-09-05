package framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明一个 Controller 方法处理指定路径的 GET 请求。
 * 该注解保留到运行期，DispatcherServlet 才能通过反射读取。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMapping {
    /** 请求路径，例如 {@code /hello}。 */
    String value();
}
