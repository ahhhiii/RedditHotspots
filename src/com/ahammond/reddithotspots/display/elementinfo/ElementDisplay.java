package com.ahammond.reddithotspots.display.elementinfo;

import com.ahammond.reddithotspots.display.ColorPalette;
import com.ahammond.reddithotspots.display.ElementPanel;
import com.ahammond.reddithotspots.redditapi.RedditPost;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ElementDisplay extends JFrame {

    private final RedditPost post;
    private PostPanel panel;
    public ElementDisplay(RedditPost post, int offset, ElementPanel core) {
        this.post=post;

        initFrame();
        initPosts();
        pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width/2-this.getSize().width/2) + offset, (dim.height/2-this.getSize().height/2) + offset);

        SwingUtilities.invokeLater(() -> setVisible(true));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                core.alertOpened();
            }
        });
    }

    public void loadImage() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new URL(post.imageURL));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        assert img != null;
        img = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 613, 419);
        panel.imgPanel.setIcon(new ImageIcon(img));
    }

    private void initFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}

        setPreferredSize(new Dimension(880, 482));
        setBackground(ColorPalette.getMainBackground());
        setResizable(false);
        setTitle(post.title);
    }

    private void initPosts() {
        panel = new PostPanel(post);
        add(panel);
    }

}
