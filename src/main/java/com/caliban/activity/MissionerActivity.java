package com.caliban.activity;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.helper.ScreenLocations;
import com.caliban.service.MissionActionsService;
import com.caliban.service.ScreenActions;

public class MissionerActivity {

    private AtomicBoolean mainLoop = new AtomicBoolean(false);

    private MissionActionsService actionInterfacer = new MissionActionsService();
    private ScreenActions screenActions = new ScreenActions();
    private ScreenLocations screenLocations = new ScreenLocations();

    private enum MissionState {
        READYFORMISSION, MISSIONACTIVE, NEEDROUTE, NEXTROUTE, RETURNHOME, INWARP
    }

    private String selectedAgent;

    public void start(String agent, int numberCharacters) {
        mainLoop.set(true);
        selectedAgent = agent;

        while (mainLoop.get()) {
            screenActions.firstDesktop();
            for (int i = 0; i < numberCharacters; i++) {
                MissionState state = assessState();
                System.out.println("State: " + state);

                switch (state) {
                    case READYFORMISSION:
                        actionInterfacer.startNewMission(selectedAgent);
                        loadCargoAndUndock();
                        break;
                    case MISSIONACTIVE:
                        if (screenActions.hasItemInHanger()) {
                            loadCargoAndUndock();
                        } else {
                            actionInterfacer.completeMission();
                            actionInterfacer.activateHomeBookmark();
                            actionInterfacer.undock();
                        }
                        break;
                    case NEEDROUTE:
                        actionInterfacer.setMissionDestination();
                        actionInterfacer.warpToRouteDestination();
                        break;
                    case NEXTROUTE:
                        actionInterfacer.warpToRouteDestination();
                        break;
                    case RETURNHOME:
                        actionInterfacer.activateHomeBookmark();
                        actionInterfacer.undock();
                        break;
                    case INWARP:
                        break;
                }

                screenActions.nextDesktop();
                if (!mainLoop.get()) break;
            }
            if (!mainLoop.get()) break;
            // actionInterfacer.simulateIdleBehaviour(5000);
        }
    }

    private void loadCargoAndUndock() {
        actionInterfacer.moveFirstHangerItemToCargoHold();
        actionInterfacer.undock();
    }

    public void stop() {
        mainLoop.getAndSet(false);
    }

    private MissionState assessState() {
        //Spend most time in space so shortcut nextRoute check:
        if (hasRouteSet()) return MissionState.NEXTROUTE;

        if (!isInStation()) {
            if (isInWarp()) {
                return MissionState.INWARP;
            }
            if (hasRouteSet()) {
                return MissionState.NEXTROUTE;
            } else if (!hasActiveMission()) {
                return MissionState.RETURNHOME;
            } else {
                return MissionState.NEEDROUTE;
            }
        } else {
            if (hasActiveMission()) {
                return MissionState.MISSIONACTIVE;
            } else if (agentCommsPossible()) {
                return MissionState.READYFORMISSION;
            } else {
                return MissionState.RETURNHOME;
            }
        }
    }

    private boolean isInWarp() {
        Rectangle location = screenActions.findImage(screenLocations.statusCircle(), "inWarp.png");
        return (location != null);
    }

    private boolean hasActiveMission() {
        Rectangle location = screenActions.findImageParital(screenLocations.leftQuarter(),"missionActive.png");
        if (location != null) return true;
        location = screenActions.findImage(screenLocations.agentPanel(),"missionAccepted.png");
        return (location != null);
    }

    private boolean hasRouteSet() {
        return (screenActions.hasRouteSet());
    }

    private boolean isInStation() {
        Rectangle location = screenActions.findImage(screenLocations.stationPanel(), "inStation.png");
        return (location != null);
    }

    private boolean agentCommsPossible() {
        Rectangle location = screenActions.findImage(screenLocations.agentPanel(), "\\agents\\"+selectedAgent);
        return (location != null);
    }
}