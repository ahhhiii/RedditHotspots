package net.bruhitsalex.reddithotspots.display;

import net.bruhitsalex.reddithotspots.Core;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContentPanel extends JPanel {

    private final Core core;
    public ContentPanel(Core core) {
        this.core=core;

        initPanel();
        initSubPanels();
        initLayout();
    }

    public JPanel hourPanel, dayPanel, elements;
    public JButton togglePreview;
    public static final int HORIZONTAL_WIDTH = 1445, DAY_HEIGHT = 637;

    private void initSubPanels() {
        dayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

                g2d.setColor(ColorPalette.getTitleText());
                int panelHeight = dayPanel.getHeight() / 7;
                g2d.setFont(new Font(core.display.font, Font.BOLD, 11));
                for (int i = 0; i < 7; i++) {
                    Rectangle rect = new Rectangle(0, i * panelHeight, dayPanel.getWidth(), panelHeight);
                    drawCenter(g2d, intToTitle(i), rect);
                }
            }
        };
        dayPanel.setPreferredSize(new Dimension(85, getPreferredSize().height - 98));
        dayPanel.setBackground(ColorPalette.getAxisBackground());

        hourPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

                g2d.setColor(ColorPalette.getTitleText());
                int panelWidth = hourPanel.getWidth() / 24;
                g2d.setFont(new Font(core.display.font, Font.BOLD, 15));
                for (int i = 0; i < 24; i++) {
                    Rectangle rect = new Rectangle(i * panelWidth, 0, panelWidth, hourPanel.getHeight());
                    drawCenter(g2d, i + ":00", rect);
                }
            }
        };
        hourPanel.setPreferredSize(new Dimension(1445, 98));
        hourPanel.setBackground(ColorPalette.getAxisBackground());

        elements = new JPanel();
        elements.setBackground(null);
        elements.setPreferredSize(new Dimension(HORIZONTAL_WIDTH, DAY_HEIGHT));

        togglePreview = new JButton();
        togglePreview.setPreferredSize(new Dimension(85, 98));
        togglePreview.setBackground(ColorPalette.getAxisBackground());
        togglePreview.setText("<html><center>Preview<br>Post</center></html>");
        togglePreview.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        togglePreview.setBorder(null);
        togglePreview.setFocusable(false);
        togglePreview.setForeground(ColorPalette.getSwitchThemeText());
        togglePreview.addActionListener(e -> {
            for (Component comp : elements.getComponents()) {
                if (comp instanceof ElementPanel) {
                    ((ElementPanel) comp).showPreview();
                }
            }
        });
    }

    private String intToTitle(int i) {
        String[] days = new String[] {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        return days[i];
    }

    private void drawCenter(Graphics2D g, String msg, Rectangle rect) {
        FontMetrics metrics = g.getFontMetrics();
        int x = rect.x + (rect.width - metrics.stringWidth(msg)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(msg, x, y);
    }

    private void initPanel() {
        setPreferredSize(new Dimension(1530, 735));
    }

    private void initLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(dayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(togglePreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(hourPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(elements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        ));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(hourPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(togglePreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(dayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(elements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        ));
    }

    public void setElementsAndShow(ArrayList<ArrayList<ElementPanel>> table) {
        System.out.println("Printing table...");
        elements.removeAll();

        FlowLayout flow = new FlowLayout();
        flow.layoutContainer(elements);
        flow.setHgap(0);
        flow.setVgap(0);
        elements.setLayout(flow);

        for (int i = 0; i < 7; i++) {
            for (int n = 0; n < 24; n++) {
                elements.add(table.get(i).get(n));
            }
        }

        elements.revalidate();
        elements.repaint();
        core.display.search.setEnabled(true);
        core.display.search.setText("SEARCH");
    }

}
