package model;

import logic.Block;

import java.awt.Color;

public class SBlock extends Block {
    public SBlock() {
        super(new int[][] {
                {0, 0, 0, 0},
                {0, 0, 5, 5},
                {0, 5, 5, 0},
                {0, 0, 0, 0}
        }, new Color[]{Color.GREEN, Color.YELLOW,  Color.MAGENTA,  Color.DARK_GRAY});
    }
}
