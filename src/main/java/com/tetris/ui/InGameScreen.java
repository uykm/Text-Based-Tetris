package com.tetris.ui;


import com.tetris.logic.Block;
import com.tetris.logic.BoardController;
import com.tetris.logic.InGameScoreController;
import com.tetris.logic.SettingController;
import com.tetris.model.BlockType;

import javax.swing.*;
import java.awt.*;

public class InGameScreen extends JPanel {

    // 게임 보드의 가로, 세로 길이
    private final int EXTEND_BOARD_WIDTH = 16;
    private final int EXTEND_BOARD_HEIGHT = 26;

    // 실제 게임 플레이 영역
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;

    private final int CELL_SIZE; // 셀의 크기
    private final int BLOCK_SIZE; // 블록 크기
    private final int SCORE_SIZE; // 점수판 크기
    private final int MESSAGE_SIZE; // 메시지 크기

    private final int NEXT_BLOCK_BOARD_WIDTH = 8; // 다음 블록 표시 영역의 가로 길이
    private final int NEXT_BLOCK_BOARD_HEIGHT = 8; // 다음 블록 표시 영역의 세로 길이
    private final int[][] board; // 게임 보드 상태
    private int[][] nextBlockBoard; // 다음 블록 표시 영역

    private final int NEXT_DISTURB_BOARD_WIDTH = 10; // 다음 방해 블록 표시 영역의 가로 길이
    private final int NEXT_DISTURB_BOARD_HEIGHT = 15; // 다음 방해 블록 표시 영역의 세로 길이
    private int[][] nextDisturbBoard = new int[NEXT_DISTURB_BOARD_HEIGHT][NEXT_DISTURB_BOARD_WIDTH]; // 다음 방해 블록 표시 영역
    // disturbBoardStatus[disturbLine][16] -> nextDisturbBoard[20][10]

    private final BoardController boardController; // 게임 보드 컨트롤러
    private final InGameScoreController inGameScoreController; // 인게임 점수 컨트롤러
    SettingController settingController;
    private int score; // 점수

    private boolean isDualMode;

    public InGameScreen(BoardController boardController, InGameScoreController inGameScoreController, boolean isDualMode) {
        // 임시 데이터로 초기화
        board = boardController.getBoard();

        this.boardController = boardController;
        this.inGameScoreController = inGameScoreController;
        this.isDualMode = isDualMode;
        settingController = new SettingController();

        initNextBlockBoard();

        String screenSize = settingController.getScreenSize("screenSize", "medium");
        CELL_SIZE = settingController.getCellSize(screenSize);
        BLOCK_SIZE = settingController.getBlockSize(screenSize);
        SCORE_SIZE = settingController.getScoreSize(screenSize);
        MESSAGE_SIZE = settingController.getMessageSize(screenSize);

        // 화면 크기 설정
        setPreferredSize(new Dimension((EXTEND_BOARD_WIDTH + NEXT_BLOCK_BOARD_WIDTH + 2) * CELL_SIZE, EXTEND_BOARD_HEIGHT * CELL_SIZE));
        setBackground(Color.LIGHT_GRAY);
    }

    // 다음 블록 표시 영역 초기화
    private void initNextBlockBoard() {
        Block nextBlock = boardController.getNextBlock();
        // 다음 블록 영역 초기화
        nextBlockBoard = new int[NEXT_BLOCK_BOARD_HEIGHT][NEXT_BLOCK_BOARD_WIDTH];
        // 다음 블록 영역 테두리 그리기
        for (int i = 0; i < NEXT_BLOCK_BOARD_HEIGHT; i++) {
            for (int j = 0; j < NEXT_BLOCK_BOARD_WIDTH; j++) {
                if (i == 0 || i == NEXT_BLOCK_BOARD_HEIGHT - 1
                        || j == 0 || j == NEXT_BLOCK_BOARD_WIDTH - 1) {
                    nextBlockBoard[i][j] = -1; // 테두리
                }
            }
        }
        // 다음 블록 영역에 블록 그리기
        // TODO: 3/24/24 : 블록이 정확히 가운데에 위치하면 좋을 것 같다
        for (int i = 2; i < 6; i++) {
            for (int j = 2; j < 6; j++) {
                nextBlockBoard[i][j] = nextBlock.getShape(j - 2, i - 2);
            }
        }
    }

    private void initDisturbBlockBoard() {
        int[][] disturbBoardStatus = boardController.getShouldAddLines();

        // 새로운 방해 블록이 도착할 때만 실행
        if (disturbBoardStatus != null) {
            int disturbLine = disturbBoardStatus.length;

            // 다음 방해 블록 영역 초기화
            nextDisturbBoard = new int[NEXT_DISTURB_BOARD_HEIGHT][NEXT_DISTURB_BOARD_WIDTH];

            for (int i = 0; i < disturbLine; i++) {
                for (int j = 3; j < 13; j++) {
                    int newValue = disturbBoardStatus[i][j] != 0 ? 30 : 0;
                    nextDisturbBoard[NEXT_DISTURB_BOARD_HEIGHT - disturbLine + i][j - 3] = newValue;
                }
            }
        }
    }

    @Override
    // 화면을 그리는 메소드
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String scoreMessage = inGameScoreController.getScoreMessages();

        score = inGameScoreController.getScore();

        // 전체 게임 보드의 배경을 검정색으로 설정
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, EXTEND_BOARD_WIDTH * CELL_SIZE, EXTEND_BOARD_HEIGHT * CELL_SIZE);

        // 다음 블록 영역의 배경도 검정색으로 설정
        int nextBlockBoardX = (EXTEND_BOARD_WIDTH + 2) * CELL_SIZE; // 다음 블록 영역의 시작 x 좌표
        int nextBlockBoardY = CELL_SIZE; // 다음 블록 영역의 시작 y 좌표
        int nextBlockInBoardWidth = (NEXT_BLOCK_BOARD_WIDTH - 2) * CELL_SIZE; // 다음 블록 영역의 너비
        int nextBlockInBoardHeight = (NEXT_BLOCK_BOARD_HEIGHT - 2) * CELL_SIZE; // 다음 블록 영역의 높이
        g.fillRect(nextBlockBoardX, nextBlockBoardY, nextBlockInBoardWidth, nextBlockInBoardHeight);

        // 게임 보드 그리기
        for (int i = 0; i < EXTEND_BOARD_HEIGHT; i++) {
            for (int j = 0; j < EXTEND_BOARD_WIDTH; j++) {
                drawCell(g, j * CELL_SIZE, i * CELL_SIZE, board[i][j] , false);
            }
        }
        int borderWidth = CELL_SIZE / 3 * 2; // 테두리 선의 너비
        // 어두운 회색 상단 선 그리기
        drawBorderLine(g, CELL_SIZE * 2 - borderWidth, CELL_SIZE * 2 - borderWidth, (EXTEND_BOARD_WIDTH - 4) * CELL_SIZE + borderWidth * 2, borderWidth);
        // 하단 선 그리기
        drawBorderLine(g, CELL_SIZE * 2 - borderWidth, EXTEND_BOARD_HEIGHT * CELL_SIZE - CELL_SIZE * 2, (EXTEND_BOARD_WIDTH - 4) * CELL_SIZE + borderWidth * 2, borderWidth);
        // 좌측 선 그리기
        drawBorderLine(g, CELL_SIZE * 2 - borderWidth, CELL_SIZE * 2, borderWidth, (EXTEND_BOARD_HEIGHT - 4) * CELL_SIZE);
        // 우측 선 그리기
        drawBorderLine(g, EXTEND_BOARD_WIDTH * CELL_SIZE - CELL_SIZE * 2, CELL_SIZE * 2, borderWidth, (EXTEND_BOARD_HEIGHT - 4) * CELL_SIZE);

        // 다음 블록 영역 그리기
        for (int i = 0; i < NEXT_BLOCK_BOARD_HEIGHT; i++) {
            for (int j = 0; j < NEXT_BLOCK_BOARD_WIDTH; j++) {
                if (nextBlockBoard[i][j] != ' ') {
                    drawCell(g, (EXTEND_BOARD_WIDTH + 1 + j) * CELL_SIZE, i * CELL_SIZE, nextBlockBoard[i][j], false);
                }
            }
        }
        borderWidth = CELL_SIZE / 3;
        // 어두운 회색 상단 선 그리기
        drawBorderLine(g, nextBlockBoardX - borderWidth, CELL_SIZE - borderWidth, nextBlockInBoardWidth + borderWidth * 2, borderWidth);
        // 하단 선 그리기
        drawBorderLine(g, nextBlockBoardX - borderWidth, nextBlockBoardY + nextBlockInBoardHeight, nextBlockInBoardWidth + borderWidth * 2, borderWidth);
        // 좌측 선 그리기
        drawBorderLine(g, nextBlockBoardX - borderWidth, CELL_SIZE, borderWidth, nextBlockInBoardHeight);
        // 우측 선 그리기
        drawBorderLine(g, nextBlockBoardX + nextBlockInBoardWidth, CELL_SIZE, borderWidth, nextBlockInBoardHeight);


        // 점수판 상자 그리기
        int scoreboardX = EXTEND_BOARD_WIDTH * CELL_SIZE + CELL_SIZE; // 점수판 x 위치
        int scoreboardY = NEXT_BLOCK_BOARD_HEIGHT * CELL_SIZE + CELL_SIZE; // 점수판 y 위치
        int scoreboardWidth = NEXT_BLOCK_BOARD_WIDTH * CELL_SIZE; // 점수판 너비
        int scoreboardHeight = CELL_SIZE * 4; // 점수판 높이, 필요에 따라 조정
        g.setColor(Color.GRAY);
        g.fillRect(scoreboardX, scoreboardY, scoreboardWidth, scoreboardHeight); // 사각형 상자 그리기

        // "Scores" 문자열 그리기
        Font scoresFont = new Font("Arial", Font.PLAIN, SCORE_SIZE);
        g.setFont(scoresFont);
        g.setColor(Color.GREEN); // "Scores" 문자열 색상
        FontMetrics metrics = g.getFontMetrics(scoresFont);
        int scoresWidth = metrics.stringWidth("Scores");
        g.drawString("Scores", scoreboardX + (scoreboardWidth - scoresWidth) / 2, scoreboardY + metrics.getAscent());

        // 점수 문자열 그리기
        g.setColor(Color.RED); // 점수 문자열 색상
        String scoreText = "" + score;
        g.drawString(scoreText, scoreboardX + (scoreboardWidth - metrics.stringWidth(scoreText)) / 2, scoreboardY + CELL_SIZE * 2 + metrics.getAscent());

        // 상대방으로부터 넘어올 블록 미리 표시하는 구간
        if (isDualMode) {
            int halfCellSize = CELL_SIZE / 2;
            int disturbBoardWidth = NEXT_DISTURB_BOARD_WIDTH * halfCellSize;
            int disturbBoardHeight = NEXT_DISTURB_BOARD_HEIGHT * halfCellSize;
            int disturbBoardX = scoreboardX + (scoreboardWidth - disturbBoardWidth) / 2;
            int disturbBoardY = scoreboardY + scoreboardHeight + CELL_SIZE;

            // 상대방 방해 블록 표시할 상자
            g.setColor(Color.BLACK);
            g.fillRect(disturbBoardX, disturbBoardY, disturbBoardWidth, disturbBoardHeight);

            if (nextDisturbBoard != null) {

                for (int i = 0; i < NEXT_DISTURB_BOARD_HEIGHT; i++) {
                    for (int j = 0; j < NEXT_DISTURB_BOARD_WIDTH; j++) {
                        drawCell(g, disturbBoardX + j * halfCellSize, disturbBoardY + i * halfCellSize, nextDisturbBoard[i][j], true);
                    }
                }
            }
        }
    }

    // 스코어 메시지를 여러 줄로 그리는 메소드
    private void drawScoreMessages(Graphics g, String scoreMessages) {
        int messageX = EXTEND_BOARD_WIDTH * CELL_SIZE + CELL_SIZE; // 점수 메시지 x 위치
        int messageY = NEXT_BLOCK_BOARD_HEIGHT * CELL_SIZE + CELL_SIZE * 6; // 점수 메시지 y 위치, 위치 조정 필요
        g.setColor(Color.WHITE); // 점수 메시지 색상
        Font messageFont = new Font("Arial", Font.PLAIN, MESSAGE_SIZE);
        g.setFont(messageFont);
        drawStringMultiLine(g, scoreMessages, messageX, messageY);
    }

    // 멀티라인 문자열을 그리는 메소드
    private void drawStringMultiLine(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }

    // 셀을 그리는 메소드
    private void drawCell(Graphics g, int x, int y, int content, boolean isDisturb) {

        int curCellSize = isDisturb ? CELL_SIZE / 2 : CELL_SIZE;
        int fontSize = isDisturb ? BLOCK_SIZE / 2 : BLOCK_SIZE;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);

        int charWidth = metrics.stringWidth("ㅁ");
        int charHeight = metrics.getHeight();

        switch (content) {
            default:
                g.setColor(Color.BLACK); // 검정색으로 배경을 채움
                g.fillRect(x, y, curCellSize, curCellSize);
                break;
            case -1:
                g.setColor(Color.GRAY); // 회색으로 테두리를 그림
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                break;
            case 20:
                g.setColor(Color.WHITE); // 'X' 문자를 흰색으로 그림
                g.drawString("X", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 1, 2, 4, 6, 7:
                // 다른 블록 타입에 대한 처리
                g.setColor(Block.getBlock(BlockType.getBlockTypeByIndex(content - 1)).getColor());
                charWidth = metrics.stringWidth("ㅁ");
                g.drawString("ㅁ", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 3, 5:
                g.setColor(Block.getBlock(BlockType.getBlockTypeByIndex(content - 1)).getColor());
                charWidth = metrics.stringWidth("ㅇ");
                g.drawString("ㅇ", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 8: // 줄삭제 아이템
                g.setColor(Color.WHITE); // "L" 문자를 위한 색상 지정
                charWidth = metrics.stringWidth("L");
                g.drawString("L", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 9: // 무게추 아이템
                g.setColor(Color.DARK_GRAY);
                charWidth = metrics.stringWidth("ㅇ");
                g.drawString("ㅇ", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 10: // 물 아이템
                g.setColor(Color.BLUE);
                charWidth = metrics.stringWidth("᯾");
                g.drawString("᯾", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 11: // 폭탄 아이템
                g.setColor(Color.RED);
                charWidth = metrics.stringWidth("◎");
                g.drawString("◎", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 12: // 폭탄 아이템 외형(꽁다리)
                g.setColor(Color.YELLOW);
                charWidth = metrics.stringWidth("☄");
                g.drawString("☄", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 13: // 폭탄 터지는 이벤트
                g.setColor(Color.YELLOW);
                charWidth = metrics.stringWidth("✸");
                g.drawString("✸", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 14: // 확장 아이템(확장 전)
                g.setColor(Color.WHITE);
                charWidth = metrics.stringWidth("✷");
                g.drawString("✵", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 15: // 확장 아이템(확장 후)
                g.setColor(Color.CYAN);
                charWidth = metrics.stringWidth("✷");
                g.drawString("✵", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 40: // 게임 보드 안의 방해 블록
                g.setColor(Color.GRAY);
                charWidth = metrics.stringWidth("ㅁ");
                g.drawString("ㅁ", x + (CELL_SIZE - charWidth) / 2 + (CELL_SIZE - charWidth) % 2, y + (CELL_SIZE - charHeight) / 3 + (CELL_SIZE - charHeight) % 2 + metrics.getAscent());
                break;
            case 30: // 앞으로 나올 방해 블록 표시 보드
                g.setColor(Color.WHITE); // 방해 블록의 색상 지정
                int halfCharX = x + (curCellSize - charWidth) / 2;
                int halfCharY = y + (curCellSize - charHeight) / 2 + metrics.getAscent();

                g.drawString("ㅁ", halfCharX, halfCharY);
                break;
            case -2: // 한 줄이 꽉 차서 지워지기 전
                g.setColor(Color.YELLOW);
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
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

    // 게임 보드 업데이트 메소드 nextBlockBoard를 업데이트 하기 위함
    public void updateBoard() {
        initNextBlockBoard();
        initDisturbBlockBoard();
        repaint();
    }
}
