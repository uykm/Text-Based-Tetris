package com.tetris.model;

import com.tetris.logic.Block;

import java.awt.Color;

public class JBlock extends Block {
    public JBlock() {
        super(new int[][] {
            {0, 0, 0, 0},
            {0, 2, 2, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 0}
        }, new Color[]{Color.BLUE, Color.BLUE,  Color.BLUE,  Color.BLUE}
        , BlockType.JBlock);
    }
}
