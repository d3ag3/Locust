package com.caliban.gui;

import com.caliban.activity.WatcherActivity;
import com.caliban.config.ScreenLocations;
import com.caliban.enums.Images;
import com.caliban.gui.TronComponents.TronSpinner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class WatcherPanel extends TronComponents.TronPanel {

    private final WatcherActivity watcherActivity = new WatcherActivity();
    private Thread watchThread; // Separate thread for watchButton
    private Thread watchForChangeThread; // Separate thread for watchForChangeButton

    public WatcherPanel() {
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        // Replace BorderLayout with GridLayout for better arrangement of components
        // Adjust GridLayout to 3 rows and 1 column
        controlPanel.setLayout(new GridLayout(3, 1, 5, 5)); // 3 rows, 1 column, with 5px gaps

        JButton watchButton = new JButton("Watch");
        JComboBox<Images> imageComboBox = new JComboBox<>(Images.values());
        imageComboBox.setSelectedItem(Images.SCANNER_BELT);
        JComboBox<String> locationComboBox;
        TronSpinner intervalSpinner = new TronSpinner(new SpinnerNumberModel(10, 1, 60, 1));

        Field[] fields = ScreenLocations.class.getDeclaredFields();
        List<String> locationNames = new ArrayList<>();
        for (Field field : fields) {
            if (Modifier.isPublic(field.getModifiers()) &&
                    Modifier.isStatic(field.getModifiers()) &&
                    field.getType().equals(Rectangle.class)) {
                locationNames.add(field.getName());
            }
        }
        locationComboBox = new JComboBox<>(locationNames.toArray(new String[0]));
        JButton watchForChangeButton = new JButton("Watch for change");
        JComboBox<String> changeLocationComboBox = new JComboBox<>(locationNames.toArray(new String[0]));

        watchButton.addActionListener(e -> {
            if (watchThread == null || !watchThread.isAlive()) {
                Images selectedImage = (Images) imageComboBox.getSelectedItem();
                String selectedLocationName = (String) locationComboBox.getSelectedItem();
                int interval = (int) intervalSpinner.getValue();

                try {
                    Field selectedField = ScreenLocations.class.getField(selectedLocationName);
                    Rectangle selectedRectangle = (Rectangle) selectedField.get(null);

                    watchThread = new Thread(() -> {
                        watcherActivity.watch(selectedRectangle, selectedImage.toString(), interval);
                    });
                    watchThread.start();

                    watchButton.setText("Stop");
                    watchButton.setBackground(Color.GREEN);
                    watchButton.setOpaque(true);

                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            } else {
                watcherActivity.stop();
                watchButton.setText("Watch");
                watchButton.setBackground(null);
            }
        });

        watchForChangeButton.addActionListener(e -> {
            if (watchForChangeThread == null || !watchForChangeThread.isAlive()) {
                String selectedLocationName = (String) changeLocationComboBox.getSelectedItem();
                int interval = (int) intervalSpinner.getValue();

                try {
                    Field selectedField = ScreenLocations.class.getField(selectedLocationName);
                    Rectangle selectedRectangle = (Rectangle) selectedField.get(null);

                    watchForChangeThread = new Thread(() -> {
                        boolean hasChanged = watcherActivity.watchForChange(selectedRectangle, interval);
                        if (hasChanged) {
                            System.out.println("Change detected in: " + selectedLocationName);
                        }
                    });
                    watchForChangeThread.start();

                    watchForChangeButton.setText("Watching");
                    watchForChangeButton.setBackground(Color.GREEN);
                    watchForChangeButton.setOpaque(true);

                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            } else {
                watcherActivity.stopWatchingForChange();
                watchForChangeButton.setText("Watch for change");
                watchForChangeButton.setBackground(null);
            }
        });

        // Create sub-panels for each row
        JPanel row1 = new JPanel(new GridLayout(1, 1)); // 1 column for intervalSpinner
        JPanel row2 = new JPanel(new GridLayout(1, 3, 5, 5)); // 3 columns for watchButton, imageComboBox, locationComboBox
        JPanel row3 = new JPanel(new GridLayout(1, 2, 5, 5)); // 2 columns for watchForChangeButton, changeLocationComboBox

        // Add components to respective rows
        row1.add(intervalSpinner);
        row2.add(watchButton);
        row2.add(imageComboBox);
        row2.add(locationComboBox);
        row3.add(watchForChangeButton);
        row3.add(changeLocationComboBox);

        // Add rows to the control panel
        controlPanel.add(row1);
        controlPanel.add(row2);
        controlPanel.add(row3);

        add(controlPanel, BorderLayout.NORTH);
    }
}
