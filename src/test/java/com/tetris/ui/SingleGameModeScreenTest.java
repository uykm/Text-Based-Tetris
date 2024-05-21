package com.tetris.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingleGameModeScreenTest {

    private SingleGameModeScreen screen;
    private DifficultyScreen difficultyScreen;
    private Robot robot;

    @BeforeEach
    void setUp() throws AWTException {
        screen = new SingleGameModeScreen();
        robot = new Robot();
        robot.setAutoDelay(50);
    }

    @Test
    // 마우스 조작
    void actionPerformed() {

        // Click Normal
        ActionEvent normalEvent = new ActionEvent(screen.getBtnNormal(), ActionEvent.ACTION_PERFORMED, "normal");
        difficultyScreen = new DifficultyScreen(false);
        screen.actionPerformed(normalEvent);
        assertFalse(screen.isVisible());
        assertTrue(difficultyScreen.isVisible());

        // Click Item
        ActionEvent itemEvent = new ActionEvent(screen.getBtnItem(), ActionEvent.ACTION_PERFORMED, "item");
        difficultyScreen = new DifficultyScreen(true);
        screen.actionPerformed(itemEvent);
        assertFalse(screen.isVisible());
        assertTrue(difficultyScreen.isVisible());

        // Click Menu
        ActionEvent menuEvent = new ActionEvent(screen.getBtnMenu(), ActionEvent.ACTION_PERFORMED, "menu");
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        screen.actionPerformed(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(mainMenuScreen.isVisible());
    }

    @Test
    // 키보드 조작
    void keyListenerTest() {

        // Enter "Normal" button
        KeyEvent normalEvent = new KeyEvent(screen.getBtnNormal(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        difficultyScreen = new DifficultyScreen(false);
        screen.getBtnNormal().dispatchEvent(normalEvent);
        assertFalse(screen.isVisible());
        assertTrue(difficultyScreen.isVisible());

        // Enter "Item" button
        KeyEvent itemEvent = new KeyEvent(screen.getBtnItem(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        difficultyScreen = new DifficultyScreen(true);
        screen.getBtnItem().dispatchEvent(itemEvent);
        assertFalse(screen.isVisible());
        assertTrue(difficultyScreen.isVisible());

        // Enter "Menu" Button
        KeyEvent menuEvent = new KeyEvent(screen.getBtnMenu(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        screen.getBtnMenu().dispatchEvent(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(mainMenuScreen.isVisible());
    }
}
