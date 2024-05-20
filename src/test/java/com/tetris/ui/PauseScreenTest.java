package com.tetris.ui;

import com.tetris.logic.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PauseScreenTest {

    private PauseScreen screen;
    private GameController gameController;
    private Robot robot;
    private boolean gameResumed;
    private boolean frameHidden;

    @BeforeEach
    void setUp() throws AWTException {
        gameController = new GameController(false, false, false);
        screen = new PauseScreen(false, false, false);
        screen.setVisible(true);
        robot = new Robot();
        robot.setAutoDelay(100);
    }

    @Test
    void testKeyListener() {

        KeyEvent backEvent = new KeyEvent(screen.btnBack, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.btnBack.dispatchEvent(backEvent);
        assertFalse(screen.isVisible());
    }

    @Test
    void testFocusMovement() throws  InterruptedException {
        screen.btnBack.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.btnReplay.isFocusOwner(), "Focus should move to the Replay button");

        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.btnMainMenu.isFocusOwner(), "Focus should move to the Menu button");

        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.btnQuit.isFocusOwner(), "Focus should move to the Quit button");

        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.btnMainMenu.isFocusOwner(), "Focus should move to the Menu button");

        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.btnReplay.isFocusOwner(), "Focus should move to the Replay button");

        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.btnBack.isFocusOwner(), "Focus should move to the Back to the Game button");
    }
}
