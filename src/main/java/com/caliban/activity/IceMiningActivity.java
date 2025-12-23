package com.caliban.activity;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.config.ScreenLocations;
import com.caliban.enums.CharacterType;
import com.caliban.enums.Images;
import com.caliban.enums.MinerType;
import com.caliban.service.DroneService;
import com.caliban.service.MiningActionsService;
import com.caliban.service.OreService;
import com.caliban.service.ScreenActions;
import com.caliban.service.ThreatCheckerService;

public class IceMiningActivity extends Activity {

    private static final int ROID_DOCK_THRESHOLD = 0;
    private static final int IDLE_TIME_MS = 15000;

    private final AtomicBoolean mainLoop = new AtomicBoolean(false);

    private final MiningActionsService actionInterfacer = new MiningActionsService();
    private final DroneService droneService = new DroneService();
    private final ScreenActions screenActions = new ScreenActions();
    private final OreService oreService = new OreService();
    private final ThreatCheckerService threatChecker = new ThreatCheckerService();
    private final Random random = new Random();

    private int roidsLeft = 0;
    private double manageOreProbability = 0.6; // Starting at 60%

    public void start(int numberCharacters, MinerType minerType) {
        sendAlert("Starting Mining");
        mainLoop.set(true);
        setState("MINING");
        
        // Set manageOreProbability to 1.0 for HULK miners
        if (minerType == MinerType.HULK) {
            manageOreProbability = 1.0;
        }

        while (mainLoop.get()) {
            screenActions.firstDesktop();
            
            boolean shouldManageOre = shouldManageOreThisLoop();
            boolean manageOreCalled = false;

            for (int i = 0; i < numberCharacters && mainLoop.get(); i++) {
                checkForDock();
/*                status = threatChecker.checkForThreats(status, 
                    () -> sendAlert("Hostiles in area, docking up"),
                    () -> {
                        sendAlert("Character mentioned in local");
                        playSound("chatAlarm.wav");
                    });*/ 

                CharacterType characterType = getCharType();

                switch (characterType) {
                    case BOOST:
                        roidsLeft = handleBooster();
                        
                        break;
                    case MINER:
                        if ("MINING".equals(getState()) && roidsLeft > 0) {
                            if (shouldManageOre || screenActions.isCompressionNeeded()) {
                                oreService.manageOre(minerType);
                                manageOreCalled = true;
                            }
                            oreService.mineOre();
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
                    actionInterfacer.simulateIdleBehaviour(IDLE_TIME_MS);
                }
            }
        }
    }

    private int handleBooster() {
        roidsLeft = screenActions.countTotalRows();
        sendAlert("Roids left: " + roidsLeft);

        if (roidsLeft <= ROID_DOCK_THRESHOLD && "MINING".equals(getState())) {
            setState("DOCKING");
            sendAlert("No roids left. Docking up");
            actionInterfacer.activateBoostHighslots();
        }
        return roidsLeft;
    }

    private void manageDrones() {
        if (roidsLeft <= 4) {
            droneService.recallDrone();
        }
     }

    private void checkForDock() {
        if ("DOCKING".equals(getState()) && !screenActions.isInStation()) {
            actionInterfacer.activateHomeBookmark();
        }
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



    private CharacterType getCharType() {
        if (screenActions.findImage(ScreenLocations.modulePanel, Images.BOOSTER.toString()) != null) {
            return CharacterType.BOOST;
        }
        if (screenActions.findImage(ScreenLocations.modulePanel, Images.ICE_MINER.toString()) != null) {
            return CharacterType.MINER;
        }
        if (screenActions.isInStation()) {
            return CharacterType.DOCKED;
        }
        sendAlert("Unable to get charType");
        return CharacterType.UNKNOWN;
    }
}