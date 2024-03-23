package game.Blocks;

import java.awt.Color;

public abstract class Block {
    protected int[][] shape;
    protected Color color;

    public Block(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public int getShape(int x, int y) {
        return shape[y][x];
    }

    public Color getColor() {
        return color;
    }

    public int height() {
        return shape.length;
    }

    public int width() {
        return shape[0].length;
    }

    public void rotate() {
        int[][] newShape = new int[width()][height()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                newShape[x][height() - y - 1] = shape[y][x];
            }
        }
        shape = newShape;
    }
}
