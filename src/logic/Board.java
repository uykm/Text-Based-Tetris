package logic;

public class Board {
    private final int width = 10;
    private final int height = 20;
    private final int extendedWidth = 16;
    private final int extendedHeight = 26;

    final private int[][] board;
    public Board() {
        board = new int[extendedHeight][extendedWidth];
        initBoard();
    }

    private void initBoard() {
        for(int i=0; i<extendedHeight; i++) {
            for(int j=0; j<extendedWidth; j++) {
                board[i][j] = -1;
            }
        }
        for(int i=2; i<height+4; i++) {
            for(int j=2; j<width+4; j++) {
                if(i == 2 || i == height+3 || j == 2 || j == width+3) {
                    board[i][j] = 10;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }


    // 게임 보드 및 블록 상태 업데이트 메소드들을 여기에 추가
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
