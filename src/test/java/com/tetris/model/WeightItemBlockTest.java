package com.tetris.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeightItemBlockTest {

    @Test
    void rotate_doesNothing() {
        // Arrange
        WeightItemBlock weightItemBlock = new WeightItemBlock();
        int[][] initialShape = weightItemBlock.getShape();

        // Act
        weightItemBlock.rotate();
        int[][] shapeAfterRotate = weightItemBlock.getShape();

        // Assert
        assertArrayEquals(initialShape, shapeAfterRotate);
    }

    @Test
    void rotateBack_doesNothing() {
        // Arrange
        WeightItemBlock weightItemBlock = new WeightItemBlock();
        int[][] initialShape = weightItemBlock.getShape();

        // Act
        weightItemBlock.rotateBack();
        int[][] shapeAfterRotateBack = weightItemBlock.getShape();

        // Assert
        assertArrayEquals(initialShape, shapeAfterRotateBack);
    }
}
