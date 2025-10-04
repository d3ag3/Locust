package com.caliban.activity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.caliban.model.AutomatedCharacter;
import com.caliban.config.CharacterConfig;
import com.caliban.service.MiningActionsService;
public class SimpleMining {

    private AtomicBoolean mainLoop = new AtomicBoolean(false);

    private CharacterConfig characterConfig = new CharacterConfig();

    private List<AutomatedCharacter> characters = characterConfig.getCharacters();

    private MiningActionsService actionInterfacer = new MiningActionsService();

    public void start() {
        mainLoop.getAndSet(true);

        while (mainLoop.get()) {
            for (AutomatedCharacter character : characters) {
                if (!mainLoop.get()) break;
                if ("BOOST".equals(character.getType())) {
                    actionInterfacer.switchToCharacter(character);
                    //do some safety checks
                    //Run ore analyzier
                }

                if ("MINER".equals(character.getType())) {
                    actionInterfacer.switchToCharacter(character);

                    if (!actionInterfacer.isAlreadyMining()) {
                        if (!mainLoop.get()) break;
                        actionInterfacer.targetRandomAsteroids();
                        if (!mainLoop.get()) break;
                        actionInterfacer.activateHighSlotsMultipleTargets();
                    }
                    if (!mainLoop.get()) break;
                    actionInterfacer.compressOre();
                    if (!mainLoop.get()) break;
                }
            }
            if (!mainLoop.get()) break;

            actionInterfacer.simulateIdleBehaviour(15000);
        }
    }

    public void stop() {
        mainLoop.getAndSet(false);
    }
}