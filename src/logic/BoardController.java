package logic;

import model.Direction;
import model.NullBlock;
import model.WeightItemBlock;
import ui.InGameScreen;

import java.util.LinkedList;
import java.util.Queue;

public class BoardController {
    // 게임 보드
    final private Board grid;
    // 게임 보드의 너비, 높이
    final private int WIDTH;
    final private int HEIGHT;
    private int erasedLineCount = 0;
    public static int erasedLintCountForItem = 0;
    private boolean isItemMode;
    // 현재 블록, 다음 블록
    private Block currentBlock;
    private Block nextBlock = new NullBlock();
    // stopCount: 조작이 없으면 1씩 증가,
    private int stopCount = 0;
    // limitCount: 블록이 바닥에 닿은 순간 1씩 증가, 2 초과시 블록을 고정.
    // 블록이 moveDown => 0으로 초기화
    private int limitCount = 0;

    private boolean canPlaceBlock;

    private int score;

    private long lastLineEraseTime;

    private int blockCountWithNoLineErase;

    private int placedBlockCount;
    private boolean canMoveSide;

    final int BLOCK_COUNT_TO_SPEED_UP = 10;
    final int LINE_COUNT_TO_SPEED_UP = 10;
    final int BLOCK_SPEED_UP_THRESHOLD = 10;
    final int LINE_SPEED_UP_THRESHOLD = 20;

    // 블록의 초기 좌표
    int x, y;

    private void checkSpeedUp() {

        if (placedBlockCount >= BLOCK_COUNT_TO_SPEED_UP) {
            placedBlockCount = 0;

            gameController.speedUp(BLOCK_SPEED_UP_THRESHOLD);
        }
        if (erasedLineCount % LINE_COUNT_TO_SPEED_UP == 0 && erasedLineCount != 0) {
            gameController.speedUp(LINE_SPEED_UP_THRESHOLD);
        }
    }

    private final Queue<String> Messages;


    GameController gameController;
    InGameScreen inGameScreen;


    public String getScoreMessages() {
        StringBuilder sb = new StringBuilder();
        for (String message : Messages) {
            sb.append(message).append("\n");
        }
        return sb.toString();
    }


    public BoardController(GameController gameController, Boolean isItemMode) {
        this.isItemMode = isItemMode;
        this.grid = new Board();
        this.WIDTH = grid.getWidth();
        this.HEIGHT = grid.getHeight();
        this.score = 0;
        this.canPlaceBlock = true;
        this.blockCountWithNoLineErase = 0;
        this.Messages = new LinkedList<>();
        this.gameController = gameController;
        this.lastLineEraseTime = 0;
        this.canMoveSide = true;
        this.nextBlock = nextBlock.selectBlock(isItemMode, erasedLineCount);
        // 초기 블록 배치
        placeNewBlock();
    }

    // 점수 추가
    private void addScore(int score) {
        this.score += score;
    }

    // 블록 이동 시 점수 추가
    private void addScoreOnBlockMoveDown() {
        addScore(1);
    }

    private void addScoreOnBlockMoveDown(int changedY) {
        addScore(changedY);
    }


    // 라인 삭제 시 점수 추가, 한번에 여러 라인 삭제 시 추가 점수, 3초 안에 라인 삭제 시 추가 점수
    private void addScoreOnLineEraseWithBonus(int lineCount) {
        long currLineEraseTime = System.currentTimeMillis();
        long diff = currLineEraseTime - lastLineEraseTime;
        lastLineEraseTime = currLineEraseTime;
        int multiplier = 1;
        if (diff < 3000) {
            multiplier = 2;
        }
        int addedScore = (13 * lineCount - 3) * multiplier;
        addScore(addedScore);
        if (multiplier == 2) {
            if (lineCount == 1) {
                addScoreMessage("+" + addedScore + ": Line erased (x2)");
            } else {
                addScoreMessage("+" + addedScore + ": Lines erased (x2)");
            }
        } else {
            if (lineCount == 1) {
                addScoreMessage("+" + addedScore + ": Line erased");
            } else {
                addScoreMessage("+" + addedScore + ": Lines erased");
            }
        }
    }

    private void subScoreOnLineNotEraseIn10Blocks() {
        if (blockCountWithNoLineErase > 10) {
            addScore(-5);
            blockCountWithNoLineErase = 0;
            addScoreMessage("-5: No line erased in 10 blocks");
        }
    }

    public void addScoreMessage(String message) {
        if (Messages.size() > 7) {
            Messages.remove();
        }
        Messages.add(message);
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
        this.currentBlock = nextBlock;
        canMoveSide = true;
        this.nextBlock = nextBlock.selectBlock(isItemMode, erasedLineCount);
        if (collisionCheck(6, 2)) {
            checkSpeedUp();
            x = 6;
            y = 2;
        } else {
            canPlaceBlock = false;
            this.currentBlock = new NullBlock();
        }
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


    // 충돌 검사, 충돌하지 않으면 true 반환
    public boolean collisionCheck(int newX, int newY) {
        for (int i = 0; i < currentBlock.height(); i++) {
            for (int j = 0; j < currentBlock.width(); j++) {
                if (currentBlock.getShape(j, i) != 0) { // Check if part of the block
                    int boardX = newX + j;
                    int boardY = newY + i;
                    if (boardX < 3 || boardX >= WIDTH + 3 || boardY < 3 || boardY >= HEIGHT + 3) {
                        return false; // Out of bounds
                    }
                    if (grid.getBoard()[boardY][boardX] != 0) {
                        // F((WeightItemBlock)currentBlock).isActive = false;
                        return false; // Position already occupied
                    }
                }
            }
        }
        return true; // No collision detected
    }


    // 라인이 꽉 찼는지 확인하고 꽉 찼으면 지우기
    public void lineCheck() {
        int lineCount = 0;
        for (int i = 3; i < HEIGHT + 3; i++) {
            boolean canErase = true;
            for (int j = 3; j < WIDTH + 3; j++) {
                if (grid.getBoard()[i][j] == 0) {
                    canErase = false;
                }
                if (grid.getBoard()[i][j] == 8) {
                    // 줄 삭제 아이템이 있는 경우
                    canErase = true;
                    addScoreMessage("Event: Line Erased");
                    break;
                }
            }
            if (canErase) {
                blinkLine(i);
                lineCount++;
                erasedLineCount++;
            }
        }
        if (lineCount > 0) {
            addScoreOnLineEraseWithBonus(lineCount);
        } else {
            subScoreOnLineNotEraseIn10Blocks();
        }
    }

    private void blinkLine(int line) {
        for (int i = 3; i < WIDTH + 3; i++) {
            grid.getBoard()[line][i] = -2;
        }
    }

    public void blinkCheck() {

        for (int i = 3; i < HEIGHT + 3; i++) {
            if (grid.getBoard()[i][3] == -2) {
                eraseLine(i);
            }
        }
    }

    // 라인을 지우고 위에 있는 블록들을 내림
    public void eraseLine(int line) {

        for (int i = line; i > 3; i--) {
            System.arraycopy(grid.getBoard()[i - 1], 3, grid.getBoard()[i], 3, WIDTH);
        }
    }

    // 무게 추로 아래 블럭 지우기
    private void breakBlocks(int x, int y, int length) {

        if (x <= 2 || x >= WIDTH + 3 || y <= 2 || y >= HEIGHT + 2) {
            placeBlock();
            lineCheck();
            placeNewBlock();
            stopCount = 0;
            limitCount = 0;
            return;
        }

        for (int i = x; i < x + length; i++) {
            grid.getBoard()[y + 1][i] = 0;
        }

        for (int i = y; i > 3; i--) {
            System.arraycopy(grid.getBoard()[i - 1], x, grid.getBoard()[i], x, length);
        }

        this.y += 1;
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
        eraseCurrentBlock();
        if (!canPlaceBlock) {
            return;
        }
        switch (direction) {
            case LEFT -> {
                if (canMoveSide && collisionCheck(x - 1, y)) {
                    x--;
                    stopCount = 0;
                }
                placeBlock();
            }
            case RIGHT -> {
                if (canMoveSide && collisionCheck(x + 1, y)) {
                    x++;
                    stopCount = 0;
                }
                placeBlock();
            }
            case DOWN -> {
                if (collisionCheck(x, y + 1)) {
                    placeDown();
                    addScoreOnBlockMoveDown(); // 한 칸 내릴 때마다 1점 추가
                } else { // 충돌!
                    if (currentBlock instanceof WeightItemBlock) {
                        canMoveSide = false;
                        breakBlocks(x, y + currentBlock.height() - 1, currentBlock.width());
                    } else {
                        stopCount++;
                        limitCount++;
                    }
                    placeBlock();
                    // 2틱 동안 움직임 없거나 충돌 후 2틱이 지나면 블록을 고정시킴
                    if (stopCount > 1 || limitCount > 1) {
                        checkGameOver();
                        lineCheck();
                        placeNewBlock();
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
                while (collisionCheck(x, y + 1)) {
                    y++;
                    addScoreOnBlockMoveDown(); // 한 칸 내릴 때마다 1점 추가
                }
                placeBlock();

                if (currentBlock instanceof WeightItemBlock) {
                    break;
                }

                lineCheck();
                placeNewBlock();
                limitCount = 0;
            }
            default -> placeBlock();
        }
    }

    // 블록을 회전시킴. 충돌 시 회전하지 않음
    public void rotateBlock() {
        currentBlock.rotate();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (collisionCheck(x + i, y + j)) {
                    addScoreOnBlockMoveDown(j);
                    x += i;
                    y += j;
                    return;
                }
                if (collisionCheck(x - i, y + j)) {
                    addScoreOnBlockMoveDown(j);
                    x -= i;
                    y += j;
                    return;
                }
            }
        }
        currentBlock.rotateBack();
    }

    // Debug: 게임 보드 출력
    public void printBoard() {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(grid.getBoard()[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Game Over Check
    // TODO: 3/24/24 : 게임 오버 조건 수정 확인 필요, ScoreController에게 점수 전달 로직 추가 필요
    public boolean checkGameOver() {
        for (int i = 3; i < WIDTH + 3; i++) {
            if ((grid.getBoard()[2][i] > 10) || !canPlaceBlock) {
                return true;
            }
        }
        return false;
    }

    // TODO: 3/24/24 : 점수 계산 로직 추가 필요

    public int getScore() {
        return score;
    }

    private void placeDown() {
        stopCount = 0;
        limitCount = 0;
        y++;
        placeBlock();
    }
}
