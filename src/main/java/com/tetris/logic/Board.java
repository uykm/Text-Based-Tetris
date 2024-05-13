package com.tetris.logic;

public class Board {
    // 게임 보드 높이, 너비
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    // 게임 보드 확장된(=> 오버플로우 방지) 높이, 너비
    private final int extendedWidth = 16;
    private final int extendedHeight = 26;

    // 게임 보드를 int[][]로 표현, 0은 빈 공간, 10은 벽, -1은 외부, 1~7은 블록
    final private int[][] board;
    public Board() {
        board = new int[extendedHeight][extendedWidth];
        initBoard();
    }

    // 게임 보드 초기화
    private void initBoard() {
        for(int i=0; i<extendedHeight; i++) {
            for(int j=0; j<extendedWidth; j++) {
                board[i][j] = -1;
            }
        }
        for(int i=2; i<HEIGHT+4; i++) {
            for(int j=2; j<WIDTH+4; j++) {
                if(i == 2 || i == HEIGHT+3 || j == 2 || j == WIDTH+3) {
                    board[i][j] = 20;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    // 게임 보드 초기화
    public void clearBoard() {
        for(int i=2; i<HEIGHT+4; i++) {
            for(int j=2; j<WIDTH+4; j++) {
                board[i][j] = 0;
            }
        }
    }

    // 게임 보드 배열을 반환
    public int[][] getBoard() { return board; }

    // 게임 보드의 너비, 높이 반환
    public int getWidth() { return WIDTH; }
    public int getHeight() {
        return HEIGHT;
    }

    public void eraseOneBlock(int x, int y) { board[y][x] = 0; }

    public void placeOneBlock(int x, int y, int blockType) { board[y][x] = blockType; }

    // 충돌 검사, 충돌하지 않으면 true 반환
    public boolean collisionCheck(Block block, int newX, int newY) {
        for (int i = 0; i < block.height(); i++) {
            for (int j = 0; j < block.width(); j++) {
                if (block.getShape(j, i) != 0) { // Check if part of the block
                    int boardX = newX + j;
                    int boardY = newY + i;
                    if (boardX < 3 || boardX >= WIDTH + 3 || boardY < 3 || boardY >= HEIGHT + 3) {
                        return false; // Out of bounds
                    }
                    if (board[boardY][boardX] != 0 && board[boardY][boardX] != 13 && board[boardY][boardX] != -2) {
                        // 폭탄 이벤트와 줄 삭제 이벤트도 충돌로 취급 X
                        return false; // Position already occupied
                    }
                }
            }
        }
        return true; // No collision detected
    }
}
