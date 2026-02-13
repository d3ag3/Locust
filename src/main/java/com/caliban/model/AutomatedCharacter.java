package com.caliban.model;

import java.awt.Rectangle;

import com.caliban.enums.CharacterType;

public class AutomatedCharacter {

    private Rectangle activationBoundry;
    private String status = "UNKNOWN";
    private CharacterType characterType;

    public AutomatedCharacter(Rectangle activationBoundry) {
        this.activationBoundry = activationBoundry;
    }

    public Rectangle getActivationBoundry() {
        return activationBoundry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }
}
