package framework;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.FileLoader;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.Writer;

/** View 层适配器：根据 ModelAndView 查找 Pebble 模板并输出最终 HTML。 */
public class ViewEngine {
    private final PebbleEngine engine;

    public ViewEngine(ServletContext servletContext) {
        // 模板位于 WEB-INF 下，浏览器无法绕过 Controller 直接访问。
        String templateRoot = servletContext.getRealPath("/WEB-INF/templates");
        if (templateRoot == null) {
            throw new IllegalStateException("Templates require an exploded webapp directory");
        }

        // Demo 直接从源码目录加载模板，修改 HTML 后无需重新打包。
        FileLoader loader = new FileLoader();
        loader.setCharset("UTF-8");
        loader.setPrefix(templateRoot);
        loader.setSuffix("");
        this.engine = new PebbleEngine.Builder()
                // 自动转义模板变量，防止普通字符串被当作 HTML 执行。
                .autoEscaping(true)
                // 开发阶段关闭缓存，模板修改后刷新浏览器即可生效。
                .cacheActive(false)
                .loader(loader)
                .build();
    }

    /** 将模板渲染结果直接写入 Servlet Response 提供的 Writer。 */
    public void render(ModelAndView modelAndView, Writer writer) throws IOException {
        PebbleTemplate template = engine.getTemplate(modelAndView.view());
        template.evaluate(writer, modelAndView.model());
    }
}
