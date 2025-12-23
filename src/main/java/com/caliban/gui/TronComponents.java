package com.caliban.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TronComponents {
    
    // Tron color scheme
    public static final Color TRON_DARK = new Color(10, 10, 10);
    public static final Color TRON_MEDIUM = new Color(26, 26, 26);
    public static final Color TRON_CYAN = new Color(0x6FD2DC);
    public static final Color TRON_CYAN_DARK = new Color(0x0066cc);
    public static final Color TRON_CYAN_DARKER = new Color(0x003366);
    public static final Color TRON_ORANGE = new Color(255, 153, 0);
    public static final Color TRON_GREEN = new Color(0, 255, 102);
    public static final Color TRON_RED = new Color(255, 51, 51);
    
    public static class TronButton extends JButton {
        private boolean isGlowing = false;
        
        public TronButton(String text) {
            super(text);
            setupTronStyle();
        }
        
        private void setupTronStyle() {
            setBackground(TRON_MEDIUM);
            setForeground(TRON_CYAN);
            setBorder(new TronBorder(TRON_CYAN));
            setFocusPainted(false);
            setOpaque(true);
            // Tron-style button font
            Font[] tronFonts = {
                new Font("Consolas", Font.BOLD, 15),
                new Font("Courier New", Font.BOLD, 15),
                new Font("Monospaced", Font.BOLD, 15),
                new Font("OCR A Extended", Font.BOLD, 15)
            };
            setFont(tronFonts[0]);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(TRON_CYAN_DARKER);
                    setBorder(new TronBorder(TRON_CYAN, true));
                    isGlowing = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(TRON_MEDIUM);
                    setBorder(new TronBorder(TRON_CYAN, false));
                    isGlowing = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isGlowing) {
                // Add glow effect
                for (int i = 1; i <= 3; i++) {
                    g2d.setColor(new Color(TRON_CYAN.getRed(), TRON_CYAN.getGreen(), TRON_CYAN.getBlue(), 30));
                    g2d.fillRoundRect(-i, -i, getWidth() + (2 * i), getHeight() + (2 * i), 8, 8);
                }
            }
            
            super.paintComponent(g);
            g2d.dispose();
        }
    }
    
    public static class TronPanel extends JPanel {
        
        public TronPanel() {
            setupTronStyle();
        }
        
        public TronPanel(LayoutManager layout) {
            super(layout);
            setupTronStyle();
        }
        
        private void setupTronStyle() {
            setBackground(TRON_DARK);
            setBorder(new TronBorder(TRON_CYAN_DARK));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Add subtle grid pattern
            g2d.setColor(new Color(TRON_CYAN_DARKER.getRed(), TRON_CYAN_DARKER.getGreen(), TRON_CYAN_DARKER.getBlue(), 20));
            for (int x = 0; x < getWidth(); x += 20) {
                g2d.drawLine(x, 0, x, getHeight());
            }
            for (int y = 0; y < getHeight(); y += 20) {
                g2d.drawLine(0, y, getWidth(), y);
            }
            
            g2d.dispose();
        }
    }
    
    public static class TronLabel extends JLabel {
        
        public TronLabel(String text) {
            super(text);
            setupTronStyle();
        }
        
        private void setupTronStyle() {
            setForeground(TRON_CYAN);
            // Try Tron-style fonts in order of preference
            Font[] tronFonts = {
                new Font("Consolas", Font.BOLD, 15),
                new Font("Courier New", Font.BOLD, 15),
                new Font("Monospaced", Font.BOLD, 15),
                new Font("OCR A Extended", Font.BOLD, 15),
                new Font("Lucida Console", Font.BOLD, 15)
            };
            
            // Use the first available font
            for (Font font : tronFonts) {
                if (getFontMetrics(font) != null) {
                    setFont(font);
                    break;
                }
            }
        }
    }
    
    public static class TronRadioButton extends JRadioButton {
        
        public TronRadioButton(String text) {
            super(text);
            setupTronStyle();
        }
        
        private void setupTronStyle() {
            setBackground(TRON_DARK);
            setForeground(TRON_CYAN);
            setFont(new Font("Consolas", Font.BOLD, 14));
            setFocusPainted(false);
            setOpaque(false);
            
            // Apply custom Tron radio button UI
            setUI(new TronRadioButtonUI());
        }
    }
    
    public static class TronComboBox<E> extends JComboBox<E> {
        
        public TronComboBox(E[] items) {
            super(items);
            setupTronStyle();
        }
        
        private void setupTronStyle() {
            setBackground(TRON_MEDIUM);
            setForeground(TRON_CYAN);
            setBorder(new TronBorder(TRON_CYAN_DARK));
            setFont(new Font("Consolas", Font.BOLD, 14));
        }
    }
    
    public static class TronSpinner extends JSpinner {
        
        public TronSpinner(SpinnerModel model) {
            super(model);
            setupTronStyle();
        }
        
        private void setupTronStyle() {
            setBackground(TRON_MEDIUM);
            setForeground(TRON_CYAN);
            setBorder(new TronBorder(TRON_CYAN_DARK));
            setFont(new Font("Consolas", Font.BOLD, 14));
            
            // Apply custom Tron spinner UI
            setUI(new TronSpinnerUI());
            
            // Style the editor
            JComponent editor = getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
                defaultEditor.getTextField().setBackground(TRON_MEDIUM);
                defaultEditor.getTextField().setForeground(TRON_CYAN);
                defaultEditor.getTextField().setCaretColor(TRON_CYAN);
                defaultEditor.getTextField().setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            }
        }
    }
    
    public static class TronBorder implements Border {
        private Color color;
        private boolean isGlowing;
        
        public TronBorder(Color color) {
            this(color, false);
        }
        
        public TronBorder(Color color, boolean isGlowing) {
            this.color = color;
            this.isGlowing = isGlowing;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isGlowing) {
                // Draw glow effect
                for (int i = 0; i < 3; i++) {
                    g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80 - (i * 20)));
                    g2d.setStroke(new BasicStroke(3 - i));
                    g2d.drawRoundRect(x + i, y + i, width - 2 * (i + 1), height - 2 * (i + 1), 6, 6);
                }
            }
            
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(x, y, width - 1, height - 1, 6, 6);
            
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}