package com.caliban.service;

import com.caliban.enums.Images;
import com.caliban.model.AutomatedCharacter;
import com.caliban.config.Locations;
import com.caliban.helper.Randomizer;
import com.caliban.helper.ScreenLocationsHelper;

import java.util.ArrayList;
import java.awt.*;

public class GeneralActionsService {

    protected Randomizer randomizer = new Randomizer();

    protected MouseActions mouseActions = new MouseActions();

    protected ScreenActions screenActions = new ScreenActions();

    protected ScreenLocationsHelper screenLocations = new ScreenLocationsHelper();

    public void switchToCharacter(AutomatedCharacter character) {

        mouseActions.simulateWait(2000,3000);

        clickActivationArea(character.getActivationBoundry());
    }

    public void targetAll(String target, int maxTargets) {
        ArrayList<Rectangle> matches = screenActions.findAllImage(Locations.targetList, target);
        if (matches.size() == 0) { return;}
        //reduce list down to max size
        while (matches.size() > maxTargets) {
            matches.remove(matches.size() - 1);
        }

        mouseActions.holdControl();
        mouseActions.simulateWait(500,1000);
        for (Rectangle match : matches) {
            clickActivationArea(match);
            mouseActions.simulateWait(250,500);
        }
        mouseActions.releaseControl();
    }

    public void activateHighSlots() {
        mouseActions.simulateWait(500, 1000);
        if (!screenActions.isHighSlot1Active()) activateHighSlot1();
        if (!screenActions.isHighSlot2Active()) activateHighSlot2();
    }

    public void activateHighSlotsMultipleTargets() {
        mouseActions.simulateWait(500, 1000);
        if (!screenActions.isHighSlot1Active()) {
            activateFirstTargetedItem();
            activateHighSlot1();
        }
        if (!screenActions.isHighSlot2Active()) {
            activateSecondTargetedItem();
            activateHighSlot2();
        }
    }

    private void activateFirstTargetedItem() {
        clickActivationArea(Locations.lockedTarget1);
    }

    private void activateSecondTargetedItem() {
        clickActivationArea(Locations.lockedTarget2);
    }

    private void activateHighSlot1() {
        //clickActivationArea(Locations.hislot1);
        mouseActions.pressF1();
    }

    private void activateHighSlot2() {
        //clickActivationArea(Locations.hislot2);
        mouseActions.pressF2();
    }


    protected void activateCargoHoldAndSelectAll() {
        clickActivationArea(Locations.cargoHoldActivate);
        mouseActions.selectAll();
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

    public void openCargoHold() {
        Rectangle cargoLocation = screenActions.findImage(screenLocations.leftQuarter(), Images.CARGO_OPEN.toString());
        if (cargoLocation==null) doubleClickActivationArea(Locations.cargoHoldActivate);
    }

    public void emptyCargo() {
        if (!screenActions.hasItemInCargo()) return;

        activateCargoHoldAndSelectAll();
        mouseMoveToArea(Locations.cargoHoldItem1);
        mouseActions.hold();
        mouseMoveToArea(Locations.firstItemHanger);
        mouseActions.release();
    }

    public void moveFirstHangerItemToCargoHold() {
        if (!screenActions.hasItemInHanger()) return;
        // check if cargo hold is open
        openCargoHold();
        // move cargo item to hold
        mouseMoveToArea(Locations.firstItemHanger);
        mouseActions.hold();
        mouseMoveToArea(Locations.cargoHoldActivate);
        mouseActions.release();
    }

    public void undock() {
        clickActivationArea(Locations.stationUndock);
    }

    public void warpToRouteDestination() {
        rightClickActivationArea(Locations.firstRouteSystem);

        Rectangle jump = screenActions.findImage(screenLocations.leftQuarter(), Images.ROUTE_JUMP_THROUGH_STARGATE.toString());

        if(jump != null) {
            clickActivationArea(jump);
            return;
        }

        Rectangle dock = screenActions.findImage(screenLocations.leftQuarter(), Images.ROUTE_DOCK.toString());

        if(dock != null) {
            clickActivationArea(dock);
        }
    }

    public void activateHomeBookmark() {
        rightClickActivationArea(Locations.bookmarkHome);

        Rectangle dock = screenActions.findImage(screenLocations.rightQuarter(), Images.BOOKMARK_HOME_DOCK.toString());

        if(dock != null) {
            clickActivationArea(dock);
            return;
        }

        Rectangle setDestination = screenActions.findImage(screenLocations.rightQuarter(), Images.BOOKMARK_HOME_SET_DESTINATION.toString());

        if(setDestination != null) {
            clickActivationArea(setDestination);
        }
    }

        public void activateAgentHomeBookmark() {
        rightClickActivationArea(Locations.agentBookmarkHome);

        Rectangle dock = screenActions.findImage(screenLocations.rightQuarter(), Images.BOOKMARK_HOME_DOCK.toString());

        if(dock != null) {
            clickActivationArea(dock);
            return;
        }

        Rectangle setDestination = screenActions.findImage(screenLocations.rightQuarter(), Images.BOOKMARK_HOME_SET_DESTINATION.toString());

        if(setDestination != null) {
            clickActivationArea(setDestination);
        }
    }

    protected void clickActivationArea(Rectangle area) {
        mouseActions.simulateWait(100,500);
        mouseMoveToArea(area);
        mouseActions.click();
    }

    protected void rightClickActivationArea(Rectangle area) {
        mouseActions.simulateWait(100,500);
        mouseMoveToArea(area);
        mouseActions.rightClick();
    }

    protected void doubleClickActivationArea(Rectangle area) {
        mouseActions.simulateWait(100,500);
        mouseMoveToArea(area);
        mouseActions.doubleClick();
    }

    protected Point mouseMoveToArea(Rectangle area) {
        int x = randomizer.generateRandom((int)area.getX(), (int)area.getX()+(int)area.getWidth());
        int y = randomizer.generateRandom((int)area.getY(), (int)area.getY()+(int)area.getHeight());

        mouseActions.moveMouse(x, y);

        return new Point(x,y);
    }
}
