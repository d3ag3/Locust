package com.caliban.actions;

import java.awt.AWTException;
import java.awt.Robot;

import com.caliban.helper.Randomizer;
import com.caliban.helper.ScreenLocationsHelper;
import com.caliban.service.MouseActions;
import com.caliban.service.ScreenActions;

/**
 * Parent class for all action operations.
 * Provides common functionality and utilities for action classes.
 */
public class Action {
    
    protected Robot robot;
        protected Randomizer randomizer = new Randomizer();
        protected MouseActions mouseActions = new MouseActions();
        protected ScreenActions screenActions = new ScreenActions();
        protected ScreenLocationsHelper screenLocations = new ScreenLocationsHelper();
    
    /**
     * Constructor initializes the Robot instance.
     */
    public Action() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Failed to initialize Robot", e);
        }
    }
}