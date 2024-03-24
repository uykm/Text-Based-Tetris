package ui;


import logic.Board;

import javax.swing.*;
import java.awt.*;

public class InGameScreen extends JPanel {
    private final int BOARD_WIDTH = 14;
    private final int BOARD_HEIGHT = 24;
    private final int CELL_SIZE = 15;
    private final int NEXT_BLOCK_BOARD_WIDTH = 8; // 다음 블록 표시 영역의 가로 길이
    private final int NEXT_BLOCK_BOARD_HEIGHT = 7; // 다음 블록 표시 영역의 세로 길이
    private char[][] board; // 게임 보드 상태
    private char[][] nextBlockBoard; // 다음 블록 상태
    private int score; // 점수

    public InGameScreen() {
        // 임시 데이터로 초기화
        initBoard();
        initNextBlockBoard();
        this.score = 0; // 점수 초기화

        setPreferredSize(new Dimension((BOARD_WIDTH + NEXT_BLOCK_BOARD_WIDTH + 2) * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE));
        setBackground(Color.LIGHT_GRAY);
    }

    private void initBoard() {
        // 임시 게임 보드 생성
        board = new char[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (i == 0 || i == BOARD_HEIGHT - 1 || j == 0 || j == BOARD_WIDTH - 1) {
                    board[i][j] = 'G'; // 바깥쪽 테두리
                }
            }
        }
        for (int i = 1; i < BOARD_HEIGHT-1; i++) {
            for (int j = 1; j < BOARD_WIDTH-1; j++) {
                if (i == 1 || i == BOARD_HEIGHT - 2 || j == 1 || j == BOARD_WIDTH - 2) {
                    board[i][j] = 'X'; // 안쪽 테두리
                }
            }
        }
    }

    private void initNextBlockBoard() {
        // 다음 블록 영역 초기화
        nextBlockBoard = new char[NEXT_BLOCK_BOARD_HEIGHT][NEXT_BLOCK_BOARD_WIDTH];
        // 다음 블록 영역 테두리 그리기
        for (int i = 0; i < NEXT_BLOCK_BOARD_HEIGHT; i++) {
            for (int j = 0; j < NEXT_BLOCK_BOARD_WIDTH; j++) {
                if (i == 0 || i == NEXT_BLOCK_BOARD_HEIGHT-1
                        || j == 0 || j == NEXT_BLOCK_BOARD_WIDTH-1) {
                    nextBlockBoard[i][j] = 'G'; // 테두리
                }
            }
        }

        // 임시 블록
        nextBlockBoard[2][2] = 'O';
        nextBlockBoard[2][3] = 'O';
        nextBlockBoard[3][2] = 'O';
        nextBlockBoard[3][3] = 'O';
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 전체 게임 보드의 배경을 검정색으로 설정
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);

        // 다음 블록 영역의 배경도 검정색으로 설정
        int nextBlockBoardX = (BOARD_WIDTH + 2) * CELL_SIZE; // 다음 블록 영역의 시작 x 좌표
        int nextBlockBoardY = CELL_SIZE; // 다음 블록 영역의 시작 y 좌표
        int nextBlockInBoardWidth = (NEXT_BLOCK_BOARD_WIDTH - 2) * CELL_SIZE; // 다음 블록 영역의 너비
        int nextBlockInBoardHeight = (NEXT_BLOCK_BOARD_HEIGHT - 2) * CELL_SIZE; // 다음 블록 영역의 높이
        g.fillRect(nextBlockBoardX, nextBlockBoardY, nextBlockInBoardWidth, nextBlockInBoardHeight);

        // 게임 보드 그리기
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                drawCell(g, j * CELL_SIZE, i * CELL_SIZE, board[i][j]);
            }
        }
        // 어두운 회색 상단 선 그리기
        int borderWidth = CELL_SIZE / 3; // 테두리 선의 너비
        drawBorderLine(g, CELL_SIZE - borderWidth, CELL_SIZE - borderWidth, (BOARD_WIDTH - 1) * CELL_SIZE - borderWidth, borderWidth);
        // 하단 선 그리기
        drawBorderLine(g, CELL_SIZE - borderWidth, BOARD_HEIGHT * CELL_SIZE - CELL_SIZE, (BOARD_WIDTH - 1) * CELL_SIZE - borderWidth, borderWidth);
        // 좌측 선 그리기
        drawBorderLine(g, CELL_SIZE - borderWidth, CELL_SIZE, borderWidth, (BOARD_HEIGHT - 2) * CELL_SIZE);
        // 우측 선 그리기
        drawBorderLine(g, BOARD_WIDTH * CELL_SIZE - CELL_SIZE, CELL_SIZE, borderWidth, (BOARD_HEIGHT - 2) * CELL_SIZE);

        // 다음 블록 영역 그리기
        for (int i = 0; i < NEXT_BLOCK_BOARD_HEIGHT; i++) {
            for (int j = 0; j < NEXT_BLOCK_BOARD_WIDTH; j++) {
                if (nextBlockBoard[i][j] != ' ') {
                    drawCell(g, (BOARD_WIDTH + 1 + j) * CELL_SIZE, i * CELL_SIZE, nextBlockBoard[i][j]);
                }
            }
        }
        // 어두운 회색 상단 선 그리기
        drawBorderLine(g, nextBlockBoardX - borderWidth, CELL_SIZE - borderWidth, nextBlockInBoardWidth + borderWidth * 2, borderWidth);
        // 하단 선 그리기
        drawBorderLine(g, nextBlockBoardX - borderWidth, nextBlockBoardY + nextBlockInBoardHeight, nextBlockInBoardWidth + borderWidth * 2, borderWidth);
        // 좌측 선 그리기
        drawBorderLine(g, nextBlockBoardX - borderWidth, CELL_SIZE, borderWidth, nextBlockInBoardHeight);
        // 우측 선 그리기
        drawBorderLine(g, nextBlockBoardX + nextBlockInBoardWidth, CELL_SIZE, borderWidth, nextBlockInBoardHeight);


        // 점수판 상자 그리기
        int scoreboardX = BOARD_WIDTH * CELL_SIZE + CELL_SIZE; // 점수판 x 위치
        int scoreboardY = NEXT_BLOCK_BOARD_HEIGHT * CELL_SIZE + CELL_SIZE; // 점수판 y 위치
        int scoreboardWidth = NEXT_BLOCK_BOARD_WIDTH * CELL_SIZE; // 점수판 너비
        int scoreboardHeight = CELL_SIZE * 4; // 점수판 높이, 필요에 따라 조정
        g.setColor(Color.GRAY);
        g.fillRect(scoreboardX, scoreboardY, scoreboardWidth, scoreboardHeight); // 사각형 상자 그리기

        // "Scores" 문자열 그리기
        Font scoresFont = new Font("Arial", Font.PLAIN, 20);
        g.setFont(scoresFont);
        g.setColor(Color.GREEN); // "Scores" 문자열 색상
        FontMetrics metrics = g.getFontMetrics(scoresFont);
        int scoresWidth = metrics.stringWidth("Scores");
        g.drawString("Scores", scoreboardX + (scoreboardWidth - scoresWidth) / 2, scoreboardY + metrics.getAscent());

        // 점수 문자열 그리기
        g.setColor(Color.RED); // 점수 문자열 색상
        String scoreText = "" + score;
        g.drawString(scoreText, scoreboardX + (scoreboardWidth - metrics.stringWidth(scoreText)) / 2, scoreboardY + CELL_SIZE * 2 + metrics.getAscent());
    }

    private void drawCell(Graphics g, int x, int y, char content) {
        switch (content) {
            case 'G':
                g.setColor(Color.GRAY); // 회색으로 색상 변경
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE); // 셀 그리기
                break;
            case 'X':
                // 폰트를 굵게 설정
                Font font = new Font("Serif", Font.BOLD, 24); // "Serif"를 예로 듭니다. 환경에 맞는 폰트로 변경 가능
                g.setFont(font);
                g.setColor(Color.WHITE); // 색상을 흰색으로 변경

                // 문자를 셀 중앙에 배치하기 위해 캐릭터의 너비와 높이 계산
                FontMetrics metrics = g.getFontMetrics(font);
                int charWidth = metrics.stringWidth("X");
                int charHeight = metrics.getHeight();

                // 'X' 문자를 셀 중앙에 그리기
                g.drawString("X", x + (CELL_SIZE - charWidth) / 2, y + (CELL_SIZE - charHeight) / 3 + metrics.getAscent());
                break;
            case 'O':
                g.setColor(Color.RED);
                // 'O'를 중앙에 배치하는 비슷한 방법 사용
                font = new Font("Gothic", Font.BOLD, 24);
                g.setFont(font);
                metrics = g.getFontMetrics(font);
                charWidth = metrics.stringWidth("O");
                charHeight = metrics.getHeight();

                // 'O' 문자를 셀 중앙에 그리기
                g.drawString("o", x + (CELL_SIZE - charWidth) / 2, y + (CELL_SIZE - charHeight) / 3 + metrics.getAscent());
                break;
            default:
                break;
        }
    }

    private void drawBorderLine(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.DARK_GRAY); // 선의 색상 설정
        g.fillRect(x, y, width, height);
    }

    // 점수 업데이트 메소드 (임시 데이터를 사용하여 점수를 변경하는 메소드)
    public void updateScore(int newScore) {
        this.score = newScore;
        repaint(); // 화면을 다시 그림
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris Game");
            InGameScreen gameScreen = new InGameScreen();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(gameScreen);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // 콘솔에서 상태 확인을 위한 임시 코드
            // 실제 게임에서는 게임 로직에 따라 점수를 업데이트하게 됩니다.
            gameScreen.updateScore(0); // 점수를 임시로 0으로 설정
        });
    }
}