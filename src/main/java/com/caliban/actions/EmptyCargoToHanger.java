package com.caliban.actions;

import com.caliban.checks.CargoChecks;

/**
 * Action class for emptying cargo to hangar.
 */
public class EmptyCargoToHanger extends Action {
    
    CargoChecks cargoChecks = new CargoChecks();
    OpenCargoHold openCargoHold = new OpenCargoHold();

    public void preCheck() {
        //CargoHold is open
            //If not open, open it
        openCargoHold.action();
        //Hanger is open
            //If not open, open it
        //Cargo is present in hold
            //If not present, abort action
    }
    
    public void postCheck() {
        //Cargo hold is empty
    }
}