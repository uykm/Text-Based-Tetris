package com.tetris.ui;

import com.tetris.component.Button;
import com.tetris.logic.DualTetrisController;
import com.tetris.logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.tetris.component.ScreenSize.setWidthHeight;

public class KeySettingModeScreen extends JFrame implements ActionListener {

    private JButton btnSingle, btnMultiA, btnMultiB;
    private JButton btnMenu;

    private SettingController settingController = new SettingController();

    private final int btnSize;

    public KeySettingModeScreen() {

        setTitle("Tetris - GameMode");
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
        btnSingle = Button.createLogoBtnUp("Single", "single", this, screenSize, "/image/single.png");
        btnSingle.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnMultiA = Button.createLogoBtnUp("PlayerA", "playerA", this, screenSize, "/image/multi.png");
        btnMultiA.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnMultiB = Button.createLogoBtnUp("PlayerB", "playerB", this, screenSize, "/image/multi.png");
        btnMultiB.setPreferredSize((new Dimension(btnSize, btnSize)));

        btnSingle.addKeyListener(new MyKeyListener());
        btnMultiA.addKeyListener(new MyKeyListener());
        btnMultiB.addKeyListener(new MyKeyListener());

        topPanel.add(btnSingle);
        topPanel.add(btnMultiA);
        topPanel.add(btnMultiB);

        add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        btnMenu = Button.createLogoBtn("menu", this, "/image/backLogo.png");
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
            case "single" -> {
                new KeySettingScreen("single", false);
            }
            case "playerA" -> {
                new KeySettingScreen("playerA", true);
            }
            case "playerB" -> {
                new KeySettingScreen("playerB", true);
            }
            case "menu" -> {
                new SettingScreen();
            }
        }
    }

    private void moveScreen() {
        setVisible(false);
        if (btnSingle.isFocusOwner()) {
            new KeySettingScreen("single", false);
        } else if (btnMultiA.isFocusOwner()) {
            new KeySettingScreen("playerA", true);
        } else if (btnMultiB.isFocusOwner()) {
            new KeySettingScreen("playerB", true);
        } else if (btnMenu.isFocusOwner()) {
            new SettingScreen();
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
        if (btnSingle.isFocusOwner()) {
            btnMultiA.requestFocusInWindow();
        } else if (btnMultiA.isFocusOwner()) {
            btnMultiB.requestFocusInWindow();
        }
    }

    private void focusLeftButton() {
        if (btnMultiB.isFocusOwner()) {
            btnMultiA.requestFocusInWindow();
        } else if (btnMultiA.isFocusOwner()) {
            btnSingle.requestFocusInWindow();
        }
    }

    private void focusUpButton() {
        if (btnMenu.isFocusOwner()) {
            btnMultiA.requestFocusInWindow();
        }
    }

    private void focusDownButton() {
        if (btnSingle.isFocusOwner() || btnMultiA.isFocusOwner() || btnMultiB.isFocusOwner()) {
            btnMenu.requestFocusInWindow();
        }
    }

    // 테스트 코드를 위한 GETTER
    public JButton getBtnSingle() {
        return btnSingle;
    }
    public JButton getBtnMultiA() {
        return btnMultiA;
    }
    public JButton getBtnMultiB() {
        return btnMultiB;
    }
    public JButton getBtnMenu() {
        return btnMenu;
    }
}
