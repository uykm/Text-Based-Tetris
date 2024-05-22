package com.tetris.logic;

import com.tetris.model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {
    private BoardController boardController;
    private GameController gameController;
    private InGameScoreController inGameScoreController;

    @BeforeEach
    void setUp() {
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
        // 검증하기 위한 로직 추가 필요
    }

    @Test
    void testLineCheck() {
        boardController.placeNewBlock();
        Block currentBlock = boardController.getNextBlock();
        boardController.placeBlock();
        boardController.lineCheck();
        // 검증하기 위한 로직 추가 필요
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
        boardController.moveBlock(Direction.DOWN);
        assertEquals(3, currentBlock.getY());
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
        // 검증하기 위한 로직 추가 필요
    }

    @Test
    void testAddLines() {
        int[][] linesToAdd = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        boardController.addLines(linesToAdd);
        int[][] shouldAddLines = boardController.getShouldAddLines();
        assertNotNull(shouldAddLines);
    }

    @Test
    void testCheckGameOver() {
        boolean gameOver = boardController.checkGameOver();
        assertFalse(gameOver);
    }

//    @Test
//    void testCheckWinner() {
//
//    }

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
