package game.Blocks;

import java.awt.Color;

public class TBlock extends Block{
    public TBlock() {
        super(new int[][] {
            {0, 0, 0, 0},
            {0, 6, 6, 6},
            {0, 0, 6, 0},
            {0, 0, 0, 0}
        }, Color.MAGENTA);
    }
}
