package ui;

import logic.ScoreController;
import logic.SettingController;

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
    ScoreController scoreController = new ScoreController();
    SettingController settingController = new SettingController();

    public Setting() {
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

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        btnSize1 = createBtn("Small", "small", this::actionPerformed);
        btnSize2 = createBtn("Medium", "medium", this::actionPerformed);
        btnSize3 = createBtn("Big", "big", this::actionPerformed);
        btnSize1.addKeyListener(new MyKeyListener());
        btnSize2.addKeyListener(new MyKeyListener());
        btnSize3.addKeyListener(new MyKeyListener());
        settingsPanel.add(createOptionPanel("Screen Size", new JButton[]{btnSize1, btnSize2, btnSize3}));

        btnInitializeScore = createBtn("Yes", "scoreYes", this::actionPerformed);
        btnInitializeScore.addKeyListener(new MyKeyListener());
        settingsPanel.add(createOptionPanel("Initialize Score Board", new JButton[]{btnInitializeScore}));

        btnColorBlind1 = createBtn("Colorblind1", "colorBlind1", this::actionPerformed);
        btnColorBlind2 = createBtn("Colorblind2", "colorBlind2", this::actionPerformed);
        btnColorBlind3 = createBtn("Colorblind3", "colorBlind3", this::actionPerformed);
        btnColorBlind1.addKeyListener(new MyKeyListener());
        btnColorBlind2.addKeyListener(new MyKeyListener());
        btnColorBlind3.addKeyListener(new MyKeyListener());
        settingsPanel.add(createOptionPanel("Colorblind Mode", new JButton[]{btnColorBlind1, btnColorBlind2, btnColorBlind3}));

        btnInitializeSetting = createBtn("Initialize Setting", "initialize", this::actionPerformed);
        btnInitializeSetting.addKeyListener(new MyKeyListener());
        settingsPanel.add(createOptionPanel("Initialize Setting", new JButton[]{btnInitializeSetting}));

        btnBack = createBtn("Back", "back", this::actionPerformed);
        btnBack.addKeyListener(new MyKeyListener());
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.add(btnBack);

        add(settingsPanel, BorderLayout.CENTER);
        add(backPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 각 Setting 옵션들에 대한 JPanel 만들기 : 제목 + 버튼
    private JPanel createOptionPanel(String labelText, JButton[] buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        panel.add(label);
        panel.add(buttonPanel);
        return panel;
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
            settingController.saveSettings("screenSize", "small");
        } else if (btnSize2.isFocusOwner()) {
            setWidthHeight(600, 750, this);
            settingController.saveSettings("screenSize", "medium");
        } else if (btnSize3.isFocusOwner()) {
            setWidthHeight(800, 950, this);
            settingController.saveSettings("screenSize", "big");
        } else if (btnInitializeScore.isFocusOwner()) {
            scoreController.resetScores();
        } else if (btnInitializeSetting.isFocusOwner()) {
            setWidthHeight(400, 550, this);
            settingController.saveSettings("screenSize", "small");
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
            settingController.saveSettings("screenSize", "small");
        } else if (command.equals("medium")) {
            setWidthHeight(600, 750, this);
            settingController.saveSettings("screenSize", "medium");
        } else if (command.equals("big")) {
            setWidthHeight(800, 950, this);
            settingController.saveSettings("screenSize", "big");
        } else if (command.equals("back")) {
            StartScreen startScreen = new StartScreen();
            setVisible(false);
        } else if (command.equals("scoreYes")) {
            scoreController.resetScores();
        } else if (command.equals("initialize")) {
            settingController.initializeSettings();
            setWidthHeight(400, 550, this);
            settingController.saveSettings("screenSize", "small");
        } else if (command.equals("colorBlind1")) {

        } else if (command.equals("colorBlind2")) {

        } else if (command.equals("colorBlind3")) {

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Setting::new);
    }
}