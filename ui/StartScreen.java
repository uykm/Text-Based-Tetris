import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

public class StartScreen extends JFrame implements ActionListener {

    static int _width = 500, _height = 500;

    public void setWidthHeight(int width, int height) {
        _width = width;
        _height = height;
        setSize(width, height);
    }
    public StartScreen() {
        setTitle("Tetris");
        setSize(_width, _height); // Adjusted size for demonstration
        setLocationRelativeTo(null); // Centered window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Use BoxLayout with Y_AXIS alignment for vertical alignment
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton btnGameStart = createBtn("Game Start", "gameStart");
        JButton btnOption = createBtn("Setting", "setting");
        JButton btnScoreBoard = createBtn("Score Board", "scoreboard");
        JButton btnExit = createBtn("Exit", "exit");

        add(new JLabel("Tetris"));
        // Add buttons to the frame
        add(btnGameStart);
        add(btnOption);
        add(btnScoreBoard);
        add(btnExit);

        setVisible(true);
    }

    public JButton createBtn(String btnName, String command) {
        JButton button = new JButton(btnName);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align button
        button.setActionCommand(command);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("gameStart")) {
            System.out.println("Game Start!!!");
        } else if (command.equals("setting")) {
            new Setting();
            setVisible(false);
        } else if (command.equals("scoreboard")) {
            System.out.println("Score Board!!!");
        } else if (command.equals("exit")) {
            System.exit(0);
        }
    }
}
