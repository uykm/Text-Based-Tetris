package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static component.Button.createBtn;
import static component.ScreenSize.*;

public class StartScreen extends JFrame implements ActionListener {
    public StartScreen() {
        setTitle("Tetris");
        setWidthHeight(_width, _height, this); // Adjusted size for demonstration
        setLocationRelativeTo(null); // Centered window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Use BoxLayout with Y_AXIS alignment for vertical alignment
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton btnGameStart = createBtn("Game Start", "gameStart", this::actionPerformed);
        JButton btnOption = createBtn("Setting", "setting", this::actionPerformed);
        JButton btnScoreBoard = createBtn("Score Board", "scoreboard", this::actionPerformed);
        JButton btnExit = createBtn("Exit", "exit", this::actionPerformed);

        add(new JLabel("Tetris"));
        // Add buttons to the frame
        add(btnGameStart);
        add(btnOption);
        add(btnScoreBoard);
        add(btnExit);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("gameStart")) {
            new InGameScreen();
            setVisible(false);
        } else if (command.equals("setting")) {
            new Setting();
            setVisible(false);
        } else if (command.equals("scoreboard")) {
            new ScoreBoardUI();
            setVisible(false);
        } else if (command.equals("exit")) {
            System.exit(0);
        }
    }
}
