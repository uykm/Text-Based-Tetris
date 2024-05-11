package com.tetris.logic;

import com.tetris.model.Direction;
import com.tetris.ui.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// 게임의 전반적인 흐름을 제어하는 클래스
public class GameController implements PauseScreenCallback {
    private BoardController boardController;
    private InGameScreen inGameScreen;
    private InGameScoreController inGameScoreController;
    private RankScoreController rankScoreController;
    private final SettingController settingController = new SettingController();
    private GameController opponent;
    private final int[] keyCodes = settingController.getKeyCodes();
    private int ROTATE = keyCodes[0];
    private int LEFT = keyCodes[1];
    private int RIGHT = keyCodes[2];
    private int DOWN = keyCodes[3];
    private int DROP = keyCodes[4];
    JFrame frame;
    Timer timer;

    final int MAX_SPEED = 200;

    int currentSpeed;
    private boolean isItem;

    // 게임 컨트롤러 생성자
    public GameController(boolean isItem) {
        this(isItem, false);
    }

    public GameController(boolean isItem, boolean isDualMode) {
        initialize(isItem);
        startGame(isItem, isDualMode);
    }

    private void initialize(boolean isItem) {
        this.isItem = isItem;
        initUI();
        this.inGameScoreController = new InGameScoreController();
        this.boardController = new BoardController(this, this.inGameScoreController, isItem);
        this.inGameScreen = new InGameScreen(this.boardController, this.inGameScoreController);
        this.rankScoreController = new RankScoreController();
    }

    // 게임 UI 초기화
    private void initUI() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(inGameScreen);
            frame.pack();
            frame.setLocationRelativeTo(null);
            setupKeyListener(frame);
            frame.setVisible(true);
            frame.setResizable(false);
            inGameScreen.updateScore(0);
        });
    }

    @Override
    public void onResumeGame() {
        timer.start();
    }

    @Override
    public void onHideFrame() {
        frame.setVisible(false);
    }

    // 키보드 이벤트 처리
    private void setupKeyListener(JFrame frame) {
        // Create the PauseScreen instance once during initialization

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Use the custom key codes for control
                if (e.getKeyCode() == LEFT) {
                    boardController.moveBlock(Direction.LEFT);
                } else if (e.getKeyCode() == RIGHT) {
                    boardController.moveBlock(Direction.RIGHT);
                } else if (e.getKeyCode() == DOWN) {
                    boardController.moveBlock(Direction.DOWN);
                    inGameScreen.updateBoard();
                } else if (e.getKeyCode() == ROTATE) {
                    boardController.moveBlock(Direction.UP); // Consider renaming Direction.UP to ROTATE for clarity
                } else if (e.getKeyCode() == DROP) {
                    boardController.moveBlock(Direction.SPACE); // Consider renaming Direction.SPACE to DROP for clarity
                    inGameScreen.updateBoard();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    timer.stop();
                    PauseScreen pauseScreen = new PauseScreen(isItem);
                    pauseScreen.setCallback(GameController.this); // Set the callback
                    pauseScreen.setVisible(true); // Show the PauseScreen
                }
                inGameScreen.repaint();
            }
        });
    }

    public void controlGame(String key) {
        switch (key) {
            case "LEFT" -> boardController.moveBlock(Direction.LEFT);
            case "RIGHT" -> boardController.moveBlock(Direction.RIGHT);
            case "DOWN" -> {
                boardController.moveBlock(Direction.DOWN);
                inGameScreen.updateBoard();
            }
            case "ROTATE" -> boardController.moveBlock(Direction.UP);
            case "DROP" -> {
                boardController.moveBlock(Direction.SPACE);
                inGameScreen.updateBoard();
            }
            case "PAUSE" -> timer.stop(); // Todo : 대전 모드 PAUSE 처리
            case "RESUME" -> onResumeGame();
            case "REPLAY" -> {
                frame.dispose();
                new GameController(isItem);
            }
        }
        inGameScreen.repaint();
    }

    private void startGame(boolean isItem, boolean isDualMode) {
        currentSpeed = 1000;
        boardController.placeNewBlock();

        timer = new Timer(currentSpeed, e -> {
            boolean blink = boardController.blinkCheck();
            if(blink) {
                while (blink) {
                    inGameScreen.updateBoard();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    blink = boardController.blinkCheck();
                }
            }

            inGameScreen.updateBoard();
            if(boardController.getNewBlockState()) boardController.placeNewBlock();
            boardController.moveBlock(Direction.DOWN);
            inGameScreen.updateBoard();

            if (boardController.checkGameOver()) {
                frame.dispose();
                timer.stop();
                if(!isDualMode){
                    if (rankScoreController.isScoreInTop10(inGameScoreController.getScore(), isItem)) {
                        new RegisterScoreScreen(inGameScoreController.getScore(), isItem);
                    } else {
                        new GameOverScreen(inGameScoreController.getScore(), isItem);
                    }
                } else {
                    // Todo : 대전 모드 일 경우 GameOver 처리
                }
            }
        });
        timer.start();
    }

    public void speedUp(int speed) {
        if (currentSpeed >= MAX_SPEED) {
            if(settingController.getDifficulty()==0) {
                speed = (int) (speed * 0.8);
            }
            else if(settingController.getDifficulty()==2) {
                speed = (int) (speed * 1.2);
            }
            currentSpeed -= speed;
            timer.setDelay(currentSpeed);
            inGameScoreController.setScoreOnBlockMoveDown((1100-currentSpeed)/100);
            inGameScoreController.addScoreMessage("Speed up! Current speed: " + currentSpeed);
            inGameScoreController.addScoreMessage("Score per Block Move Down: " + inGameScoreController.getScoreOnBlockMoveDown());
        }
    }

    public InGameScreen getInGameScreen() {
        return inGameScreen;
    }

    public void setOpponent(GameController opponent) {
        this.opponent = opponent;
    }

    public void addLines(int[][] lines) {
        boardController.addLines(lines);
    }

    public void sendLines(int[][] lines) {
        opponent.addLines(lines);
    }

}
