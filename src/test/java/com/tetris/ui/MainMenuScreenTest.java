package com.tetris.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainMenuScreenTest {

    private MainMenuScreen screen;
    private Robot robot;

    @BeforeEach
    void setUp() throws AWTException {
        screen = new MainMenuScreen();
        screen.setVisible(true);
        robot = new Robot();
        robot.setAutoDelay(100);
    }

    @AfterEach
    void tearDown() throws Exception {
        screen.setVisible(false);
        screen.dispose();
        EventQueue.invokeAndWait(() -> {}); // Ensure the event queue is empty
    }

    @Test
    // 마우스 조작
    void actionPerformedTest() {

        // Click "Single"
        ActionEvent singleEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "single");
        SingleGameModeScreen singleGameModeScreen = new SingleGameModeScreen();
        screen.actionPerformed(singleEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Single.");
        assertTrue(singleGameModeScreen.isVisible(), "GameModeScreen should become invisible after pressing Click on Single.");

        // Click "Multi"
        ActionEvent multiEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "multi");
        MultiGameModeScreen multiGameModeScreen = new MultiGameModeScreen();
        screen.actionPerformed(multiEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Multi.");
        assertTrue(multiGameModeScreen.isVisible(), "GameModeScreen should become invisible after pressing Click on Multi.");

        // Click "Setting"
        ActionEvent settingEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "setting");
        SettingScreen settingScreen = new SettingScreen();
        screen.actionPerformed(settingEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Setting.");
        assertTrue(settingScreen.isVisible(), "SettingScreen should become invisible after pressing Click on Setting.");

        // Click "Rank"
        ActionEvent rankingEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "ranking");
        ScoreboardScreen scoreboardScreen = new ScoreboardScreen();
        screen.actionPerformed(rankingEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Rank.");
        assertTrue(scoreboardScreen.isVisible(), "ScoreBoardScreen should become invisible after Click on Rank.");
    }

    @Test
    // 키보드 조작
    void keyListenerTest() {

        // Enter on Single
        KeyEvent playEvent = new KeyEvent(screen.btnSingle, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        SingleGameModeScreen singleGameModeScreen = new SingleGameModeScreen();
        screen.btnSingle.dispatchEvent(playEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Single.");
        assertTrue(singleGameModeScreen.isVisible(), "GameModeScreen should become invisible after pressing ENTER on Single.");

        // Enter on Multi
        KeyEvent multiEvent = new KeyEvent(screen.btnMulti, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        MultiGameModeScreen multiGameModeScreen = new MultiGameModeScreen();
        screen.btnMulti.dispatchEvent(multiEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Multi.");
        assertTrue(multiGameModeScreen.isVisible(), "GameModeScreen should become invisible after pressing ENTER on Multi.");

        // Enter on Setting
        KeyEvent settingEvent = new KeyEvent(screen.btnMulti, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        SettingScreen settingScreen = new SettingScreen();
        screen.btnSetting.dispatchEvent(settingEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Setting.");
        assertTrue(settingScreen.isVisible(), "SettingScreen should become invisible after pressing ENTER on Setting.");

        // Enter on Rank
        KeyEvent rankEvent = new KeyEvent(screen.btnRanking, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, ' ');
        ScoreboardScreen scoreboardScreen = new ScoreboardScreen();
        screen.btnSetting.dispatchEvent(rankEvent);
        assertFalse(screen.isVisible(), "MainMenuScreen should become invisible after pressing ENTER on Rank.");
        assertTrue(scoreboardScreen.isVisible(), "ScoreBoardScreen should become invisible after pressing ENTER on Rank.");
    }

    @Test
    void testFocusUp() throws InterruptedException {

        // Initial focus to Setting, test moving to Single
        screen.btnSetting.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        assertTrue(screen.btnSingle.isFocusOwner());

        // Initial focus to Ranking, test moving to Single
        robot.setAutoDelay(100);
        screen.btnRanking.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        assertTrue(screen.btnSingle.isFocusOwner());

        // Initial focus to Exit, test moving to Single
        robot.setAutoDelay(100);
        screen.btnExit.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        assertTrue(screen.btnSingle.isFocusOwner());
    }

    @Test
    void testFocusDown() throws InterruptedException {

        // Initial focus to Single, test moving to Ranking
        screen.btnSingle.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        // assertTrue(screen.btnRanking.isFocusOwner());

        // Initial focus to Multi, test moving to Ranking
        robot.setAutoDelay(100);
        screen.btnMulti.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        assertTrue(screen.btnRanking.isFocusOwner());
    }

    @Test
    void testFocusLeft() throws InterruptedException {

        // Initial focus to Multi, test moving to Single
        screen.btnMulti.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.btnSingle.isFocusOwner());

        // Initial focus to Exit, test moving to Ranking
        robot.setAutoDelay(100);
        screen.btnExit.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.btnRanking.isFocusOwner());

        // Initial focus to Ranking, test moving to Setting
        robot.setAutoDelay(100);
        screen.btnRanking.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        assertTrue(screen.btnSetting.isFocusOwner());
    }

    @Test
    void testFocusRight() throws InterruptedException {

        // Initial focus to Single, test moving to Multi
        screen.btnSingle.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.btnMulti.isFocusOwner());

        // Initial focus to Setting, test moving to Ranking
        robot.setAutoDelay(100);
        screen.btnSetting.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.btnRanking.isFocusOwner());

        // Initial focus to Ranking, test moving to Setting
        robot.setAutoDelay(100);
        screen.btnRanking.requestFocusInWindow();
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        assertTrue(screen.btnExit.isFocusOwner());
    }
}
