package component;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class ButtonTest {
    JButton btnTarget = new JButton();

    public ButtonTest() {
        btnTarget.setFocusable(true);
    }
    @Test
    void createLogoBtnNext() {
        // Define test variables
        String btnName = "Next";
        String command = "next";

        // Create a button to request focus later
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Test ActionListener
                String actionCommand = e.getActionCommand();
                if (actionCommand.equals("next")) {
                    btnTarget.requestFocusInWindow();
                    System.out.println("CLICKED");
                }
            }
        };

        String screenSize = "small";
        String iconPath = "icon_next.png";

        // Call the method to create the button
        JButton button = Button.createLogoBtnNext(btnName, command, listener, screenSize, iconPath);

        // Set button focusable to true
        button.setFocusable(true);
        button.requestFocusInWindow();
        btnTarget.setFocusable(true);

        // Simulate clicking the button
//        button.doClick();
//
//        // Check if the button gained focus
//        assertTrue(btnTarget.isFocusOwner(), "Button should have gained focus after ActionListener action performed.");
    }


    @Test
    void createLogoBtnUp() {
        // Define test variables
        String btnName = "Up";
        String command = "up";

        // Create a button to request focus later
        JButton upBtn = new JButton();

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Test ActionListener
                String actionCommand = e.getActionCommand();
                if (actionCommand.equals("up")) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            upBtn.requestFocus();
                            System.out.println("CLICKED UP");
                        }
                    });
                }
            }
        };

        String screenSize = "big"; // Assuming a big screen size for this test
        String iconPath = "icon_up.png"; // Assuming an icon path for this test

        // Call the method to create the button
        JButton button = Button.createLogoBtnUp(btnName, command, listener, screenSize, iconPath);

        // Set button focusable to true
        button.setFocusable(true);

        // Simulate clicking the button
//        button.doClick();

        // Check if the button gained focus
//        assertTrue(upBtn.isFocusOwner(), "Button should have gained focus after ActionListener action performed.");
    }


    @Test
    void createLogoBtn() {
        // Define test variables
        String command = "logo";
        String iconPath = "icon_logo.png";

        // Create a button to request focus later
        JButton logoBtn = new JButton();

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Test ActionListener
                String actionCommand = e.getActionCommand();
                if (actionCommand.equals("logo")) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            logoBtn.requestFocus();
                            System.out.println("CLICKED LOGO");
                        }
                    });
                }
            }
        };

        // Call the method to create the button
        JButton button = Button.createLogoBtn(command, listener, iconPath);

        // Set button focusable to true
        button.setFocusable(true);

        // Simulate clicking the button
//        button.doClick();

        // Check if the button gained focus
//        assertTrue(logoBtn.isFocusOwner(), "Button should have gained focus after ActionListener action performed.");
    }


    @Test
    void createBtn() {
        // Define test variables
        String btnName = "CustomButton";
        String command = "customCommand";

        // Create a button to request focus later
        JButton customBtn = new JButton();

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Test ActionListener
                String actionCommand = e.getActionCommand();
                if (actionCommand.equals("customCommand")) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            customBtn.requestFocus();
                            System.out.println("CLICKED Custom Button");
                        }
                    });
                }
            }
        };

        // Call the method to create the button
        JButton button = Button.createBtn(btnName, command, listener);

        // Set button focusable to true
        button.setFocusable(true);

        // Simulate clicking the button
//        button.doClick();

        // Check if the button gained focus
//        assertTrue(customBtn.isFocusOwner(), "Button should have gained focus after ActionListener action performed.");
    }

}
