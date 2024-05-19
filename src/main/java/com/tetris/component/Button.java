package com.tetris.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class Button {

    static public JButton createLogoBtnNext(String btnName, String command, ActionListener listener, String screenSize, String iconPath) {
        int fontSize;
        int logoSize;

        switch (screenSize) {
            case "small":
                logoSize = 25;
                fontSize = 20;
                break;
            case "big":
                logoSize = 52;
                fontSize = 40;
                break;
            default:
                logoSize = 40;
                fontSize = 30;
                break;
        }

        JButton button = new JButton(btnName);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, fontSize));

        if (iconPath != null && !iconPath.isEmpty()) {
            URL iconUrl = Button.class.getResource(iconPath);
            if (iconUrl != null) {
                ImageIcon icon = new ImageIcon(iconUrl);
                Image image = icon.getImage();
                Image newImg = image.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                button.setIcon(icon);
                button.setHorizontalTextPosition(SwingConstants.RIGHT);
                button.setIconTextGap(5);
            } else {
                System.err.println("Icon not found: " + iconPath);
            }
        }

        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }

    static public JButton createLogoBtnUp(String btnName, String command, ActionListener listener, String screenSize, String iconPath) {
        int fontSize;
        int logoSize;

        switch (screenSize) {
            case "small":
                logoSize = 20;
                fontSize = 15;
                break;
            case "big":
                logoSize = 45;
                fontSize = 25;
                break;
            default:
                logoSize = 30;
                fontSize = 20;
                break;
        }

        JButton button = new JButton(btnName);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, fontSize));

        if (iconPath != null && !iconPath.isEmpty()) {
            URL iconUrl = Button.class.getResource(iconPath);
            if (iconUrl != null) {
                ImageIcon icon = new ImageIcon(iconUrl);
                Image image = icon.getImage();
                Image newImg = image.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                button.setIcon(icon);
                button.setHorizontalTextPosition(JButton.CENTER);
                button.setVerticalTextPosition(JButton.BOTTOM);
                button.setIconTextGap(5);
            } else {
                System.err.println("Icon not found: " + iconPath);
            }
        }

        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }

    static public JButton createLogoBtn(String command, ActionListener listener, String iconPath) {
        JButton button = new JButton();
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (iconPath != null && !iconPath.isEmpty()) {
            URL iconUrl = Button.class.getResource(iconPath);
            if (iconUrl != null) {
                ImageIcon icon = new ImageIcon(iconUrl);
                Image image = icon.getImage();
                Image newImg = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                button.setIcon(icon);
            } else {
                System.err.println("Icon not found: " + iconPath);
            }
        }

        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }

    static public JButton createBtn(String btnName, String command, ActionListener listener) {
        JButton button = new JButton(btnName);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }
}
