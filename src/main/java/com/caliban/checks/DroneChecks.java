package com.caliban.checks;

import com.caliban.config.ScreenLocations;

/**
 * Class for performing cargo-related checks.
 */
public class DroneChecks extends Checks {
    
    public boolean isDroneIdle() {
        return screenActions.findImage(ScreenLocations.droneWindow, "idleDrone.png") != null;
    }
}