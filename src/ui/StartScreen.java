package ui;

import logic.GameController;
import logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.System.exit;

import static component.Button.createBtn;
import static component.ScreenSize.*;

public class StartScreen extends JFrame implements ActionListener {

    JButton btnGameStart;
    JButton btnSetting;
    JButton btnScoreBoard;
    JButton btnExit;
    SettingController settingController = new SettingController();

    public StartScreen() {
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
        setLocationRelativeTo(null); // Centered window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Use BoxLayout with Y_AXIS alignment for vertical alignment
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        btnGameStart = createBtn("Game Start", "gameStart", this::actionPerformed);
        btnSetting = createBtn("Setting", "setting", this::actionPerformed);
        btnScoreBoard = createBtn("Score Board", "scoreboard", this::actionPerformed);
        btnExit = createBtn("Exit", "exit", this::actionPerformed);

        // Create a JLabel for "Tetris" with center alignment and bold font
        JLabel gameTitle = new JLabel("Tetris", SwingConstants.CENTER);
        gameTitle.setFont(new Font(gameTitle.getFont().getName(), Font.BOLD, 20));
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(gameTitle);
        add(btnGameStart);
        add(btnSetting);
        add(btnScoreBoard);
        add(btnExit);

        setVisible(true);

        // Set initial focus to the "Game Start" button after the GUI is fully initialized
        btnGameStart.requestFocusInWindow();

        // Attach a key listener to each button
        btnGameStart.addKeyListener(new MyKeyListener());
        btnSetting.addKeyListener(new MyKeyListener());
        btnScoreBoard.addKeyListener(new MyKeyListener());
        btnExit.addKeyListener(new MyKeyListener());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("gameStart")) {
            setVisible(false);
            new GameController();
            setVisible(false);
        } else if (command.equals("setting")) {
            new SettingScreen();
            setVisible(false);
        } else if (command.equals("scoreboard")) {
            new ScoreboardScreen();
            setVisible(false);
        } else if (command.equals("exit")) {
            System.exit(0);
        }
    }

    // Key listener class
    private class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_DOWN) {
                focusNextButton();
            } else if (keyCode == KeyEvent.VK_UP) {
                focusPreviousButton();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                moveScreen();
            }

            System.out.println("KeyPressed");
        }
    }

    private void moveScreen() {
        setVisible(false);
        if (btnGameStart.isFocusOwner()) {
            new GameController();
        } else if (btnSetting.isFocusOwner()) {
            new SettingScreen();
        } else if (btnScoreBoard.isFocusOwner()) {
            new ScoreboardScreen();
        }
        else if (btnExit.isFocusOwner()) {
            exit(0);
        }
    }

    private void focusNextButton() {
        if (btnGameStart.isFocusOwner()) {
            btnSetting.requestFocusInWindow();
        } else if (btnSetting.isFocusOwner()) {
            btnScoreBoard.requestFocusInWindow();
        } else if (btnScoreBoard.isFocusOwner()) {
            btnExit.requestFocusInWindow();
        } else if (btnExit.isFocusOwner()) {
            btnGameStart.requestFocusInWindow(); // Wrap around to the first button
        }
    }

    private void focusPreviousButton() {
        if (btnGameStart.isFocusOwner()) {
            btnExit.requestFocusInWindow();
        } else if (btnSetting.isFocusOwner()) {
            btnGameStart.requestFocusInWindow();
        } else if (btnScoreBoard.isFocusOwner()) {
            btnSetting.requestFocusInWindow();
        } else if (btnExit.isFocusOwner()) {
            // Wrap around to the last button if the first button is focused
            btnScoreBoard.requestFocusInWindow();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(StartScreen::new);
    }
}


