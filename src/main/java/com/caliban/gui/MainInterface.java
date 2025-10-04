package com.caliban.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainInterface extends JFrame {

    private JTabbedPane tabbedPane;

    public MainInterface() {
        setTitle("Locust");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLayout(new BorderLayout());

        // Create tabs
        tabbedPane = new JTabbedPane();

        // Create a panel for the "Mining" tab
        JPanel generalPanel = new JPanel();
        JPanel miningPanel = new MiningPanel();
        JPanel missionsPanel = new MissionPanel();
        JPanel watcherPanel = new WatcherPanel();

        // Add tabs to the tabbed pane
        tabbedPane.addTab("Mining", miningPanel);
        tabbedPane.addTab("Missions", missionsPanel);
        tabbedPane.addTab("Watcher", watcherPanel);
        tabbedPane.addTab("General", generalPanel);

        // Add the tabbed pane to the main window
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
