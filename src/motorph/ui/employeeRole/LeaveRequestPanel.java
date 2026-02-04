package motorph.ui.employeeRole;

import motorph.model.core.Employee;
import motorph.model.LeaveRequest;
import motorph.service.LeaveService;
import motorph.ui.components.CustomFont;
import java.util.Calendar;
import java.util.Date;

public class LeaveRequestPanel extends javax.swing.JPanel {

        private Employee employee;

        public LeaveRequestPanel(Employee employee) {
                this.employee = employee;
                initComponents();
                setMinimumDate();
                centerTableText();
                showLeaveRequests();
                
                leaveListTable.getTableHeader().setReorderingAllowed(false);

        }

        private void setMinimumDate() {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);

                Date today = cal.getTime();

                cal.add(Calendar.YEAR, 1);
                Date oneYearFromNow = cal.getTime();

                fromDateChooser.setMinSelectableDate(today);
                toDateChooser.setMinSelectableDate(today);

                fromDateChooser.setMaxSelectableDate(oneYearFromNow);
                toDateChooser.setMaxSelectableDate(oneYearFromNow);
        }
        
        private void centerTableText() {
            javax.swing.table.DefaultTableCellRenderer center =
                new javax.swing.table.DefaultTableCellRenderer();

            center.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            for (int i = 0; i < leaveListTable.getColumnCount(); i++) {
                leaveListTable.getColumnModel()
                              .getColumn(i)
                              .setCellRenderer(center);
            }
        }
        
        private void showLeaveRequests() {
            javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) leaveListTable.getModel();
            model.setRowCount(0);

            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM-dd-yyyy");

            motorph.service.LeaveService service = new motorph.service.LeaveService();
            java.util.List<motorph.model.LeaveRequest> list =
                service.findLeaveRequestsByEmployee(employee.getEmployeeNumber());

            for (motorph.model.LeaveRequest req : list) {
                Date from = req.getFrom();
                Date to = req.getTo();

                long days = 0;
                if (from != null && to != null && !to.before(from)) {
                    long diffMs = to.getTime() - from.getTime();
                    days = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(diffMs) + 1;
                }

                model.addRow(new Object[] {
                    req.getRequestDate() == null ? "" : df.format(req.getRequestDate()),
                    from == null ? "" : df.format(from),
                    to == null ? "" : df.format(to),
                    days,
                    req.getStatus()
                });
            }
        }

        

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooseLeaveType = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        leaveListTable = new javax.swing.JTable();
        lblSubTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        welcomeMsg = new javax.swing.JLabel();
        boarder1 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        vacationLeaveBttn = new javax.swing.JRadioButton();
        sickLeaveBttn = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        reason = new javax.swing.JTextArea();
        applyLeaveButton = new javax.swing.JButton();
        cancelLeaveButton = new javax.swing.JButton();
        toDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        fromDateChooser = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1054, 720));
        setMinimumSize(new java.awt.Dimension(1054, 720));

        leaveListTable.setAutoCreateRowSorter(true);
        leaveListTable.setFont(CustomFont.getExtendedRegular(12f));
        leaveListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Reqest Date", "From", "To", "Days", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        leaveListTable.setEnabled(false);
        leaveListTable.setFocusable(false);
        leaveListTable.setRowSelectionAllowed(false);
        leaveListTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(leaveListTable);

        lblSubTitle.setFont(CustomFont.getExtendedSemiBold(14f));
        lblSubTitle.setText("My Leave Requests");
        lblSubTitle.setMaximumSize(new java.awt.Dimension(400, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.lightGray));

        welcomeMsg.setFont(CustomFont.getExtendedSemiBold(14f));
        welcomeMsg.setText("Request new leave");

        boarder1.setBackground(new java.awt.Color(0, 66, 102));
        boarder1.setForeground(new java.awt.Color(0, 66, 102));

        jLabel13.setFont(CustomFont.getExtendedRegular(12f));
        jLabel13.setText("Leave Type:");

        jLabel14.setFont(CustomFont.getExtendedRegular(12f));
        jLabel14.setText("From:");

        jLabel15.setFont(CustomFont.getExtendedRegular(12f));
        jLabel15.setText("Reason:");

        chooseLeaveType.add(vacationLeaveBttn);
        vacationLeaveBttn.setText("Vacation Leave");
        vacationLeaveBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        vacationLeaveBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        vacationLeaveBttn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        vacationLeaveBttn.setMaximumSize(new java.awt.Dimension(160, 30));
        vacationLeaveBttn.setMinimumSize(new java.awt.Dimension(160, 30));
        vacationLeaveBttn.setPreferredSize(new java.awt.Dimension(160, 30));
        vacationLeaveBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vacationLeaveBttnActionPerformed(evt);
            }
        });

        chooseLeaveType.add(sickLeaveBttn);
        sickLeaveBttn.setText("Sick Leave");
        sickLeaveBttn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sickLeaveBttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        sickLeaveBttn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        sickLeaveBttn.setMaximumSize(new java.awt.Dimension(160, 30));
        sickLeaveBttn.setMinimumSize(new java.awt.Dimension(160, 30));
        sickLeaveBttn.setPreferredSize(new java.awt.Dimension(160, 30));
        sickLeaveBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sickLeaveBttnActionPerformed(evt);
            }
        });

        reason.setColumns(20);
        reason.setFont(CustomFont.getExtendedRegular(12f));
        reason.setRows(5);
        jScrollPane2.setViewportView(reason);

        applyLeaveButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        applyLeaveButton.setForeground(new java.awt.Color(255, 255, 255));
        applyLeaveButton.setText("Apply Leave");
        applyLeaveButton.setBorder(null);
        applyLeaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyLeaveButtonActionPerformed(evt);
            }
        });

        cancelLeaveButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Grey"));
        cancelLeaveButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelLeaveButton.setText("Cancel");
        cancelLeaveButton.setBorder(null);
        cancelLeaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelLeaveButtonActionPerformed(evt);
            }
        });

        toDateChooser.setMaxSelectableDate(null);
        toDateChooser.setMinSelectableDate(null);
        toDateChooser.setPreferredSize(new java.awt.Dimension(180, 22));

        jLabel16.setFont(CustomFont.getExtendedRegular(12f));
        jLabel16.setText("To:");

        fromDateChooser.setMaxSelectableDate(null);
        fromDateChooser.setMinSelectableDate(null);
        fromDateChooser.setPreferredSize(new java.awt.Dimension(180, 22));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boarder1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(fromDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                                .addComponent(toDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(vacationLeaveBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sickLeaveBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelLeaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(applyLeaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(welcomeMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {sickLeaveBttn, vacationLeaveBttn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(welcomeMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boarder1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(vacationLeaveBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sickLeaveBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelLeaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(applyLeaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {sickLeaveBttn, vacationLeaveBttn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(lblSubTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lblSubTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))
                .addContainerGap(116, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
        private void applyLeaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // leave type must be selected
                if (!vacationLeaveBttn.isSelected() && !sickLeaveBttn.isSelected()) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Please select a leave type (Vacation or Sick Leave)",
                                        "Missing Leave Type",
                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // from date must be selected
                if (fromDateChooser.getDate() == null) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Please select a 'From' date",
                                        "Missing From Date",
                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // to date must be selected
                if (toDateChooser.getDate() == null) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Please select a 'To' date",
                                        "Missing To Date",
                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // reason should be provided
                if (reason.getText().trim().isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Please provide a reason for your leave request",
                                        "Missing Reason",
                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // All validations passed - proceed with leave submission
                String leaveType = vacationLeaveBttn.isSelected() ? "Vacation Leave" : "Sick Leave";
                String reasonText = reason.getText().trim();

                LeaveRequest request = new LeaveRequest(
                        employee.getEmployeeNumber(),
                        new Date(),
                        fromDateChooser.getDate(),
                        toDateChooser.getDate(),
                        reasonText,
                        leaveType,
                        "Pending"
                );

                try {
                    LeaveService leaveService = new LeaveService();
                    leaveService.submitLeaveRequest(request);

                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Leave request submitted successfully!",
                            "Success",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);

                    clearForm();
                    showLeaveRequests(); 

                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Error saving leave request: " + e.getMessage(),
                            "Error",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                }
        }

        private void cancelLeaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
                clearForm();
        }

        private void clearForm() {
                chooseLeaveType.clearSelection();
                fromDateChooser.setDate(null);
                toDateChooser.setDate(null);
                reason.setText("");
        }

        private void vacationLeaveBttnActionPerformed(java.awt.event.ActionEvent evt) {

        }

        private void sickLeaveBttnActionPerformed(java.awt.event.ActionEvent evt) {

        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyLeaveButton;
    private javax.swing.JSeparator boarder1;
    private javax.swing.JButton cancelLeaveButton;
    private javax.swing.ButtonGroup chooseLeaveType;
    private com.toedter.calendar.JDateChooser fromDateChooser;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JTable leaveListTable;
    private javax.swing.JTextArea reason;
    private javax.swing.JRadioButton sickLeaveBttn;
    private com.toedter.calendar.JDateChooser toDateChooser;
    private javax.swing.JRadioButton vacationLeaveBttn;
    private javax.swing.JLabel welcomeMsg;
    // End of variables declaration//GEN-END:variables
}
