package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.caliban.event.AlertEvent;
import com.caliban.event.EventBus;
import com.caliban.gui.TronComponents.*;

public class AlertPanel extends TronPanel {

    private JTextArea historyArea;
    private List<String> history = new LinkedList<>();

    public AlertPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(TronComponents.TRON_CYAN, 2),
            "[ SYSTEM ALERTS ]",
            0, 0,
            new Font("Consolas", Font.BOLD, 12),
            TronComponents.TRON_CYAN
        ));

        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false);
        historyArea.setBackground(TronComponents.TRON_DARK);
        historyArea.setForeground(TronComponents.TRON_CYAN);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        historyArea.setCaretColor(TronComponents.TRON_CYAN);
        historyArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBackground(TronComponents.TRON_DARK);
        scrollPane.setBorder(BorderFactory.createLineBorder(TronComponents.TRON_CYAN, 1));
        scrollPane.getViewport().setBackground(TronComponents.TRON_DARK);
        
        // Apply Tron scrollbar styling
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new TronScrollBarUI());
        verticalBar.setBackground(TronComponents.TRON_DARK);
        
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        horizontalBar.setUI(new TronScrollBarUI());
        horizontalBar.setBackground(TronComponents.TRON_DARK);
        
        add(scrollPane, BorderLayout.CENTER);

        EventBus.getInstance().subscribe(AlertEvent.class, this::handleAlertEvent);
    }

    private void handleAlertEvent(AlertEvent event) {
        updateAlerts(event.getMessage());
    }

    public void updateAlerts(String action) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date());
        String message = timestamp + " - " + action + "\n";
        history.add(message);

        // Limit the history to 1000 items
        if (history.size() > 1000) {
            history.remove(0); // Remove the oldest item
            String fullHistory = String.join("", history);
            historyArea.setText(fullHistory);
        } else {
            historyArea.append(message);
        }
        
        // Auto-scroll to the bottom to show the latest message
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }
}
