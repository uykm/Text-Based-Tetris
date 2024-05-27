package com.tetris.model;

import com.tetris.logic.Block;
import com.tetris.logic.RWSelection;

import java.awt.*;
import java.util.Random;

public class WaterItemBlock extends Block {
    private WaterItemBlock(int[][] shape, Color[] colors) {
        super(shape, colors, BlockType.WaterItemBlock);
    }

    public BlockType getBlockType() {
        return BlockType.WaterItemBlock;
    }
    public static WaterItemBlock create() {
        RWSelection rwSelection = new RWSelection(1);
        Block basicBlock = Block.getBlock(BlockType.getBlockTypeByIndex(rwSelection.select()));
        int[][] transformedShape = transformToWater(basicBlock);
        Color[] colors = new Color[]{Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE}; // Assume all water blocks are blue
        return new WaterItemBlock(transformedShape, colors);
    }

    static int[][] transformToWater(Block basicBlock) {
        int[][] newShape = basicBlock.shapeCopy(); // Copy the shape to avoid modifying the original
        for (int y = 0; y < newShape.length; y++) {
            for (int x = 0; x < newShape[y].length; x++) {
                if (newShape[y][x] > 0) {
                    newShape[y][x] = 10; // Convert active cells to water cells
                }
            }
        }
        return newShape;
    }
}
