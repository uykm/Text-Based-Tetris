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

    private SettingScreen settingScreen;
    private SettingController settingController;

    @Test
    void testScreenSizeButtonSmall() {
        // Simulate clicking the "Small" button
        SettingScreen settingScreen = new SettingScreen();
        SettingController settingController = new SettingController();
        ActionEvent smallEvent = new ActionEvent(settingScreen.btnSize1, ActionEvent.ACTION_PERFORMED, "small");

        // Act
        settingScreen.actionPerformed(smallEvent);

        // Check if the setting controller saved the correct screen size
        assertEquals("small", settingController.getScreenSize("screenSize", "medium"));
    }

    @Test
    void testScreenSizeButtonMedium() {
        // Simulate clicking the "Medium" button
        SettingScreen settingScreen = new SettingScreen();
        SettingController settingController = new SettingController();
        ActionEvent mediumEvent = new ActionEvent(settingScreen.btnSize2, ActionEvent.ACTION_PERFORMED, "medium");

        // Act
        settingScreen.actionPerformed(mediumEvent);

        // Check if the setting controller saved the correct screen size
        assertEquals("medium", settingController.getScreenSize("screenSize", "medium"));
    }

    @Test
    void testScreenSizeButtonBig() {
        // Simulate clicking the "Big" button
        SettingScreen settingScreen = new SettingScreen();
        SettingController settingController = new SettingController();
        ActionEvent bigEvent = new ActionEvent(settingScreen.btnSize3, ActionEvent.ACTION_PERFORMED, "big");

        // Act
        settingScreen.actionPerformed(bigEvent);

        // Check if the setting controller saved the correct screen size
        assertEquals("big", settingController.getScreenSize("screenSize", "medium"));
    }

}