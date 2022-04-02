package net.bruhitsalex.reddithotspots.display.elementinfo;

import net.bruhitsalex.reddithotspots.display.ColorPalette;
import net.bruhitsalex.reddithotspots.display.Display;
import net.bruhitsalex.reddithotspots.redditapi.RedditPost;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

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

        txtPanel = new JPanel();
        txtPanel.setPreferredSize(new Dimension(200, 280));
        txtPanel.setForeground(ColorPalette.getSwitchThemeText());
        txtPanel.setBackground(ColorPalette.getMainBackground());

        JTextPane title = new JTextPane();
        title.setPreferredSize(txtPanel.getPreferredSize());
        title.setBackground(ColorPalette.getMainBackground());
        title.setForeground(ColorPalette.getSwitchThemeText());
        title.setText(post.title);
        title.setStyledDocument(getStyleDocument(title));
        title.setEditable(false);
        title.setFont(new Font(Display.font, Font.BOLD, 15));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        JTextPane createdAndScore = new JTextPane();
        createdAndScore.setPreferredSize(txtPanel.getPreferredSize());
        createdAndScore.setBackground(ColorPalette.getMainBackground());
        createdAndScore.setForeground(ColorPalette.getSwitchThemeText());
        createdAndScore.setText("Posted " + formatter.format(post.creation) + "\nwith a score of " +
                NumberFormat.getIntegerInstance().format(post.score));
        createdAndScore.setEditable(false);
        createdAndScore.setFont(new Font(Display.font, Font.PLAIN, 15));

        JTextPane postLink = new JTextPane();
        postLink.setPreferredSize(txtPanel.getPreferredSize());
        postLink.setBackground(ColorPalette.getMainBackground());
        postLink.setForeground(Color.blue);
        postLink.setText("Post: " + post.postlink);
        postLink.setEditable(false);
        postLink.setFont(new Font(Display.font, Font.PLAIN, 15));
        postLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        postLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(post.postlink));
                } catch (IOException | URISyntaxException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        JTextPane picLink = new JTextPane();
        picLink.setPreferredSize(txtPanel.getPreferredSize());
        picLink.setBackground(ColorPalette.getMainBackground());
        picLink.setForeground(Color.blue);
        picLink.setText("Image link: " + post.imageURL);
        picLink.setEditable(false);
        picLink.setFont(new Font(Display.font, Font.PLAIN, 15));
        picLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        picLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(post.imageURL));
                } catch (IOException | URISyntaxException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        txtPanel.add(title);
        txtPanel.add(createdAndScore);
        txtPanel.add(postLink);
        txtPanel.add(picLink);

        initTextPanelLayout();
    }

    private StyledDocument getStyleDocument(JTextPane pane) {
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        return doc;
    }

    private void initTextPanelLayout() {
        txtPanel.setLayout(new BoxLayout(txtPanel, BoxLayout.Y_AXIS));
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
