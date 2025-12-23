package com.caliban.service;

import com.caliban.config.Locations;
import com.caliban.config.ScreenLocations;

import java.awt.*;

public class MissionActionsService extends GeneralActionsService {

    public void startNewMission(String selectedAgent) {
        //find agent icon
        Rectangle agentLocation = screenActions.findImage(ScreenLocations.agentPanel,"\\agents\\"+selectedAgent);

        if(agentLocation == null) {
            System.out.println("Error, can't find agent to start mission");
            return;
        }

        doubleClickActivationArea(agentLocation);
        //Check for low sec
        Rectangle lowSecCheck = screenActions.findImage(ScreenLocations.centreScreen,"lowsecmission.png");
        if (lowSecCheck!=null) {
            //decline mission
            clickActivationArea(Locations.declineMission);
            clickActivationArea(Locations.declineMissionYes);
        }
        //accept mission
        clickActivationArea(Locations.missionAcceptButton);

        //close mission window
        clickActivationArea(Locations.missionCloseButton);
    }

    public void completeMission() {
        //find agent icon
        Rectangle agentLocation = screenActions.findImage(ScreenLocations.agentPanel,"missionAccepted.png");

        if(agentLocation == null) {
            System.out.println("Error, can't find agent to complete mission");
            return;
        }

        doubleClickActivationArea(agentLocation);

        //complete mission
        clickActivationArea(Locations.missionCompleteButton);

        //close mission window
        clickActivationArea(Locations.missionCompleteCloseButton);
    }

    public void setMissionDestination() {
        Rectangle setDestination = screenActions.findImageParital(screenLocations.leftQuarter(), "missionSetDestination.png");

        if (setDestination==null) {
            //Destination might be in same system.  Look for dock button instead.
            setDestination = screenActions.findImageParital(screenLocations.leftQuarter(), "Dock.png");
        }

        if (setDestination==null) {
            System.out.println("Error unable to find destination while mission active.");
            return;
        }

        clickActivationArea(setDestination);
    }

    public void startMissionConversation(String selectedAgent) {
        Rectangle setDestination = screenActions.findImageParital(screenLocations.rightQuarter(), "\\agents\\selectedAgent.png");

        if (setDestination==null) {
            System.out.println("Agent not found");
            return;
        }

        doubleClickActivationArea(setDestination);
    }
}
