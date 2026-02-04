package motorph.ui.components;

import motorph.model.Role;
import motorph.ui.Attendance;
import motorph.ui.EmployeePanel;
import motorph.ui.DashboardPanel;
import motorph.ui.MenuHandler;
import motorph.ui.PayrunsPanel;
import motorph.ui.MyProfilePanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.beans.Beans;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import motorph.model.core.Employee;

import net.miginfocom.swing.MigLayout;


public class Menu extends JComponent {

    private MigLayout layout;
    private JPanel mainPanel;

    private MenuItem selectedItem;
    private String firstName = "User";
    private Role role = Role.EMPLOYEE;
    private Employee loggedInEmployee;

    // Menu structure: {Main Menu}
    private final MenuHandler[] menuItems = new MenuHandler[] {

            // Employee self-service (EMPLOYEE only)
            new MenuHandler("2White", "My Profile", MenuHandler.menuType.MENU),
            new MenuHandler("3White", "My Attendance", MenuHandler.menuType.MENU),
            new MenuHandler("3White", "Leave", MenuHandler.menuType.MENU),
            new MenuHandler("3White", "My Payslip", MenuHandler.menuType.MENU),

            // Non-employee dashboard
            new MenuHandler("0White", "Dashboard", MenuHandler.menuType.MENU),

            // Admin / HR
            new MenuHandler("2White", "Employee", MenuHandler.menuType.MENU),

            // Admin / HR / Finance
            new MenuHandler("3White", "Attendance", MenuHandler.menuType.MENU),

            // Admin / Finance
            new MenuHandler("3", "Payroll", MenuHandler.menuType.MENU),
            // Spacers
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

            new MenuHandler("logout", "Log Out", MenuHandler.menuType.MENU)
    };
    
        public Menu() { 
        if (!Beans.isDesignTime()) {
            init();
        } else {
                setOpaque(true);
                }
        }


    public void setMainPanel(JPanel panel) {
        this.mainPanel = panel;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setRole(Role role) {
        this.role = (role == null) ? Role.EMPLOYEE : role;
        rebuildMenu();
    }

    public void setEmployee(Employee employee) {
        this.loggedInEmployee = employee;
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

    private void init() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill", "top");
        setLayout(layout);
        setOpaque(true);
        rebuildMenu();
    }

    private void rebuildMenu() {
        removeAll();
        selectedItem = null;

        for (int i = 0; i < menuItems.length; i++) {
            MenuHandler handler = menuItems[i];

            if (!shouldShow(handler.getName())) {
                continue;
            }

            addMenu(handler, i);
        }

        revalidate();
        repaint();
    }

    private boolean shouldShow(String name) {
        // Always keep visual layout items
        if (name == null || name.isEmpty() || "divider".equals(name) || "Log Out".equals(name)) {
            return true;
        }

        if ("My Profile".equals(name) || "My Attendance".equals(name)
                || "Leave".equals(name) || "My Payslip".equals(name)) {
            return role == Role.EMPLOYEE;
        }

        if ("Dashboard".equals(name)) {
            return role != Role.EMPLOYEE;
        }

        if ("Employee".equals(name)) {
            return role == Role.ADMIN || role == Role.HR;
        }

        if ("Attendance".equals(name)) {
            return role == Role.ADMIN || role == Role.HR || role == Role.FINANCE;
        }

        if ("Payroll".equals(name)) {
            return role == Role.ADMIN || role == Role.FINANCE;
        }

        // Default: hide unknown items for safety
        return false;
    }

    private void addMenu(MenuHandler handler, int index) {
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
                handleClick(handler.getName(), item);
            }
        });

        add(item);
    }

    private void handleClick(String name, MenuItem item) {
        if ("Log Out".equals(name)) {
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

        if (mainPanel != null) {
            mainPanel.removeAll();

            switch (name) {
                case "My Profile":
                    mainPanel.add(new MyProfilePanel(loggedInEmployee));
                    break;
                case "Dashboard":
                    mainPanel.add(new DashboardPanel(firstName));
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

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setColor(new Color(0, 66, 102));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(grphcs);
    }
}
