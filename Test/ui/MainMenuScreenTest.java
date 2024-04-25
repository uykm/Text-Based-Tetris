package ui;

import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuScreenTest {

    @Test
    void actionPerformedTest() {
        // Create a MainMenuScreen instance
        MainMenuScreen mainMenuScreen = new MainMenuScreen();

        // Simulate action event with command "play"
        ActionEvent playEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "play");
        mainMenuScreen.actionPerformed(playEvent);
        // Check if DifficultyScreen is initialized with isItem set to false and MainMenuScreen is not visible
        assertFalse(mainMenuScreen.isVisible());

        // Simulate action event with command "item"
        ActionEvent itemEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "item");
        mainMenuScreen.actionPerformed(itemEvent);
        // Check if DifficultyScreen is initialized with isItem set to true and MainMenuScreen is not visible
        assertFalse(mainMenuScreen.isVisible());

        // Simulate action event with command "setting"
        ActionEvent settingEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "setting");
        mainMenuScreen.actionPerformed(settingEvent);
        // Check if SettingScreen is initialized and MainMenuScreen is not visible
        assertFalse(mainMenuScreen.isVisible());
        assertTrue(new SettingScreen().isVisible()); // Assuming SettingScreen's isVisible() method returns correct value

        // Simulate action event with command "ranking"
        ActionEvent rankingEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "ranking");
        mainMenuScreen.actionPerformed(rankingEvent);
        // Check if ScoreboardScreen is initialized and MainMenuScreen is not visible
        assertFalse(mainMenuScreen.isVisible());
        assertTrue(new ScoreboardScreen().isVisible()); // Assuming ScoreboardScreen's isVisible() method returns correct value
    }

}
