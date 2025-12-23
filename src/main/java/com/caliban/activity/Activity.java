package com.caliban.activity;

import com.caliban.event.AlertEvent;
import com.caliban.event.EventBus;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public abstract class Activity {

    // Thread-safe set to track currently playing sounds
    private static final Set<String> currentlyPlayingSounds = Collections.synchronizedSet(new HashSet<>());

    private String state = "NOT_STARTED";
    
    public String getState() {
        return state;
    }

    protected void setState(String newState) {
        this.state = newState;
    }

    protected void sendAlert(String message) {
        EventBus.getInstance().publish(new AlertEvent(message));
    }

    /**
     * Plays a sound file from the resources/sounds folder
     * @param soundFileName the name of the sound file (e.g., "alarm.wav")
     */
    protected void playSound(String soundFileName) {
        // Check if this sound is already playing
        if (currentlyPlayingSounds.contains(soundFileName)) {
            return; // Don't play the same sound again
        }
        
        try {
            // Add to currently playing sounds
            currentlyPlayingSounds.add(soundFileName);
            
            // Load the sound file from resources/sounds folder
            String soundPath = "/sounds/" + soundFileName;
            InputStream audioSrc = getClass().getResourceAsStream(soundPath);
            
            if (audioSrc == null) {
                currentlyPlayingSounds.remove(soundFileName); // Remove from tracking if file not found
                System.err.println("Sound file not found: " + soundPath);
                return;
            }
            
            // Create AudioInputStream from the input stream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioSrc);
            
            // Get a clip to play the audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            
            // Play the sound
            clip.start();
            
            // Optional: Add a listener to close resources when playback completes
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    currentlyPlayingSounds.remove(soundFileName); // Remove from tracking when done
                    clip.close();
                    try {
                        audioInputStream.close();
                        audioSrc.close();
                    } catch (IOException e) {
                        System.err.println("Error closing audio resources: " + e.getMessage());
                    }
                }
            });
            
        } catch (UnsupportedAudioFileException e) {
            currentlyPlayingSounds.remove(soundFileName); // Remove from tracking on error
            System.err.println("Unsupported audio file format: " + soundFileName);
        } catch (IOException e) {
            currentlyPlayingSounds.remove(soundFileName); // Remove from tracking on error
            System.err.println("Error reading sound file: " + soundFileName);
        } catch (LineUnavailableException e) {
            currentlyPlayingSounds.remove(soundFileName); // Remove from tracking on error
            System.err.println("Audio line unavailable for: " + soundFileName);
        }
    }
}
