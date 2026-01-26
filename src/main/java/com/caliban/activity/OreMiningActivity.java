package com.caliban.activity;

import java.util.List;

import com.caliban.config.Locations;
import com.caliban.enums.CharacterType;
import com.caliban.enums.Images;
import com.caliban.enums.MinerType;
import com.caliban.enums.Status;

public class OreMiningActivity extends Mining {

    private static final int ROID_DOCK_THRESHOLD = 2;
    private static final int IDLE_TIME_MS = 15000;

    private List<String> lockedOreAsteroidTypes = List.of(
        "overviewTargeted.png"
    );

    private List<String> unlockedOreAsteroidTypes = List.of(
        Images.ASTEROID_SMALL.toString(), Images.ASTEROID_MEDIUM.toString(), Images.ASTEROID_LARGE.toString()
    );

    public void start(int numberCharacters, MinerType minerType) {
        this.minerType = minerType;
        sendAlert("Starting Mining");
        mainLoop.set(true);
        status = Status.MINING;

        while (mainLoop.get()) {
            screenActions.firstDesktop();
            
            boolean shouldManageOre = shouldManageOreThisLoop();
            boolean manageOreCalled = false;

            for (int i = 0; i < numberCharacters && mainLoop.get(); i++) {
                CharacterType characterType = getCharType();

                switch (characterType) {
                    case BOOST:
                        roidsLeft = handleBooster();
                        break;
                    case MINER:
                        if (status == Status.MINING && roidsLeft > 0) {
                            if (shouldManageOre) {
                                manageOre();
                                manageOreCalled = true;
                            }
                            mineOre();
                        }
                        manageDrones();
                        break;
                    default:
                        // DOCKED or UNKNOWN, do nothing
                        break;
                }
                screenActions.nextDesktop();
            }

            screenActions.firstDesktop();
            updateManageOreProbability(manageOreCalled);
            
            if (mainLoop.get()) {
                performIdleBehavior(minerType);
            }
        }
    }

    @Override
    protected int handleBooster() {
        roidsLeft = countAsteroids(lockedOreAsteroidTypes, unlockedOreAsteroidTypes);
        sendAlert("Roids left: " + roidsLeft);

        if (roidsLeft <= ROID_DOCK_THRESHOLD && status == Status.MINING) {
            status = Status.DOCKING;
            sendAlert("No roids left. Docking up");
            actionInterfacer.activateBoostHighslots();
        }
        return roidsLeft;
    }

    protected void handledAsteroids() {

    }

    @Override
    protected void mineOre() {
        if (lockedRoids == null || lockedRoids.size() <= 2) {
            overviewManager.lockTarget(unlockedRoids);
        }

        int activeMiners = screenActions.countAvailableImage(Locations.targetedItems, Images.ORE_MINER_ACTIVE.toString());

        if (activeMiners < 2) {
            actionInterfacer.activateHighSlotsMultipleTargets();
        }
    }

    @Override
    protected void manageOre() {
        cargoManager.compressOre();
        if(minerType == MinerType.HULK) {
            cargoManager.moveCargoToFleetHanger();
        }
    }

    protected void checkForDock() {
        if (status == Status.DOCKING && !screenActions.isInStation()) {
            actionInterfacer.activateHomeBookmark();
        }
    }

    @Override
    protected void performIdleBehavior(MinerType minerType) {
        if (minerType == MinerType.HULK) {
            actionInterfacer.simulateIdleBehaviour(5000);
        } else if (minerType == MinerType.MACKINAW) {
            actionInterfacer.simulateIdleBehaviour(IDLE_TIME_MS);
        }
    }

    @Override
    protected void handleMinerLogic() {
        // Implementation for Mackinaw specific miner logic if needed
    }

    @Override
    protected void sendRoidsLeftAlert() {
        sendAlert("Roids left: " + roidsLeft);
    }

    @Override
    protected void performIdleBehavior() {
        actionInterfacer.simulateIdleBehaviour(IDLE_TIME_MS);
    }
}