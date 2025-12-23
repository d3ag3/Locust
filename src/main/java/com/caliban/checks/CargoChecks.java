package com.caliban.checks;

import java.awt.Color;
import java.awt.Point;

import com.caliban.config.Locations;
import com.caliban.config.ScreenLocations;

/**
 * Class for performing cargo-related checks.
 */
public class CargoChecks extends Checks {
    
    /**
     * Checks if cargo hold is open.
     */
    public boolean isCargoHoldOpen() {
        return screenActions.findImage(ScreenLocations.leftQuarter, "miningHold.png") != null;
    }

    /**
     * Checks if ore hold is open.
     */
    public boolean isOreHoldOpen() {
        return screenActions.findImage(ScreenLocations.leftQuarter, "miningHold.png") != null;
    }
    
    /**
     * Checks if cargo is present in the hold.
     */
    public boolean isCargoPresent() {
        return true; // TODO: Implement cargo presence check
    }
    
    /**
     * Checks if cargo hold is empty.
     */
    public boolean isCargoHoldEmpty() {
        return true; // TODO: Implement cargo hold empty check
    }

    public boolean isCompressionNeeded() {
        Color pixelColor = robot.getPixelColor((int)Locations.cargoFullnessBar.getX(), (int)Locations.cargoFullnessBar.getY());

        if (pixelColor.getRed() > 50 || pixelColor.getGreen() > 50 || pixelColor.getBlue() > 50) return true;
            
        return false;
    }

    public boolean hasItemInHanger() {
        //wait a second for item to show up
        robot.delay(1000);
        Point point1 = generateRandomPointWithinArea(Locations.firstItemHanger);
        Point point2 = generateRandomPointWithinArea(Locations.firstItemHanger);
        Point point3 = generateRandomPointWithinArea(Locations.firstItemHanger);
        Point point4 = generateRandomPointWithinArea(Locations.firstItemHanger);
        Point point5 = generateRandomPointWithinArea(Locations.firstItemHanger);

        Color pixelColor1 = robot.getPixelColor((int)point1.getX(), (int)point1.getY());
        Color pixelColor2 = robot.getPixelColor((int)point2.getX(), (int)point2.getY());
        Color pixelColor3 = robot.getPixelColor((int)point3.getX(), (int)point3.getY());
        Color pixelColor4 = robot.getPixelColor((int)point4.getX(), (int)point4.getY());
        Color pixelColor5 = robot.getPixelColor((int)point5.getX(), (int)point5.getY());

        return (!isDarkPixel(pixelColor1) || !isDarkPixel(pixelColor2) || !isDarkPixel(pixelColor3) || !isDarkPixel(pixelColor4) || !isDarkPixel(pixelColor5));
    }

    public boolean hasItemInCargo() {
        //wait a second for item to show up
        robot.delay(1000);
        Point point1 = generateRandomPointWithinArea(Locations.cargoHoldItem1);
        Point point2 = generateRandomPointWithinArea(Locations.cargoHoldItem1);
        Point point3 = generateRandomPointWithinArea(Locations.cargoHoldItem1);
        Point point4 = generateRandomPointWithinArea(Locations.cargoHoldItem1);
        Point point5 = generateRandomPointWithinArea(Locations.cargoHoldItem1);

        Color pixelColor1 = robot.getPixelColor((int)point1.getX(), (int)point1.getY());
        Color pixelColor2 = robot.getPixelColor((int)point2.getX(), (int)point2.getY());
        Color pixelColor3 = robot.getPixelColor((int)point3.getX(), (int)point3.getY());
        Color pixelColor4 = robot.getPixelColor((int)point4.getX(), (int)point4.getY());
        Color pixelColor5 = robot.getPixelColor((int)point5.getX(), (int)point5.getY());

        return (!isDarkPixel(pixelColor1) || !isDarkPixel(pixelColor2) || !isDarkPixel(pixelColor3) || !isDarkPixel(pixelColor4) || !isDarkPixel(pixelColor5));
    }
}