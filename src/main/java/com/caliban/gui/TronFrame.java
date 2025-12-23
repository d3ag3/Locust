package com.caliban.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TronFrame extends JFrame {
    
    private Timer animationTimer;
    private float glowIntensity = 0.0f;
    private boolean glowIncreasing = true;
    
    public TronFrame(String title) {
        super(title);
        setupTronFrame();
    }
    
    private void setupTronFrame() {
        // Set Tron colors
        getContentPane().setBackground(TronComponents.TRON_DARK);
        
        // Create animated glow effect
        animationTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (glowIncreasing) {
                    glowIntensity += 0.02f;
                    if (glowIntensity >= 1.0f) {
                        glowIntensity = 1.0f;
                        glowIncreasing = false;
                    }
                } else {
                    glowIntensity -= 0.02f;
                    if (glowIntensity <= 0.3f) {
                        glowIntensity = 0.3f;
                        glowIncreasing = true;
                    }
                }
                repaint();
            }
        });
        animationTimer.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw animated border glow
        int alpha = (int) (glowIntensity * 255);
        Color glowColor = new Color(TronComponents.TRON_CYAN.getRed(), 
                                  TronComponents.TRON_CYAN.getGreen(), 
                                  TronComponents.TRON_CYAN.getBlue(), alpha);
        
        for (int i = 0; i < 5; i++) {
            g2d.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), alpha / (i + 1)));
            g2d.setStroke(new BasicStroke(3 + i));
            g2d.drawRoundRect(i, i, getWidth() - 2 * (i + 1), getHeight() - 2 * (i + 1), 10, 10);
        }
        
        g2d.dispose();
    }
    
    @Override
    public void dispose() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        super.dispose();
    }
}