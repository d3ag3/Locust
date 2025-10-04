package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.caliban.activity.IceMiningActivity;
import com.caliban.activity.SimpleIceMining;
import com.caliban.activity.SimpleMining;

public class MiningPanel extends JPanel {

    private JButton onButton, offButton;
    private JRadioButton iceButton, oreButton;
    private JTextArea historyArea;
    private List<String> history = new LinkedList<>();

    //private SimpleIceMining simpleIceMining = new SimpleIceMining();
    private IceMiningActivity simpleIceMining = new IceMiningActivity();
    private SimpleMining simpleOreMining = new SimpleMining();

    public MiningPanel() {

        setLayout(new FlowLayout());

        // Create a panel for the history
        JPanel buttonPanel = getButtonPanel();
        JPanel alertPanel = getAlertPanel();

        onButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAlerts("Mining On");

                //onButton.setEnabled(false);
                //offButton.setEnabled(true);
                Thread imageFinderThread = new Thread(() -> {
                    activatePanelAction();
                });
                imageFinderThread.start();
            }
        });

        offButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //onButton.setEnabled(true);
                //offButton.setEnabled(false);
                updateAlerts("Off");

                simpleIceMining.stop();
                simpleOreMining.stop();
            }
        });

        add(buttonPanel, BorderLayout.NORTH);
        add(alertPanel, BorderLayout.SOUTH);
    }


    private JPanel getButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        iceButton = new JRadioButton("Ice");
        iceButton.setSelected(true);
        oreButton = new JRadioButton("Ore");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(iceButton);
        radioGroup.add(oreButton);

        onButton = new JButton("Start");
        offButton = new JButton("Stop");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(onButton);
        buttonGroup.add(offButton);

        panel.add(iceButton);
        panel.add(oreButton);
        panel.add(onButton);
        panel.add(offButton);
        return panel;
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
        if (oreButton.isSelected()) simpleOreMining.start();

        if (iceButton.isSelected()) simpleIceMining.start(10);
    }

}
