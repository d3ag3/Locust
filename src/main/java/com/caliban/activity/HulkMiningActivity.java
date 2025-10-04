package com.caliban.activity;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.helper.ScreenLocations;
import com.caliban.service.MiningActionsService;
import com.caliban.service.ScreenActions;

public class HulkMiningActivity {

    private AtomicBoolean mainLoop = new AtomicBoolean(false);
    private String status = "MINING";

    private MiningActionsService actionInterfacer = new MiningActionsService();
    private ScreenActions screenActions = new ScreenActions();
    private ScreenLocations screenLocations = new ScreenLocations();

    public void start(int numberCharacters) {
        System.out.println("Starting Mining");

        mainLoop.getAndSet(true);
        status = "MINING";

        int roidsLeft =0;

        String characterType;
        while (mainLoop.get()) {
            screenActions.firstDesktop();

            for (int i = 0; i < numberCharacters; i++) {
                if (!mainLoop.get()) break;

                checkForDock();
                //checkForThreats();

                characterType = getCharType();

                if ("BOOST".equals(characterType) && "MINING".equals(status)) {
                    roidsLeft = screenActions.countTotalRows();

                    System.out.println("Roids left: "+ roidsLeft);
                    actionInterfacer.moveFirstHangerItemToCargoHold();
                    actionInterfacer.compressOre();

                    //If there's only few roids left, dock on next cycle
                    if (roidsLeft<=2 && "MINING".equals(status)) {
                        status = "DOCK";
                        System.out.println("No roids left.  Docking up");
                        actionInterfacer.activateBoostHighslots();
                    }
                }

                if ("MINER".equals(characterType)&&"MINING".equals(status)&&roidsLeft>0) {
                    minerActions();
                }
                screenActions.nextDesktop();
            }
            if (!mainLoop.get()) break;
            actionInterfacer.simulateIdleBehaviour(5000);
        }
    }

    private void minerActions() {

        actionInterfacer.moveFirstCargoHoldItemToHanger();

        //Target asteroids if current locked count is low
        if(screenActions.countTargetsAvailable("overviewTargeted.png") <=4) actionInterfacer.targetAll("asteroidOverview.png", 5);
        //Activate highslots is needed
        actionInterfacer.activateHighSlots();

    }

    private void checkForDock() {
        if("DOCK".equals(status)) {
            if(!screenActions.isInStation()) {
                actionInterfacer.activateHomeBookmark();
            }
        }
    }

    private void checkForThreats() {
        if (checkLocalForHostiles()) {
            System.out.println("Hostiles in area, dockup");
            status= "DOCK";
        }
        //Check for local numbers jump
        //Check for talos / catalyst numbers on radar
    }

    private boolean checkLocalForHostiles() {
        return (screenActions.countAvailableImage(screenLocations.chatArea(),"negativeStandings.png")>2);
    }

    public void stop() {
        mainLoop.getAndSet(false);
    }

    private String getCharType() {
       Rectangle match = screenActions.findImage(screenLocations.modulePanel(), "booster.png");

       if(match != null) return "BOOST";

       match = screenActions.findImage(screenLocations.modulePanel(), "iceMiner.png");

       if(match != null) return "MINER";

       if(screenActions.isInStation()) return "DOCKED";
        
       System.out.println("Unable to get charType");
       return "UNKNOWN";
    }
}