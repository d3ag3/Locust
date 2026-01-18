package com.caliban.activity;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.caliban.service.FleetActionsService;
import com.caliban.service.InterfaceActions;
import com.caliban.config.ScreenLocations;
import com.caliban.helper.ScreenLocationsHelper;
import com.caliban.service.ScreenActions;


public class FleetActivity extends Activity {

    private final FleetActionsService fleetActions = new FleetActionsService();
    private final ScreenActions screenActions = new ScreenActions();

    public void fleetUp(int numberCharacters) {
        sendAlert("Fleeting Up");


        //Send fleet invites

        //Accept fleet invites

        //Open cargoholds and switch to mining tab

        //Open Locations tab an open mining tab

        for (int i = 0; i < numberCharacters; i++) {
            screenActions.nextDesktop();
        }


        }

}