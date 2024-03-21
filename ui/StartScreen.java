import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame implements ActionListener {

    public StartScreen() {
        setTitle("Tetris");
        setSize(800, 500);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton btnGameStart = new JButton("Game Start");
        btnGameStart.setActionCommand("gameStart");
        btnGameStart.addActionListener(this);
        add(btnGameStart);

        JPanel panel = new JPanel();
        panel.add(btnGameStart);
        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("gameStart")) {
            System.out.println("Game Start!!!");
        }
    }

}
