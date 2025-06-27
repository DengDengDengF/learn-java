import edu.princeton.cs.algs4.StdDraw;
public class Main {
    public static void main(String[] args) {
        // 设置画布和坐标范围
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 400);
        StdDraw.setYscale(0, 400);

        // 设置画笔属性
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.RED);

        // 绘制正方形
        StdDraw.square(200, 200, 100);


        // 显示结果（双缓冲模式下必需）
        StdDraw.show();
    }
}
