package ui;

import logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static component.Button.createBtn;

public class KeySettingScreen extends JFrame {
    private JLabel[] labels = new JLabel[6];
    private JTextField[] textFields = new JTextField[6];
    private SettingController settingController = new SettingController();
    private final int[] keyCodes = new int[5];
    private final String[] keyShape = settingController.getKeyShape();

    private int focusedIndex = 0;

    public KeySettingScreen() {
        setTitle("Key Setting");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize labels and text fields
        for (int i = 0; i < textFields.length; i++) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            labels[i] = new JLabel();
            if (i == 0) {
                labels[i].setText("Change Shape");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 1) {
                labels[i].setText("Left Key");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 2) {
                labels[i].setText("Right Key");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 3) {
                labels[i].setText("Down Key");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 4) {
                labels[i].setText("Go down at once");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else {
                textFields[i] = new JTextField("Back");
            }
            textFields[i].setEditable(false);
            textFields[i].setBackground(Color.WHITE);
            textFields[i].setHorizontalAlignment(JTextField.CENTER);

            panel.add(labels[i]);
            panel.add(textFields[i]);

            add(panel);
        }


        add(Box.createVerticalStrut(20));

        updateFocus();

        // 키 바인딩 설정
        setFocusable(true);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upAction");
        getRootPane().getActionMap().put("upAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                focusedIndex = (focusedIndex - 1 + textFields.length) % textFields.length;
                updateFocus();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        getRootPane().getActionMap().put("downAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                focusedIndex = (focusedIndex + 1) % textFields.length;
                updateFocus();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        getRootPane().getActionMap().put("enterAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterInputMode();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateFocus() {
        for (int i = 0; i < textFields.length; i++) {
            if (i == focusedIndex) {
                textFields[i].setBackground(Color.YELLOW);
            } else {
                textFields[i].setBackground(Color.WHITE);
            }
        }
    }

    private void enterInputMode() {

        if (focusedIndex == textFields.length - 1) { // If "Back" text field is focused
            setVisible(false);
            new MainMenuScreen();
            return; // Exit the method early
        }

        JDialog inputDialog = new JDialog(this, "Input Key", true);
        inputDialog.setLayout(new FlowLayout());
        inputDialog.setSize(300, 100);

        JLabel instructionLabel = new JLabel("Press any key...");
        inputDialog.add(instructionLabel);

        inputDialog.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                String keyString = KeyEvent.getKeyText(keyCode);
                
                // Disallowing certain keys
                if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER ||
                        keyCode == KeyEvent.VK_WINDOWS || keyCode == KeyEvent.VK_CONTROL ||
                        keyCode == KeyEvent.VK_META || keyCode == KeyEvent.VK_ALT) {
                    JOptionPane.showMessageDialog(KeySettingScreen.this, "This key is not allowed to be assigned.", "Invalid Key", JOptionPane.WARNING_MESSAGE);
                    inputDialog.dispose();
                    return;
                }

                boolean keyAssigned = false;
                for (JTextField textField : textFields) {
                    if (textField.getText().equals(keyString)) {
                        keyAssigned = true;
                        break;
                    }
                }
                if (!keyAssigned) {
                    textFields[focusedIndex].setText(keyString);
                    keyCodes[focusedIndex] = keyCode;
                    keyShape[focusedIndex] = keyString;
                    settingController.setKeyCodes(keyCodes);
                    settingController.setKeyShape(keyShape);
                } else {
                    JOptionPane.showMessageDialog(KeySettingScreen.this, "This key is already assigned to another section.", "Key Already Assigned", JOptionPane.WARNING_MESSAGE);
                }
                inputDialog.dispose();
            }
        });

        inputDialog.setVisible(true);
    }
}
