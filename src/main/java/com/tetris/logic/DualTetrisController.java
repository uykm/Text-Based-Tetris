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
    private JLabel timeLabel;
    private boolean isTimeAttack;
    private  boolean isItem;

    private JFrame frame;

    public JFrame getDualFrame() {
        return this.frame;
    }

    // gameController1 조작을 위한 키 코드 w, a, s, d, Left Shift
    private int[] keyCodes1 = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_SHIFT};

    // gameController1 조작을 위한 키 코드 방향키, Right Shift
    private int[] keyCodes2 = {KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT};

    public DualTetrisController(boolean isItem, boolean isTimeAttack) {
        this.isTimeAttack = isTimeAttack;
        this.isItem = isItem;
        gameController1 = new GameController(isItem, true, isTimeAttack);
        gameController2 = new GameController(isItem, true, isTimeAttack);

        gameController1.setStrPlayer("A");
        gameController2.setStrPlayer("B");

        gameController1.setOpponent(gameController2);
        gameController2.setOpponent(gameController1);

        gameController1.setDualTetrisController(this);
        gameController2.setDualTetrisController(this);

        if(isTimeAttack) {
            timeController();
        }

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
                }
            }
        });
    }

    private void initUI() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Dual Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout()); // Change to BorderLayout for better control placement

            JPanel gamePanel = new JPanel(new GridLayout(1, 2));
            JPanel headerPanel = new JPanel(new GridLayout(1, 3)); // Panel for player names and timer

            // Player labels
            JLabel player1Label = new JLabel("Player A", SwingConstants.CENTER);
            JLabel player2Label = new JLabel("Player B", SwingConstants.CENTER);

            // Time or Mode label
            timeLabel = new JLabel("", SwingConstants.CENTER);
            timeLabel.setFont(new Font("Serif", Font.BOLD, 16));

            if (isTimeAttack) {
                timeLabel.setText("Time: 03:00"); // Initial time setup for time attack
            } else {
                // Display the mode when not in time attack
                String modeText = isItem ? "Item Mode" : "Normal Mode";
                timeLabel.setText(modeText);
            }

            // Adding components to header panel
            headerPanel.add(player1Label);
            headerPanel.add(timeLabel);
            headerPanel.add(player2Label);

            // Adding game screens
            gamePanel.add(gameController1.getInGameScreen());
            gamePanel.add(gameController2.getInGameScreen());

            frame.add(headerPanel, BorderLayout.NORTH);
            frame.add(gamePanel, BorderLayout.CENTER);

            frame.pack();
            setupKeyListener(frame);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }

    private void updateTime(int remainingTimeInSeconds) {
        int minutes = remainingTimeInSeconds / 60;
        int seconds = remainingTimeInSeconds % 60;
        SwingUtilities.invokeLater(() -> {
            timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        });
    }

    private void timeController() {
        limitTimer = new Timer();
        final int totalDuration = 180; // in seconds for easier countdown handling
        java.util.TimerTask countdownTask = new java.util.TimerTask() {
            int remainingTime = totalDuration;

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    if (gameController1.getScore() > gameController2.getScore()) {
                        gameController2.gameOver();
                    } else {
                        gameController1.gameOver();
                    }
                    System.out.println("Time Over");
                    limitTimer.cancel(); // Stop the timer after game over
                } else {
                    updateTime(remainingTime);
                    remainingTime--;
                }
            }
        };

        limitTimer.scheduleAtFixedRate(countdownTask, 0, 1000); // Schedule task to run every second
    }

    public static void main(String[] args) {
        new DualTetrisController(false, true); // Launch the dual Tetris controller
    }
}
