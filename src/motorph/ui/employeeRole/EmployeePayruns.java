package motorph.ui.employeeRole;

import motorph.ui.components.CustomFont;
import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.repository.DataHandler;
import motorph.service.PayrollService;
import motorph.model.User;
import motorph.ui.components.MyRender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class EmployeePayruns extends javax.swing.JPanel {
    private User currentUser;
    private int currentPage = 1;
    private final int rowsPerPage = 9;
    private int totalPages = 1;
    private String currentPayDate;
    private List<LocalDate> payDates;


    public EmployeePayruns(User loggedInUser) {
        this.currentUser = loggedInUser;
        initComponents();
        applyCustomFontAndSizes();
        setupTable();
        loadMyPayrollList();
    }

    private void applyCustomFontAndSizes() {
        title.setFont(CustomFont.getExtendedSemiBold(14f));
        empID.setFont(CustomFont.getExtendedSemiBold(10f));
        empName.setFont(CustomFont.getExtendedSemiBold(10f));
        displayFullName.setFont(CustomFont.getExtendedRegular(12f));
        displayEmpID.setFont(CustomFont.getExtendedRegular(12f));
    }

    private void setupTable() {
        DefaultTableModel model = (DefaultTableModel) myPayrunsHistory.getModel();
        model.setRowCount(0);
        myPayrunsHistory.setRowHeight(25);

        // Align center
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply to columns
        myPayrunsHistory.getColumnModel().getColumn(0).setCellRenderer(center);
        myPayrunsHistory.getColumnModel().getColumn(1).setCellRenderer(center);
        myPayrunsHistory.getColumnModel().getColumn(2).setCellRenderer(new MyRender()); // keep custom renderer for button
        
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        myPayrunsHistory.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = myPayrunsHistory.rowAtPoint(evt.getPoint());
            int col = myPayrunsHistory.columnAtPoint(evt.getPoint());

            if (col == 2 && row >= 0) {
                String payDate = myPayrunsHistory.getValueAt(row, 0).toString();
                showPayslipDialog(payDate);
            }
        }
    });
}


    private void loadMyPayrollList() {
        String normalizedFullName = normalize(currentUser.getFullName());

        // Match the user in the time logs to get their employee number
        List<EmployeeTimeLogs> allLogs = DataHandler.readEmployeeTimeLogs();
        List<EmployeeTimeLogs> userLogs = allLogs.stream()
            .filter(log -> normalize(log.getFirstName() + " " + log.getLastName()).equals(normalizedFullName))
            .toList();

        if (userLogs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No payroll records found.");
            return;
        }

        String empNumber = userLogs.get(0).getEmployeeNumber();

        EmployeeDetails emp = DataHandler.readEmployeeDetails().stream()
            .filter(e -> e.getEmployeeNumber().equals(empNumber))
            .findFirst()
            .orElse(null);

        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Employee details not found.");
            return;
        }

        // ✅ Set display
        displayFullName.setText(emp.getFullName());
        displayEmpID.setText("EMP ID: " + emp.getEmployeeNumber());

        // Group time logs into cutoff pay periods
        payDates = userLogs.stream()
            .map(log -> LocalDate.parse(log.getDate()))
            .map(date -> (date.getDayOfMonth() <= 15)
                ? date.withDayOfMonth(15)
                : date.withDayOfMonth(date.lengthOfMonth()))
            .distinct()
            .sorted()
            .toList();

        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter monthYearFormat = DateTimeFormatter.ofPattern("MM-yyyy");

        DefaultTableModel model = (DefaultTableModel) myPayrunsHistory.getModel();
        model.setRowCount(0);


        totalPages = (int) Math.ceil((double) payDates.size() / rowsPerPage);


        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, payDates.size());

        List<LocalDate> pageDates = payDates.subList(start, end);

        for (LocalDate cutoffDate : pageDates) {
            String displayDate = cutoffDate.format(displayFormat);
            String monthYear = cutoffDate.format(monthYearFormat);
            int payPeriod = (cutoffDate.getDayOfMonth() == 15) ? 1 : 2;

            List<EmployeeTimeLogs> periodLogs = userLogs.stream()
                .filter(log -> isInPayPeriod(LocalDate.parse(log.getDate()), cutoffDate))
                .toList();

            double netPay = PayrollService.calculateNetPay(emp, periodLogs, monthYear, payPeriod);
            String formattedAmount = String.format("₱%,.2f", netPay);

            model.addRow(new Object[]{ displayDate, formattedAmount, "View Payslip" });
        }

        lblPageInfo.setText("Page " + currentPage + " of " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);

    }
    
    private void showPayslipDialog(String payDate) {
        EmployeePayslip payslipPanel = new EmployeePayslip();
        payslipPanel.setPayDate(payDate);
        payslipPanel.setEmployeeId(displayEmpID.getText().replace("EMP ID: ", ""));
        payslipPanel.setPayPeriod(determinePeriodRange(payDate));

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Payslip", true);
        dialog.getContentPane().add(payslipPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }




    private String normalize(String input) {
        return input == null ? "" : input.trim().replaceAll("\\s+", " ").toLowerCase();
    }


    private boolean isInPayPeriod(LocalDate logDate, LocalDate cutoffDate) {
        LocalDate startDate = (cutoffDate.getDayOfMonth() == 15)
            ? cutoffDate.withDayOfMonth(1)
            : cutoffDate.withDayOfMonth(16);
        return !logDate.isBefore(startDate) && !logDate.isAfter(cutoffDate);
    }
    
    private String determinePeriodRange(String payDate) {
        try {
            LocalDate cutoff = LocalDate.parse(payDate, DateTimeFormatter.ofPattern("MMMM d, yyyy"));
            LocalDate start = (cutoff.getDayOfMonth() == 15)
                ? cutoff.withDayOfMonth(1)
                : cutoff.withDayOfMonth(16);
            return start.format(DateTimeFormatter.ofPattern("MMM d, yyyy")) + " - " +
                   cutoff.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
        } catch (Exception e) {
            return "";
        }
    }

    
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPageInfo = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        payrollRepBox = new javax.swing.JPanel();
        empID = new javax.swing.JLabel();
        displayFullName = new javax.swing.JLabel();
        empName = new javax.swing.JLabel();
        displayEmpID = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        myPayrunsHistory = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(null);
        setPreferredSize(new java.awt.Dimension(1000, 485));

        lblPageInfo.setText("Page 1 of 1");

        title.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        title.setText("My Payroll Summary Report");

        payrollRepBox.setBackground(new java.awt.Color(0, 66, 102));
        payrollRepBox.setForeground(new java.awt.Color(255, 255, 255));
        payrollRepBox.setMaximumSize(new java.awt.Dimension(942, 54));
        payrollRepBox.setMinimumSize(new java.awt.Dimension(942, 54));
        payrollRepBox.setName(""); // NOI18N

        empID.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        empID.setForeground(new java.awt.Color(255, 255, 255));
        empID.setText("Employee ID");

        displayFullName.setForeground(new java.awt.Color(255, 255, 255));
        displayFullName.setText("FirstName LastName");

        empName.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        empName.setForeground(new java.awt.Color(255, 255, 255));
        empName.setText("Employee Name");

        displayEmpID.setForeground(new java.awt.Color(255, 255, 255));
        displayEmpID.setText("10000");

        javax.swing.GroupLayout payrollRepBoxLayout = new javax.swing.GroupLayout(payrollRepBox);
        payrollRepBox.setLayout(payrollRepBoxLayout);
        payrollRepBoxLayout.setHorizontalGroup(
            payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payrollRepBoxLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(empName)
                    .addComponent(displayFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 478, Short.MAX_VALUE)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(empID)
                    .addComponent(displayEmpID))
                .addGap(17, 17, 17))
        );
        payrollRepBoxLayout.setVerticalGroup(
            payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payrollRepBoxLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empName)
                    .addComponent(empID))
                .addGap(3, 3, 3)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayFullName)
                    .addComponent(displayEmpID))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        myPayrunsHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Payment Date", "Payment Amount", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        myPayrunsHistory.setAutoscrolls(false);
        jScrollPane1.setViewportView(myPayrunsHistory);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPageInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(title, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(payrollRepBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payrollRepBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(lblPageInfo)
                    .addComponent(btnPrev))
                .addContainerGap(31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadMyPayrollList();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (currentPage < totalPages) {
            currentPage++;
            loadMyPayrollList();
        }
    }//GEN-LAST:event_btnNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JLabel displayEmpID;
    private javax.swing.JLabel displayFullName;
    private javax.swing.JLabel empID;
    private javax.swing.JLabel empName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JTable myPayrunsHistory;
    private javax.swing.JPanel payrollRepBox;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
