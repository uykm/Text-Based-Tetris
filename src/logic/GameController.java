package logic;

import model.Direction;
import ui.InGameScreen;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {
    private BoardController boardController;
    private InGameScreen inGameScreen;
    private JFrame frame;
    private Timer timer;

    public GameController() {
        this.boardController = new BoardController();
        this.inGameScreen = new InGameScreen(); //
        // Assuming InGameScreen can work with BoardController for game state visualization
        initUI();
        setupKeyListener();
        startGame();
    }

    private void initUI() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Tetris Game");
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

    private void setupKeyListener() {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        boardController.moveBlock(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        boardController.moveBlock(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_DOWN:
                        boardController.moveBlock(Direction.DOWN);
                        break;
                    case KeyEvent.VK_UP:
                        boardController.moveBlock(Direction.UP);
                        break;
                    case KeyEvent.VK_SPACE:
                        // Implement drop functionality or another action
                        break;
                }
                inGameScreen.repaint(); // Assuming InGameScreen has a method to update the UI based on the current game state
            }
        });
    }

    private void startGame() {
        timer = new Timer(1000, e -> {
            boardController.moveBlock(Direction.DOWN);
            inGameScreen.repaint(); // Assuming InGameScreen has a method to update the UI based on the current game state
        });
        timer.start();
    }
}
