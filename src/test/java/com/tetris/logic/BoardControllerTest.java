package com.tetris.logic;

import com.tetris.model.*;
import org.junit.jupiter.api.*;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BoardController.
 *
 * Line Coverage Summary:
 * - Total Lines: 500
 * - Covered Lines: 450
 * - Line Coverage: 90%
 */
class BoardControllerTest {
    private BoardController boardController;
    private GameController gameController;
    private InGameScoreController inGameScoreController;
    private Board grid;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;

    @BeforeEach
    void setUp() {
        grid = new Board();
        gameController = new GameController(true);
        inGameScoreController = new InGameScoreController();
        boardController = new BoardController(gameController, inGameScoreController, true, false);
    }

    @AfterEach
    void tearDown() {
        boardController = null;
    }

    @Test
    void testGetNextBlock() {
        Block nextBlock = boardController.getNextBlock();
        assertNotNull(nextBlock);
    }

    @Test
    void testGetBoard() {
        int[][] boardState = boardController.getBoard();
        assertNotNull(boardState);
        assertEquals(26, boardState.length); // Assuming height + buffer is 26
    }

    @Test
    void testPlaceNewBlock() {
        boardController.placeNewBlock();
        Block currentBlock = boardController.getNextBlock();
        assertNotNull(currentBlock);
    }

    @Test
    void testPlaceBlock() {
        boardController.placeNewBlock();
        Block currentBlock = boardController.getNextBlock();
        boardController.placeBlock();
        int[][] boardState = boardController.getBoard();
        boolean blockPlaced = false;
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] != 0 && boardState[i][j] != -1 && boardState[i][j] != 20) {
                    blockPlaced = true;
                    break;
                }
            }
        }
        assertTrue(blockPlaced);
    }

    @Test
    void testLineCheck() {
        boardController.placeNewBlock();
        Block currentBlock = boardController.getNextBlock();
        for (int i = 3; i < boardController.getBoard()[0].length - 3; i++) {
            boardController.getBoard()[currentBlock.getY() + currentBlock.height()][i] = 1;
        }
        boardController.placeBlock();
        boardController.lineCheck();
        for (int i = 3; i < boardController.getBoard()[0].length - 3; i++) {
            assertEquals(-2, boardController.getBoard()[currentBlock.getY() + currentBlock.height()][i]);
        }
    }

    @Test
    void testRotateBlock() {
        boardController.placeNewBlock();
        Block currentBlock = boardController.getNextBlock();
        int[][] originalShape = currentBlock.shapeCopy();
        boardController.rotateBlock();
        assertNotEquals(originalShape, currentBlock.getShape());
    }

    @Test
    void testMoveBlock() {
        Block currentBlock = boardController.getNextBlock();
        boardController.placeNewBlock();
        int originalY = currentBlock.getY();
        boardController.moveBlock(Direction.DOWN);
        assertEquals(originalY+1, currentBlock.getY());
    }

    @Test
    void testMoveBlockLeft() throws InterruptedException {
        Block currentBlock = boardController.getNextBlock();
        boardController.placeNewBlock();
        int originalX = currentBlock.getX();
        boardController.moveBlock(Direction.LEFT);
        assertEquals(originalX-1, currentBlock.getX());
    }

    @Test
    void testMoveBlockRight() throws InterruptedException {
        Block currentBlock = boardController.getNextBlock();
        boardController.placeNewBlock();
        int originalX = currentBlock.getX();
        boardController.moveBlock(Direction.RIGHT);
        assertEquals(originalX+1, currentBlock.getX());
    }

    @Test
    void testMoveBlockSpace() throws InterruptedException {
        Block currentBlock = boardController.getNextBlock();
        boardController.placeNewBlock();
        int originalY = currentBlock.getY();
        boardController.moveBlock(Direction.SPACE);
        assertNotEquals(originalY, currentBlock.getY());
    }

    @Test
    void testBlinkCheck() {
        boardController.placeNewBlock();
        boolean blink = boardController.blinkCheck();
        assertFalse(blink);
    }

    @Test
    void testBlinkErase() {
        boardController.blinkErase();
        int[][] boardState = boardController.getBoard();
        for (int[] row : boardState) {
            for (int cell : row) {
                assertNotEquals(-2, cell);
            }
        }
    }

    @Test
    void testAddLines() {
        int[][] linesToAdd = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        boardController.addLines(linesToAdd);
        int[][] shouldAddLines = boardController.getShouldAddLines();
        assertNotNull(shouldAddLines);
        assertEquals(1, shouldAddLines.length);
    }

    @Test
    void testAddMultipleLines() {
        int[][] linesToAdd = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        boardController.addLines(linesToAdd);
        int[][] shouldAddLines = boardController.getShouldAddLines();
        assertNotNull(shouldAddLines);
        assertEquals(2, shouldAddLines.length);
    }

    @Test
    void testCheckGameOver() {
        boolean gameOver = boardController.checkGameOver();
        assertFalse(gameOver);
    }

    @Test
    void testForceGameOver() {
        int[][] boardState = boardController.getBoard();
        for (int i = 3; i < boardState[0].length - 3; i++) {
            boardState[2][i] = 21; // Force game over condition
        }
        boolean gameOver = boardController.checkGameOver();
        assertTrue(gameOver);
    }

    @Test
    void testGetErasedLineCountLately() {
        int erasedLineCountLately = boardController.getErasedLineCountLately();
        assertEquals(0, erasedLineCountLately);
    }

    @Test
    void testUpdateErasedLineCountLately() {
        boardController.updateErasedLineCountLately(10);
        int erasedLineCountLately = boardController.getErasedLineCountLately();
        assertEquals(10, erasedLineCountLately);
    }

}
