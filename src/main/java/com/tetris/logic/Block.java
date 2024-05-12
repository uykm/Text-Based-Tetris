package com.tetris.logic;

import com.tetris.model.*;

import java.awt.*;

import static com.tetris.logic.SettingProperties.COLOR_BLIND_MODE;
import static com.tetris.logic.SettingProperties.DEFAULT_COLOR_MODE;

public abstract class Block {
    protected int[][] shape;
    protected Color[] colors;
    private final BlockType blockType;
    private final SettingController settingController;
    private final RWSelection rwSelection;
    public boolean canMoveSide;

    // 아이템 블럭이 생성될 수 있는 지워진 줄의 개수
    final private int erasedLineCountToCreateItemBlock = 2;

    // stopCount: 조작이 없으면 1씩 증가,
    private int stopCount = 0;
    // limitCount: 블록이 바닥에 닿은 순간 1씩 증가, 2 초과시 블록을 고정.
    // 블록이 moveDown => 0으로 초기화
    private int limitCount = 0;

    // 블록의 초기 좌표
    private int x, y;

    public Block(int[][] shape, Color[] colors, BlockType blockType) {
        settingController = new SettingController();
        rwSelection = new RWSelection(settingController.getDifficulty());
        this.x = 6;
        this.y = 2;
        this.shape = shape;
        this.colors = colors;
        this.blockType = blockType;
        this.canMoveSide = true;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void initializeXY() { this.x = 6; this.y = 2; }
    public int moveRight() { return this.x++; }
    public int moveLeft() { return this.x--; }
    public int moveDown() { return this.y++; }
    public int moveUp() { return this.y--; }
    public int moveDownNSpaces(int n) { return this.y += n; }
    public int moveRightNSpaces(int n) { return this.x += n; }
    public int moveLeftNSpaces(int n) { return this.x -= n; }
    
    public int getShape(int x, int y) {
        return shape[y][x];
    }

    public int[][] getShape() {
        return shape;
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

    public Color[] getColors() {
        return colors;
    }

    public BlockType getType() {
        return blockType; // 블록 타입 반환
    }

    public int[][] shapeCopy() {
        int[][] copy = new int[shape.length][shape[0].length];
        for (int i = 0; i < shape.length; i++) {
            System.arraycopy(shape[i], 0, copy[i], 0, shape[i].length);
        }
        return copy;
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

    public void initializeLimitCount() { this.limitCount = 0; }

    public void initializeStopCount() { this.stopCount = 0; }

    public void limitCountToTwo() { this.limitCount = 2; }

    public void stopCountToTwo() { this.stopCount = 2; }

    public void increaseLimitCount() { this.limitCount++; }

    public void increaseStopCount() { this.stopCount++; }


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
            case BombItemBlock -> new BombItemBlock();
            case ExtensionItemBlock -> new ExtensionItemBlock();
            case WaterItemBlock -> WaterItemBlock.create();
            case LineEraseItemBlock -> LineEraseItemBlock.create();
        };
    }


    // 지워진 라인을 체크하고 아이템 블럭을 생성해도 되는지 체크
    private boolean checkErasedLineCount(BoardController boardController, int erasedLineCount) {
        int lastCreated = boardController.getErasedLineCountLately();
        int newLinesSinceLastItem = erasedLineCount - lastCreated;

        if (newLinesSinceLastItem >= erasedLineCountToCreateItemBlock) {
            // 라인이 한 번에 11줄이 지워졌다고 하면, 10줄이 저장됌
            boardController.updateErasedLineCountLately(erasedLineCountToCreateItemBlock);
            return true;
        }
        return false;
    }

    //select for the block
    public Block selectBlock(BoardController boardController, boolean isItem, int erasedLineCount) {
        // `erasedLineCountToCreateItemBlock`개의 줄이 삭제될 때마다 아이템 블록 생성
        if (isItem && checkErasedLineCount(boardController, erasedLineCount)) {
            return selectItemBlock();
        } else {
            // 일반 블록 선택 로직 (10줄이 삭제되지 않았을 때)
            int blockType = rwSelection.select();
            // 기본 블록 선택 로직
            return switch (blockType) {
                case 0 -> new IBlock();
                case 1 -> new JBlock();
                case 2 -> new LBlock();
                case 3 -> new OBlock();
                case 4 -> new SBlock();
                case 5 -> new TBlock();
                case 6 -> new ZBlock();
                default -> throw new IllegalArgumentException("Invalid block type.");
            };
        }
    }

    public Block selectItemBlock() {
        RWSelection rwSelection = new RWSelection(3);
        return switch (rwSelection.select()) {
//            case 0 -> WaterItemBlock.create();
//            case 1 -> LineEraseItemBlock.create();
//            case 2 -> new BombItemBlock();
//            case 3 -> new WeightItemBlock();
            default -> new BombItemBlock();
        };
    }

    public int getStopCount() { return this.stopCount; }

    public int getLimitCount() { return this.limitCount; }
}
