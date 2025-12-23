package com.caliban.gui;

import com.caliban.config.Locations;
import com.caliban.config.ScreenLocations;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class LocationsPanel extends TronComponents.TronPanel {

    public LocationsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createTablePanel(Locations.class, "Image Locations"));
        add(createTablePanel(ScreenLocations.class, "Screen Locations"));
    }

    private JPanel createTablePanel(Class<?> clazz, String title) {
        TronComponents.TronPanel panel = new TronComponents.TronPanel(new BorderLayout());
        
        // Create Tron-style titled border
        javax.swing.border.TitledBorder titledBorder = BorderFactory.createTitledBorder("[ " + title.toUpperCase() + " ]");
        titledBorder.setTitleColor(TronComponents.TRON_CYAN);
        titledBorder.setTitleFont(new Font("Consolas", Font.BOLD, 12));
        panel.setBorder(titledBorder);

        String[] columnNames = {"Name", "X", "Y", "Width", "Height"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        TronTable table = new TronTable(model);

        List<Rectangle> rectangles = new ArrayList<>();

        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) && field.getType() == Rectangle.class) {
                    String name = field.getName();
                    Rectangle rect = (Rectangle) field.get(null);
                    rectangles.add(rect);
                    model.addRow(new Object[]{name, rect.x, rect.y, rect.width, rect.height});
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Rectangle rect = rectangles.get(selectedRow);
                    new HighlightRectangle(rect).showFor(2000);
                }
            }
        });
        
        // Set column widths - make name column twice as wide
        if (table.getColumnCount() > 0) {
            TableColumn nameColumn = table.getColumnModel().getColumn(0);
            nameColumn.setPreferredWidth(300);
            nameColumn.setMaxWidth(400);
        }

        JScrollPane scrollPane = TronTable.createTronScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
