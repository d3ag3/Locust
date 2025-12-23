package com.caliban.checks;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.Random;

import com.caliban.config.ScreenLocations;
import com.caliban.service.ScreenActions;

/**
 * Parent class for all check operations.
 * Provides common functionality and utilities for check classes.
 */
public class Checks {
    
    protected Robot robot;
    protected Random random;
    protected ScreenActions screenActions = new ScreenActions();
    
    /**
     * Constructor initializes the Robot and Random instances.
     */
    public Checks() {
        try {
            this.robot = new Robot();
            this.random = new Random();
        } catch (AWTException e) {
            throw new RuntimeException("Failed to initialize Robot", e);
        }
    }
    
    /**
     * Generates a random point within the specified rectangular area.
     * 
     * @param area the rectangular area to generate a point within
     * @return a random point within the area
     */
    protected Point generateRandomPointWithinArea(Rectangle area) {
        int x = area.x + random.nextInt(area.width);
        int y = area.y + random.nextInt(area.height);
        return new Point(x, y);
    }
    
    /**
     * Checks if a pixel color is considered "dark" (low RGB values).
     * 
     * @param color the color to check
     * @return true if the pixel is dark, false otherwise
     */
    protected boolean isDarkPixel(Color color) {
        // Consider a pixel dark if all RGB values are below 30
        return color.getRed() < 30 && color.getGreen() < 30 && color.getBlue() < 30;
    }
}