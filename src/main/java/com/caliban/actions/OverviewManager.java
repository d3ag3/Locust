package com.caliban.actions;

import java.awt.Rectangle;
import java.util.List;

public class OverviewManager extends Action {

    public List<Rectangle> getTargets(String targetImage) {
        return screenActions.findAllImage(screenLocations.rightQuarter(), targetImage);
    }

    public void lockTarget(List<Rectangle> targets) {
        if (targets == null || targets.isEmpty()) {
            return;
        }

        // Limit to maximum of 3 targets
        if (targets.size() > 3) {
            targets = targets.subList(0, 3);
        }

        int widthOffset = randomizer.generateRandom(50, 150);
        
        mouseActions.holdControl();
        mouseActions.simulateWait(300,500);
        for (Rectangle target : targets) {
            target.setSize(target.width + widthOffset, target.height);
            mouseActions.moveMouse(target);
            mouseActions.click();
            mouseActions.simulateWait(150,250);
        }
        mouseActions.releaseControl();
    }
}