import java.awt.*;

public class Board {
    private int width;
    private int height;
    private Clan[][] matrix;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new Clan[this.width][this.height];
    }

    public void generate() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(this.width * 4, this.height * 4);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);

        StdDraw.setPenRadius(0.0005);
        for (int x = 0; x < this.width; x++) {
            StdDraw.line(x, 0, x, this.height);
        }

        for (int y = 0; y < this.height; y++) {
            StdDraw.line(0, y, this.width, y);
        }

        StdDraw.disableDoubleBuffering();
        StdDraw.show();
    }

    public void draw(int x, int y, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
    }

    public void fill(Clan[] players) {
        for (Clan player : players) {
            this.matrix[player.getCoreX()][player.getCoreY()] = player;
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setCell(int x, int y, Clan value) {
        this.matrix[x][y] = value;
    }

    public Clan getCell(int x, int y) {
        return this.matrix[x][y];
    }
}
