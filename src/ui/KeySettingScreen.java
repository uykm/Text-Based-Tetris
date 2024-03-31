package ui;

import logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static component.Button.createBtn;
import static component.ScreenSize._width;
import static component.ScreenSize.setWidthHeight;

public class KeySettingScreen extends JFrame implements ActionListener {

    String toLeft, toRight, descend, changeDirection;
    JLabel lblToLeft, lblToRight, lblDescend, lblChangeDirection;
    JTextField toLeftTextField, toRightTextField, descendTextField, changeDirectionTextField;
    SettingController settingController = new SettingController();

    JButton btnInitializeKeySetting, btnBack, btnConfirm;

    public KeySettingScreen() {
        setTitle("Key Setting");

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

        lblToLeft = new JLabel("To Left");
        lblToRight = new JLabel("To Right");
        lblDescend = new JLabel("Descend");
        lblChangeDirection = new JLabel("Change Direction");

        JLabel gameTitle = new JLabel("Change Key Setting", SwingConstants.CENTER);
        gameTitle.setFont(new Font(gameTitle.getFont().getName(), Font.BOLD, 30));
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 중앙 : 이름 입력 칸 + 제출 버튼
        JPanel toLeftPanel = new JPanel();

        toLeftTextField = new JTextField(toLeft, _width - 50);
        toLeftTextField.setHorizontalAlignment(JTextField.CENTER);
        toLeftTextField.setForeground(Color.GRAY);
        toLeftPanel.add(lblToLeft);
        toLeftPanel.add(toLeftTextField);
        toLeftTextField.addKeyListener(new MyKeyListener());

        add(gameTitle);
        add(toLeftPanel);

        JPanel toRightPanel = new JPanel();

        toRightTextField = new JTextField(toRight, _width - 50);
        toRightTextField.setHorizontalAlignment(JTextField.CENTER);
        toRightTextField.setForeground(Color.GRAY);
        toRightPanel.add(lblToRight);
        toRightPanel.add(toRightTextField);
        toRightTextField.addKeyListener(new MyKeyListener());

        add(toRightPanel);

        JPanel descendPanel = new JPanel();

        descendTextField = new JTextField(descend, _width - 50);
        descendTextField.setHorizontalAlignment(JTextField.CENTER);
        descendTextField.setForeground(Color.GRAY);
        descendPanel.add(lblDescend);
        descendPanel.add(descendTextField);
        descendTextField.addKeyListener(new MyKeyListener());

        add(descendPanel);

        JPanel changeDirectionPanel = new JPanel();

        changeDirectionTextField = new JTextField(changeDirection, _width - 50);
        changeDirectionTextField.setHorizontalAlignment(JTextField.CENTER);
        changeDirectionTextField.setForeground(Color.GRAY);
        changeDirectionPanel.add(lblChangeDirection);
        changeDirectionPanel.add(changeDirectionTextField);
        changeDirectionTextField.addKeyListener(new MyKeyListener());

        add(changeDirectionPanel);

        toLeftTextField.requestFocusInWindow();


        btnConfirm = createBtn("Confirm", "confirm", this);
        btnInitializeKeySetting = createBtn("Initialize Key Setting", "initializeKeySetting", this);
        btnBack = createBtn("Back", "back", this);
        btnConfirm.addKeyListener(new MyKeyListener());
        btnInitializeKeySetting.addKeyListener(new MyKeyListener());
        btnBack.addKeyListener(new MyKeyListener());
        add(btnConfirm);
        add(btnInitializeKeySetting);
        add(btnBack);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("toLeft")) {
        } else if (command.equals("toRight")) {
        } else if (command.equals("descend")) {
        } else if (command.equals("changeDirection")) {
        }
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_DOWN) {
                focusDownButton();
            } else if (keyCode == KeyEvent.VK_UP) {
                focusUpButton();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                applySetting();
            }
        }
    }

    private void applySetting() {
        if (toLeftTextField.isFocusOwner()) {
            toLeft = toLeftTextField.getText();
        } else if (toRightTextField.isFocusOwner()) {
            toRight = toRightTextField.getText();
        } else if (descendTextField.isFocusOwner()) {
            descend = descendTextField.getText();
        } else if (changeDirectionTextField.isFocusOwner()) {
            changeDirection = changeDirectionTextField.getText();
        } else if (btnConfirm.isFocusOwner()) {

        } else if (btnInitializeKeySetting.isFocusOwner()) {

        } else if (btnBack.isFocusOwner()) {
            setVisible(false);
            new SettingScreen();
        }
    }


    private void focusDownButton() {
        if (toLeftTextField.isFocusOwner()) {
            toRightTextField.requestFocusInWindow();
        } else if (toRightTextField.isFocusOwner()) {
            descendTextField.requestFocusInWindow();
        } else if (descendTextField.isFocusOwner()) {
            changeDirectionTextField.requestFocusInWindow();
        } else if (changeDirectionTextField.isFocusOwner()) {
            btnConfirm.requestFocusInWindow();
        } else if (btnConfirm.isFocusOwner()) {
            btnInitializeKeySetting.requestFocusInWindow();
        } else if (btnInitializeKeySetting.isFocusOwner()) {
            btnBack.requestFocusInWindow();
        }
    }

    private void focusUpButton() {
        if (btnBack.isFocusOwner()) {
            btnInitializeKeySetting.requestFocusInWindow();
        } else if (btnInitializeKeySetting.isFocusOwner()) {
            btnConfirm.requestFocusInWindow();
        } else if (btnConfirm.isFocusOwner()) {
            changeDirectionTextField.requestFocusInWindow();
        } else if (changeDirectionTextField.isFocusOwner()) {
            descendTextField.requestFocusInWindow();
        } else if (descendTextField.isFocusOwner()) {
            toRightTextField.requestFocusInWindow();
        } else if (toRightTextField.isFocusOwner()) {
            toLeftTextField.requestFocusInWindow();
        }
    }
}
