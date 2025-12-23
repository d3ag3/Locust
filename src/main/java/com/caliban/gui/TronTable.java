package com.caliban.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;

public class TronTable extends JTable {
    
    public TronTable(TableModel model) {
        super(model);
        setupTronStyle();
    }
    
    private void setupTronStyle() {
        // Basic table colors
        setBackground(TronComponents.TRON_DARK);
        setForeground(TronComponents.TRON_CYAN);
        setGridColor(TronComponents.TRON_CYAN_DARK);
        setSelectionBackground(TronComponents.TRON_CYAN_DARKER);
        setSelectionForeground(TronComponents.TRON_CYAN);
        
        // Font
        setFont(new Font("Consolas", Font.PLAIN, 12));
        
        // Row height and grid
        setRowHeight(25);
        setShowGrid(true);
        setIntercellSpacing(new Dimension(1, 1));
        
        // Selection mode
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Header styling
        setupTronHeader();
        
        // Cell renderer
        setDefaultRenderer(Object.class, new TronCellRenderer());
    }
    
    private void setupTronHeader() {
        JTableHeader header = getTableHeader();
        header.setBackground(TronComponents.TRON_MEDIUM);
        header.setForeground(TronComponents.TRON_CYAN);
        header.setFont(new Font("Consolas", Font.BOLD, 12));
        header.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN_DARK));
        
        // Custom header renderer
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(TronComponents.TRON_MEDIUM);
                c.setForeground(TronComponents.TRON_CYAN);
                c.setFont(new Font("Consolas", Font.BOLD, 12));
                setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN_DARK));
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });
    }
    
    private static class TronCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (isSelected) {
                c.setBackground(TronComponents.TRON_CYAN_DARKER);
                c.setForeground(TronComponents.TRON_CYAN);
            } else {
                // Alternating row colors for better readability
                if (row % 2 == 0) {
                    c.setBackground(TronComponents.TRON_DARK);
                } else {
                    c.setBackground(new Color(15, 15, 15)); // Slightly lighter dark
                }
                c.setForeground(TronComponents.TRON_CYAN);
            }
            
            c.setFont(new Font("Consolas", Font.PLAIN, 11));
            setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            
            return c;
        }
    }
    
    public static JScrollPane createTronScrollPane(TronTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(TronComponents.TRON_DARK);
        scrollPane.setBorder(new TronComponents.TronBorder(TronComponents.TRON_CYAN_DARK));
        scrollPane.getViewport().setBackground(TronComponents.TRON_DARK);
        
        // Apply custom Tron scrollbar UI
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new TronScrollBarUI());
        verticalBar.setBackground(TronComponents.TRON_DARK);
        verticalBar.setPreferredSize(new Dimension(16, 0));
        
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        horizontalBar.setUI(new TronScrollBarUI());
        horizontalBar.setBackground(TronComponents.TRON_DARK);
        horizontalBar.setPreferredSize(new Dimension(0, 16));
        
        return scrollPane;
    }
}