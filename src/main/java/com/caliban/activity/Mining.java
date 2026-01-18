package com.caliban.activity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.actions.CargoManager;
import com.caliban.actions.DroneManager;
import com.caliban.actions.OverviewManager;
import com.caliban.config.ScreenLocations;
import com.caliban.enums.Images;
import com.caliban.enums.MinerType;
import com.caliban.enums.Status;
import com.caliban.service.MiningActionsService;
import com.caliban.service.ScreenActions;
import com.caliban.service.ThreatCheckerService;

public abstract class Mining extends Activity {

    protected static final int ROID_DOCK_THRESHOLD = 0;

    protected final AtomicBoolean mainLoop = new AtomicBoolean(false);
    protected Status status = Status.MINING;
    protected MinerType minerType;

    protected final CargoManager cargoManager = new CargoManager();
    protected final OverviewManager overviewManager = new OverviewManager();
    protected final MiningActionsService actionInterfacer = new MiningActionsService();
    protected final DroneManager droneManager = new DroneManager();
    protected final ScreenActions screenActions = new ScreenActions();
    protected final ThreatCheckerService threatChecker = new ThreatCheckerService();
    protected final Random random = new Random();

    protected int roidsLeft = 0;
    protected List<Rectangle> unlockedRoids = new ArrayList<>();
    protected List<Rectangle> lockedRoids = new ArrayList<>();
    protected double manageOreProbability = 0.6; // Starting at 60%
    protected boolean boostActivated = false;

    protected int countAsteroids(List<String> lockedTypes, List<String> unlockedTypes) {
        int totalCount = 0;
        totalCount += setLockedAsteroids(lockedTypes);
        totalCount += setUnLockedAsteroids(unlockedTypes);
        return totalCount;
    }

    protected int setLockedAsteroids(List<String> asteroidTypes) {
        for (String asteroidType : asteroidTypes) {
            lockedRoids.addAll(screenActions.findAllImage(ScreenLocations.overview2Icons, asteroidType));
        }
        return lockedRoids.size();
    }

     protected int setUnLockedAsteroids(List<String> asteroidTypes) {
        for (String asteroidType : asteroidTypes) {
            unlockedRoids.addAll(screenActions.findAllImage(ScreenLocations.overview2Icons, asteroidType));
        }
        return unlockedRoids.size();
    }

    protected void manageDrones() {
        if (roidsLeft <= 4) {
            droneManager.recallDrone();
        }
    }

    protected boolean shouldManageOreThisLoop() {
        return random.nextDouble() < manageOreProbability;
    }

    protected void updateManageOreProbability(boolean manageOreCalled) {
        if (manageOreCalled) {
            // Reset probability to base 60% when manageOre was called
            manageOreProbability = 0.6;
        } else {
            // Increase probability by 10% when manageOre wasn't called (max 100%)
            manageOreProbability = Math.min(1.0, manageOreProbability + 0.1);
        }
    }

    protected void checkForDock() {
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

    // Abstract methods that need to be implemented by child classes
    protected abstract int handleBooster();
    protected abstract void handleMinerLogic();
    protected abstract void mineOre();
    protected abstract void manageOre();
    protected abstract void sendRoidsLeftAlert();
    protected abstract void performIdleBehavior();
    protected abstract void performIdleBehavior(MinerType minerType);
}