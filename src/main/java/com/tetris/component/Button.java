package com.tetris.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align button
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, fontSize));

        // 이미지 경로가 제공되었으면 이미지 아이콘을 버튼에 추가
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);

            // 이미지 크기 조정
            Image image = icon.getImage();
            // 예시로, 24x24 픽셀 크기로 이미지 조정
            Image newImg = image.getScaledInstance(logoSize, logoSize,  java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            button.setIcon(icon);
            // 텍스트와 아이콘 위치 조정이 필요하다면 여기에 설정
            // button.setHorizontalTextPosition(JButton.CENTER); // 텍스트를 이미지 중앙에 위치
            // button.setVerticalTextPosition(JButton.BOTTOM); // 텍스트를 이미지 아래에 위치
            button.setHorizontalTextPosition(SwingConstants.RIGHT); // 텍스트를 아이콘의 오른쪽에 나란하게 설정
            button.setIconTextGap(5); // 아이콘과 텍스트 사이의 간격 설정
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
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align button
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, fontSize));

        // 이미지 경로가 제공되었으면 이미지 아이콘을 버튼에 추가
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);

            // 이미지 크기 조정
            Image image = icon.getImage();
            Image newImg = image.getScaledInstance(logoSize, logoSize,  java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            button.setIcon(icon);

            button.setHorizontalTextPosition(JButton.CENTER); // 텍스트를 이미지 중앙에 위치
            button.setVerticalTextPosition(JButton.BOTTOM); // 텍스트를 이미지 아래에 위치

            button.setIconTextGap(5); // 아이콘과 텍스트 사이의 간격 설정
        }

        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }

    static public JButton createLogoBtn(String command, ActionListener listener, String iconPath) {
        JButton button = new JButton();
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align button

        // 이미지 경로가 제공되었으면 이미지 아이콘을 버튼에 추가
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);

            // 이미지 크기 조정
            Image image = icon.getImage();
            // 예시로, 24x24 픽셀 크기로 이미지 조정
            Image newImg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            button.setIcon(icon);
        }

        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }

    // 아이콘 경로 없이 버튼을 생성하는 메서드
    static public JButton createBtn(String btnName, String command, ActionListener listener) {
        JButton button = new JButton(btnName);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align button
        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }
}
