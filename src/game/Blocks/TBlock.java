package game.Blocks;

import java.awt.Color;

public class TBlock extends Block{
    public TBlock() {
        super(new int[][] {
            {1, 1, 1},
            {0, 1, 0}
        }, Color.MAGENTA);
    }
}
