package com.tetris.logic;

import com.tetris.model.*;

import static com.tetris.logic.ItemBlockController.BOMB_BODY;
import static com.tetris.logic.ItemBlockController.BOMB_EVENT;

public class BoardController {
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

    // 게임 보드의 너비, 높이
    final private int WIDTH;
    final private int HEIGHT;
    private int erasedLineCount = 0;
    private final boolean isItemMode;

    private boolean canPlaceBlock;

    private long lastLineEraseTime;

    private int blockCountWithNoLineErase;

    private int placedBlockCount;

    private boolean needNewBlock = false;

    public BoardController(GameController gameController, InGameScoreController inGameScoreController, Boolean isItemMode) {
        this.isItemMode = isItemMode;
        this.grid = new Board();
        this.itemBlockController = new ItemBlockController(grid, grid.getWidth(), grid.getHeight());
        this.gameController = gameController;
        this.inGameScoreController = inGameScoreController;
        this.WIDTH = grid.getWidth();
        this.HEIGHT = grid.getHeight();
        this.canPlaceBlock = true;
        this.blockCountWithNoLineErase = 0;
        this.lastLineEraseTime = 0;
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
        currentBlock = nextBlock;
        currentBlock.canMoveSide = true;
        this.nextBlock = nextBlock.selectBlock(isItemMode, erasedLineCount);
        if (grid.collisionCheck(currentBlock, 6, 2)) {
            checkSpeedUp();
            currentBlock.initializeXY();
        } else {
            canPlaceBlock = false;
            currentBlock = new NullBlock();
        }
        setNewBlockState(false);
        // addScoreMessage(Block.getErasedLineCountForItem() + "");
    }


    // 블록을 게임 보드에 배치
    public void placeBlock() {
        // 기본 블록 배치 로직
        for (int j = 0; j < currentBlock.height(); j++) {
            for (int i = 0; i < currentBlock.width(); i++) {
                int x = currentBlock.getX();
                int y = currentBlock.getY();
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
            itemBlockController.handleItemBlock(currentBlock, currentBlock.getX(), currentBlock.getY());
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
                int x = currentBlock.getX();
                int y = currentBlock.getY();
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
                if (currentBlock.canMoveSide && grid.collisionCheck(currentBlock, currentBlock.getX() - 1, currentBlock.getY())) {
                    currentBlock.moveLeft();
                    currentBlock.initializeStopCount();
                }
                placeBlock();
            }
            case RIGHT -> {
                if (currentBlock.canMoveSide && grid.collisionCheck(currentBlock, currentBlock.getX() + 1, currentBlock.getY())) {
                    currentBlock.moveRight();
                    currentBlock.initializeStopCount();
                }
                placeBlock();
            }
            case DOWN -> {
                if (grid.collisionCheck(currentBlock, currentBlock.getX(), currentBlock.getY() + 1)) {
                    placeDown();
                    inGameScoreController.addScoreOnBlockMoveDown(); // 한 칸 내릴 때마다 1점 추가
                } else { // 충돌!
                    // 무게추 블럭인 경우
                    if (currentBlock instanceof WeightItemBlock) {
                        currentBlock.canMoveSide = false;
                        itemBlockController.handleItemBlock(currentBlock, currentBlock.getX(), currentBlock.getY());
                    } else {
                        // 무게추 블럭이 아닌 경우
                        currentBlock.increaseStopCount();
                        currentBlock.increaseLimitCount();
                    }

                    placeBlock();

                    // 경계선에 닿은 경우 혹은 2틱 동안 움직임 없거나 충돌 후 2틱이 지나면 블록을 고정시킴
                    if (currentBlock.getStopCount() > 1 || currentBlock.getLimitCount() > 1) {
                        checkGameOver();
                        setNewBlockState(true);
                        lineCheck();
                        currentBlock.initializeStopCount();
                        currentBlock.initializeLimitCount();
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

                while (grid.collisionCheck(currentBlock, currentBlock.getX(), currentBlock.getY() + 1)) {
                    currentBlock.moveDown();
                    inGameScoreController.addScoreOnBlockMoveDown(); // 한 칸 내릴 때마다 1점 추가
                }
                placeBlock();

                // 무게 추 블럭인 경우 새로운 다음 블럭을 불러오면 안됌
                if (currentBlock instanceof WeightItemBlock) {
                    break;
                }

                setNewBlockState(true);
                lineCheck();

                currentBlock.initializeLimitCount();
            }
            default -> placeBlock();
        }
    }


    // 블록을 회전시킴. 충돌 시 회전하지 않음
    public void rotateBlock() {
        if (currentBlock instanceof WeightItemBlock || currentBlock instanceof BombItemBlock || currentBlock instanceof ExtensionItemBlock) {
            return;
        }
        currentBlock.rotate();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.collisionCheck(currentBlock, currentBlock.getX() + i, currentBlock.getY() + j)) {
                    inGameScoreController.addScoreOnBlockMoveDown(j);
                    currentBlock.moveRightNSpaces(i);
                    currentBlock.moveDownNSpaces(j);
                    return;
                }
                if (grid.collisionCheck(currentBlock, currentBlock.getX() - i, currentBlock.getY() + j)) {
                    inGameScoreController.addScoreOnBlockMoveDown(j);
                    currentBlock.moveLeftNSpaces(i);
                    currentBlock.moveDownNSpaces(j);
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
        currentBlock.initializeLimitCount();
        currentBlock.initializeStopCount();
        currentBlock.moveDown();
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

    public Block getCurrentBlock() { return currentBlock; }

    public int getPlacedBlockCount() { return placedBlockCount; }

}