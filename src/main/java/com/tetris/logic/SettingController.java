package com.tetris.logic;

import com.tetris.ui.KeySettingScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static com.tetris.logic.SettingProperties.COLOR_BLIND_MODE;
import static com.tetris.logic.SettingProperties.configPath;

public class SettingController {
    private final Properties properties;

    // Screen을 위한 사이즈 변수
    private String screenSize;

    public SettingController() {
        properties = new Properties();
        loadSettings();
    }

    // 스크린 크기 받아오기
    public String getScreenSize(String key, String defaultValue) {

        // screenSize=small/medium/big
        return properties.getProperty(key, defaultValue);
    }

    // 색맹모드 받아오기
    public String getColorMode(String key, String defaultValue) {

        // colorMode=one/two/three
        return properties.getProperty(key, defaultValue);
    }

    public int getCellSize(String screenSize) {
        return switch (screenSize) {
            case "small" -> 15;
            case "big" -> 35;
            default -> 25;
        };
    }

    public int getScoreSize(String screenSize) {
        return switch (screenSize) {
            case "small" -> 20;
            case "big" -> 60;
            default -> 40;
        };
    }

    public int getMessageSize(String screenSize) {
        return switch (screenSize) {
            case "small" -> 12;
            case "big" -> 22;
            default -> 17;
        };
    }

    public int getBlockSize(String screenSize) {
        return switch (screenSize) {
            case "small" -> 22;
            case "big" -> 48;
            default -> 36;
        };
    }

    public int getDifficulty() {
        return Integer.parseInt(properties.getProperty("difficulty", "1"));
    }


    // single - playerA - playerB
    public int[] getKeyCodes(String player) {
        int[] keyCodes = new int[5];
        keyCodes[0] = Integer.parseInt(properties.getProperty(player + "_key_rotate", "38")); // 기본값은 KeyEvent.VK_UP
        keyCodes[1] = Integer.parseInt(properties.getProperty(player + "_key_left", "37")); // 기본값은 KeyEvent.VK_LEFT
        keyCodes[2] = Integer.parseInt(properties.getProperty(player + "_key_right", "39")); // 기본값은 KeyEvent.VK_RIGHT
        keyCodes[3] = Integer.parseInt(properties.getProperty(player + "_key_down", "40")); // 기본값은 KeyEvent.VK_DOWN
        keyCodes[4] = Integer.parseInt(properties.getProperty(player + "_key_drop", "32")); // 기본값은 KeyEvent.VK_P
        return keyCodes;
    }

    public void setKeyCodes(int[] keyCodes, String player) {
        saveSettings(player + "_key_rotate", Integer.toString(keyCodes[0]));
        saveSettings(player + "_key_left", Integer.toString(keyCodes[1]));
        saveSettings(player + "_key_right", Integer.toString(keyCodes[2]));
        saveSettings(player + "_key_down", Integer.toString(keyCodes[3]));
        saveSettings(player + "_key_drop", Integer.toString(keyCodes[4]));
    }

    public String[] getKeyShape(String player) {
        String[] keyShape = new String[5];
        keyShape[0] = properties.getProperty(player + "_key_rotate_shape", "Up");
        keyShape[1] = properties.getProperty(player + "_key_left_shape", "Left");
        keyShape[2] = properties.getProperty(player + "_key_right_shape", "Right");
        keyShape[3] = properties.getProperty(player + "_key_down_shape", "Down");
        keyShape[4] = properties.getProperty(player + "_key_drop_shape", "Space");
        return keyShape;
    }

    public void setKeyShape(String[] keyShape, String player) {
        saveSettings(player + "_key_rotate_shape", keyShape[0]);
        saveSettings(player + "_key_left_shape", keyShape[1]);
        saveSettings(player + "_key_right_shape", keyShape[2]);
        saveSettings(player + "_key_down_shape", keyShape[3]);
        saveSettings(player + "_key_drop_shape", keyShape[4]);
    }

    public void saveSettings(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream fos = new FileOutputStream(configPath)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
        } catch (IOException e) {
            initializeSettings();
        }
    }

    // 각각의 키세팅 초기화
    public void initializeKeySettings(String player) {
        
        int[] keyCodes;
        switch (player) {
            case "single" -> {
                keyCodes = new int[]{38, 37, 39, 40, 32};
            }
            case "playerA" -> {
                keyCodes = new int[]{KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_SHIFT};
            }
            case "playerB" -> {
                keyCodes = new int[]{KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT};
            }
            default -> throw new IllegalStateException("Unexpected value: " + player);
        }
        
        String[] keyShapes = new String[5];
        for (int i = 0; i < 5; i++) {
            String keyShape = KeyEvent.getKeyText(keyCodes[i]);
            keyShapes[i] = keyShape;
        }

        setKeyCodes(keyCodes, player);
        setKeyShape(keyShapes, player);
    }

    // 모든 키세팅 초기화
    public void initializeAllKeySettings() {
        initializeKeySettings("single");
        initializeKeySettings("playerA");
        initializeKeySettings("playerB");
    }

    // 다흔 플레이어와 키가 겹치는지 확인
    public boolean isKeyAssigned(int keyCode, String player) {

        int[] assignedKeyCodes;
        if (player.equals("playerA")) {
            assignedKeyCodes = getKeyCodes("playerB");
        } else {
            assignedKeyCodes = getKeyCodes("playerA");
        }

        for (int assignedKeycode : assignedKeyCodes) {
            if (keyCode == assignedKeycode) {
                return true;
            }
        }
        return false;
    }

    public void initializeSettings() {

        saveSettings("screenSize", "medium");
        saveSettings("colorBlindMode", "default");
        saveSettings("difficulty", "1");
        initializeAllKeySettings();
    }

    public void setColorBlindMode(String mode) {
        saveSettings(COLOR_BLIND_MODE, mode);
    }
}
