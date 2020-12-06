package com.ahammond.reddithotspots.display.elementinfo;

import com.ahammond.reddithotspots.display.ColorPalette;
import com.ahammond.reddithotspots.display.Display;
import com.ahammond.reddithotspots.redditapi.RedditPost;

import javax.swing.*;
import java.awt.*;

public class PostPanel extends JPanel {

    private final RedditPost post;
    public PostPanel(RedditPost post) {
        this.post=post;

        initPanel();
        initLayout();
    }

    private JPanel txtPanel;
    public JLabel imgPanel;

    private void initPanel() {
        setPreferredSize(new Dimension(880, 457));
        setBackground(ColorPalette.getMainBackground());
        setBorder(BorderFactory.createEmptyBorder());

        imgPanel = new JLabel("Loading image...");
        imgPanel.setPreferredSize(new Dimension(613, 419));

        txtPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintText((Graphics2D) g);
            }
        };
        txtPanel.setPreferredSize(new Dimension(224, 280));
        txtPanel.setForeground(ColorPalette.getSwitchThemeText());
    }

    private void paintText(Graphics2D g) {
        g.setColor(ColorPalette.getSwitchThemeText());
        findFont(post.title, new Font(Display.font, Font.BOLD, 100), g, txtPanel.getPreferredSize().width);
        g.drawString(post.title, 0, 0);
    }

    private void findFont(String title, Font font, Graphics2D g, int width) {
        while (g.getFontMetrics().stringWidth(title) > width) {
            g.setFont(new Font(font.getName(), font.getStyle(), g.getFont().getSize() - 1));
        }
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(imgPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addGap(89, 89, 89)
                                        .addComponent(txtPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
        );

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(imgPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(txtPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );
    }

}
