package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static component.Button.createBtn;
import static component.ScreenSize.*;

public class
Setting extends JFrame implements ActionListener {
    JButton btnSize1, btnSize2, btnSize3;
    JButton btnInitializeScore;
    JButton btnColorBlind1, btnColorBlind2, btnColorBlind3;
    JButton btnInitializeSetting;
    JButton btnBack;
    public Setting() {
        setTitle("Tetris");
        setWidthHeight(_width, _height, this);
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
        btnSize1 = createBtn("Small", "small", this::actionPerformed);
        btnSize2 = createBtn("Medium", "medium", this::actionPerformed);
        btnSize3 = createBtn("Big", "big", this::actionPerformed);
        btnSize1.addKeyListener(new MyKeyListener());
        btnSize2.addKeyListener(new MyKeyListener());
        btnSize3.addKeyListener(new MyKeyListener());
        sizePanel.add(btnSize1);
        sizePanel.add(btnSize2);
        sizePanel.add(btnSize3);


        // Panel for initializing score board
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scorePanel.add(new JLabel("Initialize Score Board"));
        btnInitializeScore = createBtn("Yes", "scoreYes", this::actionPerformed);
        btnInitializeScore.addKeyListener(new MyKeyListener());
        scorePanel.add(btnInitializeScore);

        // Panel for colorblind mode settings
        JPanel colorBlindPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        colorBlindPanel.add(new JLabel("Colorblind Mode"));
        btnColorBlind3 = createBtn("Colorblind3", "colorBLind3", this::actionPerformed);
        btnColorBlind2 = createBtn("Colorblind2", "colorBLind2", this::actionPerformed);
        btnColorBlind1 = createBtn("Colorblind1", "colorBLind1", this::actionPerformed);
        btnColorBlind1.addKeyListener(new MyKeyListener());
        btnColorBlind2.addKeyListener(new MyKeyListener());
        btnColorBlind3.addKeyListener(new MyKeyListener());
        colorBlindPanel.add(btnColorBlind1);
        colorBlindPanel.add(btnColorBlind2);
        colorBlindPanel.add(btnColorBlind3);

        JPanel initializePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnInitializeSetting = createBtn("Initialize Setting", "initialize", this::actionPerformed);
        btnInitializeSetting.addKeyListener(new MyKeyListener());
        initializePanel.add(btnInitializeSetting);

        // Panel for "Back" button
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnBack = createBtn("Back", "back", this::actionPerformed);
        btnBack.addKeyListener(new MyKeyListener());
        backPanel.add(btnBack);

        // Add all panels to settingsPanel
        settingsPanel.add(sizePanel);
        settingsPanel.add(scorePanel);
        settingsPanel.add(colorBlindPanel);
        settingsPanel.add(initializePanel);

        // Add settingsPanel and backPanel to the frame
        add(settingsPanel, BorderLayout.CENTER);
        add(backPanel, BorderLayout.SOUTH);

        setVisible(true);
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
                applySetting();
            }
        }
    }

    private void applySetting() {
        if (btnSize1.isFocusOwner()) {
            setWidthHeight(400, 550, this);
        } else if (btnSize2.isFocusOwner()) {
            setWidthHeight(600, 750, this);
        } else if (btnSize3.isFocusOwner()) {
            setWidthHeight(800, 950, this);
        } else if (btnInitializeScore.isFocusOwner()) {

        } else if (btnColorBlind1.isFocusOwner()) {

        } else if (btnColorBlind2.isFocusOwner()) {

        } else if (btnColorBlind3.isFocusOwner()) {

        } else if (btnInitializeSetting.isFocusOwner()) {

        } else if (btnBack.isFocusOwner()) {
            setVisible(false);
            new StartScreen();
        }
    }

    private void focusDownButton() {
        if (btnSize1.isFocusOwner() || btnSize2.isFocusOwner() || btnSize3.isFocusOwner()) {
            btnInitializeScore.requestFocusInWindow();
        } else if (btnInitializeScore.isFocusOwner()) {
            btnColorBlind2.requestFocusInWindow();
        } else if (btnColorBlind1.isFocusOwner() || btnColorBlind2.isFocusOwner() || btnColorBlind3.isFocusOwner()) {
            btnInitializeSetting.requestFocusInWindow();
        } else if (btnInitializeSetting.isFocusOwner()) {
            btnBack.requestFocusInWindow();
        }
    }

    private void focusUpButton() {
        if (btnBack.isFocusOwner()) {
            btnInitializeSetting.requestFocusInWindow();
        } else if (btnInitializeSetting.isFocusOwner()) {
            btnColorBlind2.requestFocusInWindow();
        } else if (btnColorBlind1.isFocusOwner() || btnColorBlind2.isFocusOwner() || btnColorBlind3.isFocusOwner()) {
            btnInitializeScore.requestFocusInWindow();
        } else if (btnInitializeScore.isFocusOwner()) {
            btnSize2.requestFocusInWindow();
        }
    }



    private void focusRightButton() {
        if (btnSize1.isFocusOwner()) {
            btnSize2.requestFocusInWindow();
        } else if (btnSize2.isFocusOwner()) {
            btnSize3.requestFocusInWindow();
        } else if (btnSize3.isFocusOwner()) {
            btnSize1.requestFocusInWindow();
        } else if (btnColorBlind1.isFocusOwner()) {
            btnColorBlind2.requestFocusInWindow();
        } else if (btnColorBlind2.isFocusOwner()) {
            btnColorBlind3.requestFocusInWindow();
        } else if (btnColorBlind3.isFocusOwner()) {
            btnColorBlind1.requestFocusInWindow();
        }
    }

    private void focusLeftButton() {
        if (btnSize2.isFocusOwner()) {
            btnSize1.requestFocusInWindow();
        } else if (btnSize3.isFocusOwner()) {
            btnSize2.requestFocusInWindow();
        } else if (btnSize1.isFocusOwner()) {
            btnSize3.requestFocusInWindow();
        } else if (btnColorBlind3.isFocusOwner()) {
            btnColorBlind2.requestFocusInWindow();
        } else if (btnColorBlind2.isFocusOwner()) {
            btnColorBlind1.requestFocusInWindow();
        } else if (btnColorBlind1.isFocusOwner()) {
            btnColorBlind3.requestFocusInWindow();
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("small")) {
            setWidthHeight(400, 550, this);
        } else if (command.equals("medium")) {
            setWidthHeight(600, 750, this);
        } else if (command.equals("big")) {
            setWidthHeight(800, 950, this);
        } else if (command.equals("back")) {
            StartScreen startScreen = new StartScreen();
            setVisible(false);
        } else if (command.equals("initialize")) {

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Setting::new);
    }
}