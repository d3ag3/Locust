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
    public static final Rectangle overviewAreaSymbolColumn = new Rectangle(1540, 200, 50, 490);
}
