package com.caliban.service;

import com.caliban.config.Locations;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;
import java.awt.*;

public class MiningActionsService extends GeneralActionsService {

    public boolean isAlreadyMining() {
        return screenActions.isHighSlot1Active() && screenActions.isHighSlot2Active();
    }

    public void targetRandomAsteroids() {

        int numRowsAvailable = screenActions.countTotalRows();

        // Generate a random number of targets to click
        int randomClicks = 2;
        if (numRowsAvailable > 5) randomClicks = randomizer.generateRandom(2, 7);

        Set<Integer> randomRows = new HashSet<>();

        for (int i = 0; i < randomClicks; i++) {
            randomRows.add(randomizer.generateRandom(1, numRowsAvailable));
        }

        // Convert to a list so the rows can be sorted
        List<Integer> rowList = new ArrayList<>(randomRows);
        Collections.sort(rowList);

        mouseActions.holdControl();
        for (Integer row : rowList) {
            int rowLocation = row * 19; // Rows are 19 pixels apart

            // Minus 5 pixels to move towards top of row
            int startActivateX = (int) Locations.targetListTop.getX();
            int startActivateY = (int) Locations.targetListTop.getY() + rowLocation - 5;

            int endActiveX = startActivateX + 7;
            int endActiveY = startActivateY + 100;

            clickActivationArea(new Point(startActivateX, startActivateY), new Point(endActiveX, endActiveY));
        }
        mouseActions.releaseControl();
    }

    public void activateBoostHighslots() {
        clickActivationArea(Locations.hislot1Start, Locations.hislot1End);
        clickActivationArea(Locations.hislot2Start, Locations.hislot2End);
        clickActivationArea(Locations.hislot3Start, Locations.hislot3End);
        clickActivationArea(Locations.hislot4Start, Locations.hislot4End);
        clickActivationArea(Locations.hislot5Start, Locations.hislot5End);
        clickActivationArea(Locations.hislot6Start, Locations.hislot6End);
    }

    public void compressOre() {
        if (!screenActions.isCompressionNeeded()) {
            return;
        }

        activateCargoHoldAndSelectAll();

        Rectangle compressMenuItemLocation = findCompressMenuItemWithRetry();

        if (compressMenuItemLocation == null) {
            return; // Compress menu item is still missing somehow.  Ignore.
        }

        clickCompressMenuItem(compressMenuItemLocation);
    }

    private Rectangle findCompressMenuItemWithRetry() {
        Rectangle compressMenuItemLocation = findCompressMenuItem();
        if (compressMenuItemLocation == null) {
            // Compress selection can be lost if ore is deposited while selected. Try again
            activateCargoHoldAndSelectAll();
            compressMenuItemLocation = findCompressMenuItem();
        }
        return compressMenuItemLocation;
    }

    private Rectangle findCompressMenuItem() {
        rightClickActivationArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        mouseActions.simulateWait(100, 500);
        return screenActions.findImage(screenLocations.leftQuarter(),"compressMenuItem.png");
    }

    private void clickCompressMenuItem(Rectangle compressMenuItemLocation) {
        Point compressMenuStart = new Point((int) compressMenuItemLocation.getX(), (int) compressMenuItemLocation.getY());
        Point compressMenuEnd = new Point(
                (int) (compressMenuItemLocation.getX() + compressMenuItemLocation.getWidth()),
                (int) (compressMenuItemLocation.getY() + compressMenuItemLocation.getHeight())
        );
        clickActivationArea(compressMenuStart, compressMenuEnd);
    }

    // Simulate random mouse activity
    public void simulateIdleBehaviour(int defaultWait) {
        int randomChoice = randomizer.generateRandom(1, 10);
        switch (randomChoice) {
            case 1:
                mouseActions.simulateWait(5000, 30000);
                break;
            case 2:
                mouseActions.simulateMouseWheelFiddle();
                break;
            case 3:
                mouseActions.simulateMouseWiggle();
                break;
            default:
                mouseActions.simulateWait(defaultWait, defaultWait*2);
                break;
        }
    }
    
    public void positionCharacter() {
        int totalRows = screenActions.countTotalRows();
        if (totalRows <= 3) {
            //warp to station
            return;
        }
        int targetableRows = screenActions.countRowsInRange();

        if (targetableRows <=3) {
            //move ship
        }
    }
}
