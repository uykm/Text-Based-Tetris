package com.tetris.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeySettingModeScreenTest {

    private KeySettingModeScreen screen;
    private Robot robot;

    @BeforeEach
    void setUp() throws AWTException {
        screen = new KeySettingModeScreen();
        robot = new Robot();
        robot.setAutoDelay(100);
    }

    @Test
    // 마우스 조작
    void actionPerformedTest() {

        // Simulate clicking the "Single" button
        ActionEvent singleEvent = new ActionEvent(screen.getBtnSingle(), ActionEvent.ACTION_PERFORMED, "single");
        KeySettingScreen singleKeySettingScreen = new KeySettingScreen("single", false);
        screen.actionPerformed(singleEvent);
        assertFalse(screen.isVisible());
        assertTrue(singleKeySettingScreen.isVisible());

        // Simulate clicking the "playerA" button
        ActionEvent playerAEvent = new ActionEvent(screen.getBtnMultiA(), ActionEvent.ACTION_PERFORMED, "playerA");
        KeySettingScreen playerAKeySettingScreen = new KeySettingScreen("playerA", true);
        screen.actionPerformed(playerAEvent);
        assertFalse(screen.isVisible());
        assertTrue(playerAKeySettingScreen.isVisible());

        // Simulate clicking the "playerB" button
        ActionEvent playerBEvent = new ActionEvent(screen.getBtnMultiB(), ActionEvent.ACTION_PERFORMED, "playerB");
        KeySettingScreen playerBKeySettingScreen = new KeySettingScreen("playerB", true);
        screen.actionPerformed(playerAEvent);
        assertFalse(screen.isVisible());
        assertTrue(playerBKeySettingScreen.isVisible());

        // Simulate clicking the "Menu" button
        ActionEvent menuEvent = new ActionEvent(screen.getBtnMenu(), ActionEvent.ACTION_PERFORMED, "menu");
        SettingScreen settingScreen = new SettingScreen();
        screen.actionPerformed(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
    }

    @Test
    // 키보드 조작
    void keyListenerTest() {

        // Enter "Single" button
        KeyEvent singleEvent = new KeyEvent(screen.getBtnSingle(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        KeySettingScreen singleKeySettingScreen = new KeySettingScreen("single", false);
        screen.getBtnSingle().dispatchEvent(singleEvent);
        assertFalse(screen.isVisible());
        assertTrue(singleKeySettingScreen.isVisible());

        // Enter "playerA" button
        KeyEvent playerAEvent = new KeyEvent(screen.getBtnMultiA(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        KeySettingScreen playerAKeySettingScreen = new KeySettingScreen("playerA", true);
        screen.getBtnMultiA().dispatchEvent(playerAEvent);
        assertFalse(screen.isVisible());
        assertTrue(playerAKeySettingScreen.isVisible());

        // Enter "playerB" button
        KeyEvent playerBEvent = new KeyEvent(screen.getBtnMultiB(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        KeySettingScreen playerBKeySettingScreen = new KeySettingScreen("playerB", true);
        screen.getBtnMultiA().dispatchEvent(playerAEvent);
        assertFalse(screen.isVisible());
        assertTrue(playerBKeySettingScreen.isVisible());

        // Enter "Menu" button
        KeyEvent menuEvent = new KeyEvent(screen.getBtnMenu(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        SettingScreen settingScreen = new SettingScreen();
        screen.getBtnMenu().dispatchEvent(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
    }

    @Test
    // 포커스 이동
    void testFocusMovement() {

        // Single -> playerA
        screen.getBtnSingle().requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.getBtnMultiA().isFocusOwner());

        // playerA -> playerB
        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.getBtnMultiB().isFocusOwner());

        // playerB -> playerA
        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.getBtnMultiA().isFocusOwner());

        // playerA -> Single
        robot.setAutoDelay(100);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.getBtnSingle().isFocusOwner());
    }
}
