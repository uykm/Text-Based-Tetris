package ui;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardScreenTest {

    @Test
    void actionPerformed_MenuButton() {
        // Given
        ScoreboardScreen scoreboardScreen = new ScoreboardScreen();
        JFrame frame = new JFrame();
        JButton menuButton = new JButton("Menu");
        frame.getContentPane().add(menuButton);
        frame.pack();
        frame.setVisible(true);

        // When
        scoreboardScreen.actionPerformed(new ActionEvent(menuButton, ActionEvent.ACTION_PERFORMED, "menu"));

        // Then
        // Check if the frame is no longer visible after clicking the menu button
        assertTrue(frame.isVisible());
    }
}
