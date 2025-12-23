package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainInterface extends TronFrame {

    private TronTabbedPane tabbedPane;

    public MainInterface() {
        super("◤ LOCUST ◥ - Tron Interface");
        // Apply Tron look and feel
        TronLookAndFeel.apply();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 720);
        setLayout(new BorderLayout());
        
        // Set Tron-style background
        getContentPane().setBackground(TronComponents.TRON_DARK);

        // Create tabs with Tron styling
        tabbedPane = new TronTabbedPane();

        // Create a panel for the "Mining" tab
        ActivityLogPanel activityLogPanel = new ActivityLogPanel();
        AlertPanel alertPanel = new AlertPanel();
        JPanel generalPanel = new GeneralPanel();
        JPanel miningPanel = new MiningPanel();
        JPanel watcherPanel = new WatcherPanel();
        JPanel missionsPanel = new MissionsPanel();
        JPanel imagePanel = new ImagePanel();
        JPanel locationsPanel = new LocationsPanel();

        // Add tabs to the tabbed pane with Tron-style icons
        tabbedPane.addTab("[ GENERAL ]", generalPanel);
        tabbedPane.addTab("[ MINING ]", miningPanel);
        tabbedPane.addTab("[ WATCHER ]", watcherPanel);
        tabbedPane.addTab("[ MISSIONS ]", missionsPanel);
        tabbedPane.addTab("[ IMAGES ]", imagePanel);
        tabbedPane.addTab("[ LOCATIONS ]", locationsPanel);

        // Create horizontal split pane for Activity Log and Alert panels
        JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, activityLogPanel, alertPanel);
        bottomSplitPane.setDividerLocation(960); // Half of 1920 width
        bottomSplitPane.setResizeWeight(0.5); // Equal weight distribution
        bottomSplitPane.setDividerSize(3);
        bottomSplitPane.setBorder(null);
        bottomSplitPane.setBackground(TronComponents.TRON_DARK);

        // Create main vertical split pane with tabs on top and bottom panels below
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, bottomSplitPane);
        splitPane.setDividerLocation(360); // Half of 720 height
        splitPane.setResizeWeight(0.5); // Equal weight distribution
        splitPane.setDividerSize(3);
        splitPane.setBorder(null);
        splitPane.setBackground(TronComponents.TRON_DARK);
        
        // Style the divider
        splitPane.getUI().getClass();
        
        add(splitPane, BorderLayout.CENTER);
    }
}
