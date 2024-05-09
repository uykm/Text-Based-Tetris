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
    private final InGameScoreController inGameScoreController;
    private final RankScoreController rankScoreController;
    private final SettingController settingController = new SettingController();
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

        // 노말 모드 vs 아이템 모드
        this.isItem = isItem;

        initUI();
        this.inGameScoreController = new InGameScoreController();
        this.boardController = new BoardController(this, this.inGameScoreController, isItem);
        this.inGameScreen = new InGameScreen(this.boardController, this.inGameScoreController);
        this.rankScoreController = new RankScoreController();

        startGame(isItem);
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

            // 콘솔에서 상태 확인을 위한 임시 코드
            // 실제 게임에서는 게임 로직에 따라 점수를 업데이트하게 됩니다.
            // TODO: 3/24/24 : 현재 점수 계산 로직 없음, BoardController 또는 GameController에서 점수 계산 로직 추가 필요
            inGameScreen.updateScore(0); // 점수를 임시로 0으로 설정
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
    // TODO: 3/24/24 : 효정이가 KeyListener 구현 하면 바꿀 예정
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
        if (key.equals("LEFT")) {
            boardController.moveBlock(Direction.LEFT);
        } else if (key.equals("RIGHT")) {
            boardController.moveBlock(Direction.RIGHT);
        } else if (key.equals("DOWN")) {
            boardController.moveBlock(Direction.DOWN);
            inGameScreen.updateBoard();
        } else if (key.equals("ROTATE")) {
            boardController.moveBlock(Direction.UP);
        } else if (key.equals("DROP")) {
            boardController.moveBlock(Direction.SPACE);
            inGameScreen.updateBoard();
        } else if (key.equals("PAUSE")) {
            timer.stop();
        } else if (key.equals("RESUME")) {
            onResumeGame();
        } else if (key.equals("REPLAY")){
            frame.dispose();
            new GameController(isItem);
        }
        inGameScreen.repaint();
    }

    private void startGame(boolean isItem) {
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
                if (rankScoreController.isScoreInTop10(inGameScoreController.getScore(), isItem)) {
                    new RegisterScoreScreen(inGameScoreController.getScore(), isItem);
                } else {
                    new GameOverScreen(inGameScoreController.getScore(), isItem);
                }
                timer.stop();
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

    //Todo: 듀얼 게임 관련 로직

    // 삭제된 라인 받아오기

    // 라인 추가하기

    // 추가될 수 있는 라인 수 가져오기

    // 게임 오버 상태 받아오기






}
