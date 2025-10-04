package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.caliban.activity.MissionerActivity;

public class MissionPanel extends JPanel {

    private JButton onButton, offButton;
    private JRadioButton miningButton, distButton;
    private JComboBox<ImageIcon> agentDropdown;
    private String selectedAgent;
    private JTextArea historyArea;
    private List<String> history = new LinkedList<>();

    private Map<ImageIcon, String> agentImageValueMap = new HashMap<>();
    private MissionerActivity missionerActivity= new MissionerActivity();

    public MissionPanel() {

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
        miningButton = new JRadioButton("Mine");
        miningButton.setSelected(true);
        distButton = new JRadioButton("Distribution");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(miningButton);
        radioGroup.add(distButton);

        onButton = new JButton("Start");
        offButton = new JButton("Stop");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(onButton);
        buttonGroup.add(offButton);

        String pathYourSystem = System.getProperty("user.dir");

        agentImageValueMap.put(new ImageIcon(pathYourSystem + "\\src\\main\\resources\\images\\agents\\agent1.png"), "agent1.png"); 
        agentImageValueMap.put(new ImageIcon(pathYourSystem + "\\src\\main\\resources\\images\\agents\\agent2.png"), "agent2.png");
        agentImageValueMap.put(new ImageIcon(pathYourSystem + "\\src\\main\\resources\\images\\agents\\agent1.png"), "agent3.png");

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

        panel.add(miningButton);
        panel.add(distButton);
        panel.add(onButton);
        panel.add(offButton);
        panel.add(agentDropdown);
        return panel;
    }

    private void setupButtonListeners() {
        onButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAlerts("Missions On");

                Thread imageFinderThread = new Thread(() -> {
                    activatePanelAction();
                });
                imageFinderThread.start();
            }
        });

        offButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                missionerActivity.stop();
                updateAlerts("Off");
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
        historyArea = new JTextArea(10, 20);
        historyArea.setEditable(false);
        historyPanel.add(new JScrollPane(historyArea), BorderLayout.CENTER);
        historyPanel.setBorder(new TitledBorder("Alerts"));
        return historyPanel;
    }

    private void updateAlerts(String action) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date());
        history.add(timestamp + " - " + action);

        // Limit the history to 1000 items
        if (history.size() > 1000) {
            history.remove(0); // Remove the oldest item
        }

        // Create an array of strings from the history list
        String[] historyText = history.toArray(new String[history.size()]);

        // Join the array elements into a single string with newline separators
        String historyString = String.join("\n", historyText);

        historyArea.setText(historyString);
    }

    private void activatePanelAction() {
        if (selectedAgent != null) missionerActivity.start(selectedAgent, 10);
    }

}
