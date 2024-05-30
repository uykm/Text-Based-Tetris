package com.tetris.model;

import com.tetris.logic.Block;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LineEraseItemBlockTest {

    @Before
    public void setUp() {
        // Set up any necessary initializations or mocks
    }

    @Test
    public void testLineEraseItemBlockCreation() {
        LineEraseItemBlock block = LineEraseItemBlock.create();
        assertNotNull(block);
        assertEquals(BlockType.LineEraseItemBlock, block.getBlockType());
    }

    @Test
    public void testTransformToLineEraseItem() {
        // Create a basic block and use its shape
        Block basicBlock = Block.getBlock(BlockType.IBlock); // Assuming I-block is a valid basic block type
        int[][] shape = basicBlock.shapeCopy();

        // Make sure we have some non-zero elements in the shape
        assertTrue(hasNonZeroElement(shape));

        // Transform the shape to LineEraseItemBlock
        LineEraseItemBlock.transformToLineEraseItem(shape);

        // Check if there is exactly one '8' in the shape
        assertEquals(1, countSpecificElement(shape, 8));
    }

    @Test
    public void testColorsAreCopied() {
        LineEraseItemBlock block = LineEraseItemBlock.create();
        Block basicBlock = Block.getBlock(BlockType.IBlock); // Assuming I-block is a valid basic block type
        Color[] basicBlockColors = basicBlock.getColors();

        assertArrayEquals(basicBlockColors, block.getColors());
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

    private int countSpecificElement(int[][] shape, int element) {
        int count = 0;
        for (int[] row : shape) {
            for (int cell : row) {
                if (cell == element) {
                    count++;
                }
            }
        }
        return count;
    }
}
