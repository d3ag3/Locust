package com.caliban.enums;

public enum Images {
    ASTEROID_OVERVIEW("asteroidOverview.png"),
    BOOSTER("booster.png"),
    ICE_MINER("iceMiner.png"),
    MINER("miner.png"),
    IN_STATION("inStation.png"),
    NEGATIVE_STANDINGS("negativeStandings.png"),
    OVERVIEW_METERS("overviewMeters.png"),
    OVERVIEW_TARGETED("overviewTargeted.png"),
    ROUTE_CURRENT_LOCATION("routeCurrentLocation.png"),
    TEN_KM("10km.png"),
    CARGO_OPEN("cargoOpen.png"),
    ROUTE_JUMP_THROUGH_STARGATE("routeJumpThroughStargate.png"),
    ROUTE_DOCK("routeDock.png"),
    SCANNER_BELT("scannerBelt.png"),
    GOSALAV_ICEBELT("gosalavIceBelt.png"),
    CLEAR_ICEBELT("clearIceBelt.png"),
    BOOKMARK_HOME_DOCK("bookmarkHomeDock.png"),
    BOOKMARK_HOME_SET_DESTINATION("bookmarkHomeSetDestination.png"),
    COMPRESS_MENU_ITEM("compressMenuItem.png"),
    ASTEROID_SMALL("overviewSmallAsteroid.png"),
    ASTEROID_MEDIUM("overviewMediumAsteroid.png"),
    ASTEROID_LARGE("overviewLargeAsteroid.png"),
    MINING_HOLD("miningHold.png"),
    ICE_ICON("iceIconUnlocked.png"),
    ICE_ICON_LOCKED("iceLocked.png"),
    ICE_MINER_ACTIVE("iceMinerActive.png"),
    ORE_MINER_ACTIVE("oreMinerActive.png"),
    EVE_DESKTOP_ICON("eveDesktop.png");

    private final String filename;

    Images(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return filename;
    }

    public String getFilename() {
        return filename;
    }
}
