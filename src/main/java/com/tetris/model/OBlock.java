package com.tetris.model;

import com.tetris.logic.Block;

import java.awt.Color;

public class OBlock extends Block {
    public OBlock() {
        super(new int[][] {
            {0, 0, 0, 0},
            {0, 4, 4, 0},
            {0, 4, 4, 0},
            {0, 0, 0, 0}
        }, new Color[]{Color.YELLOW, Color.GRAY,  Color.GRAY,  Color.GRAY}
        , BlockType.OBlock);
    }
}
