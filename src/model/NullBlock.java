package model;

import logic.Block;

import java.awt.Color;

public class NullBlock extends Block {
    public NullBlock() {
        super(new int[][] {
                {0},
        }, new Color[]{Color.BLACK, Color.BLACK, Color.BLACK,  Color.BLACK});
    }
}
