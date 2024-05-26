package com.tetris.model;

import com.tetris.logic.Block;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class WaterItemBlockTest {

    @Before
    public void setUp() {
        // Set up any necessary initializations or mocks
    }

    @Test
    public void testWaterItemBlockCreation() {
        WaterItemBlock block = WaterItemBlock.create();
        assertNotNull(block);
        assertEquals(BlockType.WaterItemBlock, block.getBlockType());
    }

    @Test
    public void testTransformToWater() {
        // Create a basic block and use its shape
        Block basicBlock = Block.getBlock(BlockType.IBlock); // Assuming I-block is a valid basic block type
        int[][] shape = basicBlock.shapeCopy();

        // Make sure we have some non-zero elements in the shape
        assertTrue(hasNonZeroElement(shape));

        // Transform the shape to WaterItemBlock
        int[][] transformedShape = WaterItemBlock.transformToWater(basicBlock);

        // Check if all non-zero elements are transformed to '10'
        for (int y = 0; y < transformedShape.length; y++) {
            for (int x = 0; x < transformedShape[y].length; x++) {
                if (shape[y][x] > 0) {
                    assertEquals(10, transformedShape[y][x]);
                }
            }
        }
    }

    @Test
    public void testColorsAreBlue() {
        WaterItemBlock block = WaterItemBlock.create();
        Color[] expectedColors = {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE};
        assertArrayEquals(expectedColors, block.getColors());
    }

    private boolean hasNonZeroElement(int[][] shape) {
        for (int[] row : shape) {
            for (int cell : row) {
                if (cell > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
