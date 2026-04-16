import edu.princeton.cs.algs4.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        String s = "the quick brown fox jumps over the lazy dog.";
        String r = s.replaceAll("\\s([a-z]{3})\\s", " <b>$1</b> ");
        System.out.println(r);
    }
}
