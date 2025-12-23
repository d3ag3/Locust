package com.caliban.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TronRadioButtonUI extends BasicRadioButtonUI {
    
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Get dimensions
        Dimension size = c.getSize();
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate icon area
        int iconSize = 14;
        int iconX = 2;
        int iconY = (size.height - iconSize) / 2;
        
        // Calculate text area
        String text = button.getText();
        int textX = iconX + iconSize + 6;
        int textY = (size.height + fm.getAscent() - fm.getDescent()) / 2;
        
        // Draw the radio button circle
        paintRadioIcon(g2d, iconX, iconY, iconSize, button.isSelected(), button.getModel().isRollover());
        
        // Draw the text
        if (text != null && !text.isEmpty()) {
            g2d.setColor(button.getForeground());
            g2d.setFont(button.getFont());
            g2d.drawString(text, textX, textY);
        }
        
        g2d.dispose();
    }
    
    private void paintRadioIcon(Graphics2D g2d, int x, int y, int size, boolean selected, boolean rollover) {
        // Outer ring
        if (rollover) {
            g2d.setColor(TronComponents.TRON_CYAN);
            g2d.setStroke(new BasicStroke(2));
        } else {
            g2d.setColor(TronComponents.TRON_CYAN_DARK);
            g2d.setStroke(new BasicStroke(1));
        }
        
        Ellipse2D outerCircle = new Ellipse2D.Float(x, y, size, size);
        g2d.draw(outerCircle);
        
        // Inner background
        g2d.setColor(TronComponents.TRON_DARK);
        Ellipse2D innerBg = new Ellipse2D.Float(x + 2, y + 2, size - 4, size - 4);
        g2d.fill(innerBg);
        
        // Selected dot with glow effect
        if (selected) {
            // Glow effect
            for (int i = 0; i < 3; i++) {
                g2d.setColor(new Color(TronComponents.TRON_CYAN.getRed(), 
                                     TronComponents.TRON_CYAN.getGreen(), 
                                     TronComponents.TRON_CYAN.getBlue(), 
                                     80 - (i * 20)));
                int glowSize = size - 6 + i;
                int glowOffset = (size - glowSize) / 2;
                Ellipse2D glow = new Ellipse2D.Float(x + glowOffset, y + glowOffset, glowSize, glowSize);
                g2d.fill(glow);
            }
            
            // Center dot
            g2d.setColor(TronComponents.TRON_CYAN);
            Ellipse2D centerDot = new Ellipse2D.Float(x + 4, y + 4, size - 8, size - 8);
            g2d.fill(centerDot);
        }
    }
    
    @Override
    public Dimension getPreferredSize(JComponent c) {
        AbstractButton button = (AbstractButton) c;
        String text = button.getText();
        
        FontMetrics fm = c.getFontMetrics(c.getFont());
        int textWidth = (text != null) ? fm.stringWidth(text) : 0;
        int textHeight = fm.getHeight();
        
        int iconSize = 14;
        int gap = 6;
        
        return new Dimension(iconSize + gap + textWidth + 4, Math.max(iconSize, textHeight) + 4);
    }
}