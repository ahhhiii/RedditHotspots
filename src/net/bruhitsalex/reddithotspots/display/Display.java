package net.bruhitsalex.reddithotspots.display;

import net.bruhitsalex.reddithotspots.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Display extends JFrame {

    private final Core core;
    public Display(Core core) {
        this.core=core;
        initFrame();
        initPanels();
        initLayout();
        setColourTheme();
        pack();

        setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public ContentPanel contentPanel;
    public JTextField input;
    public JButton search;
    public JButton theme;
    public static String font = "Segoe UI";

    private void initFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ignore) {}

        setPreferredSize(new Dimension(1600, 900));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("RedditHotspots");

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            font = "Helvetica Neue";
        } else {
            font = "Segoe UI";
        }
    }

    private void initPanels() {
        contentPanel = new ContentPanel(core);

        input = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g2d);
            }
        };
        input.setText("/r/Astrophotography");
        input.setBorder(BorderFactory.createEmptyBorder());
        input.setPreferredSize(new Dimension(255, 53));
        input.setFont(new Font(font, Font.PLAIN, 20));
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                search.setEnabled(input.getText().length() > 3 && input.getText().substring(0, 3).equalsIgnoreCase("/r/"));
            }
        });
        input.setHorizontalAlignment(SwingConstants.CENTER);

        search = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g2d);
            }
        };
        search.setText("SEARCH");
        search.setBorder(null);
        search.setFont(new Font(font, Font.BOLD, 37));
        search.setPreferredSize(new Dimension(255, 53));
        search.setFocusable(false);
        search.addActionListener(e -> new Thread() {
            @Override
            public void run() {
                super.run();
                core.query(input.getText().substring(3));
            }
        }.start());
        search.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        theme = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g2d);
            }
        };
        theme.setText("Switch Theme");
        theme.setFont(new Font(font, Font.PLAIN, 12));
        theme.setBorder(null);
        theme.setPreferredSize(new Dimension(85, 31));
        theme.setFocusable(false);
        theme.addActionListener(e -> {
            ColorPalette.dark = !ColorPalette.dark;
            setColourTheme();
            revalidate();
        });
        theme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setColourTheme() {
        input.setBackground(ColorPalette.getSubredditInputBackground());
        input.setForeground(ColorPalette.getTitleText());
        search.setBackground(ColorPalette.getSearchBackground());
        search.setForeground(ColorPalette.getTitleText());
        theme.setBackground(ColorPalette.getContentPanelBackground());
        theme.setForeground(ColorPalette.getSwitchThemeText());
        contentPanel.setBackground(ColorPalette.getContentPanelBackground());
        contentPanel.hourPanel.setBackground(ColorPalette.getAxisBackground());
        contentPanel.dayPanel.setBackground(ColorPalette.getAxisBackground());
        for (Component comp : contentPanel.elements.getComponents()) {
            comp.setBackground(ColorPalette.getContentPanelBackground());
        }
        contentPanel.togglePreview.setBackground(ColorPalette.getAxisBackground());
        contentPanel.togglePreview.setForeground(ColorPalette.getSwitchThemeText());
        getContentPane().setBackground(ColorPalette.getMainBackground());
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(theme, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(401, 401, 401)
                                        .addComponent(input, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(49, 49, 49)
                                        .addComponent(search, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                )
                                .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addContainerGap(35, Short.MAX_VALUE)
                )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(theme, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(input, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(search, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGap(26, 26, 26)
                        .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                )
        );
    }

    public void alert(String str) {
        int fontSize = 50;
        Font fontS = new Font(font, Font.BOLD, fontSize);
        while (fontSize > 2 && search.getFontMetrics(fontS).stringWidth(str) > search.getWidth() - 30) {
            fontSize--;
            fontS = new Font(font, Font.BOLD, fontSize);
        }

        search.setFont(fontS);
        search.setText(str);
    }
}
