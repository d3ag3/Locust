package com.caliban.service;

import com.caliban.config.Locations;
import com.caliban.enums.Images;

import java.awt.*;
import java.util.ArrayList;

public class FleetActionsService extends GeneralActionsService {

    public void assignDesktops(int numberCharacters) {
        screenActions.firstDesktop();

        // Assign characters to desktops
        screenActions.displayDesktops();

        ArrayList<Rectangle> matches = screenActions.findAllImage(screenLocations.entireScreen(), Images.EVE_DESKTOP_ICON.toString());

        // Loop through characters to fleet them up
        for (Rectangle match : matches) {
            // Right click on match
            mouseActions.moveMouse(match);
            mouseActions.rightClick();

            // Find move to desktop option
            // Select appropriate desktop
        }
    }

    public void openCargoHold() {
        // TODO: Implement cargo hold opening functionality
    }

    public void openLocationsTab() {
        // TODO: Implement locations tab opening functionality
    }

    public void sendFleetInvite(Rectangle target) {
        // TODO: Implement fleet invite sending functionality
    }

    public void acceptFleetInvite(Rectangle target) {
        // TODO: Implement fleet invite acceptance functionality
    }
}
