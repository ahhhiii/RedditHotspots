package com.ahammond.reddithotspots.display;

import com.ahammond.reddithotspots.Core;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

    private final Core core;
    public ContentPanel(Core core) {
        this.core=core;

        initPanel();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(1530, 735));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(x, y, w, h, 50, 50);
    }
}
