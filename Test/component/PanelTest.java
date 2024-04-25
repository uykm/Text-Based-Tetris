package component;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PanelTest {

    @Test
    void createPanel() {
        // Define test variables
        String labelText = "Test Panel";
        JButton[] buttons = {new JButton("Button 1"), new JButton("Button 2"), new JButton("Button 3")};
        String screenSize = "small";

        // Call the method to create the panel
        JPanel panel = Panel.createPanel(labelText, buttons, screenSize);

        // Check if panel is not null and has the expected properties
        assertNotNull(panel);

        // Check if the panel contains a label with the specified text
        Component[] components = panel.getComponents();
        boolean labelFound = false;
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().equals(labelText)) {
                    labelFound = true;
                    break;
                }
            }
        }
        assertTrue(labelFound, "Panel should contain a label with the specified text.");

        // Check if the panel contains buttons
        boolean buttonsFound = false;
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel buttonPanel = (JPanel) component;
                Component[] buttonComponents = buttonPanel.getComponents();
                for (Component buttonComponent : buttonComponents) {
                    if (buttonComponent instanceof JButton) {
                        buttonsFound = true;
                        break;
                    }
                }
                if (buttonsFound) {
                    break;
                }
            }
        }
        assertTrue(buttonsFound, "Panel should contain buttons.");
    }
}
