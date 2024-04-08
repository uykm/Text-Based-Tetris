package logic;

import model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static logic.SettingProperties.COLOR_BLIND_MODE;
import static logic.SettingProperties.DEFAULT_COLOR_MODE;

public abstract class Block {
    protected int[][] shape;
    protected Color[] colors;
    private final SettingController settingController;
    private final RWSelection rwSelection;
    private static int erasedLineCountForItem = 0;

    public Block(int[][] shape, Color[] colors) {
        settingController = new SettingController();
        rwSelection = new RWSelection(settingController.getDifficulty());
        this.shape = shape;
        this.colors = colors;
    }

    public int getShape(int x, int y) {
        return shape[y][x];
    }

    public Color getColor() {
        String mode = settingController.getColorMode(COLOR_BLIND_MODE, DEFAULT_COLOR_MODE);

        switch (mode) {
            case "protanopia":
                return colors[1]; // 적생맹
            case "deuteranopia":
                return colors[2]; // 녹생맹
            case "tritanopia":
                return colors[3]; // 청색맹
            default:
                return colors[0]; // 기본 색상
        }
    }

    public int height() {
        return shape.length;
    }

    public int width() {
        return shape[0].length;
    }

    public void rotate() {
        rotateArray(true);
    }

    public void rotateBack() {
        rotateArray(false);
    }

    // Rotate the block clockwise or counterclockwise
    private void rotateArray(boolean clockwise) {
        int n = shape.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - i - 1; j++) {
                int temp = shape[i][j];
                if (clockwise) {
                    shape[i][j] = shape[n - j - 1][i];
                    shape[n - j - 1][i] = shape[n - i - 1][n - j - 1];
                    shape[n - i - 1][n - j - 1] = shape[j][n - i - 1];
                    shape[j][n - i - 1] = temp;
                } else {
                    shape[i][j] = shape[j][n - i - 1];
                    shape[j][n - i - 1] = shape[n - i - 1][n - j - 1];
                    shape[n - i - 1][n - j - 1] = shape[n - j - 1][i];
                    shape[n - j - 1][i] = temp;
                }
            }
        }
    }

    public static Block getBlock(BlockType blockType) {
        return switch (blockType) {
            case IBlock -> new IBlock();
            case JBlock -> new JBlock();
            case LBlock -> new LBlock();
            case OBlock -> new OBlock();
            case SBlock -> new SBlock();
            case TBlock -> new TBlock();
            case ZBlock -> new ZBlock();
            case NullBlock -> new NullBlock();
            case WeightItemBlock -> new WeightItemBlock();
        };
    }

    public static void setErasedLineCountForItem(int count) {
        erasedLineCountForItem = count;
    }

    public static int getErasedLineCountForItem() {
        return erasedLineCountForItem;
    }

    //select for the block
    public Block selectBlock(boolean isItem, int erasedLineCount) {

        int blockType = rwSelection.select();
        Random random = new Random();

        // 기본 블록 선택 로직
        Block basicBlock = switch (blockType) {
            case 0 -> new IBlock();
            case 1 -> new JBlock();
            case 2 -> new LBlock();
            case 3 -> new OBlock();
            case 4 -> new SBlock();
            case 5 -> new TBlock();
            case 6 -> new ZBlock();
            default -> throw new IllegalArgumentException("Invalid block type.");
        };

        // 10줄이 삭제될 때마다 아이템 블록 생성 로직
        if (isItem && erasedLineCount % 3 == 0 && getErasedLineCountForItem() < erasedLineCount) {
            setErasedLineCountForItem(erasedLineCount);

            // 50% 확률로 기존 아이템 블록 또는 WeightItemBlock을 선택
            if (random.nextBoolean()) {
                // 기존 아이템 블록 로직
                ArrayList<Point> greaterThanZeroIndices = new ArrayList<>();
                for (int y = 0; y < basicBlock.shape.length; y++) {
                    for (int x = 0; x < basicBlock.shape[y].length; x++) {
                        if (basicBlock.shape[y][x] > 0) {
                            greaterThanZeroIndices.add(new Point(x, y));
                        }
                    }
                }

                if (!greaterThanZeroIndices.isEmpty()) {
                    Point selectedPoint = greaterThanZeroIndices.get(random.nextInt(greaterThanZeroIndices.size()));
                    basicBlock.shape[selectedPoint.y][selectedPoint.x] = 8;
                }

                return basicBlock;

            } else {
                // WeightItemBlock 생성 로직
                return new WeightItemBlock();
            }
        } else {
            // 일반 블록 선택 로직 (10줄이 삭제되지 않았을 때)
            return basicBlock;
        }
    }
}
