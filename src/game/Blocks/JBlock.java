package game.Blocks;

import java.awt.Color;

public class JBlock extends Block{
    public JBlock() {
        super(new int[][] {
            {0, 0, 0, 0},
            {0, 2, 2, 2},
            {0, 0, 0, 2},
            {0, 0, 0, 0}
        }, Color.BLUE);
    }
}
