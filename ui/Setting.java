import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Setting extends JFrame implements ActionListener {

    static int _width = 500, _height = 500;

    public Setting() {
        setTitle("Tetris");
        setSize(_width, _height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Use BorderLayout to organize components
        setLayout(new BorderLayout());

        // Panel for settings options
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        // Panel for screen size settings
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sizePanel.add(new JLabel("Screen Size"));
        JButton size1 = createBtn("Small", "small");
        JButton size2 = createBtn("Medium", "medium");
        JButton size3 = createBtn("Big", "big");
        sizePanel.add(size1);
        sizePanel.add(size2);
        sizePanel.add(size3);

        // Panel for key mode settings
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        keyPanel.add(new JLabel("Key Mode"));
        JButton keyYes = createBtn("Yes", "keyYes");
        JButton keyNo = createBtn("No", "keyNo");
        keyPanel.add(keyYes);
        keyPanel.add(keyNo);

        // Panel for initializing score board
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scorePanel.add(new JLabel("Initialize Score Board"));
        JButton scoreYes = createBtn("Yes", "scoreYes");
        JButton scoreNo = createBtn("No", "scoreNo");
        scorePanel.add(scoreYes);
        scorePanel.add(scoreNo);

        // Panel for colorblind mode settings
        JPanel colorBlindPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        colorBlindPanel.add(new JLabel("Colorblind Mode"));
        JButton colorYes = createBtn("Yes", "colorYes");
        JButton colorNo = createBtn("No", "colorNo");
        colorBlindPanel.add(colorYes);
        colorBlindPanel.add(colorNo);

        JPanel initializePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnIntialize = createBtn("Initialize Setting", "initialize");
        initializePanel.add(btnIntialize);

        // Panel for "Back" button
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnBack = createBtn("Back", "back");
        backPanel.add(btnBack);

        // Add all panels to settingsPanel
        settingsPanel.add(sizePanel);
        settingsPanel.add(keyPanel);
        settingsPanel.add(scorePanel);
        settingsPanel.add(colorBlindPanel);
        settingsPanel.add(initializePanel);

        // Add settingsPanel and backPanel to the frame
        add(settingsPanel, BorderLayout.CENTER);
        add(backPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setWidthHeight(int width, int height) {
        _width = width;
        _height = height;
        setSize(_width, _height);
    }

    public JButton createBtn(String btnName, String command) {
        JButton button = new JButton(btnName);
        button.setActionCommand(command);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("small")) {
            setWidthHeight(400, 400);
            System.out.println("Small!!!");
        } else if (command.equals("medium")) {
            setWidthHeight(800, 800);
            System.out.println("Medium!!!");
        } else if (command.equals("big")) {
            setWidthHeight(1200, 1200);
            System.out.println("Big!!!");
        } else if (command.equals("back")) {
            // Here, you should handle what happens when the "Back" button is clicked
            // For now, let's just close the Setting window
            StartScreen startScreen = new StartScreen();
            startScreen.setWidthHeight(_width, _height);
            setVisible(false);
        } else if (command.equals("initialize")) {

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Setting::new);
    }
}
