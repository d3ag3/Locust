package com.caliban.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumn;

import com.caliban.helper.ScreenLocationsHelper;
import com.caliban.service.ScreenActions;
import com.caliban.gui.HighlightRectangle;

public class ImagePanel extends TronComponents.TronPanel {

    private ScreenActions screenActions;
    private ScreenLocationsHelper screenLocations;

    public ImagePanel() {
        setLayout(new BorderLayout());
        
        // Initialize services
        this.screenActions = new ScreenActions();
        this.screenLocations = new ScreenLocationsHelper();

        ImageTableModel tableModel = new ImageTableModel();
        TronTable table = new TronTable(tableModel);

        // Set row heights to fit images
        for (int i = 0; i < table.getRowCount(); i++) {
            ImageIcon icon = (ImageIcon) table.getValueAt(i, 1);
            if (icon != null) {
                table.setRowHeight(i, Math.max(25, icon.getIconHeight() + 4));
            } else {
                table.setRowHeight(i, 25);
            }
        }

        // Set column widths
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        nameColumn.setPreferredWidth(300);
        nameColumn.setMaxWidth(400);

        TableColumn imageColumn = table.getColumnModel().getColumn(1);
        imageColumn.setPreferredWidth(300);
        
        // Add selection listener for click-to-highlight using actual screen search
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String imageName = (String) table.getValueAt(selectedRow, 0);
                    findAndHighlightImage(imageName);
                }
            }
        });

        add(TronTable.createTronScrollPane(table), BorderLayout.CENTER);
    }
    
    private void findAndHighlightImage(String imageName) {
        // Run image search on background thread to avoid blocking EDT
        SwingWorker<ArrayList<Rectangle>, Void> worker = new SwingWorker<ArrayList<Rectangle>, Void>() {
            @Override
            protected ArrayList<Rectangle> doInBackground() throws Exception {
                // Use ScreenActions to find all instances of the image on screen
                Rectangle searchArea = screenLocations.entireScreen();
                return screenActions.findAllImage(searchArea, imageName);
            }
            
            @Override
            protected void done() {
                try {
                    ArrayList<Rectangle> foundRectangles = get();
                    
                    if (foundRectangles != null && !foundRectangles.isEmpty()) {
                        System.out.println("Found " + foundRectangles.size() + " instance(s) of " + imageName);
                        
                        // Highlight each found rectangle for 3 seconds
                        // This runs on EDT which is safe for UI updates
                        SwingUtilities.invokeLater(() -> {
                            for (Rectangle rect : foundRectangles) {
                                HighlightRectangle highlight = new HighlightRectangle(rect);
                                highlight.showFor(3000);
                            }
                        });
                    } else {
                        System.out.println("Image not found on screen: " + imageName);
                    }
                } catch (Exception e) {
                    System.err.println("Error during image search: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        
        worker.execute();
    }
}
