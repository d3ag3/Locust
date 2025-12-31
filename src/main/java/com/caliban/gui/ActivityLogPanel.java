package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ActivityLogPanel extends TronComponents.TronPanel {

    private JTextArea logArea;

    public ActivityLogPanel() {
        setupPanel();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(TronComponents.TRON_DARK);
        setOpaque(true);
        setBorder(null);

        // Create text area for activity log
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(TronComponents.TRON_DARK);
        logArea.setForeground(TronComponents.TRON_CYAN);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        logArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create scroll pane with Tron styling
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBackground(TronComponents.TRON_DARK);
        scrollPane.setBorder(BorderFactory.createLineBorder(TronComponents.TRON_CYAN, 1));
        scrollPane.getViewport().setBackground(TronComponents.TRON_DARK);
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(true);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Add some sample log entries
        appendLog("System initialized");
        appendLog("Tron interface activated");
        appendLog("Activity monitoring started");
    }

    public void appendLog(String message) {
        if (logArea != null) {
            String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }

    public void clearLog() {
        if (logArea != null) {
            logArea.setText("");
        }
    }
}