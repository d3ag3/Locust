package com.caliban.model;

import java.awt.Rectangle;

public class AutomatedCharacter {

    private Rectangle activationBoundry;
    private String status = "UNKNOWN";

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
}
