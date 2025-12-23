package com.caliban.activity;

import com.caliban.service.ScreenActions;
import org.sikuli.script.*;

import javax.sound.sampled.Clip;
import java.awt.*;

public class WatcherActivity extends Activity {

    private final ScreenActions screenActions = new ScreenActions();
    private volatile boolean watching;
    private String previousSnapshot;

    public void watch(Rectangle area, String imageFile, int intervalSeconds) {
        watching = true;
        while (watching) {
            Rectangle match = screenActions.findImage(area, imageFile);
            if (match != null) {
                sendAlert("Image found: " + imageFile);
                playSound("deepAlarm.wav");
            }
            try {
                Thread.sleep(intervalSeconds * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                watching = false;
            }
        }
    }

    public void stop() {
        watching = false;
    }

    public boolean watchForChange(Rectangle screenLocation, int intervalSeconds) {
        try {
            Region region = new Region(screenLocation.x, screenLocation.y, screenLocation.width, screenLocation.height);

            while (true) {
                String currentSnapshotPath = "currentSnapshot.png";
                ScreenImage currentSnapshot = region.getScreen().capture(region);
                currentSnapshot.save(".", currentSnapshotPath);

                if (previousSnapshot == null) {
                    previousSnapshot = currentSnapshotPath;
                    Thread.sleep(intervalSeconds * 1000L);
                    continue; // First call, no comparison
                }

                Pattern previousPattern = new Pattern(previousSnapshot).similar(0.9f); // 90% similarity threshold
                Match match = region.find(previousPattern);

                if (match == null) {
                    // Save the current snapshot to the 'captures' folder
                    String captureFolderPath = "captures/";
                    // Include date and time in the saved snapshot file name
                    String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
                    String captureFilePath = captureFolderPath + "snapshot_" + timestamp + ".png";

                    // Ensure the 'captures' folder exists
                    java.nio.file.Files.createDirectories(java.nio.file.Paths.get(captureFolderPath));
                    java.nio.file.Files.copy(java.nio.file.Paths.get(currentSnapshotPath), java.nio.file.Paths.get(captureFilePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                    return true; // Images are different
                }

                Thread.sleep(intervalSeconds * 1000L);
            }
        } catch (FindFailed | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return false;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void stopWatchingForChange() {
        previousSnapshot = null;
    }
}