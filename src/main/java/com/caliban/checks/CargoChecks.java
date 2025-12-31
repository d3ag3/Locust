package com.caliban.checks;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.caliban.config.Locations;
import com.caliban.config.ScreenLocations;

/**
 * Class for performing cargo-related checks.
 */
public class CargoChecks extends Checks {
    
    private static final int SAMPLE_POINTS = 5;
    private static final int ITEM_CHECK_DELAY = 500;
    
    /**
     * Checks if cargo hold or ore hold is open.
     * Both use the same image detection.
     */
    public boolean isCargoHoldOpen() {
        return screenActions.findImage(ScreenLocations.cargoHold, "miningHold.png") != null;
    }
    
    /**
     * Alias for isCargoHoldOpen() since they detect the same image.
     */
    public boolean isOreHoldOpen() {
        return screenActions.findImage(ScreenLocations.cargoHold, "miningHold.png") != null;
    }

    /**
     * Checks if fleet hanger is open.
     */
    public boolean isFleetHangerOpen() {
        return screenActions.findImage(ScreenLocations.cargoHold, "fleetHanger.png") != null;
    }

    /**
     * Checks if compression is needed by examining the cargo fullness bar color.
     */
    public boolean isCompressionNeeded() {
        Color pixelColor = robot.getPixelColor((int)Locations.cargoFullnessBar.getX(), (int)Locations.cargoFullnessBar.getY());
        return pixelColor.getRed() > 50 || pixelColor.getGreen() > 50 || pixelColor.getBlue() > 50;
    }

    /**
     * Checks if there's an item in the hanger.
     */
    public boolean hasItemInHanger() {
        return hasItemInArea(Locations.firstItemHanger);
    }

    /**
     * Checks if there's an item in cargo.
     */
    public boolean hasItemInCargo() {
        return hasItemInArea(Locations.cargoHoldItem1);
    }

    /**
     * Checks if there's an item in ore hanger.
     */
    public boolean hasItemInOreHanger() {
        return hasItemInArea(Locations.firstItemOreHanger);
    }

    /**
     * Checks if there's an item in fleet hanger.
     */
    public boolean hasIteminFleetHanger() {
        return hasItemInArea(Locations.firstItemFleetHanger);
    }

        /**
     * Generic method to check if an item exists in a specified area by sampling multiple random points.
     * 
     * @param area The rectangle area to sample points from
     * @return true if any of the sampled points contain a non-dark pixel (indicating an item)
     */
    private boolean hasItemInArea(Rectangle area) {
        robot.delay(ITEM_CHECK_DELAY); // Wait for item to show up
        
        for (int i = 0; i < SAMPLE_POINTS; i++) {
            Point point = generateRandomPointWithinArea(area);
            Color pixelColor = robot.getPixelColor((int)point.getX(), (int)point.getY());
            if (!isDarkPixel(pixelColor)) {
                return true; // Found an item
            }
        }
        return false; // No items found
    }
}