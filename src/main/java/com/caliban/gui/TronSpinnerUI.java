package com.caliban.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TronSpinnerUI extends BasicSpinnerUI {
    
    @Override
    protected Component createNextButton() {
        Component c = createTronArrowButton(SwingConstants.NORTH);
        c.setName("Spinner.nextButton");
        installNextButtonListeners(c);
        return c;
    }

    @Override
    protected Component createPreviousButton() {
        Component c = createTronArrowButton(SwingConstants.SOUTH);
        c.setName("Spinner.previousButton");
        installPreviousButtonListeners(c);
        return c;
    }
    
    private Component createTronArrowButton(int direction) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(16, 8));
        button.setBackground(TronComponents.TRON_DARK);
        button.setForeground(TronComponents.TRON_CYAN);
        button.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN_DARK));
        button.setFocusPainted(false);
        button.setFont(new Font("Consolas", Font.BOLD, 8));
        
        // Set button text based on direction
        if (direction == SwingConstants.NORTH) {
            button.setText("▲");
        } else {
            button.setText("▼");
        }
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(TronComponents.TRON_CYAN_DARKER);
                button.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN, true));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(TronComponents.TRON_DARK);
                button.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN_DARK));
            }
        });
        
        return button;
    }
}