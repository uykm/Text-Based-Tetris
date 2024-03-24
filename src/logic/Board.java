package logic;

public class Board {
    private final int width = 20;
    private final int height = 22;
    private char[][] board;

    public Board() {
        board = new char[height][width];
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    board[i][j] = 'X'; // 테두리 생성
                } else {
                    board[i][j] = ' '; // 빈 공간
                }
            }
        }
    }

    public char[][] getBoard() {
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
