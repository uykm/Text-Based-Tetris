package ui;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class RegisterScoreScreenTest {

    @Test
    void actionPerformed_Submit() {
        // Given
        RegisterScoreScreen registerScoreScreen = new RegisterScoreScreen(100, false);
        registerScoreScreen.nameField = new JTextField("Test Player");

        // When
        registerScoreScreen.actionPerformed(createActionEvent("submit"));

        // Then
        // Add assertions to verify the expected behavior after submitting the score
        // For example, you can check if the RegisterScoreScreen is no longer visible
        assertFalse(registerScoreScreen.isVisible());
    }

    // Helper method to create ActionEvent for testing
    private ActionEvent createActionEvent(String command) {
        return new ActionEvent(new Object(), ActionEvent.ACTION_PERFORMED, command);
    }
}
