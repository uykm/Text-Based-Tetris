package com.tetris.ui;

import com.tetris.logic.*;

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

public class WinnerScreen extends JFrame implements ActionListener {

    private final SettingController settingController = new SettingController();
    private final RankScoreController rankScoreController = new RankScoreController();

    private DualTetrisController dualTetrisController;

    private JButton btnReplay;
    private JButton btnMenu;
    private JButton btnExit;
    private boolean isItem;
    private boolean isTimeAttack;

    private int titleSize;
    private int btnSize;


    public void setDualTetrisController(DualTetrisController dualTetrisController) {
        this.dualTetrisController = dualTetrisController;
    }

    public WinnerScreen(String winner, int scoreWinner, int scoreLoser, boolean isItem, boolean isTimeAttack) {
        this.isItem = isItem;
        this.isTimeAttack = isTimeAttack;

        setTitle(winner + " is the Winner!!");
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


        JLabel winnerLabel = new JLabel("Winner is... " + winner, SwingConstants.CENTER);
        JLabel scoreALabel = new JLabel("Winner Score: " + scoreWinner, SwingConstants.CENTER);
        JLabel scoreBLabel = new JLabel("Loser Score: " + scoreLoser, SwingConstants.CENTER);

        winnerLabel.setFont(new Font(winnerLabel.getFont().getName(), Font.BOLD, titleSize));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(winnerLabel);
        add(Box.createVerticalStrut(20));

        scoreALabel.setFont(new Font(winnerLabel.getFont().getName(), Font.BOLD, titleSize));
        scoreALabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreALabel);
        add(Box.createVerticalStrut(20));

        scoreBLabel.setFont(new Font(winnerLabel.getFont().getName(), Font.BOLD, titleSize));
        scoreBLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreBLabel);
        add(Box.createVerticalStrut(20));


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        btnMenu = createLogoBtnUp("Menu", "menu", this, screenSize, "src/main/java/com/tetris/image/menu.png");
        btnMenu.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnMenu.setFocusable(true);
        bottomPanel.add(btnMenu);

        btnReplay = createLogoBtnUp("Replay", "replay", this, screenSize, "src/main/java/com/tetris/image/replay.png");
        btnReplay.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnReplay.setFocusable(true);
        bottomPanel.add(btnReplay);

        btnExit = createLogoBtnUp("Exit", "exit", this, screenSize, "src/main/java/com/tetris/image/exit_logo.png");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        setVisible(false);
        dualTetrisController.getDualFrame().dispose();
        switch (command) {
            case "menu":
                new MainMenuScreen();
                break;
            case "replay":
                new DualTetrisController(isItem, isTimeAttack);
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
        dualTetrisController.getDualFrame().dispose();
        if (btnMenu.isFocusOwner()) {
            new MainMenuScreen();
        } else if (btnReplay.isFocusOwner()) {
            new DualTetrisController(isItem, isTimeAttack);
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
}
