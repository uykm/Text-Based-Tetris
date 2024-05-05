package com.tetris.logic;

import com.tetris.model.*;

public class BoardController {
    public static final int BOMB_BODY = 11;
    public static final int BOMB_EVENT = 13;
    final private int BLOCK_COUNT_TO_SPEED_UP = 15;
    final private int LINE_COUNT_TO_SPEED_UP = 8;
    final private int BLOCK_SPEED_UP_THRESHOLD = 20;
    final private int LINE_SPEED_UP_THRESHOLD = 40;

    final private Board grid;
    final private InGameScoreController inGameScoreController;
    final private GameController gameController;
    final private ItemBlockController itemBlockController;

    // 현재 블록, 다음 블록
    private Block currentBlock;
    private Block nextBlock = new NullBlock();

    // 블록의 초기 좌표
    private int x, y;

    // 게임 보드의 너비, 높이
    final private int WIDTH;
    final private int HEIGHT;
    private int erasedLineCount = 0;
    private final boolean isItemMode;

    // stopCount: 조작이 없으면 1씩 증가,
    private int stopCount = 0;
    // limitCount: 블록이 바닥에 닿은 순간 1씩 증가, 2 초과시 블록을 고정.
    // 블록이 moveDown => 0으로 초기화
    private int limitCount = 0;

    private boolean canPlaceBlock;

    private long lastLineEraseTime;

    private int blockCountWithNoLineErase;

    private int placedBlockCount;
    private boolean canMoveSide;

    private boolean needNewBlock = false;

    public BoardController(GameController gameController, InGameScoreController inGameScoreController, Boolean isItemMode) {
        this.isItemMode = isItemMode;
        this.grid = new Board();
        this.itemBlockController = new ItemBlockController(grid, grid.getWidth(), grid.getHeight());
        this.inGameScoreController = inGameScoreController;
        this.WIDTH = grid.getWidth();
        this.HEIGHT = grid.getHeight();
        this.canPlaceBlock = true;
        this.blockCountWithNoLineErase = 0;
        this.gameController = gameController;
        this.lastLineEraseTime = 0;
        this.canMoveSide = true;
        this.nextBlock = nextBlock.selectBlock(isItemMode, erasedLineCount);
    }

    private void checkSpeedUp() {

        if (placedBlockCount >= BLOCK_COUNT_TO_SPEED_UP) {
            placedBlockCount = 0;

            gameController.speedUp(BLOCK_SPEED_UP_THRESHOLD);
        }
        if (erasedLineCount % LINE_COUNT_TO_SPEED_UP == 0 && erasedLineCount != 0) {
            gameController.speedUp(LINE_SPEED_UP_THRESHOLD);
        }
    }


    // InGameScreen 에서 다음 블록 띄우기 위해서 추가
    public Block getNextBlock() {
        return nextBlock;
    }

    // InGameScreen 에서 게임 보드 상태를 가져오기 위해서 추가
    public int[][] getBoard() {
        return grid.getBoard();
    }


    // nextBlock을 currentBlock으로 옮기고 새로운 nextBlock을 생성
    public void placeNewBlock() {
        placedBlockCount++;
        blockCountWithNoLineErase++;
        this.currentBlock = nextBlock;
        canMoveSide = true;
        this.nextBlock = nextBlock.selectBlock(isItemMode, erasedLineCount);
        if (grid.collisionCheck(currentBlock, 6, 2)) {
            checkSpeedUp();
            x = 6;
            y = 2;
        } else {
            canPlaceBlock = false;
            this.currentBlock = new NullBlock();
        }
        setNewBlockState(false);
        // addScoreMessage(Block.getErasedLineCountForItem() + "");
    }


    // 블록을 게임 보드에 배치
    public void placeBlock() {
        // 기본 블록 배치 로직
        for (int j = 0; j < currentBlock.height(); j++) {
            for (int i = 0; i < currentBlock.width(); i++) {
                grid.getBoard()[y + j][x + i] += currentBlock.getShape(i, j);
            }
        }
    }

    public boolean getNewBlockState() {
        return needNewBlock;
    }

    private void setNewBlockState(boolean state) {
        needNewBlock=state;
        if(state){
            itemBlockController.handleItemBlock(currentBlock, x, y);
            lineCheck();
        }
    }


    // 라인이 꽉 찼는지 확인하고 꽉 찼으면 지우기
    public void lineCheck() {
        int lineCount = 0;
        for (int i = 3; i < HEIGHT + 3; i++) {
            boolean canErase = true;
            for (int j = 3; j < WIDTH + 3; j++) {
                // 블럭이 배치되어 있지 않거나 애니메이션 중인 블럭인 경우
                if (grid.getBoard()[i][j] == 0 || grid.getBoard()[i][j] == -2
                        // 폭탄 블럭은 라인체크에 반영 X
                        || grid.getBoard()[i][j] == BOMB_BODY || grid.getBoard()[i][j] == BOMB_EVENT) {
                    canErase = false;
                }
                if (grid.getBoard()[i][j] == 8) {
                    // 줄 삭제 아이템이 있는 경우
                    canErase = true;
                    inGameScoreController.addScoreMessage("Event: Line Erased");
                    break;
                }
            }
            if (canErase) {
                blinkLine(i);
                lineCount++;
                erasedLineCount++;
            }
        }
        updateScoreByErasedLineCnt(lineCount);
    }

    private void updateScoreByErasedLineCnt(int lineCount) {
        if (lineCount > 0) {
            blockCountWithNoLineErase = 0;
            long currLineEraseTime = System.currentTimeMillis();
            long diff = currLineEraseTime - lastLineEraseTime;
            lastLineEraseTime = currLineEraseTime;
            inGameScoreController.addScoreOnLineEraseWithBonus(lineCount, diff);
        } else {
            // 10개의 블럭이 생성된 경우에도 지워진 라인이 하나도 없으면 50점 감점
            if (blockCountWithNoLineErase > 9) {
                blockCountWithNoLineErase = 0;
                inGameScoreController.subScoreOnLineNotEraseIn10Blocks();
            }
        }
    }

    // 라인을 지우고 위에 있는 블록들을 내림
    public void eraseLine(int line) {
        for(int i=3; i<WIDTH+3; i++) {
            grid.getBoard()[line][i] = 0;
        }
        for (int i = line; i > 3; i--) {
            System.arraycopy(grid.getBoard()[i - 1], 3, grid.getBoard()[i], 3, WIDTH);
        }
    }


    // 현재 배열에서 블록을 지움, 블록을 회전하거나 이동하기 전에 사용
    private void eraseCurrentBlock() {
        for (int i = 0; i < currentBlock.width(); i++) {
            for (int j = 0; j < currentBlock.height(); j++) {
                if (grid.getBoard()[y + j][x + i] - currentBlock.getShape(i, j) >= 0) {
                    grid.getBoard()[y + j][x + i] -= currentBlock.getShape(i, j);
                }
            }
        }
    }

    // 블록을 이동시킴
    public void moveBlock(Direction direction) {
        if (needNewBlock) {
            return;
        }
        eraseCurrentBlock();
        if (!canPlaceBlock) {
            return;
        }
        switch (direction) {
            case LEFT -> {
                if (canMoveSide && grid.collisionCheck(currentBlock, x - 1, y)) {
                    x--;
                    stopCount = 0;
                }
                placeBlock();
            }
            case RIGHT -> {
                if (canMoveSide && grid.collisionCheck(currentBlock, x + 1, y)) {
                    x++;
                    stopCount = 0;
                }
                placeBlock();
            }
            case DOWN -> {
                if (grid.collisionCheck(currentBlock, x, y + 1)) {
                    placeDown();
                    inGameScoreController.addScoreOnBlockMoveDown(); // 한 칸 내릴 때마다 1점 추가
                } else { // 충돌!
                    // 무게추 블럭인 경우
                    if (currentBlock instanceof WeightItemBlock) {
                        canMoveSide = false;
                        itemBlockController.handleItemBlock(currentBlock, x, y);
                    } else {
                        // 무게추 블럭이 아닌 경우
                        stopCount++;
                        limitCount++;
                    }

                    placeBlock();

                    // 경계선에 닿은 경우 혹은 2틱 동안 움직임 없거나 충돌 후 2틱이 지나면 블록을 고정시킴
                    if (stopCount > 1 || limitCount > 1) {
                        checkGameOver();
                        setNewBlockState(true);
                        lineCheck();
                        stopCount = 0;
                        limitCount = 0;
                    }
                }
            }
            case UP -> {
                eraseCurrentBlock();
                rotateBlock();
                placeBlock();
            }
            case SPACE -> {
                // space바 누르면 바로 터지게
                if (currentBlock instanceof BombItemBlock) {
                    placeBlock();
                    setNewBlockState(true);
                    break;
                }

                while (grid.collisionCheck(currentBlock, x, y + 1)) {
                    y++;
                    inGameScoreController.addScoreOnBlockMoveDown(); // 한 칸 내릴 때마다 1점 추가
                }
                placeBlock();

                // 무게 추 블럭인 경우 새로운 다음 블럭을 불러오면 안됌
                if (currentBlock instanceof WeightItemBlock) {
                    break;
                }

                setNewBlockState(true);
                lineCheck();

                limitCount = 0;
            }
            default -> placeBlock();
        }
    }

    private boolean isOutOfBoard() {
        return x <= 2 || x >= WIDTH + 3 || y <= 2 || y >= HEIGHT + 2;
    }

    // 블록을 회전시킴. 충돌 시 회전하지 않음
    public void rotateBlock() {
        if (currentBlock instanceof WeightItemBlock || currentBlock instanceof BombItemBlock || currentBlock instanceof ExtensionItemBlock) {
            return;
        }
        currentBlock.rotate();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.collisionCheck(currentBlock, x + i, y + j)) {
                    inGameScoreController.addScoreOnBlockMoveDown(j);
                    x += i;
                    y += j;
                    return;
                }
                if (grid.collisionCheck(currentBlock, x - i, y + j)) {
                    inGameScoreController.addScoreOnBlockMoveDown(j);
                    x -= i;
                    y += j;
                    return;
                }
            }
        }
        currentBlock.rotateBack();
    }


    // Game Over Check
    public boolean checkGameOver() {

        for (int i = 3; i < WIDTH + 3; i++) {
            if ((grid.getBoard()[2][i] > 20) || !canPlaceBlock) {

                return true;
            }
        }
        return false;
    }

    private void placeDown() {
        stopCount = 0;
        limitCount = 0;
        y++;
        placeBlock();
    }

    // ToDo: 줄 삭제 이벤트 로직
    private void blinkLine(int line) {
        for (int i = 3; i < WIDTH + 3; i++) {
            grid.getBoard()[line][i] = -2;
        }
    }

    public boolean blinkCheck() {
        boolean blink = false;

        for (int i = 3; i < HEIGHT + 3; i++) {
            if (grid.getBoard()[i][3] == -2) {
                eraseLine(i);
                blink = true;
            }

            // 폭탄 이벤트 제거
            for (int j = 3; j < WIDTH + 3; j++) {
                if (grid.getBoard()[i][j] == 13) {
                    grid.eraseOneBlock(j, i);
                }
            }
        }

        return blink;
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public int getStopCount() {
        return stopCount;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public boolean isCanPlaceBlock() {
        return canPlaceBlock;
    }

    public int getPlacedBlockCount() {
        return placedBlockCount;
    }

    public boolean isCanMoveSide() {
        return canMoveSide;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
