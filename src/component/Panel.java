package component;

import javax.swing.*;
import java.awt.*;

public class Panel {
    static public JPanel createPanel(String labelText, JButton[] buttons) {
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
}
