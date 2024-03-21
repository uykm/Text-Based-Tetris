package game.Blocks;

import java.awt.Color;

public class SBlock extends Block{
    public SBlock() {
        super(new int[][] {
            {0, 1, 1},
            {1, 1, 0}
        }, Color.GREEN);
    }
}
