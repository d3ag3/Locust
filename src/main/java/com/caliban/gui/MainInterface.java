package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
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
        setSize(1280, 400);
        setLayout(new BorderLayout());
        setResizable(true);
        
        // Set Tron-style background and remove any borders
        getContentPane().setBackground(TronComponents.TRON_DARK);
        getRootPane().setBorder(null);
        
        // Ensure the frame itself has no insets
        setUndecorated(false);
        // Force repaint to ensure no white artifacts
        getContentPane().setLayout(new BorderLayout());

        // Create tabs with Tron styling
        tabbedPane = new TronTabbedPane();
        tabbedPane.setBackground(TronComponents.TRON_DARK);
        tabbedPane.setOpaque(true);

        // Create a panel for the "Mining" tab
        ActivityLogPanel activityLogPanel = new ActivityLogPanel();
        AlertPanel alertPanel = new AlertPanel();
        JPanel generalPanel = new GeneralPanel();
        JPanel miningPanel = new MiningPanel();
        JPanel watcherPanel = new WatcherPanel();
        JPanel missionsPanel = new MissionsPanel();
        JPanel imagePanel = new ImagePanel();
        JPanel locationsPanel = new LocationsPanel();
        JPanel autoLooterPanel = new JPanel();

        // Add tabs to the tabbed pane with Tron-style icons
        tabbedPane.addTab("[ GENERAL ]", generalPanel);
        tabbedPane.addTab("[ MINING ]", miningPanel);
        tabbedPane.addTab("[ WATCHER ]", watcherPanel);
        tabbedPane.addTab("[ MISSIONS ]", missionsPanel);
        tabbedPane.addTab("[ IMAGES ]", imagePanel);
        tabbedPane.addTab("[ LOCATIONS ]", locationsPanel);
        tabbedPane.addTab("[ AUTOLOOTER ]", autoLooterPanel);

        // Create horizontal split pane for Activity Log and Alert panels
        JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, activityLogPanel, alertPanel);
        bottomSplitPane.setDividerLocation(640); // Half of 1280 width
        bottomSplitPane.setResizeWeight(0.5); // Equal weight distribution
        bottomSplitPane.setDividerSize(3);
        bottomSplitPane.setBorder(null);
        bottomSplitPane.setBackground(TronComponents.TRON_DARK);
        bottomSplitPane.setOpaque(true);
        // Set divider color to black
        bottomSplitPane.getUI().getClass();
        bottomSplitPane.setUI(new javax.swing.plaf.basic.BasicSplitPaneUI() {
            public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
                return new javax.swing.plaf.basic.BasicSplitPaneDivider(this) {
                    public void setBorder(javax.swing.border.Border b) {}
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });

        // Create main vertical split pane with tabs on top and bottom panels below
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, bottomSplitPane);
        splitPane.setDividerLocation(200); // Half of 400 height
        splitPane.setResizeWeight(0.5); // Equal weight distribution
        splitPane.setDividerSize(3);
        splitPane.setBorder(null);
        splitPane.setBackground(TronComponents.TRON_DARK);
        splitPane.setOpaque(true);
        // Set divider color to black
        splitPane.setUI(new javax.swing.plaf.basic.BasicSplitPaneUI() {
            public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
                return new javax.swing.plaf.basic.BasicSplitPaneDivider(this) {
                    public void setBorder(javax.swing.border.Border b) {}
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        
        // Add 5-pixel border directly to the split pane
        splitPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            null
        ));
        
        add(splitPane, BorderLayout.CENTER);
    }
}
