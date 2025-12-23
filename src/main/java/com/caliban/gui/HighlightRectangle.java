package com.caliban.gui;

import javax.swing.*;
import java.awt.*;

public class HighlightRectangle extends JWindow {

    private final Rectangle rect;

    public HighlightRectangle(Rectangle rect) {
        this.rect = rect;
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(rect);
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        add(panel);
    }

    public void showFor(int milliseconds) {
        setVisible(true);
        Timer timer = new Timer(milliseconds, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
