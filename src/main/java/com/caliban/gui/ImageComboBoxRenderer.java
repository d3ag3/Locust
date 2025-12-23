package com.caliban.gui;

import com.caliban.enums.Images;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageComboBoxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Images) {
            Images image = (Images) value;
            setText(image.toString());
            URL imageUrl = getClass().getResource("/images/" + image.toString());
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaledImage));
            }
        }
        return this;
    }
}
