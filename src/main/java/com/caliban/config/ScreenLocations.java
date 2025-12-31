package com.caliban.config;

import java.awt.Rectangle;

import com.caliban.helper.ScreenLocationsHelper;

public class ScreenLocations {
    private static final ScreenLocationsHelper helper = new ScreenLocationsHelper();
    public static final Rectangle rightQuarter = helper.rightQuarter();
    public static final Rectangle leftQuarter = helper.leftQuarter();
    public static final Rectangle thirdQuarter = helper.thirdQuarter();
    public static final Rectangle centreScreen = helper.centreScreen();
    public static final Rectangle statusCircle = helper.statusCircle();

    public static final Rectangle agentPanel = new Rectangle(1653, 406, 257, 401);
    public static final Rectangle stationPanel = new Rectangle(1653, 0, 257, 800);
    public static final Rectangle modulePanel = new Rectangle(1023, 903, 400, 150);
    public static final Rectangle chatArea = new Rectangle(40, 785, 445, 293);

    public static final Rectangle overview2 = new Rectangle(1684, 150, 215, 537);
    public static final Rectangle overview2Icons = new Rectangle(1684, 150, 40, 485);
    public static final Rectangle bookmarks = new Rectangle(1688, 692, 206, 379);

    public static final Rectangle droneWindow = new Rectangle(1384, 692, 300, 379);

    public static final Rectangle cargoHold = new Rectangle(40, 554, 446, 226);
}
