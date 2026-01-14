package com.caliban.activity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.actions.CargoManager;
import com.caliban.actions.DroneManager;
import com.caliban.actions.OverviewManager;
import com.caliban.config.Locations;
import com.caliban.enums.CharacterType;
import com.caliban.enums.Images;
import com.caliban.service.MiningActionsService;
import com.caliban.service.ThreatCheckerService;
import java.awt.Rectangle;

public class HulkIceMiningActivity extends Activity {

    private static final int ROID_DOCK_THRESHOLD = 0;

    private final AtomicBoolean mainLoop = new AtomicBoolean(false);

    private final MiningActionsService actionInterfacer = new MiningActionsService();
    private final DroneManager droneManager = new DroneManager();
    private final CargoManager cargoManager = new CargoManager();
    private final ThreatCheckerService threatChecker = new ThreatCheckerService();
    private final OverviewManager overviewManager = new OverviewManager();

    private int roidsLeft = 0;
    private int previousRoidsLeft = -1; // Initialize to -1 to ensure first alert is sent
    private List<Rectangle> unlockedRoids = null;
    private List<Rectangle> lockedRoids = null;

    private boolean boostActivated = false;

    public void start(int numberCharacters) {
        sendAlert("Starting Hulk Ice Mining");
        mainLoop.set(true);
        setState("MINING");
        
        while (mainLoop.get()) {
            for (int i = 0; i < numberCharacters && mainLoop.get(); i++) {
                CharacterType characterType = getCharType();
                checkAsteroids();
                manageDrones();
                checkForDock();

                switch (characterType) {
                    case BOOST:
                        handleBooster();
                        cargoManager.moveCargoFromFleetHanger();
                        break;
                    case MINER:
                        if ("MINING".equals(getState()) && roidsLeft > 0) {
                            cargoManager.compressOre();
                            manageOre();
                            mineOre();
                        }
                        break;
                    default:
                        // DOCKED or UNKNOWN, do nothing
                        break;
                }
                screenActions.nextDesktop();
            }
            
            if ("MINING".equals(getState())) {
                actionInterfacer.simulateIdleBehaviour(5000);
            }
        }
    }

    private void handleBooster() {
        if (roidsLeft <= ROID_DOCK_THRESHOLD && "DOCKING".equals(getState()) && !boostActivated) {
            cargoManager.compressOre();
            actionInterfacer.activateBoostHighslots();
            boostActivated = true;
        }
    }

    private void manageDrones() {
        if (roidsLeft <= 4) {
            droneManager.recallDrone();
        }
    }

    private void checkAsteroids() {
        unlockedRoids = overviewManager.getTargets(Images.ICE_ICON.toString());
        lockedRoids = overviewManager.getTargets(Images.ICE_ICON_LOCKED.toString());
        roidsLeft = unlockedRoids.size() + lockedRoids.size();
        
        if (roidsLeft != previousRoidsLeft) {
            sendAlert("Roids left: " + roidsLeft);
            previousRoidsLeft = roidsLeft;
        }
    }

    private void mineOre() {
        if (lockedRoids == null || lockedRoids.size() <= 2) {
            overviewManager.lockTarget(unlockedRoids);
        }

        int activeMiners = screenActions.countAvailableImage(Locations.targetedItems, Images.ICE_MINER_ACTIVE.toString());

        if (activeMiners < 2) {
            actionInterfacer.activateHighSlots();
            actionInterfacer.activateHighSlots();
        }
    }

    public void manageOre() {
        cargoManager.compressOre();
        cargoManager.moveCargoToFleetHanger();
    }

    private void checkForDock() {
        if (roidsLeft <= ROID_DOCK_THRESHOLD && "MINING".equals(getState())) {
            setState("DOCKING");
            sendAlert("No roids left. Docking up");
        }
        if ("DOCKING".equals(getState()) && !screenActions.isInStation()) {
            actionInterfacer.activateHomeBookmark();
        }
    }

    public void stop() {
        mainLoop.set(false);
    }
}