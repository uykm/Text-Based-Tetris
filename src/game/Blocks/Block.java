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
        int n = shape.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                // 상단 저장
                int top = shape[first][i];
                // 왼쪽 -> 상단
                shape[first][i] = shape[last-offset][first];
                // 하단 -> 왼쪽
                shape[last-offset][first] = shape[last][last-offset];
                // 오른쪽 -> 하단
                shape[last][last-offset] = shape[i][last];
                // 저장된 상단 -> 오른쪽
                shape[i][last] = top;
            }
        }
    }

    public void rotateBack() {
        int n = shape.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                // 상단 저장
                int top = shape[first][i];
                // 오른쪽 -> 상단
                shape[first][i] = shape[i][last];
                // 하단 -> 오른쪽
                shape[i][last] = shape[last][last-offset];
                // 왼쪽 -> 하단
                shape[last][last-offset] = shape[last-offset][first];
                // 저장된 상단 -> 왼쪽
                shape[last-offset][first] = top;
            }
        }
    }

    public int[][] getRotatedShape() {
        int[][] rotatedShape = new int[shape.length][shape[0].length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotatedShape[i][j] = shape[i][j];
            }
        }
        return rotatedShape;
    }
}
