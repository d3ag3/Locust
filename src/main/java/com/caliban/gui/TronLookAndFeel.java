package com.caliban.gui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class TronLookAndFeel extends MetalLookAndFeel {
    
    public static void apply() {
        try {
            MetalLookAndFeel.setCurrentTheme(new TronTheme());
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            
            // Additional Tron-style customizations
            UIManager.put("Panel.background", new ColorUIResource(0x0a0a0a));
            UIManager.put("TabbedPane.background", new ColorUIResource(0x0a0a0a));
            UIManager.put("TabbedPane.foreground", new ColorUIResource(0x6FD2DC));
            UIManager.put("TabbedPane.selected", new ColorUIResource(0x1a1a1a));
            UIManager.put("TabbedPane.selectedForeground", new ColorUIResource(0x6FD2DC));
            UIManager.put("TabbedPane.tabAreaBackground", new ColorUIResource(0x0a0a0a));
            UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(0x003366));
            UIManager.put("TabbedPane.borderHightlightColor", new ColorUIResource(0x6FD2DC));
            UIManager.put("TabbedPane.darkShadow", new ColorUIResource(0x003366));
            
            UIManager.put("Button.background", new ColorUIResource(0x1a1a1a));
            UIManager.put("Button.foreground", new ColorUIResource(0x00ffff));
            UIManager.put("Button.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x00ffff), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));
            
            UIManager.put("RadioButton.background", new ColorUIResource(0x0a0a0a));
            UIManager.put("RadioButton.foreground", new ColorUIResource(0x00ffff));
            
            UIManager.put("ComboBox.background", new ColorUIResource(0x1a1a1a));
            UIManager.put("ComboBox.foreground", new ColorUIResource(0x00ffff));
            UIManager.put("ComboBox.selectionBackground", new ColorUIResource(0x003366));
            UIManager.put("ComboBox.selectionForeground", new ColorUIResource(0x00ffff));
            
            UIManager.put("Spinner.background", new ColorUIResource(0x1a1a1a));
            UIManager.put("Spinner.foreground", new ColorUIResource(0x00ffff));
            
            UIManager.put("Label.foreground", new ColorUIResource(0x6FD2DC));
            
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    
    private static class TronTheme extends MetalTheme {
        
        // Primary colors (used for highlights, selections)
        private final ColorUIResource primary1 = new ColorUIResource(0x003366); // Dark cyan
        private final ColorUIResource primary2 = new ColorUIResource(0x0066cc); // Medium cyan
        private final ColorUIResource primary3 = new ColorUIResource(0x6FD2DC); // Bright cyan
        
        // Secondary colors (used for backgrounds, borders)
        private final ColorUIResource secondary1 = new ColorUIResource(0x0a0a0a); // Very dark
        private final ColorUIResource secondary2 = new ColorUIResource(0x1a1a1a); // Dark
        private final ColorUIResource secondary3 = new ColorUIResource(0x2a2a2a); // Medium dark
        
        private final FontUIResource controlFont = new FontUIResource("Consolas", Font.BOLD, 15);
        private final FontUIResource systemFont = new FontUIResource("Consolas", Font.PLAIN, 15);
        private final FontUIResource menuFont = new FontUIResource("Consolas", Font.BOLD, 15);
        
        @Override
        public String getName() {
            return "Tron";
        }
        
        @Override
        protected ColorUIResource getPrimary1() { return primary1; }
        
        @Override
        protected ColorUIResource getPrimary2() { return primary2; }
        
        @Override
        protected ColorUIResource getPrimary3() { return primary3; }
        
        @Override
        protected ColorUIResource getSecondary1() { return secondary1; }
        
        @Override
        protected ColorUIResource getSecondary2() { return secondary2; }
        
        @Override
        protected ColorUIResource getSecondary3() { return secondary3; }
        
        @Override
        public FontUIResource getControlTextFont() { return controlFont; }
        
        @Override
        public FontUIResource getSystemTextFont() { return systemFont; }
        
        @Override
        public FontUIResource getMenuTextFont() { return menuFont; }
        
        @Override
        public FontUIResource getSubTextFont() { return systemFont; }
        
        @Override
        public FontUIResource getWindowTitleFont() { return controlFont; }
        
        @Override
        public FontUIResource getUserTextFont() { return systemFont; }
    }
}