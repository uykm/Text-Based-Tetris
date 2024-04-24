package model;

import logic.Block;
import logic.RWSelection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ItemBlock extends Block{

    RWSelection rwSelection = new RWSelection(1);
    Block basicBlock = Block.getBlock(BlockType.getBlockTypeByIndex(rwSelection.select()));

    Random random = new Random();

    public ItemBlock() {
        super(new int[][] {
                {}
        }, new Color[]{Color.WHITE, Color.WHITE,  Color.WHITE,  Color.WHITE});
    }

    public Block waterBlock() {
        for (int y = 0; y < basicBlock.getShape().length; y++) {
            for (int x = 0; x < basicBlock.getShape()[y].length; x++) {
                if (basicBlock.getShape()[y][x] > 0) {
                    basicBlock.getShape()[y][x] = 10;
                }
            }
        }
        shape = basicBlock.shapeCopy();
        colors = new Color[]{Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE};
        return this;
    }

    public Block lineBlock() {
        ArrayList<Point> greaterThanZeroIndices = new ArrayList<>();
        for (int y = 0; y < basicBlock.getShape().length; y++) {
            for (int x = 0; x < basicBlock.getShape()[y].length; x++) {
                if (basicBlock.getShape()[y][x] > 0) {
                    greaterThanZeroIndices.add(new Point(x, y));
                }
            }
        }

        if (!greaterThanZeroIndices.isEmpty()) {
            Point selectedPoint = greaterThanZeroIndices.get(random.nextInt(greaterThanZeroIndices.size()));
            basicBlock.getShape()[selectedPoint.y][selectedPoint.x] = 8;
        }

        shape = basicBlock.shapeCopy();
        colors = basicBlock.getColors();
        return this;
    }
}
