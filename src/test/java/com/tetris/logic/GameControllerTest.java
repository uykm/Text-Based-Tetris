package com.tetris.logic;

import com.tetris.ui.InGameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    public void setUp() {
        gameController = new GameController(false);
        gameController.setStrPlayer("TestPlayer");
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(gameController.getInGameScreen());
        assertEquals("TestPlayer", gameController.getStrPlayer());
    }

    @Test
    public void testStartGame() throws Exception{
        SwingUtilities.invokeAndWait(() -> gameController.startGame(false));
        assertTrue(gameController.getInGameScreen().isShowing());
    }

    @Test
    public void testControlGame() {
        gameController.controlGame("LEFT");
        // Verify the board state has been updated appropriately
        // (This requires BoardController's state verification method)
    }

    @Test
    public void testPauseAndResumeGame() {
        gameController.controlGame("PAUSE");
        assertFalse(gameController.gameTimer.isRunning());

        gameController.controlGame("RESUME");
        assertTrue(gameController.gameTimer.isRunning());
    }

    @Test
    public void testEndGameSingleMode() throws Exception {
        SwingUtilities.invokeLater(() -> gameController.endGame(false));
        Thread.sleep(1000);
        assertTrue(gameController.gameEnded);
    }

    @Test
    public void testEndGameDualMode() {
        GameController opponent = new GameController(false);
        gameController.setOpponent(opponent);
        gameController.endGame(true);
        assertFalse(gameController.getOpponent().gameTimer.isRunning());
    }

    @Test
    public void testSpeedUp() {
        int initialSpeed = gameController.currentSpeed;
        int speed=50;
        gameController.speedUp(speed);
        if (gameController.settingController.getDifficulty() == 0) {
            speed = (int) (speed * 0.8);
        } else if (gameController.settingController.getDifficulty() == 2) {
            speed = (int) (speed * 1.2);
        }
        assertEquals(initialSpeed - speed, gameController.currentSpeed);
    }

    @Test
    public void testAddLines() {
        int[][] lines = {{1, 1, 1, 1}};
        gameController.addLines(lines);
        // Verify the board state has been updated (needs BoardController method)
    }

    @Test
    public void testSendLines() {
        GameController opponent = new GameController(false);
        gameController.setOpponent(opponent);
        int[][] lines = {{1, 1, 1, 1}};
        gameController.sendLines(lines);
        // Verify the opponent's board state has been updated
    }

    @Test
    public void testGameOver() throws InterruptedException {
        GameController opponent = new GameController(false);
        gameController.setOpponent(opponent);

        SwingUtilities.invokeLater(() -> gameController.gameOver());

        Thread.sleep(1000);

        assertTrue(gameController.winnerScreenAlreadyOccured());
    }

    @Test
    public void testDualModeSetup() {
        gameController = new GameController(false, true, false);
        assertTrue(gameController.isDualMode());
    }

    @Test
    public void testTimeAttackSetup() {
        gameController = new GameController(false, false, true);
        assertTrue(gameController.isTimeAttack());
    }

    @Test
    public void testItemModeSetup() {
        gameController = new GameController(true);
        assertTrue(gameController.isItem());
    }

    @Test
    public void testBlinkCheck() {
        gameController = new GameController(false);
        gameController.triggerBlinkCheckAgain();
        assertTrue(gameController.isBlinkCheckAgain());
    }

    @Test
    public void testGameOverDuringDualMode() {
        GameController opponent = new GameController(false);
        gameController = new GameController(false);
        gameController.setOpponent(opponent);
        gameController.endGame(true);
        assertTrue(gameController.winnerScreenAlreadyOccured());
    }

    @Test
    public void testFastDrop() {
        gameController = new GameController(false);
        gameController.resetTimer();
        // Verifies that the timer delay was correctly set (needs internal state check)
    }
}
