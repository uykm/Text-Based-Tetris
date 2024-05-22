package com.tetris.logic;

import com.tetris.model.BlockType;
import org.junit.jupiter.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    private Block block;
    private final int[][] shape = {{1, 1}, {1, 1}};
    private final Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    @BeforeEach
    void setUp() {
        block = new Block(shape, colors, BlockType.OBlock) {};
    }

    @AfterEach
    void tearDown() {
        block = null;
    }

    @Test
    void getX() {
        assertEquals(6, block.getX());
    }

    @Test
    void getY() {
        assertEquals(2, block.getY());
    }

    @Test
    void initializeXY() {
        block.moveRight();
        block.moveDown();
        block.initializeXY();
        assertEquals(6, block.getX());
        assertEquals(2, block.getY());
    }

    @Test
    void moveRight() {
        int initialX = block.getX();
        block.moveRight();
        assertEquals(initialX + 1, block.getX());
    }

    @Test
    void moveLeft() {
        int initialX = block.getX();
        block.moveLeft();
        assertEquals(initialX - 1, block.getX());
    }

    @Test
    void moveDown() {
        int initialY = block.getY();
        block.moveDown();
        assertEquals(initialY + 1, block.getY());
    }

    @Test
    void moveUp() {
        int initialY = block.getY();
        block.moveUp();
        assertEquals(initialY - 1, block.getY());
    }

    @Test
    void moveDownNSpaces() {
        int initialY = block.getY();
        block.moveDownNSpaces(3);
        assertEquals(initialY + 3, block.getY());
    }

    @Test
    void moveRightNSpaces() {
        int initialX = block.getX();
        block.moveRightNSpaces(2);
        assertEquals(initialX + 2, block.getX());
    }

    @Test
    void moveLeftNSpaces() {
        int initialX = block.getX();
        block.moveLeftNSpaces(4);
        assertEquals(initialX - 4, block.getX());
    }

    @Test
    void getShape() {
        assertArrayEquals(shape, block.getShape());
    }

    @Test
    void testGetShape() {
        assertEquals(1, block.getShape(0, 0));
        assertEquals(1, block.getShape(1, 1));
    }

    @Test
    void getColor() {
        Color color = block.getColor();
        assertNotNull(color);
    }

    @Test
    void getColors() {
        assertArrayEquals(colors, block.getColors());
    }

    @Test
    void getType() {
        assertEquals(BlockType.OBlock, block.getType());
    }

    @Test
    void shapeCopy() {
        int[][] copy = block.shapeCopy();
        assertArrayEquals(shape, copy);
        assertNotSame(shape, copy);
    }

    @Test
    void height() {
        assertEquals(2, block.height());
    }

    @Test
    void width() {
        assertEquals(2, block.width());
    }

    @Test
    void rotate() {
        int[][] originalShape = block.shapeCopy();
        block.rotate();
        assertNotEquals(originalShape, block.getShape());
    }

    @Test
    void rotateBack() {
        int[][] originalShape = block.shapeCopy();
        block.rotateBack();
        assertNotEquals(originalShape, block.getShape());
    }

    @Test
    void initializeLimitCount() {
        block.increaseLimitCount();
        block.initializeLimitCount();
        assertEquals(0, block.getLimitCount());
    }

    @Test
    void initializeStopCount() {
        block.increaseStopCount();
        block.initializeStopCount();
        assertEquals(0, block.getStopCount());
    }

    @Test
    void limitCountToTwo() {
        block.increaseLimitCount();
        block.limitCountToTwo();
        assertEquals(2, block.getLimitCount());
    }

    @Test
    void stopCountToTwo() {
        block.increaseStopCount();
        block.stopCountToTwo();
        assertEquals(2, block.getStopCount());
    }

    @Test
    void increaseLimitCount() {
        int initialLimitCount = block.getLimitCount();
        block.increaseLimitCount();
        assertEquals(initialLimitCount + 1, block.getLimitCount());
    }

    @Test
    void increaseStopCount() {
        int initialStopCount = block.getStopCount();
        block.increaseStopCount();
        assertEquals(initialStopCount + 1, block.getStopCount());
    }

    @Test
    void getBlock() {
        Block iBlock = Block.getBlock(BlockType.IBlock);
        assertNotNull(iBlock);
        assertEquals(BlockType.IBlock, iBlock.getType());
    }

    @Test
    void selectBlock() {
        // Mocking dependencies might be necessary here
        BoardController boardController = new BoardController(null, null, true, true);
        Block selectedBlock = block.selectBlock(boardController, false, 0);
        assertNotNull(selectedBlock);
    }

    @Test
    void selectItemBlock() {
        Block selectedItemBlock = block.selectItemBlock();
        assertNotNull(selectedItemBlock);
    }

    @Test
    void getStopCount() {
        assertEquals(0, block.getStopCount());
    }

    @Test
    void getLimitCount() {
        assertEquals(0, block.getLimitCount());
    }
}
