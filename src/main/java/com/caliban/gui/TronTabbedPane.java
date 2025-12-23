package com.caliban.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TronTabbedPane extends JTabbedPane {
    
    public TronTabbedPane() {
        super();
        setupTronStyle();
    }
    
    private void setupTronStyle() {
        setBackground(TronComponents.TRON_DARK);
        setForeground(TronComponents.TRON_CYAN);
        setFont(new Font("Consolas", Font.BOLD, 14));
        
        // Custom UI for Tron-style tabs
        setUI(new TronTabbedPaneUI());
    }
    
    private class TronTabbedPaneUI extends BasicTabbedPaneUI {
        
        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, 
                                        int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isSelected) {
                // Selected tab - bright cyan with glow
                g2d.setColor(TronComponents.TRON_CYAN_DARKER);
                g2d.fillRoundRect(x, y, w, h, 8, 8);
                
                // Add glow effect
                g2d.setColor(new Color(TronComponents.TRON_CYAN.getRed(), 
                                     TronComponents.TRON_CYAN.getGreen(), 
                                     TronComponents.TRON_CYAN.getBlue(), 80));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(x-1, y-1, w+1, h+1, 8, 8);
            } else {
                // Unselected tab - dark background
                g2d.setColor(TronComponents.TRON_MEDIUM);
                g2d.fillRoundRect(x, y, w, h, 6, 6);
            }
            
            g2d.dispose();
        }
        
        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, 
                                    int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isSelected) {
                g2d.setColor(TronComponents.TRON_CYAN);
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setColor(TronComponents.TRON_CYAN_DARK);
                g2d.setStroke(new BasicStroke(1));
            }
            
            g2d.drawRoundRect(x, y, w-1, h-1, 6, 6);
            g2d.dispose();
        }
        
        @Override
        protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, 
                               int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            g2d.setFont(font);
            if (isSelected) {
                g2d.setColor(TronComponents.TRON_CYAN);
            } else {
                g2d.setColor(TronComponents.TRON_CYAN_DARK);
            }
            
            g2d.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            g2d.dispose();
        }
        
        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Insets insets = getContentBorderInsets(tabPlacement);
            int x = insets.left;
            int y = insets.top;
            int width = tabPane.getWidth() - insets.left - insets.right;
            int height = tabPane.getHeight() - insets.top - insets.bottom;
            
            g2d.setColor(TronComponents.TRON_CYAN_DARK);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(x, y, width-1, height-1, 8, 8);
            
            g2d.dispose();
        }
    }
}