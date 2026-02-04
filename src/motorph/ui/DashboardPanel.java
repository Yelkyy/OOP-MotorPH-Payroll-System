package motorph.ui;

import motorph.ui.components.CustomFont;

/**
 * DashboardPanel serves as the main landing page for non-EmployeeUser roles in
 * the MotorPH system.
 * Displays key dashboard statistics including active employee count, current
 * pay period, and today's leaves.
 */
public class DashboardPanel extends javax.swing.JPanel {

    public DashboardPanel(String firstName) {
        initComponents();
        applyCustomFont();
        updateTotalEmployees();
        welcomeHeader.setText("Welcome back, " + firstName + "!");
    }

    private void applyCustomFont() {
        header.setFont(CustomFont.getExtendedSemiBold(28f));
        welcomeHeader.setFont(CustomFont.getExtendedRegular(16f));
        totalLeaveText.setFont(CustomFont.getExtendedRegular(14f));
        activeEmpText.setFont(CustomFont.getExtendedRegular(14f));
        payPeriodText.setFont(CustomFont.getExtendedRegular(14f));
        payPeriodNum.setFont(CustomFont.getExtendedBold(18f));
        activeEmpNum.setFont(CustomFont.getExtendedBold(18f));
        todayLeaveNum.setFont(CustomFont.getExtendedBold(18f));
    }

    private void updateTotalEmployees() {
        int totalEmployees = motorph.repository.DataHandler.readEmployeeDetails().size();
        activeEmpNum.setText(String.valueOf(totalEmployees));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boarder1 = new javax.swing.JSeparator();
        header = new javax.swing.JLabel();
        welcomeHeader = new javax.swing.JLabel();
        totalEmpBox = new motorph.ui.components.RoundedPanel(40);
        activeEmpText = new javax.swing.JLabel();
        activeEmpNum = new javax.swing.JLabel();
        payPeriodBox = new motorph.ui.components.RoundedPanel(40);
        payPeriodText = new javax.swing.JLabel();
        payPeriodNum = new javax.swing.JLabel();
        todayLeaveBox = new motorph.ui.components.RoundedPanel(40);
        totalLeaveText = new javax.swing.JLabel();
        todayLeaveNum = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1054, 720));
        setMinimumSize(new java.awt.Dimension(1054, 720));

        boarder1.setBackground(new java.awt.Color(0, 66, 102));
        boarder1.setForeground(new java.awt.Color(0, 66, 102));

        header.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        header.setText("Dashboard Overview");

        welcomeHeader.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        welcomeHeader.setText("Welcome back, Admin!");

        totalEmpBox.setBackground(new java.awt.Color(150, 170, 252));
        totalEmpBox.setForeground(new java.awt.Color(255, 255, 255));
        totalEmpBox.setMaximumSize(new java.awt.Dimension(230, 130));
        totalEmpBox.setMinimumSize(new java.awt.Dimension(230, 130));

        activeEmpText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        activeEmpText.setText("Active Employee");

        activeEmpNum.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        activeEmpNum.setText("34");

        javax.swing.GroupLayout totalEmpBoxLayout = new javax.swing.GroupLayout(totalEmpBox);
        totalEmpBox.setLayout(totalEmpBoxLayout);
        totalEmpBoxLayout.setHorizontalGroup(
                totalEmpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(totalEmpBoxLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(
                                        totalEmpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(activeEmpNum)
                                                .addComponent(activeEmpText))
                                .addContainerGap(112, Short.MAX_VALUE)));
        totalEmpBoxLayout.setVerticalGroup(
                totalEmpBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(totalEmpBoxLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(activeEmpText, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(activeEmpNum)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        payPeriodBox.setBackground(new java.awt.Color(255, 153, 153));
        payPeriodBox.setForeground(new java.awt.Color(255, 255, 255));
        payPeriodBox.setMaximumSize(new java.awt.Dimension(230, 130));
        payPeriodBox.setMinimumSize(new java.awt.Dimension(230, 130));
        payPeriodBox.setPreferredSize(new java.awt.Dimension(230, 130));

        payPeriodText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        payPeriodText.setText("Payroll Period");

        payPeriodNum.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        payPeriodNum.setText("May 16 - May 31, 2025");

        javax.swing.GroupLayout payPeriodBoxLayout = new javax.swing.GroupLayout(payPeriodBox);
        payPeriodBox.setLayout(payPeriodBoxLayout);
        payPeriodBoxLayout.setHorizontalGroup(
                payPeriodBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(payPeriodBoxLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(payPeriodBoxLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(payPeriodNum, javax.swing.GroupLayout.DEFAULT_SIZE, 220,
                                                Short.MAX_VALUE)
                                        .addGroup(payPeriodBoxLayout.createSequentialGroup()
                                                .addComponent(payPeriodText)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap()));
        payPeriodBoxLayout.setVerticalGroup(
                payPeriodBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(payPeriodBoxLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(payPeriodText, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(payPeriodNum)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        todayLeaveBox.setBackground(new java.awt.Color(255, 153, 255));
        todayLeaveBox.setForeground(new java.awt.Color(255, 255, 255));
        todayLeaveBox.setMaximumSize(new java.awt.Dimension(230, 130));
        todayLeaveBox.setMinimumSize(new java.awt.Dimension(230, 130));
        todayLeaveBox.setPreferredSize(new java.awt.Dimension(230, 130));

        totalLeaveText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        totalLeaveText.setText("Today Leave");

        todayLeaveNum.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        todayLeaveNum.setText("1");

        javax.swing.GroupLayout todayLeaveBoxLayout = new javax.swing.GroupLayout(todayLeaveBox);
        todayLeaveBox.setLayout(todayLeaveBoxLayout);
        todayLeaveBoxLayout.setHorizontalGroup(
                todayLeaveBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(todayLeaveBoxLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(todayLeaveBoxLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(todayLeaveNum)
                                        .addComponent(totalLeaveText))
                                .addContainerGap(127, Short.MAX_VALUE)));
        todayLeaveBoxLayout.setVerticalGroup(
                todayLeaveBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(todayLeaveBoxLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(totalLeaveText, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(todayLeaveNum)
                                .addContainerGap(41, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boarder1)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(welcomeHeader)
                                        .addComponent(header))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(113, Short.MAX_VALUE)
                                .addComponent(totalEmpBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(todayLeaveBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(payPeriodBox, javax.swing.GroupLayout.PREFERRED_SIZE, 251,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(welcomeHeader)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(header)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boarder1, javax.swing.GroupLayout.PREFERRED_SIZE, 8,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(payPeriodBox, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(todayLeaveBox, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(totalEmpBox, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(460, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Dynamically sets the current pay period label based on today's date.
     * Pay periods are 1st–15th and 16th–end of the month.
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activeEmpNum;
    private javax.swing.JLabel activeEmpText;
    private javax.swing.JSeparator boarder1;
    private javax.swing.JLabel header;
    private javax.swing.JPanel payPeriodBox;
    private javax.swing.JLabel payPeriodNum;
    private javax.swing.JLabel payPeriodText;
    private javax.swing.JPanel todayLeaveBox;
    private javax.swing.JLabel todayLeaveNum;
    private javax.swing.JPanel totalEmpBox;
    private javax.swing.JLabel totalLeaveText;
    private javax.swing.JLabel welcomeHeader;
    // End of variables declaration//GEN-END:variables
}
