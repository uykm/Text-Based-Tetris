package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testValues() {
        // Arrange
        Direction[] expectedDirections = {
                Direction.LEFT,
                Direction.RIGHT,
                Direction.DOWN,
                Direction.UP,
                Direction.SPACE
        };

        // Act
        Direction[] actualDirections = Direction.values();

        // Assert
        assertArrayEquals(expectedDirections, actualDirections);
    }

    @Test
    void testValueOf() {
        // Arrange
        String directionString = "DOWN";
        Direction expectedDirection = Direction.DOWN;

        // Act
        Direction actualDirection = Direction.valueOf(directionString);

        // Assert
        assertEquals(expectedDirection, actualDirection);
    }

    @Test
    void testValueOf_invalidDirection_throwsIllegalArgumentException() {
        // Arrange
        String directionString = "INVALID_DIRECTION";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Direction.valueOf(directionString);
        });
    }
}
