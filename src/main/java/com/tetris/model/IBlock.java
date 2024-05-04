package com.tetris.model;

import com.tetris.logic.Block;

import java.awt.Color;

public class IBlock extends Block {
    public IBlock() {
        super(new int[][] {
             {0, 0, 0, 0},
             {1, 1, 1, 1},
             {0, 0, 0, 0},
             {0, 0, 0, 0},
        }, new Color[]{Color.CYAN, Color.CYAN,  Color.CYAN,  Color.WHITE});
    }
}
