package com.tetris.model;

import com.tetris.logic.Block;
import com.tetris.logic.RWSelection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LineEraseItemBlock extends Block {
    private static Random random = new Random();

    public BlockType getBlockType() {
        return BlockType.LineEraseItemBlock;
    }

    private LineEraseItemBlock(int[][] shape, Color[] colors) {
        super(shape, colors, BlockType.LineEraseItemBlock);
    }

    public static LineEraseItemBlock create() {
        RWSelection rwSelection = new RWSelection(1);
        Block basicBlock = Block.getBlock(BlockType.getBlockTypeByIndex(rwSelection.select()));
        int[][] shape = basicBlock.shapeCopy();
        Color[] colors = basicBlock.getColors();

        transformToLineEraseItem(shape);
        return new LineEraseItemBlock(shape, colors);
    }

    static void transformToLineEraseItem(int[][] shape) {
        ArrayList<Point> greaterThanZeroIndices = new ArrayList<>();
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] > 0) {
                    greaterThanZeroIndices.add(new Point(x, y));
                }
            }
        }

        if (!greaterThanZeroIndices.isEmpty()) {
            Point selectedPoint = greaterThanZeroIndices.get(random.nextInt(greaterThanZeroIndices.size()));
            shape[selectedPoint.y][selectedPoint.x] = 8;
        }
    }
}
