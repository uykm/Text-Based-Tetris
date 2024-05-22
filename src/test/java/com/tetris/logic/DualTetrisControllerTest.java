package com.tetris.logic;

import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

import static org.junit.jupiter.api.Assertions.*;

class DualTetrisControllerTest {

    private DualTetrisController dualTetrisController;

    @BeforeEach
    void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            dualTetrisController = new DualTetrisController(false, false);
            dualTetrisController.initUI();
        });
    }

    @AfterEach
    void tearDown() {
        dualTetrisController = null;
    }

    @Test
    void testGetDualFrame() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JFrame frame = dualTetrisController.getDualFrame();
            assertNotNull(frame);
            System.out.println("testGetDualFrame: Frame initialized.");
        });
    }

    @Test
    void testTimeAttackMode() throws Exception {
        DualTetrisController timeAttackController = new DualTetrisController(false, true);
        SwingUtilities.invokeAndWait(timeAttackController::initUI);
        JFrame frame = timeAttackController.getDualFrame();
        JLabel timeLabel = findLabelWithText(frame, "Time: 03:00");
        assertNotNull(timeLabel);
        System.out.println("testTimeAttackMode: Time label found.");
    }

    @Test
    void testItemMode() throws Exception {
        DualTetrisController itemModeController = new DualTetrisController(true, false);
        SwingUtilities.invokeAndWait(itemModeController::initUI);
        JFrame frame = itemModeController.getDualFrame();
        JLabel modeLabel = findLabelWithText(frame, "Item Mode");
        assertNotNull(modeLabel);
        System.out.println("testItemMode: Mode label found.");
    }

    @Test
    void testNormalMode() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JFrame frame = dualTetrisController.getDualFrame();
            JLabel modeLabel = findLabelWithText(frame, "Normal Mode");
            assertNotNull(modeLabel);
            System.out.println("testNormalMode: Normal mode label found.");
        });
    }

    @Test
    void testSetupKeyListener() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JFrame frame = dualTetrisController.getDualFrame();
            KeyListener[] keyListeners = frame.getKeyListeners();
            assertTrue(keyListeners.length > 0); // Ensure at least one KeyListener is attached
            boolean hasKeyAdapter = false;
            for (KeyListener keyListener : keyListeners) {
                if (keyListener instanceof KeyAdapter) {
                    hasKeyAdapter = true;
                    break;
                }
            }
            assertTrue(hasKeyAdapter); // Ensure there is at least one KeyAdapter
            System.out.println("testSetupKeyListener: KeyListener setup correctly.");
        });
    }

//    @Test
//    void testUpdateTime() throws Exception {
//    }


    @Test
    void testTimeController() throws Exception {
        dualTetrisController = new DualTetrisController(false, true);
        SwingUtilities.invokeAndWait(dualTetrisController::initUI);
        dualTetrisController.timeController();
        JFrame frame = dualTetrisController.getDualFrame();
        JLabel timeLabel = findLabelWithText(frame, "Time: 03:00");
        assertNotNull(timeLabel);
        System.out.println("testTimeController: Time controller initialized correctly.");
    }

    private JLabel findLabelWithText(Container container, String text) {
        if (container == null) {
            return null;
        }
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                System.out.println("Found JLabel with text: " + label.getText());
                if (label.getText().equals(text)) {
                    return label;
                }
            } else if (component instanceof Container) {
                JLabel label = findLabelWithText((Container) component, text);
                if (label != null) {
                    return label;
                }
            }
        }
        return null;
    }
}
