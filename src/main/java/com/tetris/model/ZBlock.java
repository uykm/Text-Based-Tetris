package com.tetris.model;

import com.tetris.logic.Block;

import java.awt.Color;

public class ZBlock extends Block {
    public ZBlock() {
        super(new int[][] {
            {0, 0, 0, 0},
            {0, 7, 7, 0},
            {0, 0, 7, 7},
            {0, 0, 0, 0}
        }, new Color[]{Color.RED, Color.MAGENTA,  Color.GREEN,  Color.PINK}
        , BlockType.ZBlock);
    }
}
