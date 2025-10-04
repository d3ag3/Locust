package com.caliban.helper;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import com.caliban.config.Locations;

public class ScreenLocations {

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Rectangle rightQuarter() {
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        // Calculate rectangle dimensions and position
        int rectWidth = screenWidth / 4;
        int rectHeight = screenHeight;
        int rectX = rectWidth * 3;
        int rectY = 0; 

        return new Rectangle(rectX, rectY, rectWidth, rectHeight);
    }

    public Rectangle leftQuarter() {
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int rectWidth = screenWidth / 4; 
        int rectHeight = screenHeight;
        int rectX = 0;
        int rectY = 0;

        return new Rectangle(rectX, rectY, rectWidth, rectHeight);
    }

    public Rectangle thirdQuarter() {
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        // Calculate rectangle dimensions and position
        int rectWidth = screenWidth / 4;
        int rectHeight = screenHeight;
        int rectX = rectWidth * 2;
        int rectY = 0; 

        return new Rectangle(rectX, rectY, rectWidth, rectHeight);
    }

    public Rectangle centreScreen() {
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int middleThirdWidth = screenWidth / 3;
        int middleThirdHeight = screenHeight; // Full height, middle width.

        int startX = screenWidth / 3; // Start at the beginning of the middle third.
        int startY = 0; // Start at the top.

        return new Rectangle(startX, startY, middleThirdWidth, middleThirdHeight);
    }

    public Rectangle statusCircle() {
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int middleThirdWidth = screenWidth / 3;
        int middleThirdHeight = screenHeight / 2; // Bottom half

        int startX = screenWidth / 3; // Start of middle third
        int startY = screenHeight / 2; // Start of bottom half

        return new Rectangle(startX, startY, middleThirdWidth, middleThirdHeight);
    }

    public Rectangle agentPanel() {
        return new Rectangle(1653, 406, 257, 401);
    }

    public Rectangle stationPanel() {
        return new Rectangle(1653, 0, 257, 800);
    }

    public Rectangle modulePanel() {
        return new Rectangle(1023, 903, 400, 150);
    }

    public Rectangle targetList() {
        double width = Locations.targetListBottom.getX() - Locations.targetListTop.getX();
        double height = Locations.targetListBottom.getY() - Locations.targetListTop.getY();
        return new Rectangle((int) Locations.targetListTop.getX(), (int) Locations.targetListTop.getY(),
                (int) width, (int) height);
    }

    public Rectangle chatArea() {
        return new Rectangle(40, 785, 445, 293); 
    }
}
