package com.caliban.activity;

import java.util.List;

import com.caliban.config.Locations;
import com.caliban.enums.CharacterType;
import com.caliban.enums.Images;
import com.caliban.enums.MinerType;

public class IceMiningActivity extends Mining {

    private static final int IDLE_TIME_MS = 15000;

    private List<String> lockedIceAsteroidTypes = List.of(
        Images.ICE_ICON_LOCKED.toString()
    );

    private List<String> unlockedIceAsteroidTypes = List.of(
        Images.ICE_ICON.toString()
    );

    public void start(int numberCharacters, MinerType minerType) {
        sendAlert("Starting Mining");
        mainLoop.set(true);
        setState("MINING");

        while (mainLoop.get()) {
            boolean shouldManageOre = shouldManageOreThisLoop();
            boolean manageOreCalled = false;

            for (int i = 0; i < numberCharacters && mainLoop.get(); i++) {
                CharacterType characterType = getCharType();
                roidsLeft = countAsteroids(lockedIceAsteroidTypes, unlockedIceAsteroidTypes);
                sendRoidsLeftAlert();
                manageDrones();
                checkForDock();

                switch (characterType) {
                    case BOOST:
                        handleBooster();
                        break;
                    case MINER:
                        if ("MINING".equals(getState()) && roidsLeft > 0) {
                            if (shouldManageOre || cargoManager.isCompressionNeeded()) {
                                manageOre();
                                manageOreCalled = true;
                            }
                            mineOre();
                        }
                        break;
                    default:
                        // DOCKED or UNKNOWN, do nothing
                        break;
                }
                screenActions.nextDesktop();
            }

            updateManageOreProbability(manageOreCalled);
            
            if (mainLoop.get() && "MINING".equals(getState())) {
                performIdleBehavior(minerType);
            }
        }
    }

    @Override
    protected int handleBooster() {
        if (roidsLeft <= ROID_DOCK_THRESHOLD && "DOCKING".equals(getState()) && !boostActivated) {
            actionInterfacer.activateBoostHighslots();
            boostActivated = true;
        }
        return roidsLeft;
    }

    @Override
    protected void sendRoidsLeftAlert() {
        sendAlert("Roids left: " + roidsLeft);
    }

    @Override
    protected void handleMinerLogic() {
        // Implementation for Mackinaw specific miner logic if needed
    }

    @Override
    protected void mineOre() {
        if (lockedRoids == null || lockedRoids.size() <= 2) {
            overviewManager.lockTarget(unlockedRoids);
        }

        int activeMiners = screenActions.countAvailableImage(Locations.targetedItems, Images.ICE_MINER_ACTIVE.toString());

        if (activeMiners < 2) {
            actionInterfacer.activateHighSlots();
            actionInterfacer.activateHighSlots();
        }
    }

    @Override
    protected void manageOre() {
        cargoManager.compressOre();
        if(minerType == MinerType.HULK) {
            cargoManager.moveCargoToFleetHanger();
        }
    }

    @Override
    protected void performIdleBehavior(MinerType minerType) {
        int idleTime = IDLE_TIME_MS;

        if(roidsLeft < 10) {
            idleTime = IDLE_TIME_MS / 3; // Reduce idle time when few asteroids are left
        }

        if(roidsLeft < 5) {
            idleTime = IDLE_TIME_MS / 6; // Further reduce idle time when very few asteroids are left
        }

        if (minerType == MinerType.HULK) {
            actionInterfacer.simulateIdleBehaviour(5000);
        } else if (minerType == MinerType.MACKINAW) {
            actionInterfacer.simulateIdleBehaviour(idleTime);
        }
    }
}