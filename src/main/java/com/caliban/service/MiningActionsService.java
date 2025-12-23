package com.caliban.service;

import com.caliban.config.Locations;

public class MiningActionsService extends GeneralActionsService {

    public boolean isAlreadyMining() {
        return screenActions.isHighSlot1Active() && screenActions.isHighSlot2Active();
    }

    public void activateBoostHighslots() {
        clickActivationArea(Locations.hislot1);
        clickActivationArea(Locations.hislot2);
        clickActivationArea(Locations.hislot3);
        clickActivationArea(Locations.hislot4);
        clickActivationArea(Locations.hislot5);
        clickActivationArea(Locations.hislot6);
    }

    public void compressOre() {
        screenActions.pressCtrlAltX();
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
}
