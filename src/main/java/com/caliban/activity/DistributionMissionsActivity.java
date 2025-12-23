package com.caliban.activity;

import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.config.ScreenLocations;
import com.caliban.service.MissionActionsService;
import com.caliban.service.ScreenActions;
import java.awt.*;

public class DistributionMissionsActivity extends Activity {

    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final MissionActionsService missionService = new MissionActionsService();
    private final ScreenActions screenActions = new ScreenActions();

    private enum MissionState {
        READY_FOR_MISSION, MISSION_ACTIVE, NEED_ROUTE, NEXT_ROUTE, RETURN_HOME, IN_WARP
    }

    private String selectedAgent;

    public void start(String agent, int numberCharacters) {
        if (isRunning.get()) {
            sendAlert("Activity already running, ignoring start request");
            return;
        }
        
        isRunning.set(true);
        selectedAgent = agent;
        sendAlert("Starting distribution missions activity for agent: " + agent + " with " + numberCharacters + " characters");

        try {
            runMainLoop(numberCharacters);
        } catch (Exception e) {
            sendAlert("Error during mission execution: " + e.getMessage());
        } finally {
            isRunning.set(false);
        }
    }
    
    private void runMainLoop(int numberCharacters) {
        while (isRunning.get()) {
            screenActions.firstDesktop();
            
            for (int i = 0; i < numberCharacters && isRunning.get(); i++) {
                processCharacterMission();
                screenActions.nextDesktop();
            }
            
            // Optional idle behavior could be added here
            // missionService.simulateIdleBehaviour(5000);
        }
    }
    
    private void processCharacterMission() {
        MissionState state = assessState();
        sendAlert("Current state: " + state);

        switch (state) {
            case READY_FOR_MISSION:
                handleReadyForMission();
                break;
            case MISSION_ACTIVE:
                handleMissionActive();
                break;
            case NEED_ROUTE:
                handleNeedRoute();
                break;
            case NEXT_ROUTE:
                handleNextRoute();
                break;
            case RETURN_HOME:
                handleReturnHome();
                break;
            case IN_WARP:
                // Wait during warp
                break;
        }
    }
    
    private void handleReadyForMission() {
        missionService.startNewMission(selectedAgent);
        loadCargoAndUndock();
    }
    
    private void handleMissionActive() {
        if (screenActions.hasItemInHanger()) {
            loadCargoAndUndock();
        } else {
            missionService.completeMission();
            missionService.activateAgentHomeBookmark();
            missionService.undock();
        }
    }
    
    private void handleNeedRoute() {
        missionService.setMissionDestination();
        missionService.warpToRouteDestination();
    }
    
    private void handleNextRoute() {
        missionService.warpToRouteDestination();
    }
    
    private void handleReturnHome() {
        missionService.activateAgentHomeBookmark();
        missionService.undock();
    }

    private void loadCargoAndUndock() {
        missionService.moveFirstHangerItemToCargoHold();
        missionService.undock();
    }

    public void stop() {
        sendAlert("Stopping distribution missions activity");
        isRunning.set(false);
    }

    private MissionState assessState() {
        // Cache common checks to avoid redundant calls
        boolean inStation = isInStation();
        boolean hasRoute = hasRouteSet();
        boolean hasActiveMission = hasActiveMission();
        System.out.println("assessState: inStation=" + inStation + ", hasRoute=" + hasRoute + ", hasActiveMission=" + hasActiveMission);
        // Optimize: Check most common state first (in space with route)
        if (!inStation && hasRoute) {
            return isInWarp() ? MissionState.IN_WARP : MissionState.NEXT_ROUTE;
        }
        
        if (!inStation) {
            // In space without route
            return hasActiveMission ? MissionState.NEED_ROUTE : MissionState.RETURN_HOME;
        } else {
            // In station
            if (hasActiveMission) {
                return MissionState.MISSION_ACTIVE;
            } else {
                return isAgentCommsPossible() ? MissionState.READY_FOR_MISSION : MissionState.RETURN_HOME;
            }
        }
    }

    private boolean isInWarp() {
        Rectangle location = screenActions.findImage(ScreenLocations.statusCircle, "inWarp.png");
        return (location != null);
    }

    private boolean hasActiveMission() {
        Rectangle location = screenActions.findImageParital(ScreenLocations.leftQuarter, "missionActive.png");
        if (location != null) return true;
        location = screenActions.findImage(ScreenLocations.agentPanel, "missionAccepted.png");
        return (location != null);
    }

    private boolean hasRouteSet() {
        return (screenActions.hasRouteSet());
    }

    private boolean isInStation() {
        Rectangle location = screenActions.findImage(ScreenLocations.stationPanel, "inStation.png");
        return (location != null);
    }

    private boolean isAgentCommsPossible() {
        Rectangle location = screenActions.findImage(ScreenLocations.agentPanel, "\\agents\\" + selectedAgent);
        return (location != null);
    }
    
}
