package com.caliban.activity;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.actions.CargoManager;
import com.caliban.actions.DroneManager;
import com.caliban.actions.OverviewManager;
import com.caliban.config.Locations;
import com.caliban.config.ScreenLocations;
import com.caliban.enums.CharacterType;
import com.caliban.enums.Images;
import com.caliban.enums.MinerType;
import com.caliban.enums.Status;
import com.caliban.service.MiningActionsService;
import com.caliban.service.ScreenActions;

public class HulkOreMiningActivity extends Activity {

    private static final int IDLE_TIME_MS = 15000;

    private final AtomicBoolean mainLoop = new AtomicBoolean(false);
    private Status status = Status.MINING;

    private final CargoManager cargoManager = new CargoManager();
    private final OverviewManager overviewManager = new OverviewManager();
    private final MiningActionsService actionInterfacer = new MiningActionsService();
    private final DroneManager droneManager = new DroneManager();
    private final ScreenActions screenActions = new ScreenActions();
    private final Random random = new Random();

    private int roidsLeft = 0;
    private List<Rectangle> unlockedRoids = null;
    private List<Rectangle> lockedRoids = null;

    private double manageOreProbability = 0.6; // Starting at 60%

    public void start(int numberCharacters, MinerType minerType) {
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
                if (minerType == MinerType.HULK) {
                    actionInterfacer.simulateIdleBehaviour(5000);
                } else if (minerType == MinerType.MACKINAW) {
                    actionInterfacer.simulateIdleBehaviour(IDLE_TIME_MS);
                }
            }
        }
    }

    private int handleBooster() {
        roidsLeft = countAsteroids();
        sendAlert("Roids left: " + roidsLeft);

        return roidsLeft;
    }

    private int countAsteroids() {
        int small = screenActions.countAvailableImage(ScreenLocations.overview2Icons, Images.ASTEROID_SMALL.toString());
        int medium = screenActions.countAvailableImage(ScreenLocations.overview2Icons, Images.ASTEROID_MEDIUM.toString());
        int large = screenActions.countAvailableImage(ScreenLocations.overview2Icons, Images.ASTEROID_LARGE.toString());

        return small + medium + large;
    }

    private void manageDrones() {
        if (roidsLeft <= 4) {
            droneManager.recallDrone();
        }
     }

    private void mineOre() {
        if (lockedRoids == null || lockedRoids.size() <= 2) {
            overviewManager.lockTarget(unlockedRoids);
        }

        int activeMiners = screenActions.countAvailableImage(Locations.targetedItems, Images.ORE_MINER_ACTIVE.toString());

        if (activeMiners < 2) {
            actionInterfacer.activateHighSlots();
            actionInterfacer.activateHighSlots();
        }
    }

    public void manageOre() {
        cargoManager.compressOre();
        cargoManager.moveCargoToFleetHanger();
    }

    private boolean shouldManageOreThisLoop() {
        return random.nextDouble() < manageOreProbability;
    }

    private void updateManageOreProbability(boolean manageOreCalled) {
        if (manageOreCalled) {
            // Reset probability to base 60% when manageOre was called
            manageOreProbability = 0.6;
        } else {
            // Increase probability by 10% when manageOre wasn't called (max 100%)
            manageOreProbability = Math.min(1.0, manageOreProbability + 0.1);
        }
    }

    public void stop() {
        mainLoop.set(false);
    }
}