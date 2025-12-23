package com.caliban.service;

import com.caliban.config.ScreenLocations;
import com.caliban.enums.Images;
import com.caliban.enums.Status;

public class ThreatCheckerService {

    private static final int HOSTILE_THRESHOLD = 2;
    
    private final ScreenActions screenActions = new ScreenActions();

    /**
     * Checks for various threats including hostiles in local and character name mentions
     * @param status current mining status
     * @param onHostileDetected callback to execute when hostiles are detected
     * @param onCharacterNameDetected callback to execute when character name is mentioned
     * @return the updated status after threat checking
     */
    public Status checkForThreats(Status status, Runnable onHostileDetected, Runnable onCharacterNameDetected) {
        //check for catalysts in scanner
        //check for shields down
        //check for popupbuyall
        //check for no icons (in pod)
        Status updatedStatus = status;
        
        if (checkLocalForHostiles()) {
            if (onHostileDetected != null) {
                onHostileDetected.run();
            }
            updatedStatus = Status.DOCKING;
        }
        
        if (checkForCharacterName()) {
            if (onCharacterNameDetected != null) {
                onCharacterNameDetected.run();
            }
        }
        
        return updatedStatus;
    }

    /**
     * Checks for hostile players in local chat by counting negative standing icons
     * @return true if hostile count exceeds threshold
     */
    public boolean checkLocalForHostiles() {
        return screenActions.countAvailableImage(ScreenLocations.chatArea, Images.NEGATIVE_STANDINGS.toString()) > HOSTILE_THRESHOLD;
    }

    /**
     * Checks if the character name is mentioned in local chat
     * @return true if character name is found in chat area
     */
    public boolean checkForCharacterName() {
        return screenActions.findImage(ScreenLocations.chatArea, "character/vengerzap.png") != null;
    }
}