package com.caliban.actions;

import java.awt.event.KeyEvent;
import com.caliban.checks.CargoChecks;
import com.caliban.config.Locations;
import com.caliban.enums.CharacterType;
import com.caliban.enums.MinerType;

/**
 * Unified class for managing cargo-related actions including opening cargo hold,
 * opening hangar, and emptying cargo to hangar.
 */
public class CargoManager extends Action {
    
    private final CargoChecks cargoChecks = new CargoChecks();

    public void openOreHold() {
        if (cargoChecks.isOreHoldOpen()) {
            return;
        }

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.delay(randomizer.generateRandom(100, 300));
        robot.keyPress(KeyEvent.VK_C);
        robot.delay(randomizer.generateRandom(100, 500));
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        
        robot.delay(randomizer.generateRandom(100, 500));

        if (!cargoChecks.isOreHoldOpen()) {
            System.out.println("Failed to open Ore hold.");
        }
    }

    public void openFleetHangar(CharacterType charType) {
        if (cargoChecks.isFleetHangerOpen()) { 
            return;
        }

        if(charType.equals(CharacterType.MINER)) {
            minerOpenFleetHanger();
        } else if (charType.equals(CharacterType.BOOST)) {
            boostOpenFleetHanger();
        } else {
            System.out.println("Ship type not recognized for opening hangar.");
            return;
        }

        if (!cargoChecks.isFleetHangerOpen()) {
            System.out.println("Failed to open hangar.");
        }
    }

    public void compressOre() {
        screenActions.pressCtrlAltX();
    }

    private void minerOpenFleetHanger() {
        //Activate Orca tab
        //Right click on Orca
        //Select "Open Fleet Hanger"
        //Active Mining tab
    }

    private void boostOpenFleetHanger() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.delay(randomizer.generateRandom(100, 300));
        robot.keyPress(KeyEvent.VK_H);
        robot.delay(randomizer.generateRandom(100, 500));
        robot.keyRelease(KeyEvent.VK_H);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        
        robot.delay(randomizer.generateRandom(100, 500));
    }

    public boolean isCompressionNeeded() {
        return cargoChecks.isCompressionNeeded();
    }

    public void moveCargoToFleetHanger() {
        openOreHold();
        //openFleetHangar(charType);

        if (!cargoChecks.hasItemInCargo()) {
            System.out.println("No items in cargo to move.");
            return;
        }

        mouseActions.moveMouse(Locations.cargoHoldItem1);
        mouseActions.click();
        mouseActions.selectAll();

        // Drag and drop from cargo to fleet hanger
        mouseActions.dragAndDrop(Locations.cargoHoldItem1, Locations.firstItemFleetHanger);

        robot.delay(randomizer.generateRandom(500, 1000));
    }

        public void moveCargoFromFleetHanger() {
        openOreHold();
        //openFleetHangar(charType);

        if (!cargoChecks.hasIteminFleetHanger()) {
            System.out.println("No items in cargo to move.");
            return;
        }

        mouseActions.moveMouse(Locations.firstItemFleetHanger);
        mouseActions.click();
        mouseActions.selectAll();

        // Drag and drop from cargo to fleet hanger
        mouseActions.dragAndDrop(Locations.firstItemFleetHanger, Locations.cargoHoldItem1);

        robot.delay(randomizer.generateRandom(500, 1000));
    }
}