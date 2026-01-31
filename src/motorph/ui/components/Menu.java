package motorph.ui.components;

import motorph.ui.PayrunsPanel;
import motorph.ui.HomePanel;
import motorph.ui.MenuHandler;
import motorph.ui.Attendance;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import motorph.ui.Dashboard;
import motorph.ui.EmployeePanel;
import motorph.ui.PayrunsPanel;
import net.miginfocom.swing.MigLayout;

/**
 * Menu component for the MotorPH Dashboard.
 * Dynamically generates main and sub-menu items and handles panel switching
 * within the UI.
 */
public class Menu extends JComponent {

    private MigLayout layout;
    private JPanel mainPanel;
    private MenuItem selectedItem = null;
    private String firstName = "User";

    /**
     * Allows the Dashboard to inject the main display panel (body) for content
     * swapping.
     * 
     * @param panel the body panel of the Dashboard where dynamic content is
     *              rendered
     */
    public void setMainPanel(JPanel panel) {
        this.mainPanel = panel;
    }

    // Menu structure: {Main Menu, SubItem1, SubItem2...}
    private MenuHandler[] menuItems = new MenuHandler[] {
            new MenuHandler("0White", "Dashboard", MenuHandler.menuType.MENU),
            new MenuHandler("2White", "Employee", MenuHandler.menuType.MENU),
            new MenuHandler("3White", "Attendance", MenuHandler.menuType.MENU),
            new MenuHandler("3", "Payroll", MenuHandler.menuType.MENU),

            // Add 8 empty spacers
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),
            new MenuHandler("", "", MenuHandler.menuType.MENU),

            new MenuHandler("divider", "", MenuHandler.menuType.MENU),

            // Logout
            new MenuHandler("logout", "Log Out", MenuHandler.menuType.MENU)
    };

    /**
     * Constructs the Menu and initializes layout and items.
     */
    public Menu() {
        init();
    }

    private void init() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill", "top");
        setLayout(layout);
        setOpaque(true);

        for (int i = 0; i < menuItems.length; i++) {
            MenuHandler handler = menuItems[i];
            addMenu(handler, i);
        }
    }

    private void addMenu(MenuHandler handler, int index) {
        // Handle empty spacer
        if (handler.getName().isEmpty()) {
            add(Box.createVerticalStrut(15));
            return;
        }

        if ("divider".equals(handler.getName())) {
            JSeparator separator = new JSeparator();
            separator.setForeground(new Color(200, 200, 200));
            add(separator, "gapy 10 10");
            return;
        }

        MenuItem item = new MenuItem(handler.getName(), index);
        item.setIcon(handler.toIcon());

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel != null) {
                    if ("Log Out".equals(handler.getName())) {
                        int confirm = JOptionPane.showConfirmDialog(
                                SwingUtilities.getWindowAncestor(Menu.this),
                                "Are you sure you want to log out?",
                                "Logout Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            new motorph.ui.LoginScreen().setVisible(true);
                            SwingUtilities.getWindowAncestor(Menu.this).dispose();
                        }
                        return;
                    }

                    mainPanel.removeAll();

                    switch (handler.getName()) {
                        case "Dashboard":
                            mainPanel.add(new HomePanel(firstName));
                            break;
                        case "Employee":
                            mainPanel.add(new EmployeePanel("Employee List"));
                            break;
                        case "Attendance":
                            mainPanel.add(new Attendance());
                            break;
                        case "Payroll":
                            mainPanel.add(new PayrunsPanel());
                            break;
                    }

                    mainPanel.revalidate();
                    mainPanel.repaint();
                }

                if (selectedItem != null) {
                    selectedItem.setSelected(false);
                }
                item.setSelected(true);
                selectedItem = item;
            }
        });

        add(item);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setColor(new Color(0, 66, 102));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(grphcs);
    }

    public void selectMenuItemByName(String menuName) {
        for (Component comp : getComponents()) {
            if (comp instanceof MenuItem) {
                MenuItem item = (MenuItem) comp;
                if (item.getText().equals(menuName)) {
                    if (selectedItem != null) {
                        selectedItem.setSelected(false);
                    }
                    item.setSelected(true);
                    selectedItem = item;
                    break;
                }
            }
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
