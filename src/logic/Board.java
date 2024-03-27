package logic;

public class Board {
    // 게임 보드 높이, 너비
    private final int width = 10;
    private final int height = 20;
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

    // 게임 보드 초기화
    public void clearBoard() {
        for(int i=2; i<height+4; i++) {
            for(int j=2; j<width+4; j++) {
                board[i][j] = 0;
            }
        }
    }

    // 게임 보드 배열을 반환
    public int[][] getBoard() {
        return board;
    }


    // 게임 보드의 너비, 높이 반환
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
