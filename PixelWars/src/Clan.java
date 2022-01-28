import java.awt.*;

public class Clan {
    private Vector2 core;
    private Board board;

    private int sizeX;
    private int sizeY;

    private Color color;

    public Clan(Board board) {
        this.core = new Vector2((int) Math.floor(Math.random() * board.getWidth()), (int) Math.floor(Math.random() * board.getHeight()));

        this.board = board;

        this.sizeX = board.getWidth();
        this.sizeY = board.getHeight();

        this.color = Helper.generateRandomColor();
    }

    public void spread(int x, int y) {
        set(x + 1, y);
        set(x - 1, y);
        set(x, y + 1);
        set(x, y - 1);
    }

    private void set(int x, int y) {
        if (x < 0 || x >= this.sizeX || y < 0 || y >= this.sizeY) {
            return;
        }

        if (board.getCell(x, y) == null || isSuperior(x, y)) {
            board.setCell(x, y, this);
        }
    }

    private boolean isSuperior(int x, int y) {
        int counter = 0;

        // Formula:
        // The further the cell is away from its core, the less "power" it has
        // A cell gains a +10% chance for every surrounding tile that belongs to the same clan as itself
        Vector2 currentPosition = new Vector2(x, y);
        int distance = currentPosition.getDistanceFromCore(this.core);

        if (!(x - 1 < 0 || x + 1 >= this.sizeX || y - 1 < 0 || y + 1 >= this.sizeY)) {
            if (board.getCell(x + 1, y) == this) counter++;
            if (board.getCell(x - 1, y) == this) counter++;
            if (board.getCell(x, y + 1) == this) counter++;
            if (board.getCell(x, y - 1) == this) counter++;
        }

        return Math.random() < (counter * 0.1) / ((distance * 0.01) + 1) + 0.1;
    }

    public int getCoreX() {
        return this.core.getX();
    }

    public int getCoreY() {
        return this.core.getY();
    }

    public Color getColor() {
        return this.color;
    }
}
