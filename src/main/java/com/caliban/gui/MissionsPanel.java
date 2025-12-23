package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.caliban.activity.DistributionMissionsActivity;
import com.caliban.gui.TronComponents.*;

public class MissionsPanel extends TronComponents.TronPanel {

    private TronButton toggleButton;
    private TronRadioButton miningButton, distButton;
    private boolean isActive = false;
    private JComboBox<ImageIcon> agentDropdown;
    private TronSpinner charCountSpinner;
    private TronLabel charCountLabel;
    private String selectedAgent;

    private Map<ImageIcon, String> agentImageValueMap = new HashMap<>();
    private DistributionMissionsActivity distributionMissionsActivity = new DistributionMissionsActivity();

    public MissionsPanel() {

        setLayout(new FlowLayout());

        // Create a panel for the history
        JPanel buttonPanel = getButtonPanel();
        JPanel alertPanel = getAlertPanel();
        
        setupButtonListeners();

        add(buttonPanel, BorderLayout.NORTH);
        add(alertPanel, BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        miningButton = new TronRadioButton("[ MINE ]");
        miningButton.setSelected(true);
        distButton = new TronRadioButton("[ DISTRIBUTION ]");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(miningButton);
        radioGroup.add(distButton);

        toggleButton = new TronButton("[ INITIALIZE ]");

        loadAgentImages();

        agentDropdown = new JComboBox<>(agentImageValueMap.keySet().toArray(new ImageIcon[0]));

        agentDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                ImageIcon icon = (ImageIcon) value; // Get the image icon
                label.setIcon(icon);
                label.setText(agentImageValueMap.get(icon)); // Display the associated value
                label.setHorizontalTextPosition(SwingConstants.RIGHT); // Image to the left, text to the right
                return label;
            }
        });

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(10, 1, 20, 1);
        charCountSpinner = new TronSpinner(spinnerModel);
        charCountLabel = new TronLabel("[ CHARS ]");

        panel.add(miningButton);
        panel.add(distButton);
        panel.add(toggleButton);
        panel.add(agentDropdown);
        panel.add(charCountLabel);
        panel.add(charCountSpinner);
        return panel;
    }

    private void setupButtonListeners() {
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isActive) {
                    // Starting the activity
                    Thread imageFinderThread = new Thread(() -> {
                        activatePanelAction();
                    });
                    imageFinderThread.start();
                    toggleButton.setText("[ TERMINATE ]");
                    toggleButton.setBackground(TronComponents.TRON_GREEN);
                    toggleButton.setBorder(new TronComponents.TronBorder(TronComponents.TRON_GREEN, true));
                    isActive = true;
                } else {
                    // Stopping the activity
                    distributionMissionsActivity.stop();
                    toggleButton.setText("[ INITIALIZE ]");
                    toggleButton.setBackground(TronComponents.TRON_MEDIUM);
                    toggleButton.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN));
                    isActive = false;
                }
            }
        });

        agentDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = agentDropdown.getSelectedIndex();
                if (selectedIndex != -1) { // Check if an item is selected
                    ImageIcon selectedIcon = (ImageIcon) agentDropdown.getSelectedItem();
                    selectedAgent = agentImageValueMap.get(selectedIcon);
                }
            }
        });
    }

    private JPanel getAlertPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(new TitledBorder("Alerts"));
        return historyPanel;
    }

    private void activatePanelAction() {
        if (selectedAgent != null) distributionMissionsActivity.start(selectedAgent, (int) charCountSpinner.getValue());
    }

    private void loadAgentImages() {
        String pathYourSystem = System.getProperty("user.dir");
        String agentsFolder = pathYourSystem + "\\src\\main\\resources\\images\\agents";
        
        java.io.File folder = new java.io.File(agentsFolder);
        if (folder.exists() && folder.isDirectory()) {
            java.io.File[] files = folder.listFiles((dir, name) -> {
                String lowerName = name.toLowerCase();
                return lowerName.endsWith(".png") || lowerName.endsWith(".jpg") || 
                       lowerName.endsWith(".jpeg") || lowerName.endsWith(".gif");
            });
            
            if (files != null) {
                for (java.io.File file : files) {
                    String imagePath = file.getAbsolutePath();
                    ImageIcon icon = new ImageIcon(imagePath);
                    agentImageValueMap.put(icon, file.getName());
                }
            }
        }
    }

}
