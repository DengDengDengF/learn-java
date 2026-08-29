import java.util.*;
import java.util.stream.*;

interface TextNode {
    // 设置text:
    void setText(String text);

    // 获取text:
    String getText();
}

class SpanNode implements TextNode {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return "<span>" + text + "</span>";
    }
}

abstract class NodeDecorator implements TextNode {
    protected final TextNode target;

    protected NodeDecorator(TextNode target) {
        this.target = target;
    }

    public void setText(String text) {
        this.target.setText(text);
    }
}

class BoldDecorator extends NodeDecorator {
    public BoldDecorator(TextNode target) {
        super(target);
    }

    public String getText() {
        return "<b>" + target.getText() + "</b>";
    }
}

class UnderlineDecorator extends NodeDecorator {
    public UnderlineDecorator(TextNode target) {
        super(target);
    }

    public String getText() {
        return "<u>" + target.getText() + "</u>";
    }
}

public class Main {
    public static void main(String[] args) {
        TextNode n1 = new SpanNode();
        n1.setText("Hello");
        System.out.println(n1.getText());
        TextNode n2 = new BoldDecorator(new UnderlineDecorator(new SpanNode()));
        n2.setText("Decorated");
        System.out.println(n2.getText());
    }
}
