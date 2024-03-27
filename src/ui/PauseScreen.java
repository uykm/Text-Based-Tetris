package ui;

import logic.GameController;
import logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;

import static component.Button.createBtn;
import static component.Panel.createPanel;
import static component.ScreenSize.setWidthHeight;
import static java.lang.System.exit;

public class PauseScreen extends JFrame implements ActionListener{
    SettingController settingController = new SettingController();

    JButton btnBack = createBtn("Back", "back", this);
    JButton btnReplay = createBtn("Replay", "replay", this);
    JButton btnMainMenu = createBtn("Main Menu", "mainMenu", this);
    JButton btnSetting = createBtn("Setting", "setting", this);

    JButton btnQuit = createBtn("Quit", "quit", this);

    public PauseScreen() {
        setTitle("Tetris");
        String screenSize = settingController.getSetting("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(400, 550, this);
                break;
            case "medium":
                setWidthHeight(600, 750, this);
                break;
            case "big":
                setWidthHeight(800, 950, this);
                break;
            default:
                setWidthHeight(600, 750, this);
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
        btnSetting.addKeyListener(new MyKeyListener());
        btnQuit.addKeyListener(new MyKeyListener());

        pausePannel.add(createPanel("Pause", new JButton[]{btnBack, btnReplay, btnMainMenu, btnQuit}));
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
//        } else if (btnSetting.isFocusOwner()) {
//            btnQuit.requestFocusInWindow();
//        }
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
//        } else if (btnSetting.isFocusOwner()) {
//            btnMainMenu.requestFocusInWindow();
        } else if (btnMainMenu.isFocusOwner()) {
            btnReplay.requestFocusInWindow();
        } else if (btnReplay.isFocusOwner()) {
            btnBack.requestFocusInWindow();
        }
    }

    private PauseScreenCallback callback;

    public void setCallback(PauseScreenCallback callback) {
        this.callback = callback;
    }

    private void applyPause() {
        setVisible(false);
        if (btnBack.isFocusOwner()) {
            setVisible(false);
            callback.onResumeGame();
        } else if (btnReplay.isFocusOwner()) {
            new GameController();
            callback.onHideFrame();
        } else if (btnMainMenu.isFocusOwner()) {
            new StartScreen();
            callback.onHideFrame();
//        } else if (btnSetting.isFocusOwner()) {
//            new Setting();
        } else if (btnQuit.isFocusOwner()) {
            exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        setVisible(false);
        if (command.equals("quit")) {
            exit(0);
        } else if (command.equals("replay")) {
            new GameController();
        } else if (command.equals("setting")) {
            new Setting();
        } else if (command.equals("mainMenu")) {
            new StartScreen();
        }
    }
}
