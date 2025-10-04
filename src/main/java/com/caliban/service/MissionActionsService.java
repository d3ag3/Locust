package com.caliban.service;

import com.caliban.config.Locations;

import java.awt.*;

public class MissionActionsService extends GeneralActionsService {

    public void startNewMission(String selectedAgent) {
        //find agent icon
        Rectangle agentLocation = screenActions.findImage(screenLocations.agentPanel(),"\\agents\\"+selectedAgent);

        if(agentLocation == null) {
            System.out.println("Error, can't find agent to start mission");
            return;
        }

        //double click on agent
        Point agentStart = new Point((int) agentLocation.getX(), (int) agentLocation.getY());
        Point agentEnd = new Point((int) (agentLocation.getX() + agentLocation.getWidth()), 
                                            (int) (agentLocation.getY() + agentLocation.getHeight()));

        doubleClickActivationArea(agentStart, agentEnd);
        //Check for low sec
        Rectangle lowSecCheck = screenActions.findImage(screenLocations.centreScreen(),"lowsecmission.png");
        if (lowSecCheck!=null) {
            //decline mission
            clickActivationArea(Locations.declineMissionStart, Locations.declineMissionEnd);
            clickActivationArea(Locations.declineMissionYesStart, Locations.declineMissionYesEnd);
        }
        //accept mission
        clickActivationArea(Locations.missionAcceptButtonStart, Locations.missionAcceptButtonEnd);

        //close mission window
        clickActivationArea(Locations.missionCloseButtonStart, Locations.missionCloseButtonEnd);
    }

    public void completeMission() {
        //find agent icon
        Rectangle agentLocation = screenActions.findImage(screenLocations.agentPanel(),"missionAccepted.png");

        if(agentLocation == null) {
            System.out.println("Error, can't find agent to complete mission");
            return;
        }

        //double click on agent
        Point agentStart = new Point((int) agentLocation.getX(), (int) agentLocation.getY());
        Point agentEnd = new Point((int) (agentLocation.getX() + agentLocation.getWidth()), 
                                            (int) (agentLocation.getY() + agentLocation.getHeight()));

        doubleClickActivationArea(agentStart, agentEnd);

        //complete mission
        clickActivationArea(Locations.missionCompleteButtonStart, Locations.missionCompleteButtonEnd);

        //close mission window
        clickActivationArea(Locations.missionCompleteCloseButtonStart, Locations.missionCompleteCloseButtonEnd);
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

        Point destinationStart = new Point((int) setDestination.getX(), (int) setDestination.getY());
        Point destinationEnd = new Point((int) (setDestination.getX() + setDestination.getWidth()), 
                                            (int) (setDestination.getY() + setDestination.getHeight()));

        clickActivationArea(destinationStart, destinationEnd);
    }

    public void startMissionConversation(String selectedAgent) {
        Rectangle setDestination = screenActions.findImageParital(screenLocations.rightQuarter(), "\\agents\\selectedAgent.png");

        if (setDestination==null) {
            System.out.println("Agent not found");
            return;
        }

        Point destinationStart = new Point((int) setDestination.getX(), (int) setDestination.getY());
        Point destinationEnd = new Point((int) (setDestination.getX() + setDestination.getWidth()), 
                                            (int) (setDestination.getY() + setDestination.getHeight()));

        doubleClickActivationArea(destinationStart, destinationEnd);
    }
}
