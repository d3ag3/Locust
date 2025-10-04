package com.caliban.activity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.model.AutomatedCharacter;
import com.caliban.config.CharacterConfig;
import com.caliban.service.MiningActionsService;
import com.caliban.service.ScreenActions;

public class SimpleIceMining {

    private AtomicBoolean mainLoop = new AtomicBoolean(false);
    private String status = "MINING";

    private CharacterConfig characterConfig = new CharacterConfig();
    private List<AutomatedCharacter> characters = characterConfig.getCharacters();

    private MiningActionsService actionInterfacer = new MiningActionsService();
    private ScreenActions screenActions = new ScreenActions();

    public void start() {
        mainLoop.getAndSet(true);

        int roidsLeft = screenActions.countTotalRows();
        System.out.println("Starting Mining");
        System.out.println("Roids available: " + roidsLeft); 
        
        status = "MINING";

        while (mainLoop.get()) {

            for (AutomatedCharacter character : characters) {
                if (!mainLoop.get()) break;
                actionInterfacer.switchToCharacter(character);

                if("DOCK".equals(status)) {
                    if(!screenActions.isInStation()) {
                        actionInterfacer.activateHomeBookmark();
                    }
                }

                if ("BOOST".equals(character.getType()) && "MINING".equals(status)) {
                    roidsLeft = screenActions.countTotalRows();

                    System.out.println("Roids left: "+ roidsLeft);

                    //If there's only 4 roids left, dock on next cycle
                    if (roidsLeft<=4 && "MINING".equals(status)) {
                        status = "DOCK";
                        System.out.println("No roids left.  Docking up");
                        actionInterfacer.activateBoostHighslots();
                    }
                }

                if ("MINER".equals(character.getType())&&"MINING".equals(status)) {
                    if (!mainLoop.get()) break;
                    if(screenActions.countTargetsAvailable("overviewTargeted.png") <=4) actionInterfacer.targetAll("asteroidOverview.png", 5);
                    
                    actionInterfacer.activateHighSlots();
                    actionInterfacer.compressOre();
                }
            }
            if (!mainLoop.get()) break;

            actionInterfacer.simulateIdleBehaviour(15000);
            actionInterfacer.simulateIdleBehaviour(15000);
        }
    }

    public void stop() {
        mainLoop.getAndSet(false);
    }

}