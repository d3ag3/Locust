package com.caliban.actions;

import com.caliban.checks.DroneChecks;
import com.caliban.service.DroneService;

/**
 * Unified class for managing cargo-related actions including opening cargo hold,
 * opening hangar, and emptying cargo to hangar.
 */
public class DroneManager extends Action {
    
    private final DroneChecks droneChecks = new DroneChecks();
    private final DroneService droneService = new DroneService();

    public void recallDrone() {
        if (droneChecks.isDroneIdle()) {   
            droneService.recallDrone();
        }
    }
}