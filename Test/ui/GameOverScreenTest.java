package ui;

import logic.GameController;
import logic.SettingController;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class GameOverScreenTest {

    @Test
    void actionPerformedTest() {
        // Create a GameOverScreen instance
        GameOverScreen gameOverScreen = new GameOverScreen(100, false);
        SettingController settingController = new SettingController(); // Initialize settingController

        // Simulate action event with command "menu"
        ActionEvent menuEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "menu");
        gameOverScreen.actionPerformed(menuEvent);
        // Check if GameOverScreen is not visible
        assertFalse(gameOverScreen.isShowing());
        // Check if MainMenuScreen is properly initialized
//        assertTrue(MainMenuScreen.class.isInstance(gameOverScreen.getFocusOwner()));

        // Simulate action event with command "replay"
        ActionEvent replayEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "replay");
        gameOverScreen.actionPerformed(replayEvent);
        // Check if GameController is properly initialized
//        assertNotNull(gameOverScreen.getFocusOwner());

        // Simulate action event with command "exit"
        ActionEvent exitEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "exit");
        gameOverScreen.actionPerformed(exitEvent);
        // Check if GameOverScreen is not visible
        assertFalse(gameOverScreen.isShowing());
    }

}
