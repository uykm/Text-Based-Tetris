package com.tetris.logic;

import com.tetris.model.*;

import java.util.ArrayList;

import static com.tetris.logic.ItemBlockController.BOMB_BODY;
import static com.tetris.logic.ItemBlockController.BOMB_EVENT;

public class BoardController {
    final private int BLOCK_COUNT_TO_SPEED_UP = 15;
    final private int LINE_COUNT_TO_SPEED_UP = 8;
    final private int BLOCK_SPEED_UP_THRESHOLD = 20;
    final private int LINE_SPEED_UP_THRESHOLD = 40;
    final private int MAX_ADDED_LINES = 10;

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

    // 지워진 라인 개수
    private int erasedLineCount;
    // 가장 최근에 아이템 블럭이 생성되게 한 지워진 라인 개수
    private int erasedLineCountLately;

    private final boolean isItemMode;

    private final boolean isDualmode;

    private boolean canPlaceBlock;

    private long lastLineEraseTime;

    private int blockCountWithNoLineErase;

    private int placedBlockCount;

    // 보드의 높이보다 높은 라인이 지워진 경우 추가되어야 할 라인을 저장
    private final ArrayList<int[]> extraLines;

    public BoardController(GameController gameController, InGameScoreController inGameScoreController, Boolean isItemMode, Boolean isDualMode) {
        this.isDualmode = isDualMode;
        this.isItemMode = isItemMode;
        this.grid = new Board();
        this.itemBlockController = new ItemBlockController(grid, grid.getWidth(), grid.getHeight());
        this.gameController = gameController;
        this.inGameScoreController = inGameScoreController;
        this.WIDTH = grid.getWidth();
        this.HEIGHT = grid.getHeight();
        this.erasedLineCountLately = 0;
        this.canPlaceBlock = true;
        this.blockCountWithNoLineErase = 0;
        this.lastLineEraseTime = 0;
        this.erasedLineCount = 0;
        this.nextBlock = nextBlock.selectBlock(this, isItemMode, erasedLineCount);
        this.extraLines = new ArrayList<>();
        initializeBoardState();
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
        if(!erasedLines.isEmpty()){
            if(isDualmode){
                if(erasedLines.size() > 1){
                    sendLines(copyErasedLine());
                }
                erasedLines.clear();
            }
        }
        if(shouldAddLines != null){
            addLines();
        }
        copyBoardState(grid.getBoard(), previousBoardState);
        placedBlockCount++;
        blockCountWithNoLineErase++;
        currentBlock = nextBlock;
        currentBlock.canMoveSide = true;
        this.nextBlock = nextBlock.selectBlock(this, isItemMode, erasedLineCount);

        if (grid.collisionCheck(currentBlock, 6, 2)) {
            checkSpeedUp();
            currentBlock.initializeXY();
        } else {
            canPlaceBlock = false;
            currentBlock = new NullBlock();
        }

        gameController.resetTimer();
    }

    // 블록을 게임 보드에 배치
    public void placeBlock() {
        // 기본 블록 배치 로직
        for (int j = 0; j < currentBlock.height(); j++) {
            for (int i = 0; i < currentBlock.width(); i++) {
                int x = currentBlock.getX();
                int y = currentBlock.getY();
                if ((grid.getBoard()[y + j][x + i] == -2 || grid.getBoard()[y + j][x + i] == 13) && currentBlock.getShape(i, j) > 0) {
                    // 폭탄 이벤트 값이거나 줄 삭제 이벤트 값일 땐 블록 값을 그대로 저장
                    grid.getBoard()[y + j][x + i] = currentBlock.getShape(i, j);
                } else if (grid.getBoard()[y + j][x + i] == 0){ // 비어있는 공간만 현재 블럭으로 채운다.
                    grid.getBoard()[y + j][x + i] = currentBlock.getShape(i, j);
                }
            }
        }
    }

    // 라인이 꽉 찼는지 확인하고 꽉 찼으면 지우기
    public void lineCheck() {
        int lineCount = 0;
        for (int i = 3; i < HEIGHT + 3; i++) {
            boolean canErase = true;
            for (int j = 3; j < WIDTH + 3; j++) {
                // 블럭이 배치되어 있지 않거나 애니메이션 중인 블럭인 경우
                if (grid.getBoard()[i][j] == 0 || grid.getBoard()[i][j] == -2 ||
                        grid.getBoard()[i][j] == BOMB_BODY || grid.getBoard()[i][j] == BOMB_EVENT) {
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
                // 듀얼 모드에서 지워진 라인을 기록
                if (isDualmode) {
                    erasedLines.add(i);
                }
            }
        }
        updateScoreByErasedLineCnt(lineCount);
    }

    private void restoreExtraLines(){
        if(!extraLines.isEmpty()){
            int[][] board;
            board = returnBoardStateExcludingCurrentBlock();
            int line = getHighestLine(board);
            if(line > 3){
                for(int i = line - 1; i >= 3; i --){
                    System.arraycopy(extraLines.get(extraLines.size()-1), 0, grid.getBoard()[i], 0, 16);
                    extraLines.remove(extraLines.size()-1);
                    if(extraLines.isEmpty()){
                        break;
                    }
                }
            }
        }
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
        eraseCurrentBlock();
        if (!canPlaceBlock) { return; }
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
                        itemBlockController.handleItemBlock(currentBlock, currentBlock.getX(), currentBlock.getY());
                    } else {
                        // 무게추 블럭이 아닌 경우
                        gameController.delayTimer();
                        currentBlock.increaseStopCount();
                        currentBlock.increaseLimitCount();
                    }

                    placeBlock();

                    // 경계선에 닿은 경우 혹은 2틱 동안 움직임 없거나 충돌 후 2틱이 지나면 블록을 고정시킴
                    if (currentBlock.getStopCount() > 1 || currentBlock.getLimitCount() > 1) {
                        checkGameOver();
                        lineCheck();
                        itemBlockController.handleItemBlock(currentBlock, currentBlock.getX(), currentBlock.getY());
                        placeNewBlock();
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
                blinkErase(); // 스페이스바 누르면 줄 삭제 이벤트 바로 삭제
                // space바 누르면 바로 터지게
                if (currentBlock instanceof BombItemBlock) {
                    placeBlock();
                    itemBlockController.handleItemBlock(currentBlock, currentBlock.getX(), currentBlock.getY());
                    gameController.triggerBlinkCheckAgain();
                    placeNewBlock();
                    break;
                }
                gameController.gameTimer.stop();
                while (grid.collisionCheck(currentBlock, currentBlock.getX(), currentBlock.getY() + 1)) {
                    currentBlock.moveDown();
                    inGameScoreController.addScoreOnBlockMoveDown(); // 한 칸 내릴 때마다 1점 추가
                }
                gameController.gameTimer.start();
                placeBlock();

                // 무게 추 블럭인 경우 새로운 다음 블럭을 불러오면 안됌
                if (currentBlock instanceof WeightItemBlock) {
                    break;
                }

                // 줄 삭제 이벤트가 중첩되는 경우 넘어가야 할 줄이 정상적으로 저장되도록 함
                copyBoardStateExcludingCurrentBlock();

                itemBlockController.handleItemBlock(currentBlock, currentBlock.getX(), currentBlock.getY());
                lineCheck();
                gameController.triggerBlinkCheckAgain();
                placeNewBlock();

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

    public String checkWinner() {
        return gameController.getStrPlayer();
    }

    private void placeDown() {
        currentBlock.initializeLimitCount();
        currentBlock.initializeStopCount();
        currentBlock.moveDown();
        placeBlock();
    }

    // ToDo: 줄 삭제 이벤트 로직
    private void blinkLine(int line) {
        erasedLineCount++;
        for (int i = 3; i < WIDTH + 3; i++) {
            grid.getBoard()[line][i] = -2;
        }
    }

    public void blinkErase() {
        for (int i = 3; i < HEIGHT + 3; i++) {
            // 줄삭제 이벤트 제거
            for (int j = 3; j < WIDTH + 3; j++) {
                if (grid.getBoard()[i][j] == -2) {
                    grid.eraseOneBlock(j, i);
                    if (j == WIDTH + 2) {
                        dropLines(i);
                    }
                }
            }

            // 폭탄 이벤트 제거
            for (int j = 3; j < WIDTH + 3; j++) {
                if (grid.getBoard()[i][j] == 13) {
                    grid.eraseOneBlock(j, i);
                }
            }
        }
        restoreExtraLines();
    }

    // 위에 있는 블록들을 내림
    private void dropLines(int line) {
        // 현재 블록의 shape 배열을 얻는다.
        int[][] shape = currentBlock.getShape();
        int currentBlockY = currentBlock.getY();
        int currentBlockX = currentBlock.getX();

        // 현재 블록에 속한 셀들을 확인하기 위한 boolean 배열
        boolean[][] isCurrentBlockCell = new boolean[HEIGHT + 6][WIDTH + 6];

        // shape 배열을 기반으로 현재 블록의 셀 위치를 계산한다.
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int x = currentBlockX + j;
                    int y = currentBlockY + i;
                    if (x >= 3 && x < WIDTH + 3 && y >= 3 && y < HEIGHT + 3) {
                        isCurrentBlockCell[y][x] = true;
                    }
                }
            }
        }

        // 지워진 라인 위의 블록들을 내린다.(현재 블럭 제외)
        for (int i = line - 1; i >= 3; i--) {
            for (int j = 3; j < WIDTH + 3; j++) {
                // 현재 블록에 속한 셀은 내리지 않는다.
                if (!isCurrentBlockCell[i][j] && !isCurrentBlockCell[i+1][j] ) {
                    grid.getBoard()[i+1][j] = grid.getBoard()[i][j];
                }
            }
        }

        // 맨 윗줄은 따로 0으로 초기화해줘야 한다.
        for (int j = 3; j < WIDTH + 3; j++) {
            grid.getBoard()[3][j] = 0;
        }
    }

    public boolean blinkCheck() {
        boolean blink = false;

        for (int i = 3; i < HEIGHT + 3; i++) {
            if (grid.getBoard()[i][3] == -2) {
                blink = true;
            }

            for (int j = 3; j < WIDTH + 3; j++) {
                if (grid.getBoard()[i][j] == 13) {
                    blink = true;
                }
            }
        }

        return blink;
    }

    public int getErasedLineCountLately() { return erasedLineCountLately; }

    public void updateErasedLineCountLately(int erasedLineCountToCreateItemBlock) { erasedLineCountLately += erasedLineCountToCreateItemBlock; }


    // 대전 모드 관련

    public int[][] getShouldAddLines() {
        return shouldAddLines;
    }

    private int[][] shouldAddLines; // 내 보드에 추가 되어야 할 라인
    private int[][] previousBoardState; // 이전 게임 보드 상태(새로운 블록이 생성되기 전, 라인이 지워진 후 이전 상태 업데이트)
    final ArrayList<Integer> erasedLines = new ArrayList<>(); // 지워진 라인 (상대방에게 보내기 위해 저장)

    // 게임이 시작되면 이전 게임 보드 상태를 초기화
    public void initializeBoardState() {
        previousBoardState = new int[26][16]; // Assuming 4 is the buffer boundary as seen in your board setup
        copyBoardState(grid.getBoard(), previousBoardState);
    }

    // 게임 보드 상태 복사
    private void copyBoardState(int[][] source, int[][] destination) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }
    }

    public void copyBoardStateExcludingCurrentBlock() {
        previousBoardState = returnBoardStateExcludingCurrentBlock();
    }

    public int[][] returnBoardStateExcludingCurrentBlock() {
        int[][] source = new int[grid.getBoard().length][];
        for (int i = 0; i < grid.getBoard().length; i++) {
            source[i] = grid.getBoard()[i].clone();
        }
        int[][] destination = new int[26][16];

        // source 보드에서 현재 블록을 제거
        for (int i = 0; i < currentBlock.width(); i++) {
            for (int j = 0; j < currentBlock.height(); j++) {
                int x = currentBlock.getX();
                int y = currentBlock.getY();
                if (currentBlock.getShape(i, j) > 0) {
                    source[y + j][x + i] -= currentBlock.getShape(i, j);
                }
            }
        }

        // board 상태를 destination에 복사
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }

        return destination;
    }


    // 지워진 라인 복사 (이전 상태에서 받아와서 마지막에 쌓은 블록은 표시되지 않음)
    public int[][] copyErasedLine() {
        int[][] erasedLine = new int[erasedLines.size()][16];

        for (int i = 0; i < erasedLines.size(); i++)
            System.arraycopy(previousBoardState[erasedLines.get(i)], 0, erasedLine[i], 0, 16);
        erasedLines.clear();
        return erasedLine;
    }

    // 추가 될 수 있는 라인 판단
//    private int checkCanAddLines(int linesToAdd) {
//        int currentGrayLines = 0;
//        // Count current gray lines in the board
//        for (int i = HEIGHT + 3; i >= 3; i--) {
//            boolean isGrayLine = false;
//            for (int j = 3; j < WIDTH+3; j++) {
//                if (grid.getBoard()[i][j] == 40) {
//                    isGrayLine = true;
//                    break;
//                }
//            }
//            if (isGrayLine) {
//                currentGrayLines++;
//            }
//        }
//
//        if (currentGrayLines + linesToAdd > MAX_ADDED_LINES) {
//            return MAX_ADDED_LINES - currentGrayLines;
//        }
//        return linesToAdd;
//    }

    // 상대방에게 지워진 라인을 보냄
    public void sendLines(int[][] lines) {
        gameController.sendLines(lines);
    }

    // 상대방이 lines에 배열을 담아 sendLines를 호출하면 내 보드의 shouldAddLines에 저장
    // 기존 라인이 있을 경우 아래 부분에 새로운 lines 추가
    public void addLines(int[][] lines){
        if(shouldAddLines == null){
            shouldAddLines = lines;
        } else if (shouldAddLines.length == MAX_ADDED_LINES){
            return;
        }
        else if (shouldAddLines.length + lines.length >= MAX_ADDED_LINES) {
            int[][] temp = new int[MAX_ADDED_LINES][16];
            System.arraycopy(shouldAddLines, 0, temp, 0, shouldAddLines.length);
            System.arraycopy(lines, 0, temp, shouldAddLines.length, MAX_ADDED_LINES - shouldAddLines.length);
            shouldAddLines = temp;
        } else {
            int[][] temp = new int[shouldAddLines.length + lines.length][16];
            System.arraycopy(shouldAddLines, 0, temp, 0, shouldAddLines.length);
            System.arraycopy(lines, 0, temp, shouldAddLines.length, lines.length);
            shouldAddLines = temp;
        }
    }
    // 내 보드에 추가되어야 할 라인을 추가
    public void addLines() {
        int[][] board;
        board = returnBoardStateExcludingCurrentBlock();

        for (int i = 3; i < HEIGHT + 3 - shouldAddLines.length; i++) {
            System.arraycopy(grid.getBoard()[i + shouldAddLines.length], 3, grid.getBoard()[i], 3, WIDTH+1);
        }

        for (int i = 0; i < shouldAddLines.length; i++) {
            for (int j = 3; j < WIDTH + 3; j++) {
                if(shouldAddLines[i][j] > 0) {
                    grid.getBoard()[HEIGHT + 3 - shouldAddLines.length + i][j] = 40;
                } else {
                    grid.getBoard()[HEIGHT + 3 - shouldAddLines.length+ i][j] = 0;
                }
            }
        }

        int line = getHighestLine(board);

        if(line - shouldAddLines.length < 3){
            for(int i = 3; i < line + shouldAddLines.length; i++){
                int[] temp = new int[16];
                System.arraycopy(previousBoardState[i], 0, temp, 0, 16);
                extraLines.add(temp);
            }
        }

        shouldAddLines = new int[][]{};
    }

    public int getHighestLine(int[][] board){
        int line = 0;
        for (int i = 3; i < HEIGHT + 3; i++) {
            boolean isLineEmpty = true;
            for (int j = 3; j < WIDTH + 3; j++) {
                if (board[i][j] > 0) {
                    isLineEmpty = false;
                    break;
                }
            }
            if (!isLineEmpty) {
                line = i;
                break;
            }
        }
        return line;
    }
}


