package com.caliban.config;

import java.util.List;
import java.awt.Point;
import java.util.ArrayList;

import com.caliban.model.AutomatedCharacter;

public class CharacterConfig {

    private List<AutomatedCharacter> characters = new ArrayList<>();

    public List<AutomatedCharacter> getCharacters() {

        characters.add(new AutomatedCharacter("MINER", new Point(600,50), new Point(725,119)));
        characters.add(new AutomatedCharacter("MINER", new Point(800,50), new Point(936,119)));
        characters.add(new AutomatedCharacter("MINER", new Point(1000,50), new Point(1125,119)));
        characters.add(new AutomatedCharacter("MINER", new Point(1200,50), new Point(1325,119)));
        characters.add(new AutomatedCharacter("MINER", new Point(425,180), new Point(560,260)));
        characters.add(new AutomatedCharacter("MINER", new Point(600,180), new Point(725,260)));
        characters.add(new AutomatedCharacter("MINER", new Point(800,180), new Point(936,260)));
        characters.add(new AutomatedCharacter("MINER", new Point(1000,180), new Point(1125,260)));
        characters.add(new AutomatedCharacter("MINER", new Point(1200,180), new Point(1325,260)));
        characters.add(new AutomatedCharacter("BOOST", new Point(425,50), new Point(560,119)));

        return characters;
    }
    
}
