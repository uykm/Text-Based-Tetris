package com.tetris.ui;

import com.tetris.logic.DualTetrisController;
import com.tetris.logic.GameController;
import com.tetris.logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.tetris.component.Button.createBtn;
import static com.tetris.component.Panel.createPanel;
import static com.tetris.component.ScreenSize.setWidthHeight;
import static java.lang.System.exit;

public class PauseScreen extends JFrame implements ActionListener{
    SettingController settingController = new SettingController();
    String screenSize;
    JButton btnBack = createBtn("Back", "back", this);
    JButton btnReplay = createBtn("Replay", "replay", this);
    JButton btnMainMenu = createBtn("Menu", "mainMenu", this);
    JButton btnQuit = createBtn("Quit", "quit", this);
    private boolean isItemMode;

    private boolean isDualMode;

    private boolean isTimeAttckMode;

    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


    public PauseScreen(boolean isItemMode, boolean isDualMode, boolean isTimeAttackMode) {

        // 노말모드 vs 아이템 모드
        this.isItemMode = isItemMode;
        this.isDualMode = isDualMode;
        this.isTimeAttckMode = isTimeAttackMode;

        screenSize = settingController.getScreenSize("screenSize", "small");

        setTitle("Tetris");
        String screenSize = settingController.getScreenSize("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(390, 420, this);
                break;
            case "big":
                setWidthHeight(910, 940, this);
                break;
            default:
                setWidthHeight(650, 680, this);
                break;
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel pausePannel = new JPanel();
        pausePannel.setLayout(new BoxLayout(pausePannel, BoxLayout.Y_AXIS));

        btnBack.requestFocusInWindow();

        btnBack.addKeyListener(new MyKeyListener());
        btnReplay.addKeyListener(new MyKeyListener());
        btnMainMenu.addKeyListener(new MyKeyListener());
        btnQuit.addKeyListener(new MyKeyListener());

        pausePannel.add(createPanel("Pause", new JButton[]{btnBack, btnReplay, btnMainMenu, btnQuit}, screenSize));
        add(pausePannel);

        setVisible(true);
    }

    private class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_RIGHT) {
                focusRightButton();
            } else if (keyCode == KeyEvent.VK_LEFT) {
                focusLeftButton();
            }  else if (keyCode == KeyEvent.VK_ENTER) {
                applyPause();
            }
        }
    }

    private void focusRightButton() {
        if (btnBack.isFocusOwner()) {
            btnReplay.requestFocusInWindow();
        } else if (btnReplay.isFocusOwner()) {
            btnMainMenu.requestFocusInWindow();
        } else if (btnMainMenu.isFocusOwner()) {
            btnQuit.requestFocusInWindow();
        }
        else if (btnQuit.isFocusOwner()) {
            btnBack.requestFocusInWindow();
        }
    }

    private void focusLeftButton() {
        if (btnBack.isFocusOwner()) {
            btnQuit.requestFocusInWindow();
        } else if (btnQuit.isFocusOwner()) {
            btnMainMenu.requestFocusInWindow();
        } else if (btnMainMenu.isFocusOwner()) {
            btnReplay.requestFocusInWindow();
        } else if (btnReplay.isFocusOwner()) {
            btnBack.requestFocusInWindow();
        }
    }

    private void applyPause() {
        setVisible(false);
        if (btnBack.isFocusOwner()) {
            gameController.controlGame("RESUME");
            if (isDualMode) {
                gameController.getOpponent().controlGame("RESUME");
            }
        } else if (btnReplay.isFocusOwner()) {
            gameController.controlGame("REPLAY");
        } else if (btnMainMenu.isFocusOwner()) {
            gameController.controlGame("MENU");
        } else if (btnQuit.isFocusOwner()) {
            exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        setVisible(false);
        if (command.equals("back")) {
            if (isDualMode) {
                gameController.controlGame("RESUME");
                gameController.getOpponent().controlGame("RESUME");
            }
        } else if (command.equals("replay")) {
            if (isDualMode) {
                gameController.controlGame("REPLAY");
            } else {
                new GameController(isItemMode);
            }
        } else if (command.equals("mainMenu")) {
            if (isDualMode) {
                gameController.controlGame("MENU");
            } else {
                new MainMenuScreen();
            }
        } else if (command.equals("quit")) {
            exit(0);
        }
    }
}
