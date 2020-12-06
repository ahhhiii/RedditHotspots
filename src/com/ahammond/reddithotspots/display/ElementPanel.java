package com.ahammond.reddithotspots.display;

import com.ahammond.reddithotspots.display.elementinfo.ElementDisplay;
import com.ahammond.reddithotspots.redditapi.RedditPost;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ElementPanel extends JPanel {

    public ArrayList<RedditPost> posts;
    public int day, hour;

    private ElementPanel INSTANCE;
    public ElementPanel() {
        INSTANCE = this;
        this.posts = new ArrayList<>();
        initPanel();
    }

    private void initPanel() {
        int width = ContentPanel.HORIZONTAL_WIDTH / 24;
        int height = ContentPanel.DAY_HEIGHT / 7;
        setPreferredSize(new Dimension(width, height));
        setBackground(ColorPalette.getContentPanelBackground());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (posts.size() == 0) {
                    return;
                }

                openPanels = new ArrayList<>();
                openCap = posts.size();
                openCount = 0;
                int i = (posts.size() / 2) * -20;
                for (RedditPost post : posts) {
                    ElementDisplay elementDisplay = new ElementDisplay(post, i, INSTANCE);
                    openPanels.add(elementDisplay);
                    i += 20;
                }
            }
        });
    }

    ArrayList<ElementDisplay> openPanels;
    int openCount = 0, openCap = 0;
    public void alertOpened() {
        openCount++;
        if (openCount >= openCap) {
            for (ElementDisplay display : openPanels) {
                display.loadImage();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (posts.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        if (preview) {
            paintCount(g2d);
        } else {
            paintImage(g2d);
        }
    }

    private boolean preview = true;
    public void showPreview() {
        preview = !preview;
        repaint();
    }

    private void paintCount(Graphics2D g) {
        double percentage = ((double) posts.size()) / ElementManager.HIGHEST_ELEMENT_COUNT;
        int opacity = (int) Math.round(percentage * 255.0);
        g.setColor(new Color(ColorPalette.getElementColor().getRed(), ColorPalette.getElementColor().getGreen(),
                ColorPalette.getElementColor().getBlue(), opacity));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void paintImage(Graphics2D g) {
        if (posts.size() == 0) {
            return;
        }

        RedditPost post = posts.get(0);

        if (post.thumbnailURL == null) {
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
            return;
        }

        try {
            BufferedImage image = ImageIO.read(new URL(post.thumbnailURL).openStream());

            //g.translate(this.getWidth() / 2, this.getHeight() / 2);
            //g.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
            double widthRatio = (double) getWidth() / image.getWidth();
            double heightRatio = (double) getHeight() / image.getHeight();
            double ratio = Math.max(widthRatio, heightRatio);

            g.drawImage(image, 0, 0, (int) (image.getWidth() * ratio), (int) (image.getHeight() * ratio), null);
        } catch (IOException ignored) {}
    }

}
