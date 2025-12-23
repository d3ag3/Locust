package com.caliban.event;

public class AlertEvent {
    private final String message;

    public AlertEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
