package motorph.ui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MyRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (value != null) {
            label.setText("<html><u><font color='blue'>" + value.toString() + "</font></u></html>");
        } else {
            label.setText("");
        }

        label.setHorizontalAlignment(SwingConstants.CENTER);

        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }

        return label;
    }

    public static JButton createDashboardButton(String text, Icon icon, boolean isSelected) {
        JButton button = new JButton(text, icon);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.DARK_GRAY);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(isSelected);

        if (isSelected) {
            button.setBackground(new Color(220, 220, 220)); // Light gray background
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding for oval look
        } else {
            button.setBackground(new Color(245, 245, 245));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }

        return button;
    }

    public static Font loadCustomFont(float size) {
        try {
            InputStream is = MyRender.class.getResourceAsStream("/fonts/NeuePlakExtendedRegular.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
            return new Font("SansSerif", Font.PLAIN, (int) size); // fallback
        }
    }

    public static void applyButtonStyle(JButton button, boolean isActive) {
        if (button == null)
            return;

        if (isActive) {
            button.setBackground(new Color(0, 102, 204)); // active blue
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(Color.LIGHT_GRAY);
            button.setForeground(Color.BLACK);
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    }

    public static JMenuItem createMenuItemWithLeftIcon(String text, Icon icon, int matchWidth) {
        JMenuItem item = new JMenuItem(text, icon);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.setHorizontalTextPosition(SwingConstants.LEFT); // Text left of icon
        item.setIconTextGap(10); // Space between text and icon
        item.setPreferredSize(new Dimension(matchWidth, item.getPreferredSize().height)); // Optional fixed width
        return item;
    }

}
