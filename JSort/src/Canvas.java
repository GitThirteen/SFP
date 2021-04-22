import java.awt.*;
import algorithms.Algorithm;

public class Canvas {
    private int width;
    private int height;
    private Color color;

    private Algorithm algorithm;
    private String name;

    Canvas(int width, int height, Color color, Algorithm algorithm, String name) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.algorithm = algorithm;
        this.name = name;
    }

    public void createCanvas() {
        StdDraw.setCanvasSize(this.width, this.height);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);

        StdDraw.enableDoubleBuffering();
    }

    public void draw() {
        int[] algorithm = this.algorithm.getArray();
        float colWidth = (float) this.width / algorithm.length;
        float colHeight = (float) this.height / algorithm.length;

        float curWidthPos = colWidth / 2;
        for (int i = 0; i < algorithm.length; i++) {
            StdDraw.filledRectangle(curWidthPos, 0, colWidth / 2, algorithm[i] * colHeight);
            curWidthPos += colWidth;
        }

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.textLeft(this.width * 0.05, this.height * 0.95, this.name);
        StdDraw.setPenColor(this.color);

        StdDraw.show();
    }

    public void clearCanvas() {
        StdDraw.clear(StdDraw.WHITE);
    }
}
