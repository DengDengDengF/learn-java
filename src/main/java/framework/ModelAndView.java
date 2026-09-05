package framework;

import java.util.Map;

/**
 * Controller 的标准返回值。
 *
 * @param view  Pebble 模板名称；以 {@code redirect:} 开头时表示重定向
 * @param model 传递给模板的数据
 */
public record ModelAndView(String view, Map<String, Object> model) {
    /** 创建一个不携带模板数据的返回结果。 */
    public ModelAndView(String view) {
        this(view, Map.of());
    }

    public ModelAndView {
        if (view == null || view.isBlank()) {
            throw new IllegalArgumentException("view must not be blank");
        }
        // 返回后不允许外部再改变 Model，避免渲染期间出现不可预测的数据变化。
        model = model == null ? Map.of() : Map.copyOf(model);
    }
}
