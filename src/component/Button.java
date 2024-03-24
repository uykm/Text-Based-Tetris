package component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button {
    static public JButton createBtn(String btnName, String command, ActionListener listener) {
        JButton button = new JButton(btnName);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align button
        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }
}
