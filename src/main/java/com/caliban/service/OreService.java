package com.caliban.service;

import com.caliban.enums.Images;
import com.caliban.enums.MinerType;

public class OreService {

    private static final int TARGET_LOW_THRESHOLD = 2;
    private static final int TARGET_MAX = 5;

    private final MiningActionsService actionInterfacer = new MiningActionsService();
    private final ScreenActions screenActions = new ScreenActions();

    public void manageOre(MinerType minerType) {
        if (minerType == MinerType.HULK) {
            actionInterfacer.compressOre();
            actionInterfacer.emptyCargo();
        }
        actionInterfacer.compressOre();
    }

    public void mineOre() {
        int targetedRoids = screenActions.countTargetsAvailable(Images.OVERVIEW_TARGETED.toString());

        if (targetedRoids <= TARGET_LOW_THRESHOLD) {
            actionInterfacer.targetAll(Images.ASTEROID_OVERVIEW.toString(), TARGET_MAX);
        }

        if (targetedRoids > 0) {
            actionInterfacer.activateHighSlots();
        }
    }
}