package com.tetris.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InGameScoreControllerTest {

    private InGameScoreController controller;

    @BeforeEach
    void setUp() {
        controller = new InGameScoreController();
    }

    @Test
    void getScore() {
        assertEquals(0, controller.getScore());
        controller.addScoreOnBlockMoveDown();
        assertEquals(1, controller.getScore());
    }

    @Test
    void getScoreMessages() {
        controller.addScoreMessage("Test Message 1");
        controller.addScoreMessage("Test Message 2");
        String messages = controller.getScoreMessages();
        assertTrue(messages.contains("Test Message 1"));
        assertTrue(messages.contains("Test Message 2"));
    }

    @Test
    void addScoreOnBlockMoveDown() {
        assertEquals(0, controller.getScore());
        controller.addScoreOnBlockMoveDown();
        assertEquals(1, controller.getScore());
    }

    @Test
    void getScoreOnBlockMoveDown() {
        assertEquals(1, controller.getScoreOnBlockMoveDown());
    }

    @Test
    void setScoreOnBlockMoveDown() {
        controller.setScoreOnBlockMoveDown(5);
        assertEquals(5, controller.getScoreOnBlockMoveDown());
    }

    @Test
    void testAddScoreOnBlockMoveDown() {
        assertEquals(0, controller.getScore());
        controller.addScoreOnBlockMoveDown(3);
        assertEquals(3, controller.getScore());
    }

    @Test
    void addScoreOnLineEraseWithBonus() {
        assertEquals(0, controller.getScore());
        controller.addScoreOnLineEraseWithBonus(2, 2000); // diff < 3000, should multiply by 2
        assertEquals((13 * 2 - 3) * 2, controller.getScore());
        assertTrue(controller.getScoreMessages().contains("Lines erased (x2)"));

        // Test without bonus
        controller.addScoreOnLineEraseWithBonus(1, 4000); // diff >= 3000, no multiplier
        assertEquals((13 * 2 - 3) * 2 + (13 * 1 - 3), controller.getScore());
        assertTrue(controller.getScoreMessages().contains("Line erased"));
    }

    @Test
    void subScoreOnLineNotEraseIn10Blocks() {
        controller.subScoreOnLineNotEraseIn10Blocks();
        assertEquals(-50, controller.getScore());
        assertTrue(controller.getScoreMessages().contains("No line erased in 10 blocks"));
    }

    @Test
    void addScoreMessage() {
        controller.addScoreMessage("Test message");
        assertTrue(controller.getScoreMessages().contains("Test message"));
    }

    @Test
    void addScoreOnMultipleLineEraseWithBonus() {
        assertEquals(0, controller.getScore());
        controller.addScoreOnLineEraseWithBonus(3, 1500); // diff < 3000, should multiply by 2
        int expectedScore = (13 * 3 - 3) * 2;
        assertEquals(expectedScore, controller.getScore());
        assertTrue(controller.getScoreMessages().contains("Lines erased (x2)"));

        controller.addScoreOnLineEraseWithBonus(2, 3500); // diff >= 3000, no multiplier
        expectedScore += (13 * 2 - 3);
        assertEquals(expectedScore, controller.getScore());
        assertTrue(controller.getScoreMessages().contains("Lines erased"));
    }

    @Test
    void addScoreOnSingleLineEraseWithBonus() {
        assertEquals(0, controller.getScore());
        controller.addScoreOnLineEraseWithBonus(1, 1500); // diff < 3000, should multiply by 2
        int expectedScore = (13 * 1 - 3) * 2;
        assertEquals(expectedScore, controller.getScore());
        assertTrue(controller.getScoreMessages().contains("Line erased (x2)"));

        controller.addScoreOnLineEraseWithBonus(1, 3500); // diff >= 3000, no multiplier
        expectedScore += (13 * 1 - 3);
        assertEquals(expectedScore, controller.getScore());
        assertTrue(controller.getScoreMessages().contains("Line erased"));
    }

    @Test
    void addScoreMessageQueueLimit() {
        for (int i = 0; i < 10; i++) {
            controller.addScoreMessage("Message " + i);
        }
        String messages = controller.getScoreMessages();
        assertFalse(messages.contains("Message 0"));
        assertFalse(messages.contains("Message 1"));
        assertTrue(messages.contains("Message 2"));
        assertTrue(messages.contains("Message 9"));
    }
}
