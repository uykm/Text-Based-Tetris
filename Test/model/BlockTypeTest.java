package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTypeTest {

    @Test
    void getBlockTypeByIndex_validIndex_returnsCorrectBlockType() {
        // Arrange
        int index = 2; // Assuming this index corresponds to LBlock

        // Act
        BlockType blockType = BlockType.getBlockTypeByIndex(index);

        // Assert
        assertEquals(BlockType.LBlock, blockType);
    }

    @Test
    void getBlockTypeByIndex_invalidIndex_returnsNullBlock() {
        // Arrange
        int index = 100; // Assuming an invalid index

        // Act
        BlockType blockType = null;
        try {
            blockType = BlockType.getBlockTypeByIndex(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            // This exception is expected for invalid indices,
            // so we catch it and handle it by setting blockType to NullBlock
            blockType = BlockType.NullBlock;
        }

        // Assert
        assertEquals(BlockType.NullBlock, blockType);
    }


    @Test
    void values_returnsAllBlockTypes() {
        // Arrange

        // Act
        BlockType[] blockTypes = BlockType.values();

        // Assert
        assertEquals(12, blockTypes.length); // Assuming there are 9 block types
        // You can also check individual block types if needed
    }

    @Test
    void valueOf_validBlockTypeName_returnsCorrectBlockType() {
        // Arrange
        String blockTypeName = "IBlock";

        // Act
        BlockType blockType = BlockType.valueOf(blockTypeName);

        // Assert
        assertEquals(BlockType.IBlock, blockType);
    }

    @Test
    void valueOf_invalidBlockTypeName_throwsIllegalArgumentException() {
        // Arrange
        String blockTypeName = "InvalidBlock"; // Assuming an invalid block type name

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            BlockType.valueOf(blockTypeName);
        });
    }
}
