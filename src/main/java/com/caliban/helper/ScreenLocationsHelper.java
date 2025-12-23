package com.caliban.helper;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class ScreenLocationsHelper {

    private final int screenWidth;
    private final int screenHeight;

    public ScreenLocationsHelper() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = (int) screenSize.getWidth();
        this.screenHeight = (int) screenSize.getHeight();
    }
    
    private Rectangle getQuarter(int multiplier) {
        int rectWidth = screenWidth / 4;
        int rectX = rectWidth * multiplier;
        return new Rectangle(rectX, 0, rectWidth, screenHeight);
    }

    public Rectangle rightQuarter() {
        return getQuarter(3);
    }

    public Rectangle leftQuarter() {
        return getQuarter(0);
    }

    public Rectangle thirdQuarter() {
        return getQuarter(2);
    }

    public Rectangle centreScreen() {
        int middleThirdWidth = screenWidth / 3;
        int startX = screenWidth / 3; // Start at the beginning of the middle third.
        return new Rectangle(startX, 0, middleThirdWidth, screenHeight);
    }

    public Rectangle entireScreen() {
        return new Rectangle(0, 0, screenWidth, screenHeight);
    }

    public Rectangle statusCircle() {
        int middleThirdWidth = screenWidth / 3;
        int middleThirdHeight = screenHeight / 2; // Bottom half
        int startX = screenWidth / 3; // Start of middle third
        int startY = screenHeight / 2; // Start of bottom half
        return new Rectangle(startX, startY, middleThirdWidth, middleThirdHeight);
    }
}
