package com.tetris.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class DualTetrisController {
    private GameController gameController1;
    private GameController gameController2;

    private Timer limitTimer;

    // gameController1 조작을 위한 키 코드 w, a, s, d, Left Shift
    private int[] keyCodes1 = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_SHIFT};

    // gameController1 조작을 위한 키 코드 방향키, Right Shift
    private int[] keyCodes2 = {KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT};

    public DualTetrisController(boolean isItem, boolean isTimeAttack) {
        gameController1 = new GameController(isItem, true, isTimeAttack);
        gameController2 = new GameController(isItem, true, isTimeAttack);

        gameController1.setStrPlayer("A");
        gameController2.setStrPlayer("B");

        gameController1.setOpponent(gameController2);
        gameController2.setOpponent(gameController1);

        //아이템 모드, 타임어택 모드 상태 표시
        System.out.println("Item Mode : " + isItem);
        System.out.println("Time Attack Mode : " + isTimeAttack);

        initUI();
    }

    private void setupKeyListener(JFrame frame) {
        Map<Integer, String> game1Controls = new HashMap<>();
        Map<Integer, String> game2Controls = new HashMap<>();

        // Setting up controls for gameController1
        game1Controls.put(keyCodes1[0], "ROTATE");
        game1Controls.put(keyCodes1[1], "LEFT");
        game1Controls.put(keyCodes1[2], "DOWN");
        game1Controls.put(keyCodes1[3], "RIGHT");
        game1Controls.put(keyCodes1[4], "DROP");

        // Setting up controls for gameController2
        game2Controls.put(keyCodes2[0], "ROTATE");
        game2Controls.put(keyCodes2[1], "LEFT");
        game2Controls.put(keyCodes2[2], "DOWN");
        game2Controls.put(keyCodes2[3], "RIGHT");
        game2Controls.put(keyCodes2[4], "DROP");

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                // Special handling for shared keys with different locations (e.g., SHIFT keys)
                if (keyCodes1[4] == keyCodes2[4] && e.getKeyCode() == keyCodes1[4]) {
                    if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                        gameController1.controlGame("DROP");
                        return;
                    } else if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) {
                        gameController2.controlGame("DROP");
                        return;
                    }
                }

                // Handle game controls based on the key code and location
                if (game1Controls.containsKey(e.getKeyCode())) {
                    gameController1.controlGame(game1Controls.get(e.getKeyCode()));
                } else if (game2Controls.containsKey(e.getKeyCode())) {
                    gameController2.controlGame(game2Controls.get(e.getKeyCode()));
                }

                // Handle global pause
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameController1.controlGame("PAUSE");
                    gameController2.controlGame("PAUSE");
                }
            }
        });
    }

    private void initUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dual Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 2)); // Set layout to grid with 1 row and 2 columns

            frame.add(gameController1.getInGameScreen());
            frame.add(gameController2.getInGameScreen());

            frame.pack();
            setupKeyListener(frame);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }

    private void timeController() {
        limitTimer = new Timer();
        // 3분 제한 끝나면 게임 종료
        limitTimer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                if(gameController1.getScore() > gameController2.getScore()) {
                    gameController2.gameOver();
                } else {
                    gameController1.gameOver();
                }
                System.out.println("Time Over");
            }
        }, 180000);
    }

    public static void main(String[] args) {
        new DualTetrisController(false, true); // Launch the dual Tetris controller
    }
}
