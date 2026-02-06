package motorph.ui.components;

import motorph.model.Role;
import motorph.model.users.HrUser;
import motorph.ui.AttendanceManagement;
import motorph.ui.EmployeePanel;
import motorph.ui.DashboardPanel;
import motorph.ui.LeaveManagement;
import motorph.ui.util.MenuHandler;
import motorph.ui.PayrunsPanel;
import motorph.ui.employeeRole.MyProfilePanel;
import motorph.ui.employeeRole.LeaveRequestPanel;
import motorph.service.MenuService;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.beans.Beans;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private MenuService menuService;

    private MenuItem selectedItem;
    private String firstName = "User";
    private Role role = Role.EMPLOYEE;
    private Employee loggedInEmployee;

    // Menu icon mapping (all available menu items)
    private final Map<String, String> menuIconMap = new HashMap<>();

    public Menu() {
        if (!Beans.isDesignTime()) {
            init();
        } else {
            setOpaque(true);
        }
    }

    private void initMenuIconMap() {
        menuIconMap.put("Dashboard", "0White");
        menuIconMap.put("My Profile", "2White");
        menuIconMap.put("Attendance", "3White");
        menuIconMap.put("Leave", "3White");
        menuIconMap.put("Payslip", "3White");
        menuIconMap.put("Employee Management", "2White");
        menuIconMap.put("Attendance Overview", "3White");
        menuIconMap.put("Leave Management", "2White");
        menuIconMap.put("Payroll Management", "3");
        menuIconMap.put("Generate Employee Payroll", "3");
        menuIconMap.put("Generate Payslip", "3");
        menuIconMap.put("View Payroll Reports", "3");
        menuIconMap.put("Print Payslip", "3");
        menuIconMap.put("Manage User Accounts", "2White");
        menuIconMap.put("Update Role/Access Rights", "2White");
        menuIconMap.put("System Reports", "2White");
    }

    public void setMainPanel(JPanel panel) {
        this.mainPanel = panel;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setRole(Role role) {
        this.role = (role == null) ? Role.EMPLOYEE : role;
        if (menuService == null) {
            menuService = new MenuService();
        }
        rebuildMenu();
    }

    public void setEmployee(Employee employee) {
        this.loggedInEmployee = employee;
        if (menuService == null) {
            menuService = new MenuService();
        }
        menuService.setLoggedInEmployee(employee);
        rebuildMenu();
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
        menuService = new MenuService();
        initMenuIconMap();
        rebuildMenu();
    }

    private void rebuildMenu() {
        removeAll();
        selectedItem = null;

        // Build menu dynamically from employee's menu items
        if (loggedInEmployee != null) {
            List<String> userMenuItems = loggedInEmployee.getMenuItems();

            if (userMenuItems != null) {
                for (String menuName : userMenuItems) {
                    String iconName = menuIconMap.getOrDefault(menuName, "2White");
                    MenuHandler handler = new MenuHandler(iconName, menuName, MenuHandler.menuType.MENU);
                    addMenu(handler, 0);
                }
            }
        }

        // spacers of logout button
        for (int i = 0; i < 15; i++) {
            add(Box.createVerticalStrut(15));
        }

        // divider and logout
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200));
        add(separator, "gapy 10 10");

        MenuHandler logoutHandler = new MenuHandler("logout", "Log Out", MenuHandler.menuType.MENU);
        MenuItem logoutItem = new MenuItem("Log Out", 0);
        logoutItem.setIcon(logoutHandler.toIcon());
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleClick("Log Out", logoutItem);
            }
        });
        add(logoutItem);

        revalidate();
        repaint();
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
            JPanel newPanel = null;

            switch (name) {
                case "My Profile":
                    newPanel = new MyProfilePanel(loggedInEmployee);
                    break;
                case "Leave":
                    newPanel = new LeaveRequestPanel(loggedInEmployee);
                    break;
                case "Dashboard":
                    newPanel = new DashboardPanel(firstName);
                    break;
                case "Employee Management":
                    newPanel = new EmployeePanel("Employee List");
                    break;
                case "Attendance Overview":
                    newPanel = new AttendanceManagement();
                    break;
                case "Leave Management":
                    newPanel = new LeaveManagement((HrUser) loggedInEmployee);
                    break;
                case "Payroll Management":
                    newPanel = new PayrunsPanel();
                    break;
            }

            if (newPanel != null) {
                mainPanel.removeAll();
                mainPanel.add(newPanel, java.awt.BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
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
