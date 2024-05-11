package com.tetris.ui;

import com.tetris.component.Button;
import com.tetris.logic.GameController;
import com.tetris.logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.tetris.component.ScreenSize.setWidthHeight;

public class DifficultyScreen extends JFrame implements ActionListener {

    JButton btnEasy;
    private JButton btnNormal;
    private JButton btnHard;
    private JButton btnMenu;
    private boolean isItem;
    private SettingController settingController = new SettingController();

    private final int btnSize;

    public DifficultyScreen(boolean isItem) {
        this.isItem = isItem;

        setTitle("Tetris - Difficulty");
        String screenSize = settingController.getScreenSize("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(390, 420, this);
                btnSize = 100;
                break;
            case "big":
                setWidthHeight(910, 940, this);
                btnSize = 200;
                break;
            default:
                setWidthHeight(650, 680, this);
                btnSize = 170;
                break;
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        btnEasy = Button.createLogoBtnUp("Easy", "easy", this, screenSize, "src/main/java/com/tetris/image/easy_logo.png");
        btnEasy.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnNormal = Button.createLogoBtnUp("Normal", "normal", this, screenSize, "src/main/java/com/tetris/image/normal_logo.png");
        btnNormal.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnHard = Button.createLogoBtnUp("Hard", "hard", this, screenSize, "src/main/java/com/tetris/image/hard_logo.png");
        btnHard.setPreferredSize((new Dimension(btnSize, btnSize)));

        btnEasy.addKeyListener(new MyKeyListener());
        btnNormal.addKeyListener(new MyKeyListener());
        btnHard.addKeyListener(new MyKeyListener());

        topPanel.add(btnEasy);
        topPanel.add(btnNormal);
        topPanel.add(btnHard);

        add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        btnMenu = Button.createLogoBtn("menu", this, "src/main/java/com/tetris/image/backLogo.png");
        btnMenu.setPreferredSize((new Dimension(60, 32)));
        btnMenu.addKeyListener(new MyKeyListener());

        bottomPanel.add(btnMenu);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        setVisible(false);
        switch (command) {
            case "easy":
            case "normal":
            case "hard":
                settingController.saveSettings("difficulty", command.equals("easy") ? "0" : command.equals("normal") ? "1" : "2");
                new GameController(isItem);
                break;
            case "menu":
                new MainMenuScreen();
                break;
        }
    }

    private void moveScreen() {
        setVisible(false);
        if (btnEasy.isFocusOwner()) {
            settingController.saveSettings("difficulty", "0");
            new GameController(isItem);
        } else if (btnNormal.isFocusOwner()) {
            settingController.saveSettings("difficulty", "1");
            new GameController(isItem);
        } else if (btnHard.isFocusOwner()) {
            settingController.saveSettings("difficulty", "2");
            new GameController(isItem);
        } else if (btnMenu.isFocusOwner()) {
            new MainMenuScreen();
        }
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_RIGHT) {
                focusRightButton();
            } else if (keyCode == KeyEvent.VK_LEFT) {
                focusLeftButton();
            } else if (keyCode == KeyEvent.VK_DOWN) {
                focusDownButton();
            } else if (keyCode == KeyEvent.VK_UP) {
                focusUpButton();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                moveScreen();
            }
        }
    }

    private void focusRightButton() {
        if (btnEasy.isFocusOwner()) {
            btnNormal.requestFocusInWindow();
        } else if (btnNormal.isFocusOwner()) {
            btnHard.requestFocusInWindow();
        }
    }

    private void focusLeftButton() {
        if (btnHard.isFocusOwner()) {
            btnNormal.requestFocusInWindow();
        } else if (btnNormal.isFocusOwner()) {
            btnEasy.requestFocusInWindow();
        }
    }

    private void focusUpButton() {
        if (btnMenu.isFocusOwner()) {
            btnNormal.requestFocusInWindow();
        }
    }

    private void focusDownButton() {
        if (btnEasy.isFocusOwner() || btnNormal.isFocusOwner() || btnHard.isFocusOwner()) {
            btnMenu.requestFocusInWindow();
        }
    }
}
