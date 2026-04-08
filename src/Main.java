import edu.princeton.cs.algs4.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        String time = "22:01:59";

        Pattern pattern = Pattern.compile("([0-1]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)");
        Matcher matcher = pattern.matcher(time);

        if (matcher.matches()) {
            String hour = matcher.group(1);
            String minute = matcher.group(2);
            String second = matcher.group(3);

            System.out.println("时: " + hour);
            System.out.println("分: " + minute);
            System.out.println("秒: " + second);
        }else{
            System.out.println("not match");
        }
    }
}
