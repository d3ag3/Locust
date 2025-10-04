package com.caliban.service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.caliban.config.Locations;
import com.caliban.helper.ImageMatcher;
import com.caliban.helper.Randomizer;
import com.caliban.helper.ScreenLocations;

public class ScreenActions {

    private Robot robot;
    private ScreenLocations screenLocations = new ScreenLocations();
    private Randomizer randomizer = new Randomizer();

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
        return countAvailableImage(screenLocations.targetList(), "asteroidOverview.png");
    }

    public int countRowsInRange() {
        return countTargetableRowsUnder10KM() + countTargetableRowsBetween10KMand20KM();
    }

    public int countTargetableRowsUnder10KM() {
        return countAvailableImage(screenLocations.targetList(), "overviewMeters.png");
    }

    public int countTargetableRowsBetween10KMand20KM() {
        return countAvailableImage(screenLocations.targetList(), "10km.png");
    }

    public boolean isHighSlot1Active() {
        int interval = 300;
        int differenceThreshold = 10;
        Color pixelColor1 = robot.getPixelColor((int)Locations.hislot1GreenActivePixel.getX(), (int)Locations.hislot1GreenActivePixel.getY());
        robot.delay(interval);
        Color pixelColor2 = robot.getPixelColor((int)Locations.hislot1GreenActivePixel.getX(), (int)Locations.hislot1GreenActivePixel.getY());
        robot.delay(interval);
        Color pixelColor3 = robot.getPixelColor((int)Locations.hislot1GreenActivePixel.getX(), (int)Locations.hislot1GreenActivePixel.getY());
        robot.delay(interval);
        Color pixelColor4 = robot.getPixelColor((int)Locations.hislot1GreenActivePixel.getX(), (int)Locations.hislot1GreenActivePixel.getY());

        if (getColorDifference(pixelColor1, pixelColor2) > differenceThreshold || getColorDifference(pixelColor1, pixelColor3) > differenceThreshold || getColorDifference(pixelColor1, pixelColor4) > differenceThreshold) return true;

        return false;
    }

    public boolean isHighSlot2Active() {
        int interval = 300;
        int differenceThreshold = 10;
        Color pixelColor1 = robot.getPixelColor((int)Locations.hislot2GreenActivePixel.getX(), (int)Locations.hislot2GreenActivePixel.getY());
        robot.delay(interval);
        Color pixelColor2 = robot.getPixelColor((int)Locations.hislot2GreenActivePixel.getX(), (int)Locations.hislot2GreenActivePixel.getY());
        robot.delay(interval);
        Color pixelColor3 = robot.getPixelColor((int)Locations.hislot2GreenActivePixel.getX(), (int)Locations.hislot2GreenActivePixel.getY());
        robot.delay(interval);
        Color pixelColor4 = robot.getPixelColor((int)Locations.hislot2GreenActivePixel.getX(), (int)Locations.hislot2GreenActivePixel.getY());

        if (getColorDifference(pixelColor1, pixelColor2) > differenceThreshold || getColorDifference(pixelColor1, pixelColor3) > differenceThreshold || getColorDifference(pixelColor1, pixelColor4) > differenceThreshold) return true;

        return false;    }


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

    public boolean isCompressionNeeded() {
        Color pixelColor = robot.getPixelColor((int)Locations.cargoFullnessBar.getX(), (int)Locations.cargoFullnessBar.getY());

        if (pixelColor.getRed() > 50 || pixelColor.getGreen() > 50 || pixelColor.getBlue() > 50) return true;
            
        return false;
    }

    public boolean isInStation() {
        Rectangle location = this.findImage(screenLocations.rightQuarter(), "inStation.png");
        return (location != null);
    }

    public Rectangle findImage(Rectangle screenLocation, String imageFile) {
        String pathYourSystem = System.getProperty("user.dir");
        //System.out.println("searching for:" + imageFile);
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

    public boolean hasItemInHanger() {
        //wait a second for item to show up
        robot.delay(1000);
        Point point1 = generateRandomPointWithinArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);
        Point point2 = generateRandomPointWithinArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);
        Point point3 = generateRandomPointWithinArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);
        Point point4 = generateRandomPointWithinArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);
        Point point5 = generateRandomPointWithinArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);

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
        Point point1 = generateRandomPointWithinArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        Point point2 = generateRandomPointWithinArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        Point point3 = generateRandomPointWithinArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        Point point4 = generateRandomPointWithinArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        Point point5 = generateRandomPointWithinArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);

        Color pixelColor1 = robot.getPixelColor((int)point1.getX(), (int)point1.getY());
        Color pixelColor2 = robot.getPixelColor((int)point2.getX(), (int)point2.getY());
        Color pixelColor3 = robot.getPixelColor((int)point3.getX(), (int)point3.getY());
        Color pixelColor4 = robot.getPixelColor((int)point4.getX(), (int)point4.getY());
        Color pixelColor5 = robot.getPixelColor((int)point5.getX(), (int)point5.getY());

        return (!isDarkPixel(pixelColor1) || !isDarkPixel(pixelColor2) || !isDarkPixel(pixelColor3) || !isDarkPixel(pixelColor4) || !isDarkPixel(pixelColor5));
    }

    public boolean hasRouteSet() {
        Rectangle location = this.findImage(screenLocations.leftQuarter(), "routeCurrentLocation.png");
        return (location != null);
    }

    private Point generateRandomPointWithinArea(Point start, Point end) {
        int x = randomizer.generateRandom((int)start.getX(), (int)end.getX());
        int y = randomizer.generateRandom((int)start.getY(), (int)end.getY());
        return new Point(x,y);
    }

    private boolean isDarkPixel(Color color) {
        return (color.getRed() < 50 && color.getBlue() < 50 && color.getGreen() < 50);
    }

    public void firstDesktop() {
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(1000);
        robot.mouseMove(100, 990);
        robot.mouseWheel(-3);
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

    public BufferedImage captureImage(Rectangle area) {
        return robot.createScreenCapture(area);
    }
}
