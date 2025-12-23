package com.caliban.actions;

import java.awt.event.KeyEvent;

import com.caliban.checks.CargoChecks;

/**
 * Class for performing cargo-related actions.
 */
public class OpenHanger extends Action {
    
    CargoChecks cargoChecks = new CargoChecks();

    private boolean preCheck() {
        return cargoChecks.isCargoHoldOpen();
    }
    

    private boolean postCheck() {
        return cargoChecks.isCargoHoldOpen();
    }

    /**
     * Opens the ore hold by pressing Ctrl+Alt+C.
     */
    public void action() {

        if (preCheck()) { return;}

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.delay(randomizer.generateRandom(100, 300));
        robot.keyPress(KeyEvent.VK_C);
        robot.delay(randomizer.generateRandom(100, 500));
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        
        robot.delay(randomizer.generateRandom(100, 500));

        if(!postCheck()) {System.out.println("Failed to open cargo hold.");}
    }
}