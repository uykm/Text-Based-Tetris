package model;

import logic.Block;

import java.awt.Color;

public class OBlock extends Block {
    public OBlock() {
        super(new int[][] {
            {0, 0, 0, 0},
            {0, 4, 4, 0},
            {0, 4, 4, 0},
            {0, 0, 0, 0}
        }, Color.YELLOW);
    }
}
