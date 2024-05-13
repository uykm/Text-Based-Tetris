package com.tetris.ui;

import com.tetris.logic.BoardController;
import com.tetris.logic.GameController;
import com.tetris.logic.InGameScoreController;
import com.tetris.logic.SettingController;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyScreenTest {

//    private DifficultyScreen screen;
//    private SettingController settingController;

//    @BeforeEach
//    public void setUp() {
//        SwingUtilities.invokeLater(() -> {
//            settingController = new SettingController();
//            screen = new DifficultyScreen(false);
//        });
//    }

    @Test
    void actionPerformed_easy() {
        // Arrange
        DifficultyScreen screen = new DifficultyScreen(false); // Set up with some default settings
        ActionEvent event = new ActionEvent(screen.btnEasy, ActionEvent.ACTION_PERFORMED, "easy");

        // Act
        screen.actionPerformed(event);

        // Assert
        SettingController settingController = new SettingController();
        assertEquals(0, settingController.getDifficulty());

        // Verify that the screen is not visible
        assertFalse(screen.isVisible(), "DifficultyScreen should become invisible after clicking Easy.");
    }

    @Test
    void actionPerformed_normal() {
        // Arrange
        DifficultyScreen screen = new DifficultyScreen(false);
        ActionEvent event = new ActionEvent(screen.btnNormal, ActionEvent.ACTION_PERFORMED, "normal");

        // Act
        screen.actionPerformed(event);

        // Assert
        SettingController settingController = new SettingController();
        assertEquals(1, settingController.getDifficulty());

        // Verify that the screen is not visible
        assertFalse(screen.isVisible(), "DifficultyScreen should become invisible after clicking Normal.");
    }

    @Test
    void actionPerformed_hard() {
        // Arrange
        DifficultyScreen screen = new DifficultyScreen(false);
        ActionEvent event = new ActionEvent(screen.btnHard, ActionEvent.ACTION_PERFORMED, "hard");

        // Act
        screen.actionPerformed(event);

        // Assert
        SettingController settingController = new SettingController();
        assertEquals(2, settingController.getDifficulty());

        // Verify that the screen is not visible
        assertFalse(screen.isVisible(), "DifficultyScreen should become invisible after clicking Hard.");
    }

    @Test
    void actionPerformed_menu() {
        // Arrange
        DifficultyScreen screen = new DifficultyScreen(false);
        ActionEvent event = new ActionEvent(screen.btnMenu, ActionEvent.ACTION_PERFORMED, "menu");

        // Act
        screen.actionPerformed(event);

        // Assert
        assertFalse(screen.isVisible(), "DifficultyScreen should become invisible after clicking Menu.");
    }

    @Test
    void keyListener_enter() {

        DifficultyScreen screen = new DifficultyScreen(false);
        GameController gameController = new GameController(false);
        InGameScoreController inGameScoreController = new InGameScoreController();
        BoardController boardController = new BoardController(gameController, inGameScoreController, false, false);
        InGameScreen inGameScreen = new InGameScreen(boardController, inGameScoreController);

        // Test key press "ENTER" on Easy
        KeyEvent enterEvent = new KeyEvent(screen.btnEasy, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        screen.btnEasy.dispatchEvent(enterEvent);

        assertFalse(screen.isVisible(), "DifficultyScreen should become invisible after pressing ENTER on Easy.");
        assertTrue(inGameScreen.isVisible(), "InGameScreen should become visible after clicking difficulty");
    }
}
