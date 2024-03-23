package game.Blocks;

import java.awt.Color;

public class ZBlock extends Block{
    public ZBlock() {
        super(new int[][] {
            {1, 1, 0},
            {0, 1, 1}
        }, Color.RED);
    }
}
