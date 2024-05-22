package com.tetris.ui;

import com.tetris.logic.DualTetrisController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WinnerScreenTest {

    private WinnerScreen screen;
    private DualTetrisController dualTetrisController;
    private Robot robot;

    @BeforeEach
    void setUp() throws AWTException {
        screen = new WinnerScreen("playerA", 777, 77, false, false);
        screen.setVisible(true);
        robot = new Robot();
        robot.setAutoDelay(50);
    }

    @Test
    void testKeyListener() {

        screen.getBtnMenu().requestFocusInWindow();
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // TODO : 체크하기
    }
}