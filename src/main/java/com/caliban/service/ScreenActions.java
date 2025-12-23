package com.caliban.service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.caliban.config.Locations;
import com.caliban.enums.Images;
import com.caliban.helper.ImageMatcher;
import com.caliban.helper.Randomizer;
import com.caliban.helper.ScreenLocationsHelper;

public class ScreenActions {

    private Robot robot;
    private ScreenLocationsHelper screenLocations = new ScreenLocationsHelper();
    private Randomizer randomizer = new Randomizer();
    
    // Constants for slot activity detection
    private static final int SAMPLE_INTERVAL_MS = 300;
    private static final int SAMPLE_COUNT = 5;
    private static final int GREEN_THRESHOLD = 80; // Minimum green value to consider "green pulse"

    // 0.5 = red 243, green 253, blue 130
    Color system5 = new Color(243,253,130);
    // 0.6 = red 113, green 229, blue 84
    Color system6 = new Color(113,229,84);
    // 0.7 = red 97, green 219, blue 164
    Color system7 = new Color(97,219,164);
    // 0.8 = red 78, green 206, blue 248
    Color system8 = new Color(78,206,248);
    // 0.9 = red 57, green 153, blue 233
    Color system9 = new Color(57,153,233);
    // 1.0 = red 44, green 117, blue 226
    Color system1 = new Color(44,117,226);

    public ScreenActions() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public int countTotalRows() {
        return countAvailableImage(Locations.targetList, Images.ASTEROID_OVERVIEW.toString());
    }

    public int countTotalRows(String target) {
        return countAvailableImage(Locations.targetList, target);
    }

    public int countRowsInRange() {
        return countTargetableRowsUnder10KM() + countTargetableRowsBetween10KMand20KM();
    }

    public int countTargetableRowsUnder10KM() {
        return countAvailableImage(Locations.targetList, Images.OVERVIEW_METERS.toString());
    }

    public int countTargetableRowsBetween10KMand20KM() {
        return countAvailableImage(Locations.targetList, Images.TEN_KM.toString());
    }

    public boolean isHighSlot1Active() {
        return isSlotActive(Locations.hislot1GreenActivePixel);
    }

    public boolean isHighSlot2Active() {
        return isSlotActive(Locations.hislot2GreenActivePixel);
    }
    
    /**
     * Detects if a slot is active by looking for the characteristic green pulse.
     * Active slots pulse green, so we sample the pixel a few times to catch the green flash.
     * 
     * @param pixelLocation The point to monitor for green pulses
     * @return true if a green pulse is detected, false otherwise
     */
    private boolean isSlotActive(Point pixelLocation) {
        if (pixelLocation == null) {
            return false;
        }
        
        int x = (int) pixelLocation.getX();
        int y = (int) pixelLocation.getY();
        
        // Sample the pixel multiple times to catch the green pulse
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            Color pixelColor = robot.getPixelColor(x, y);
            
            // Check if this sample shows the green pulse
            if (isGreenPulse(pixelColor)) {
                return true;
            }
            
            // Wait before next sample (except on last iteration)
            if (i < SAMPLE_COUNT - 1) {
                robot.delay(SAMPLE_INTERVAL_MS);
            }
        }
        
        return false;
    }
    
    /**
     * Determines if a color represents the green pulse of an active slot.
     * Active slots pulse with a distinctive green color.
     * 
     * @param color The color to check
     * @return true if the color indicates an active green pulse
     */
    private boolean isGreenPulse(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        
        // Look for green-dominant colors typical of active slot pulses
        return green > GREEN_THRESHOLD && 
               green > red && 
               green > blue;
    }


    private static double getColorDifference(Color color1, Color color2) {
        // Extract RGB components
        int red1 = color1.getRed();
        int green1 = color1.getGreen();
        int blue1 = color1.getBlue();

        int red2 = color2.getRed();
        int green2 = color2.getGreen();
        int blue2 = color2.getBlue();

        // Calculate the Euclidean distance in RGB space
        return Math.sqrt(
                Math.pow(red1 - red2, 2) +
                        Math.pow(green1 - green2, 2) +
                        Math.pow(blue1 - blue2, 2)
        );
    }

    @Deprecated
    public boolean isCompressionNeeded() {
        Color pixelColor = robot.getPixelColor((int)Locations.cargoFullnessBar.getX(), (int)Locations.cargoFullnessBar.getY());

        if (pixelColor.getRed() > 50 || pixelColor.getGreen() > 50 || pixelColor.getBlue() > 50) return true;
            
        return false;
    }

    public boolean isInStation() {
        Rectangle location = this.findImage(screenLocations.rightQuarter(), Images.IN_STATION.toString());
        return (location != null);
    }

    public Rectangle findImage(Rectangle screenLocation, String imageFile) {
        String pathYourSystem = System.getProperty("user.dir");

        return ImageMatcher.findImage(pathYourSystem +"\\src\\main\\resources\\images\\"+ imageFile,
        screenLocation);
    }

    public Rectangle findImageParital(Rectangle screenLocation, String imageFile) {
        String pathYourSystem = System.getProperty("user.dir");

        return ImageMatcher.findImageParital(pathYourSystem +"\\src\\main\\resources\\images\\"+ imageFile,
        screenLocation);
    }

    public ArrayList<Rectangle> findAllImage(Rectangle screenLocation, String imageFile) {
        String pathYourSystem = System.getProperty("user.dir");

        return ImageMatcher.findMatchAll(pathYourSystem +"\\src\\main\\resources\\images\\"+ imageFile,
        screenLocation);
    }

    public int countTargetsAvailable(String target) {
        return countAvailableImage(screenLocations.rightQuarter(), target);
    }

    public int countAvailableImage(Rectangle screenLocation, String imageIcon) {
        return ImageMatcher.findMatchCount(System.getProperty("user.dir") +"\\src\\main\\resources\\images\\"+ imageIcon,
                screenLocation);
    }

    @Deprecated
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

    @Deprecated
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

    public boolean hasRouteSet() {
        Rectangle location = this.findImage(screenLocations.leftQuarter(), Images.ROUTE_CURRENT_LOCATION.toString());
        return (location != null);
    }


    public Point generateRandomPointWithinArea(Rectangle area) {
        int x = randomizer.generateRandom((int)area.getX(), (int)area.getX()+(int)area.getWidth());
        int y = randomizer.generateRandom((int)area.getY(), (int)area.getX()+(int)area.getHeight());

        return new Point(x, y);
        }

    private boolean isDarkPixel(Color color) {
        return (color.getRed() < 50 && color.getBlue() < 50 && color.getGreen() < 50);
    }

    public void displayDesktops() {
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(1000);
    }

    public void firstDesktop() {
        displayDesktops();
        
        robot.mouseMove(100, 990);
        robot.mouseWheel(-6);
        robot.delay(1000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(1000);
    }

    public void nextDesktop() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_RIGHT);

        robot.keyRelease(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(500);
    }

    public void previousDesktop() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_RIGHT);

        robot.keyRelease(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(500);
    }

    public void pressShiftF() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_F);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_F);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.delay(500);
    }

    public void pressShiftR() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_R);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_R);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.delay(500);
    }

    public void pressF() {
        robot.keyPress(KeyEvent.VK_F);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_F);
        robot.delay(100);
    }

    public void pressCtrlAltX() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_X);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_X);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(500);
    }

    public BufferedImage captureImage(Rectangle area) {
        return robot.createScreenCapture(area);
    }
}
