package component;

import javax.swing.*;

public class ScreenSize {
    static public int _width = 400, _height = 600;

    static public void setWidthHeight(int width, int height, JFrame className) {
        _width = width;
        _height = height;
        className.setSize(width, height);
    }
}
