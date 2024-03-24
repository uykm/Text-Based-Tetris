package game.Blocks;

import java.awt.Color;

public class ZBlock extends Block{
    public ZBlock() {
        super(new int[][] {
            {0, 0, 0, 0},
            {0, 7, 7, 0},
            {0, 0, 7, 7},
            {0, 0, 0, 0}
        }, Color.RED);
    }
}
