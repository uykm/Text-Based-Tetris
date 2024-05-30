package com.tetris.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SettingControllerTest {
    private SettingController settingController;
    private Properties originalSettings;

    @BeforeEach
    void setUp() {
        settingController = new SettingController();

        // 테스트 전에 현재 설정 값을 저장
        originalSettings = new Properties();
        try (FileInputStream fis = new FileInputStream(SettingProperties.configPath)) {
            originalSettings.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 기본 설정으로 초기화
        settingController.initializeSettings();
    }

    @AfterEach
    void tearDown() {
        // 테스트 후에 원래 설정 값을 복원
        try (FileOutputStream fos = new FileOutputStream(SettingProperties.configPath)) {
            originalSettings.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getScreenSize() {
        String screenSize = settingController.getScreenSize("screenSize", "medium");
        assertEquals("medium", screenSize, "Default screen size should be 'medium'.");
    }

    @Test
    void getColorMode() {
        String colorMode = settingController.getColorMode("colorBlindMode", "default");
        assertEquals("default", colorMode, "Default color mode should be 'default'.");
    }

    @Test
    void getCellSize_small() {
        int cellSize = settingController.getCellSize("small");
        assertEquals(15, cellSize, "Cell size for 'small' should be 15.");
    }

    @Test
    void getCellSize_big() {
        int cellSize = settingController.getCellSize("big");
        assertEquals(27, cellSize, "Cell size for 'big' should be 27.");
    }

    @Test
    void getCellSize_medium() {
        int cellSize = settingController.getCellSize("medium");
        assertEquals(20, cellSize, "Cell size for 'medium' should be 20.");
    }

    @Test
    void getScoreSize_small() {
        int scoreSize = settingController.getScoreSize("small");
        assertEquals(20, scoreSize, "Score size for 'small' should be 20.");
    }

    @Test
    void getScoreSize_big() {
        int scoreSize = settingController.getScoreSize("big");
        assertEquals(45, scoreSize, "Score size for 'big' should be 45.");
    }

    @Test
    void getScoreSize_medium() {
        int scoreSize = settingController.getScoreSize("medium");
        assertEquals(35, scoreSize, "Score size for 'medium' should be 35.");
    }

    @Test
    void getMessageSize_small() {
        int messageSize = settingController.getMessageSize("small");
        assertEquals(12, messageSize, "Message size for 'small' should be 12.");
    }

    @Test
    void getMessageSize_big() {
        int messageSize = settingController.getMessageSize("big");
        assertEquals(22, messageSize, "Message size for 'big' should be 22.");
    }

    @Test
    void getMessageSize_medium() {
        int messageSize = settingController.getMessageSize("medium");
        assertEquals(12, messageSize, "Message size for 'medium' should be 12.");
    }

    @Test
    void getBlockSize_small() {
        int blockSize = settingController.getBlockSize("small");
        assertEquals(20, blockSize, "Block size for 'small' should be 20.");
    }

    @Test
    void getBlockSize_big() {
        int blockSize = settingController.getBlockSize("big");
        assertEquals(34, blockSize, "Block size for 'big' should be 34.");
    }

    @Test
    void getBlockSize_medium() {
        int blockSize = settingController.getBlockSize("medium");
        assertEquals(26, blockSize, "Block size for 'medium' should be 26.");
    }

    @Test
    void getDifficulty() {
        int difficulty = settingController.getDifficulty();
        assertEquals(1, difficulty, "Default difficulty should be 1.");
    }

    @Test
    void getKeyCodes_single() {
        int[] keyCodes = settingController.getKeyCodes("single");
        assertEquals(KeyEvent.VK_UP, keyCodes[0], "Default rotate key for single player should be KeyEvent.VK_UP.");
        assertEquals(KeyEvent.VK_LEFT, keyCodes[1], "Default left key for single player should be KeyEvent.VK_LEFT.");
        assertEquals(KeyEvent.VK_RIGHT, keyCodes[2], "Default right key for single player should be KeyEvent.VK_RIGHT.");
        assertEquals(KeyEvent.VK_DOWN, keyCodes[3], "Default down key for single player should be KeyEvent.VK_DOWN.");
        assertEquals(KeyEvent.VK_SPACE, keyCodes[4], "Default drop key for single player should be KeyEvent.VK_SPACE.");
    }

    @Test
    void setKeyCodes_single() {
        int[] newKeyCodes = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_SHIFT};
        settingController.setKeyCodes(newKeyCodes, "single");

        int[] keyCodes = settingController.getKeyCodes("single");
        assertArrayEquals(newKeyCodes, keyCodes, "Key codes should match the set values for single player.");
    }

    @Test
    void getKeyShape_single() {
        String[] keyShapes = settingController.getKeyShape("single");
        // assertEquals("Up", keyShapes[0], "Default rotate shape for single player should be 'Up'.");
    }

    @Test
    void setKeyShape_single() {
        String[] newKeyShapes = {"W", "A", "D", "S", "Shift"};
        settingController.setKeyShape(newKeyShapes, "single");

        String[] keyShapes = settingController.getKeyShape("single");
        assertArrayEquals(newKeyShapes, keyShapes, "Key shapes should match the set values for single player.");
    }

    @Test
    void saveSettings() {
        settingController.saveSettings("testKey", "testValue");
        String savedValue = settingController.getScreenSize("testKey", "default");
        assertEquals("testValue", savedValue, "The saved value should be 'testValue'.");
    }

    @Test
    void initializeKeySettings_single() {
        settingController.initializeKeySettings("single");
        int[] keyCodes = settingController.getKeyCodes("single");

        assertEquals(KeyEvent.VK_UP, keyCodes[0], "Rotate key after initialization for single player should be KeyEvent.VK_UP.");
    }

    @Test
    void initializeSettings() {
        settingController.initializeSettings();
        String screenSize = settingController.getScreenSize("screenSize", "default");
        assertEquals("medium", screenSize, "After initialization, screen size should be 'medium'.");
    }

    @Test
    void setColorBlindMode() {
        settingController.setColorBlindMode("modeTwo");
        String colorMode = settingController.getColorMode("colorBlindMode", "default");
        assertEquals("modeTwo", colorMode, "Color blind mode should be set to 'modeTwo'.");
    }
}
