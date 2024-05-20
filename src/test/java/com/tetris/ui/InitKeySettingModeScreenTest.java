package com.tetris.ui;

import com.tetris.logic.SettingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class InitKeySettingModeScreenTest {

    private InitKeySettingModeScreen screen;
    private SettingScreen settingScreen;
    private SettingController settingController;
    private Robot robot;

    @BeforeEach
    void setUp() throws AWTException {
        screen = new InitKeySettingModeScreen();
        settingScreen = new SettingScreen();
        settingController = new SettingController();
        robot = new Robot();
        robot.setAutoDelay(50);
    }

    @Test
    // 마우스 조작
    void actionPerformed() {

        // Click Single
        ActionEvent singleEvent = new ActionEvent(screen.getBtnSingle(), ActionEvent.ACTION_PERFORMED, "single");
        screen.actionPerformed(singleEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
        assertArrayEquals(new int[]{38, 37, 39, 40, 32}, settingController.getKeyCodes("single"), "Key codes should be reset to defaults");

        // Click playerA
        ActionEvent playerAEvent = new ActionEvent(screen.getBtnMultiA(), ActionEvent.ACTION_PERFORMED, "playerA");
        screen.actionPerformed(playerAEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
        assertArrayEquals(new int[]{KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_SHIFT}, settingController.getKeyCodes("playerA"), "Key codes should be reset to playerA");

        // Click playerB
        ActionEvent playerBEvent = new ActionEvent(screen.getBtnMultiB(), ActionEvent.ACTION_PERFORMED, "playerB");
        screen.actionPerformed(playerBEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
        assertArrayEquals(new int[]{KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT}, settingController.getKeyCodes("playerB"), "Key codes should be reset to playerB");

        // Click Menu
        ActionEvent menuEvent = new ActionEvent(screen.getBtnMultiB(), ActionEvent.ACTION_PERFORMED, "playerB");
        screen.actionPerformed(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
    }

    @Test
    // 키보드 조작
    void keyListenerTest() {

        // Enter "Single" button
        KeyEvent singleEvent = new KeyEvent(screen.getBtnSingle(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.getBtnSingle().dispatchEvent(singleEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
        assertArrayEquals(new int[]{38, 37, 39, 40, 32}, settingController.getKeyCodes("single"), "Key codes should be reset to defaults");

        // Enter "playerA" button
        KeyEvent playerAEvent = new KeyEvent(screen.getBtnMultiA(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.getBtnMultiA().dispatchEvent(playerAEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
        assertArrayEquals(new int[]{KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_SHIFT}, settingController.getKeyCodes("playerA"), "Key codes should be reset to playerA");

        // Enter "playerB" button
        KeyEvent playerBEvent = new KeyEvent(screen.getBtnMultiB(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.getBtnMultiA().dispatchEvent(playerAEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
        assertArrayEquals(new int[]{KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT}, settingController.getKeyCodes("playerB"), "Key codes should be reset to playerB");

        // Enter "Menu" Button
        KeyEvent menuEvent = new KeyEvent(screen.getBtnMenu(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.getBtnMenu().dispatchEvent(menuEvent);
        assertFalse(screen.isVisible());
        assertTrue(settingScreen.isVisible());
    }
}
