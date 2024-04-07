package component;

import logic.Score;
import logic.ScoreController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Panel {

    static public JPanel createPanel(String labelText, JButton[] buttons, String screenSize) {
        int titleSize = switch (screenSize) {
            case "small" -> 20;
            case "big" -> 40;
            default -> 30;
        };

        int fontSize = switch (screenSize) {
            case "small" -> 15;
            case "big" -> 35;
            default -> 25;
        };

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, titleSize));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (JButton button : buttons) {
            button.setFont(new Font(button.getFont().getName(), Font.PLAIN, fontSize));
            buttonPanel.add(button);
        }
        panel.add(label);
        panel.add(buttonPanel);
        return panel;
    }
}