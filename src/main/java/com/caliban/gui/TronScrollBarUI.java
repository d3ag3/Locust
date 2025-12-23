package com.caliban.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TronScrollBarUI extends BasicScrollBarUI {
    
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = TronComponents.TRON_CYAN_DARK;
        this.thumbDarkShadowColor = TronComponents.TRON_CYAN;
        this.thumbHighlightColor = TronComponents.TRON_CYAN;
        this.thumbLightShadowColor = TronComponents.TRON_CYAN_DARKER;
        this.trackColor = TronComponents.TRON_DARK;
        this.trackHighlightColor = TronComponents.TRON_MEDIUM;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createTronArrowButton(orientation);
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createTronArrowButton(orientation);
    }

    private JButton createTronArrowButton(int orientation) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(16, 16));
        button.setBackground(TronComponents.TRON_MEDIUM);
        button.setForeground(TronComponents.TRON_CYAN);
        button.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN_DARK));
        button.setFocusPainted(false);
        button.setFont(new Font("Consolas", Font.BOLD, 8));
        
        // Set arrow text based on orientation
        switch (orientation) {
            case SwingConstants.NORTH:
                button.setText("▲");
                break;
            case SwingConstants.SOUTH:
                button.setText("▼");
                break;
            case SwingConstants.EAST:
                button.setText("►");
                break;
            case SwingConstants.WEST:
                button.setText("◄");
                break;
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
                button.setBackground(TronComponents.TRON_MEDIUM);
                button.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN_DARK));
            }
        });
        
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw thumb with gradient and glow effect
        GradientPaint gradient = new GradientPaint(
            thumbBounds.x, thumbBounds.y, TronComponents.TRON_CYAN_DARK,
            thumbBounds.x + thumbBounds.width, thumbBounds.y + thumbBounds.height, TronComponents.TRON_CYAN_DARKER
        );
        g2d.setPaint(gradient);
        g2d.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                         thumbBounds.width - 4, thumbBounds.height - 4, 6, 6);
        
        // Add glow border
        g2d.setColor(TronComponents.TRON_CYAN);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRoundRect(thumbBounds.x + 2, thumbBounds.y + 2,
                         thumbBounds.width - 4, thumbBounds.height - 4, 6, 6);
        
        g2d.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill track with dark color
        g2d.setColor(TronComponents.TRON_DARK);
        g2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        
        // Add subtle grid pattern
        g2d.setColor(new Color(TronComponents.TRON_CYAN_DARKER.getRed(), 
                              TronComponents.TRON_CYAN_DARKER.getGreen(), 
                              TronComponents.TRON_CYAN_DARKER.getBlue(), 30));
        
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            for (int y = trackBounds.y; y < trackBounds.y + trackBounds.height; y += 4) {
                g2d.drawLine(trackBounds.x, y, trackBounds.x + trackBounds.width, y);
            }
        } else {
            for (int x = trackBounds.x; x < trackBounds.x + trackBounds.width; x += 4) {
                g2d.drawLine(x, trackBounds.y, x, trackBounds.y + trackBounds.height);
            }
        }
        
        g2d.dispose();
    }
}