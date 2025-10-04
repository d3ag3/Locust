package com.caliban.service;

import com.caliban.model.AutomatedCharacter;
import com.caliban.config.Locations;
import com.caliban.helper.Randomizer;
import com.caliban.helper.ScreenLocations;

import java.util.ArrayList;
import java.awt.*;

public class GeneralActionsService {

    protected Randomizer randomizer = new Randomizer();

    protected MouseActions mouseActions = new MouseActions();

    protected ScreenActions screenActions = new ScreenActions();

    protected ScreenLocations screenLocations = new ScreenLocations();

    public void switchToCharacter(AutomatedCharacter character) {

        mouseActions.simulateWait(2000,3000);

        clickActivationArea(character.getActivationBoundryStart(),
                character.getActivationBoundryEnd());
    }

    public void targetAll(String target, int maxTargets) {
        // Define the area to check
        double width = Locations.targetListBottom.getX() - Locations.targetListTop.getX();
        double height = Locations.targetListBottom.getY() - Locations.targetListTop.getY();
        Rectangle area = new Rectangle((int) Locations.targetListTop.getX(), (int) Locations.targetListTop.getY(),
                (int) width, (int) height);

        ArrayList<Rectangle> matches = screenActions.findAllImage(area, target);
        
        //reduce list down to max size
        while (matches.size() > maxTargets) {
            matches.remove(matches.size() - 1);
        }

        mouseActions.holdControl();
        mouseActions.simulateWait(500,1000);
        for (Rectangle match : matches) {
            Point start = new Point((int)match.getX(), (int)match.getY());
            Point end = new Point((int)(match.getX()+match.getWidth()), (int)(match.getY()+match.getHeight()));
            clickActivationArea(start, end);
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
        clickActivationArea(Locations.lockedTarget1Start, Locations.lockedTarget1End);
    }

    private void activateSecondTargetedItem() {
        clickActivationArea(Locations.lockedTarget2Start, Locations.lockedTarget2End);
    }

    private void activateHighSlot1() {
        clickActivationArea(Locations.hislot1Start, Locations.hislot1End);
    }

    private void activateHighSlot2() {
        clickActivationArea(Locations.hislot2Start, Locations.hislot2End);
    }


    protected void activateCargoHoldAndSelectAll() {
        clickActivationArea(Locations.cargoHoldActivateStart,Locations.cargoHoldActiveEnd);
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
        Rectangle cargoLocation = screenActions.findImage(screenLocations.leftQuarter(),"cargoOpen.png");
        if (cargoLocation==null) doubleClickActivationArea(Locations.cargoHoldActivateStart,Locations.cargoHoldActiveEnd);
    }

    public void emptyCargo() {
        if (!screenActions.hasItemInCargo()) return;

        activateCargoHoldAndSelectAll();
        mouseMoveToArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        mouseActions.hold();
        mouseMoveToArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);
        mouseActions.release();
    }

    public void moveFirstHangerItemToCargoHold() {
        if (!screenActions.hasItemInHanger()) return;
        // check if cargo hold is open
        openCargoHold();
        // move cargo item to hold
        mouseMoveToArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);
        mouseActions.hold();
        mouseMoveToArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        mouseActions.release();
    }

    public void moveFirstCargoHoldItemToHanger() {
        if (!screenActions.hasItemInHanger()) return;
        // check if cargo hold is open
        openCargoHold();
        // move cargo item to hold
        mouseMoveToArea(Locations.cargoHoldItem1Start, Locations.cargoHoldItem1End);
        mouseActions.hold();
        mouseMoveToArea(Locations.firstItemHangerStart, Locations.firstItemHangerEnd);
        mouseActions.release();
    }

    public void undock() {
        clickActivationArea(Locations.stationUndockStart, Locations.stationUndockEnd);
    }


    public void warpToRouteDestination() {
        rightClickActivationArea(Locations.firstRouteSystemStart, Locations.firstRouteSystemEnd);

        Rectangle jump = screenActions.findImage(screenLocations.leftQuarter(),"routeJumpThroughStargate.png");

        if(jump != null) {
            Point start = new Point((int) jump.getX(), (int) jump.getY());
            Point end = new Point((int) (jump.getX() + jump.getWidth()), 
                                                (int) (jump.getY() + jump.getHeight()));
    
            clickActivationArea(start, end);
            return;
        }

        Rectangle dock = screenActions.findImage(screenLocations.leftQuarter(),"routeDock.png");

        if(dock != null) {
            Point start = new Point((int) dock.getX(), (int) dock.getY());
            Point end = new Point((int) (dock.getX() + dock.getWidth()), 
                                                (int) (dock.getY() + dock.getHeight()));
    
            clickActivationArea(start, end);
        }
    }

    public void activateHomeBookmark() {
        rightClickActivationArea(Locations.bookmarkHomeStart, Locations.bookmarkHomeEnd);

        Rectangle dock = screenActions.findImage(screenLocations.rightQuarter(),"bookmarkHomeDock.png");

        if(dock != null) {
            Point start = new Point((int) dock.getX(), (int) dock.getY());
            Point end = new Point((int) (dock.getX() + dock.getWidth()), 
                                                (int) (dock.getY() + dock.getHeight()));
    
            clickActivationArea(start, end);
            return;
        }

        Rectangle setDestination = screenActions.findImage(screenLocations.rightQuarter(),"bookmarkHomeSetDestination.png");

        if(setDestination != null) {
            Point start = new Point((int) setDestination.getX(), (int) setDestination.getY());
            Point end = new Point((int) (setDestination.getX() + setDestination.getWidth()), 
                                                (int) (setDestination.getY() + setDestination.getHeight()));
    
            clickActivationArea(start, end);
        }
    }

    protected void clickActivationArea(Point start, Point end) {
        mouseActions.simulateWait(100,500);
        mouseMoveToArea(start, end);
        mouseActions.click();
    }

    protected void rightClickActivationArea(Point start, Point end) {
        mouseActions.simulateWait(100,500);
        mouseMoveToArea(start, end);
        mouseActions.rightClick();
    }

    protected void doubleClickActivationArea(Point start, Point end) {
        mouseActions.simulateWait(100,500);
        mouseMoveToArea(start, end);
        mouseActions.doubleClick();
    }

    protected Point mouseMoveToArea(Point start, Point end) {
        int characterActivateX = randomizer.generateRandom((int)start.getX(), (int)end.getX());
        int characterActivateY = randomizer.generateRandom((int)start.getY(), (int)end.getY());

        mouseActions.moveMouse(characterActivateX, characterActivateY);

        return new Point(characterActivateX,characterActivateY);
    }
}
