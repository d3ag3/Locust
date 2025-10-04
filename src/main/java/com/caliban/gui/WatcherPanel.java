package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.caliban.activity.SimpleActivityWatcher;

public class WatcherPanel extends JPanel {

    private JCheckBox beltAlarm, localHostiles;
    private JTextArea historyArea;
    private List<String> history = new LinkedList<>();

    public WatcherPanel() {
        setLayout(new FlowLayout());

        JPanel buttonPanel = getButtonsPanel();
        JPanel alertPanel = getAlertPanel();

        setupButtonListeners();
        
        add(buttonPanel, BorderLayout.NORTH);
        add(alertPanel, BorderLayout.SOUTH);
    }

    private JPanel getButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        beltAlarm = new JCheckBox("Belt Alarm");
        localHostiles = new JCheckBox("Hostiles Alarm");

        panel.add(beltAlarm);
        panel.add(localHostiles);
        return panel;
    }

    private void setupButtonListeners() {
        SimpleActivityWatcher beltWatcher = new SimpleActivityWatcher();
        SimpleActivityWatcher hostileWatcher = new SimpleActivityWatcher();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        beltAlarm.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Thread watcherThread = new Thread(() -> {
                        beltWatcher.watch(new Rectangle(1542, 788, screenSize.width - 1542, screenSize.height - 788),
                        "scannerBelt.png", "beltAlarm.wav", 5000);
                    });
                    watcherThread.start();
                    updateAlerts("Watching for Belts");
                } else {
                    beltWatcher.stop();
                    updateAlerts("Stopped Watching for Belts");
                }
            }
        });

        localHostiles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Thread watcherThread = new Thread(() -> {
                        hostileWatcher.watch(new Rectangle(0, 788, 600, screenSize.height - 788),
                        "negativeStandings.png", "bigAlarm.wav", 5000);
                    });
                    watcherThread.start();
                    updateAlerts("Watching for Belts");
                } else {
                    beltWatcher.stop();
                    updateAlerts("Stopped Watching for Belts");
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
}
