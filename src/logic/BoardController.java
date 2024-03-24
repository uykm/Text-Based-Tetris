package logic;

import model.BlockType;
import model.Direction;

public class BoardController {
    final private Board grid;
    final private int WIDTH;
    final private int HEIGHT;
    private Block currentBlock;
    private Block nextBlock = Block.getBlock(BlockType.getRandomBlockType());

    // 블록의 초기 좌표
    int x, y;

    public BoardController() {
        this.grid = new Board(); // 게임판 크기 정의
        this.WIDTH = grid.getWidth();
        this.HEIGHT = grid.getHeight();
        printBoard();
        placeNewBlock();
        printBoard();
        moveBlock(Direction.DOWN);
        printBoard();
    }

    public void placeNewBlock() {
        this.currentBlock = this.nextBlock;
        this.nextBlock = Block.getBlock(BlockType.getRandomBlockType());
        x = 6; y = 2;
        placeBlock();
    }

    public void placeBlock() {
        for(int j=0; j<currentBlock.height(); j++) {
            for(int i=0; i<currentBlock.width(); i++) {
                grid.getBoard()[y+j][x+i] += currentBlock.getShape(i, j);
            }
        }
    }

    public boolean collisionCheck(int newX, int newY, Block newBlock) {
        for (int i = 0; i < newBlock.height(); i++) {
            for (int j = 0; j < newBlock.width(); j++) {
                if (newBlock.getShape(j, i) != 0) { // Check if part of the block
                    int boardX = newX + j;
                    int boardY = newY + i;
                    if (boardX < 3 || boardX >= WIDTH + 3 || boardY < 3 || boardY >= HEIGHT + 3) {
                        return false; // Out of bounds
                    }
                    if (grid.getBoard()[boardY][boardX] != 0) {
                        return false; // Position already occupied
                    }
                }
            }
        }
        return true; // No collision detected
    }

    public void lineCheck() {
        for(int i=3; i< HEIGHT+3; i++) {
            boolean canErase = true;
            for(int j=3; j< WIDTH+3; j++) {
                if(grid.getBoard()[i][j] == 0) {
                    canErase = false;
                    break;
                }
            }
            if(canErase) {
                eraseLine(i);
            }
        }
    }

    private void eraseCurrentBlock() {
        for(int i=0; i<currentBlock.width(); i++) {
            for(int j=0; j<currentBlock.height(); j++) {
                if (grid.getBoard()[y+j][x+i] - currentBlock.getShape(i, j) >= 0){
                    grid.getBoard()[y+j][x+i] -= currentBlock.getShape(i, j);
                }
            }
        }
    }

    private void eraseLine(int line) {
        for(int i=3; i< WIDTH+3; i++) {
            grid.getBoard()[line][i] = 0;
        }
        for(int i=line; i>3; i--) {
            System.arraycopy(grid.getBoard()[i - 1], 3, grid.getBoard()[i], 3, WIDTH);
        }
    }

    public void moveBlock(Direction direction) {
        eraseCurrentBlock();
        switch(direction) {
            case LEFT -> {
                if (collisionCheck(x - 1, y, currentBlock)) {
                        x--;
                }
                placeBlock();
            }
            case RIGHT -> {
                if (collisionCheck(x + 1, y, currentBlock)) {
                    x++;
                }
                placeBlock();
            }
            case DOWN -> {
                if (collisionCheck(x, y + 1, currentBlock)) {
                    y++;
                    placeBlock();
                } else {
                    placeBlock();
                    lineCheck();
                    placeNewBlock();
                    checkGameOver();
                }
            }
            case UP -> {
                eraseCurrentBlock();
                rotateBlock();
                placeBlock();
            }
            default -> placeBlock();
        }
    }

    public void rotateBlock() {
        currentBlock.rotate();
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(collisionCheck(x+i, y+j, currentBlock)){
                    x += i;
                    y += j;
                    return;
                }
                if(collisionCheck(x-i, y-j, currentBlock)){
                    x -= i;
                    y -= j;
                    return;
                }
            }
        }
        currentBlock.rotateBack();
    }

    public void printBoard() {
        for(int i=0; i<26; i++) {
            for(int j=0; j<16; j++) {
                System.out.print(grid.getBoard()[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void checkGameOver() {
        for(int i=3; i<WIDTH+3; i++) {
            if(grid.getBoard()[3][i] != 0) {
                //내부 구현 필요
                System.out.println("Game Over");
            }
        }
    }
}
