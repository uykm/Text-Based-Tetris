package ui;

import jdk.jfr.Configuration;
import logic.SettingController;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyScreenTest {

    @Test
    void actionPerformedTest0() {
        // Create a DifficultyScreen instance
        DifficultyScreen difficultyScreen = new DifficultyScreen(false);
        SettingController settingController = new SettingController(); // Initialize settingController

        // Simulate action event with command "easy"
        ActionEvent easyEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "easy");
        difficultyScreen.actionPerformed(easyEvent);
        // Check if 'difficulty' setting is saved as "0" and GameController is initialized
        assertEquals(0, settingController.getDifficulty());
    }

    @Test
    void actionPerformedTest1() {
        // Create a DifficultyScreen instance
        DifficultyScreen difficultyScreen = new DifficultyScreen(false);
        SettingController settingController = new SettingController(); // Initialize settingController

        // Simulate action event with command "normal"
        ActionEvent normalEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "normal");
        difficultyScreen.actionPerformed(normalEvent);
        // Check if 'difficulty' setting is saved as "1" and GameController is initialized
        assertEquals(1, settingController.getDifficulty());
    }

    @Test
    void actionPerformedTest2() {
        // Create a DifficultyScreen instance
        DifficultyScreen difficultyScreen = new DifficultyScreen(false);
        SettingController settingController = new SettingController(); // Initialize settingController

        // Simulate action event with command "hard"
        ActionEvent hardEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "hard");
        difficultyScreen.actionPerformed(hardEvent);
        // Check if 'difficulty' setting is saved as "2" and GameController is initialized
        assertEquals(2, settingController.getDifficulty());
    }

    @Test
    void actionPerformedTest3() {
        DifficultyScreen difficultyScreen = new DifficultyScreen(false);
        SettingController settingController = new SettingController(); // Initialize settingController

        // Simulate action event with command "menu"
        ActionEvent menuEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "menu");
        difficultyScreen.actionPerformed(menuEvent);
        // Check if MainMenuScreen is initialized and DifficultyScreen is not visible
        assertFalse(difficultyScreen.isVisible());
        assertTrue(new MainMenuScreen().isVisible());

    }
}
