package com.caliban.config;

import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.caliban.model.AutomatedCharacter;

public class CharacterConfig {

    private List<AutomatedCharacter> characters = new ArrayList<>();

    public List<AutomatedCharacter> getCharacters() {

        characters.add(new AutomatedCharacter(new Rectangle(600, 50, 125, 69)));
        characters.add(new AutomatedCharacter(new Rectangle(800, 50, 136, 69)));
        characters.add(new AutomatedCharacter(new Rectangle(1000, 50, 125, 69)));
        characters.add(new AutomatedCharacter(new Rectangle(1200, 50, 125, 69)));
        characters.add(new AutomatedCharacter(new Rectangle(425, 180, 135, 80)));
        characters.add(new AutomatedCharacter(new Rectangle(600, 180, 125, 80)));
        characters.add(new AutomatedCharacter(new Rectangle(800, 180, 136, 80)));
        characters.add(new AutomatedCharacter(new Rectangle(1000, 180, 125, 80)));
        characters.add(new AutomatedCharacter(new Rectangle(1200, 180, 125, 80)));
        characters.add(new AutomatedCharacter(new Rectangle(425, 50, 135, 69)));

        return characters;
    }
    
}
