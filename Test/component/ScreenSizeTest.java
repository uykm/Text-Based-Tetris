package component;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ScreenSizeTest {

    @Test
    void setWidthHeight() {
        // Define test variables
        int width = 800;
        int height = 600;
        JFrame frame = new JFrame();

        // Call the method to set width and height
        ScreenSize.setWidthHeight(width, height, frame);

        // Check if width and height are set correctly
        assertEquals(width, ScreenSize._width, "Width should be set correctly.");
        assertEquals(height, ScreenSize._height, "Height should be set correctly.");

        // Check if the frame's size is set correctly
        assertEquals(width, frame.getWidth(), "Frame width should be set correctly.");
        assertEquals(height, frame.getHeight(), "Frame height should be set correctly.");
    }
}
