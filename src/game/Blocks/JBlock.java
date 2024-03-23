package game.Blocks;

import java.awt.Color;

public class JBlock extends Block{
    public JBlock() {
        super(new int[][] {
            {1, 1, 1},
            {0, 0, 1},
            {0, 0, 0}
        }, Color.BLUE);
    }
}
