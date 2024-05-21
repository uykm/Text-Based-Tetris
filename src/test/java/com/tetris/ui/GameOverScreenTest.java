package com.tetris.ui;

import com.tetris.logic.SettingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameOverScreenTest {

    private GameOverScreen screen;
    private SettingController settingController;
    private Robot robot;

    @BeforeEach
    void setUp() throws AWTException{
        settingController = new SettingController();
        robot = new Robot();
        robot.setAutoDelay(50);
    }

    @Test
    void actionPerformedTest_normal() {

        // Create a GameOverScreen instance
        screen = new GameOverScreen(100, false);

        // Simulate action event with command "menu"
        ActionEvent menuEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "menu");
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        screen.actionPerformed(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(mainMenuScreen.isVisible());
        // assertTrue(MainMenuScreen.class.isInstance(gameOverScreen.getFocusOwner()));

        // Simulate action event with command "replay"
        ActionEvent replayEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "replay");
        screen.actionPerformed(replayEvent);
        assertFalse(screen.isVisible());
    }

    @Test
    void actionPerformedTest_item() {

        // Create a GameOverScreen instance
        screen = new GameOverScreen(100, true);

        // Simulate action event with command "menu"
        ActionEvent menuEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "menu");
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        screen.actionPerformed(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(mainMenuScreen.isVisible());
        // assertTrue(MainMenuScreen.class.isInstance(gameOverScreen.getFocusOwner()));

        // Simulate action event with command "replay"
        ActionEvent replayEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "replay");
        screen.actionPerformed(replayEvent);
        assertFalse(screen.isVisible());
    }

    @Test
    void keyListenerTest_normal() {

        // Create a GameOverScreen instance
        screen = new GameOverScreen(100, false);

        KeyEvent menuEvent = new KeyEvent(screen.getBtnMenu(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        screen.getBtnMenu().dispatchEvent(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(mainMenuScreen.isVisible());

        KeyEvent replayEvent = new KeyEvent(screen.getBtnReplay(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.getBtnReplay().dispatchEvent(replayEvent);
        assertFalse(screen.isVisible());
    }

    @Test
    void keyListenerTest_item() {

        // Create a GameOverScreen instance
        screen = new GameOverScreen(100, true);

        KeyEvent menuEvent = new KeyEvent(screen.getBtnMenu(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        screen.getBtnMenu().dispatchEvent(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(mainMenuScreen.isVisible());

        KeyEvent replayEvent = new KeyEvent(screen.getBtnReplay(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.getBtnReplay().dispatchEvent(replayEvent);
        assertFalse(screen.isVisible());
    }
}
