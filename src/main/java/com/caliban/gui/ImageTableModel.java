package com.caliban.gui;

import javax.swing.table.AbstractTableModel;
import com.caliban.enums.Images;
import javax.swing.ImageIcon;
import java.io.InputStream;
import java.io.IOException;

public class ImageTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Image"};
    private final Object[][] data;

    public ImageTableModel() {
        Images[] imageEnums = Images.values();
        data = new Object[imageEnums.length][2];
        for (int i = 0; i < imageEnums.length; i++) {
            data[i][0] = imageEnums[i].toString();
            try (InputStream in = getClass().getResourceAsStream("/images/" + imageEnums[i].toString())) {
                if (in != null) {
                    byte[] buffer = in.readAllBytes();
                    data[i][1] = new ImageIcon(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
                data[i][1] = null;
            }
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) {
            return ImageIcon.class;
        }
        return String.class;
    }
}
