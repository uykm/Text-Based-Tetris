package game.Blocks;

import java.awt.Color;

public class LBlock extends Block{
    public LBlock() {
        super(new int[][] {
            {1, 1, 1},
            {1, 0, 0}
        }, Color.ORANGE);
    }
}
