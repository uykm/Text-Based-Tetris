package ui;

import logic.GameController;

import javax.swing.*;
import java.awt.event.*;

import static component.Button.createBtn;
import static component.ScreenSize.*;
import static java.lang.System.exit;

public class StartScreen extends JFrame implements ActionListener {

    JButton btnGameStart;
    JButton btnSetting;
    JButton btnScoreBoard;
    JButton btnExit;

    public StartScreen() {
        setTitle("Tetris");
        setWidthHeight(_width, _height, this); // Adjusted size for demonstration
        setLocationRelativeTo(null); // Centered window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Use BoxLayout with Y_AXIS alignment for vertical alignment
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        btnGameStart = createBtn("Game Start", "gameStart", this::actionPerformed);
        btnSetting = createBtn("Setting", "setting", this::actionPerformed);
        btnScoreBoard = createBtn("Score Board", "scoreboard", this::actionPerformed);
        btnExit = createBtn("Exit", "exit", this::actionPerformed);

        add(new JLabel("Tetris"));
        // Add buttons to the frame
        add(btnGameStart);
        add(btnSetting);
        add(btnScoreBoard);
        add(btnExit);

        setFocusable(true);

        setVisible(true);

        // Set initial focus to the "Game Start" button after the GUI is fully initialized
        btnGameStart.requestFocusInWindow();

        // Attach a key listener to each button
        btnGameStart.addKeyListener(new MyKeyListener());
        btnSetting.addKeyListener(new MyKeyListener());
        btnScoreBoard.addKeyListener(new MyKeyListener());
        btnExit.addKeyListener(new MyKeyListener());
    }

    // Key listener class
    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_DOWN) {
                focusNextButton();
            } else if (keyCode == KeyEvent.VK_UP) {
                focusPreviousButton();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                System.out.println("ENTER!!");
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
            new Setting();
        } else if (btnScoreBoard.isFocusOwner()) {
            new ScoreBoardUI();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("gameStart")) {
            setVisible(false);
            new GameController();
            setVisible(false);
        } else if (command.equals("setting")) {
            new Setting();
            setVisible(false);
        } else if (command.equals("scoreboard")) {
            new ScoreBoardUI();
            setVisible(false);
        } else if (command.equals("exit")) {
            exit(0);
        }
    }
}
