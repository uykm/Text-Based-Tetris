package com.tetris.logic;

import com.tetris.model.BlockType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemBlockControllerTest {

    private Board grid;
    private ItemBlockController itemBlockController;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;

    @BeforeEach
    void setUp() {
        grid = new Board();
        itemBlockController = new ItemBlockController(grid, WIDTH, HEIGHT);
    }


    @Test
    void handleWeightItemBlock() {
        Block weightBlock = Block.getBlock(BlockType.WeightItemBlock);
        weightBlock.initializeXY();
        itemBlockController.handleItemBlock(weightBlock, weightBlock.getX(), weightBlock.getY());

        // Assert the block cannot move side after applying weight item
        assertFalse(weightBlock.canMoveSide);
    }

    @Test
    void handleBombItemBlock() {
        Block bombBlock = Block.getBlock(BlockType.BombItemBlock);
        grid.placeOneBlock(6, 6, ItemBlockController.BOMB_BODY);  // Place a bomb block at position (6, 6)
        itemBlockController.handleItemBlock(bombBlock, 6, 6);

        // Check if the bomb exploded and removed surrounding blocks
        assertEquals(ItemBlockController.BOMB_EVENT, grid.getBoard()[6][6]); // Central bomb block should be removed
        assertEquals(ItemBlockController.BOMB_EVENT, grid.getBoard()[5][6]); // One of the surrounding blocks should be removed
    }

    @Test
    void handleExtensionItemBlock() {
        Block extensionBlock = Block.getBlock(BlockType.ExtensionItemBlock);
        grid.placeOneBlock(6, 6, ItemBlockController.EXTEND_BLOCK);  // Place an extension block at position (6, 6)
        itemBlockController.handleItemBlock(extensionBlock, 6, 6);

        // Check if the extension block created surrounding blocks
        assertEquals(ItemBlockController.EXTEND_BLOCK_EVENT, grid.getBoard()[5][6]); // One of the surrounding blocks should be created
    }
}
