package motorph.ui;

import motorph.model.User;
import motorph.model.Role;
import motorph.model.core.Employee;
import motorph.service.EmployeeService;

import javax.swing.JPanel;

/**
 * Main application window that serves as the primary container for the MotorPH
 * Payroll System.
 * Implements a single-page application architecture navigation menu
 * and a dynamic content area where different panels are displayed based on user
 * navigation.
 * The frame manages user session state and provides role-based initial view
 * configuration.
 */
public class MainFrame extends javax.swing.JFrame {
    private static JPanel mainBody;
    private User loggedInUser;
    private Employee loggedInEmployee;

    public MainFrame(User user, Employee employee) {
        initComponents();
        setLocationRelativeTo(null);

        this.loggedInUser = user;
        this.loggedInEmployee = employee;

        mainBody = body;
        menu1.setMainPanel(body);
        menu1.setRole(user.getRole());
        menu1.setFirstName(user.getFirstName());
        menu1.setEmployee(employee);

        if (user.getRole() == Role.EMPLOYEE) {
            showPanel(new MyProfilePanel(employee));
            menu1.selectMenuItemByName("My Profile");
        } else {
            showPanel(new DashboardPanel(user.getFullName()));
            menu1.selectMenuItemByName("Dashboard");
        }
    }

    public MainFrame() {
        initComponents();
        mainBody = body;
        menu1.setMainPanel(body);
        setLocationRelativeTo(null);
    }

    public void personalize(String name) {
        menu1.setFirstName(name);
        showPanel(new DashboardPanel(name));
        menu1.selectMenuItemByName("Dashboard");
    }

    public static void showPanel(JPanel panel) {
        mainBody.removeAll();
        mainBody.add(panel, java.awt.BorderLayout.CENTER);
        mainBody.revalidate();
        mainBody.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        menu1 = new motorph.ui.components.Menu();
        jLabel1 = new javax.swing.JLabel();
        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1254, 720));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 66, 102));
        jPanel1.setMaximumSize(null);

        jScrollPane2.setBackground(new java.awt.Color(0, 66, 102));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setViewportView(menu1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph/icons/Group 222 (2).png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 568,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)));

        body.setName(""); // NOI18N
        body.setRequestFocusEnabled(false);
        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(1067, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private motorph.ui.components.Menu menu1;
    // End of variables declaration//GEN-END:variables
}
