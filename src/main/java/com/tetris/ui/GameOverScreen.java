package com.tetris.ui;

import com.tetris.logic.GameController;
import com.tetris.logic.SettingController;
import com.tetris.logic.Score;
import com.tetris.logic.RankScoreController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static com.tetris.component.Button.createLogoBtnUp;
import static com.tetris.component.ScreenSize.setWidthHeight;
import static java.lang.System.exit;

public class GameOverScreen extends JFrame implements ActionListener {

    private final SettingController settingController = new SettingController();
    private final RankScoreController rankScoreController = new RankScoreController();

    private JButton btnReplay;
    private JButton btnMenu;
    private JButton btnExit;

    private boolean isItem;

    private final int titleSize;
    private final int btnSize;

    public GameOverScreen(int score, boolean isItem) {
        this.isItem = isItem;

        setTitle("Tetris - GameOver");
        String screenSize = settingController.getScreenSize("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(400, 550, this);
                titleSize = 20;
                btnSize = 90;
                break;
            case "big":
                setWidthHeight(900, 900, this);
                titleSize = 50;
                btnSize = 120;
                break;
            default:
                setWidthHeight(600, 600, this);
                titleSize = 30;
                btnSize = 100;
                break;
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel scoreLabel = new JLabel("Your Score : " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.BOLD, titleSize));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreLabel);
        add(Box.createVerticalStrut(20));

        JPanel scorePanel = createScorePanel(isItem, screenSize);
        add(scorePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        btnMenu = createLogoBtnUp("Menu", "menu", this, screenSize, "/image/menu.png");
        btnMenu.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnMenu.setFocusable(true);
        bottomPanel.add(btnMenu);

        btnReplay = createLogoBtnUp("Replay", "replay", this, screenSize, "/image/replay.png");
        btnReplay.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnReplay.setFocusable(true);
        bottomPanel.add(btnReplay);

        btnExit = createLogoBtnUp("Exit", "exit", this, screenSize, "/image/exit_logo.png");
        btnExit.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnExit.setFocusable(true);
        bottomPanel.add(btnExit);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        btnReplay.requestFocusInWindow();

        btnMenu.addKeyListener(new MyKeyListener());
        btnReplay.addKeyListener(new MyKeyListener());
        btnExit.addKeyListener(new MyKeyListener());
    }

    private JPanel createScorePanel(boolean isItem, String screenSize) {
        int titleSize = switch (screenSize) {
            case "small" -> 20;
            case "big" -> 50;
            default -> 30;
        };

        int fontSize = switch (screenSize) {
            case "small" -> 13;
            case "big" -> 20;
            default -> 15;
        };

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(11, 1));

        JLabel title = new JLabel("Ranking", SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, titleSize));
        scorePanel.add(title);

        List<Score> topScores = rankScoreController.getScores(isItem);

        for (int i = 0; i < topScores.size(); i++) {
            Score score = topScores.get(i);
            JLabel scoreLabel = new JLabel((i + 1) + ". " + score.getPlayerName() + " - " + score.getScore(), SwingConstants.CENTER);

            scoreLabel.setFont(new Font(title.getFont().getName(), Font.PLAIN, fontSize));
            scorePanel.add(scoreLabel);
        }
        return scorePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        setVisible(false);
        switch (command) {
            case "menu":
                new MainMenuScreen();
                break;
            case "replay":
                new GameController(isItem);
                break;
            case "exit":
                exit(0);
                break;
        }
    }

    private class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                focusLeftButton();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                focusRightButton();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                moveScreen();
            }
        }
    }

    private void moveScreen() {
        setVisible(false);
        if (btnMenu.isFocusOwner()) {
            new MainMenuScreen();
        } else if (btnReplay.isFocusOwner()) {
            new GameController(isItem);
        } else if (btnExit.isFocusOwner()) {
            exit(0);
        }
    }

    private void focusLeftButton() {
        if (btnExit.isFocusOwner()) {
            btnReplay.requestFocusInWindow();
        } else if (btnReplay.isFocusOwner()) {
            btnMenu.requestFocusInWindow();
        }
    }

    private void focusRightButton() {
        if (btnMenu.isFocusOwner()) {
            btnReplay.requestFocusInWindow();
        } else if (btnReplay.isFocusOwner()) {
            btnExit.requestFocusInWindow();
        }
    }

    // 테스트 코드를 위한 GETTER
    public JButton getBtnReplay() {
        return btnReplay;
    }
    public JButton getBtnMenu() {
        return btnMenu;
    }
    public JButton getBtnExit() {
        return btnExit;
    }
}
