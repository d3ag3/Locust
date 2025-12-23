package com.caliban.service;

public class DroneService extends GeneralActionsService {

    /**
     * Launches drones for the current character by pressing SHIFT+F
     */
    public void launchDrones() {
        screenActions.pressShiftF();
        mouseActions.simulateWait(1000,2000);
    }

    /**
     * Recalls a specific drone back to the drone bay by pressing SHIFT+R
     */
    public void recallDrone() {
        screenActions.pressShiftR();
        mouseActions.simulateWait(1000,2000);
    }

    /**
     * Commands drones to attack a target by pressing F
     */
    public void attack() {
        screenActions.pressF();
        mouseActions.simulateWait(1000,2000);
    }
}