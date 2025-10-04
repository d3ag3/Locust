package com.caliban.activity;

import java.awt.Rectangle;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.caliban.service.ScreenActions;

public class SimpleActivityWatcher {

    private AtomicBoolean mainLoop = new AtomicBoolean(false);

    private ScreenActions screenActions = new ScreenActions();

    public void watch(Rectangle searchRegion, String image, String alarm, int interval){
        mainLoop.getAndSet(true);

        while (mainLoop.get()) {
            System.out.println("Scanning for " + image);

            Rectangle match = screenActions.findImage(searchRegion, image);

            if (match!= null) {
                playSound(alarm);
                mainLoop.getAndSet(false);
            }
            
            if (!mainLoop.get()) break;
            try {
                //Sleep with a random slant incase something can measure the screen being read periodically
                Thread.sleep(interval + (int) (Math.random() * 3001) + 1000); 

            } catch (InterruptedException e) {
                // Handle interruption (e.g., if another thread interrupts this thread's sleep)
                System.err.println("Thread sleep interrupted.");
                Thread.currentThread().interrupt(); // Preserve interrupt status
            }
        }
    }

    public void stop() {
        mainLoop.getAndSet(false);
    }

    private void playSound(String soundFile) {
        String pathYourSystem = System.getProperty("user.dir");
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new File(pathYourSystem + "\\src\\main\\resources\\sounds\\"+soundFile));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            int nBytesRead = 0;
            byte[] abData = new byte[1024];
            while ((nBytesRead = audioInputStream.read(abData)) != -1) {
                line.write(abData, 0, nBytesRead);
            }
            line.drain();
            line.close();
            audioInputStream.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}