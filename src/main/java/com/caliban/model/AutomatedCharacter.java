package com.caliban.model;

import java.awt.Point;

public class AutomatedCharacter {

    private String type;
    private Point activationBoundryStart;
    private Point activationBoundryEnd;
    private String status = "UNKNOWN";

    public AutomatedCharacter(String type, Point activationBoundryStart, Point activationBoundryEnd) {
        this.type = type;
        this.activationBoundryStart = activationBoundryStart;
        this.activationBoundryEnd = activationBoundryEnd;
    }

    public String getType() {
        return type;
    }

    public Point getActivationBoundryStart() {
        return activationBoundryStart;
    }

    public Point getActivationBoundryEnd() {
        return activationBoundryEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
