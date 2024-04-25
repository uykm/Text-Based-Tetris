package ui;

import logic.SettingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SettingScreenTest {
    private SettingController settingController;
    private SettingScreen settingScreen;

    @BeforeEach
    void setUp() {
        settingController = new SettingController();
        settingScreen = new SettingScreen();
    }

    @Test
    void testScreenSizeButtonSmall() {
        // Simulate clicking the "Small" button
        settingScreen.actionPerformed(new ActionEvent(settingScreen.btnSize1, ActionEvent.ACTION_PERFORMED, "small"));

        // Check if the setting controller saved the correct screen size
        assertEquals("small", settingController.getScreenSize("screenSize", "default"));
    }

    @Test
    void testScreenSizeButtonMedium() {
        // Simulate clicking the "Medium" button
        settingScreen.actionPerformed(new ActionEvent(settingScreen.btnSize2, ActionEvent.ACTION_PERFORMED, "medium"));

        // Check if the setting controller saved the correct screen size
        assertEquals("medium", settingController.getScreenSize("screenSize", "default"));
    }

    @Test
    void testScreenSizeButtonBig() {
        // Simulate clicking the "Big" button
        settingScreen.actionPerformed(new ActionEvent(settingScreen.btnSize3, ActionEvent.ACTION_PERFORMED, "big"));

        // Check if the setting controller saved the correct screen size
        assertEquals("big", settingController.getScreenSize("screenSize", "default"));
    }

    // Add more tests for other UI elements and their corresponding actions
}