package motorph.ui.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import motorph.ui.util.CustomFont;

public class MenuItem extends JButton {
    private int index;
    private boolean selected = false;
    private boolean hover = false;

    public MenuItem(String name, int index) {
        super(name);
        this.index = index;

        // Handle spacers: hide if no name
        if (name == null || name.trim().isEmpty()) {
            setVisible(false);
            setEnabled(false);
            return;
        }

        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(new Color(230, 230, 230));
        setFont(CustomFont.getExtendedRegular(14f));

        setHorizontalAlignment(SwingConstants.LEFT);
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalAlignment(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
        setIconTextGap(10);
        setBorder(new EmptyBorder(8, 16, 8, 10));

        // Hover tracking
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        if (selected) {
            g2.setColor(new Color(3, 32, 48)); // Selected color
            g2.fillRect(0, 0, getWidth(), getHeight());
        } else if (hover) {
            g2.setColor(new Color(0, 90, 130)); // Hover color
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        g2.dispose();
        super.paintComponent(g); // Draw text and icon
    }
}
