package motorph.ui;

import motorph.model.core.Employee;
import motorph.model.LeaveRequest;
import motorph.model.users.HrUser;
import motorph.service.LeaveService;
import motorph.service.HRService;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import motorph.ui.util.CustomFont;

public class LeaveManagement extends javax.swing.JPanel {

        // Pagination Variables and Leave Request List Setup
        private int currentPage = 1;
        private int rowsPerPage = 18;
        private List<LeaveRequest> leaveRequests;
        private LeaveService leaveService;
        private HrUser hrUser;

        public LeaveManagement(HrUser hrUser) {
                this.hrUser = hrUser;
                initComponents();
                leaveService = new LeaveService();

                setupTableRendering();
                setupTableSelectionListener();
                loadLeaveRequestsToTable();
                hideDetailsLabels();
        }

        // ========== TABLE SETUP METHODS ==========

        private void setupTableRendering() {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

                for (int i = 0; i < leaveRequestTable.getColumnCount(); i++) {
                        leaveRequestTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
        }

        private void setupTableSelectionListener() {
                leaveRequestTable.getSelectionModel().addListSelectionListener(e -> {
                        if (!e.getValueIsAdjusting() && leaveRequestTable.getSelectedRow() >= 0) {
                                int row = leaveRequestTable.getSelectedRow();
                                showDetailsLabels(); // Show details when row is selected
                                displayLeaveRequestDetails(row);
                        }
                });
        }

        // ========== TABLE DISPLAY METHODS ==========

        public void loadLeaveRequestsToTable() {
                leaveRequests = leaveService.findAllLeaveRequests();
                currentPage = 1;
                updateTablePage();
        }

        private void updateTablePage() {
                DefaultTableModel model = (DefaultTableModel) leaveRequestTable.getModel();
                model.setRowCount(0);

                int start = (currentPage - 1) * rowsPerPage;
                int end = Math.min(start + rowsPerPage, leaveRequests.size());

                for (int i = start; i < end; i++) {
                        LeaveRequest leave = leaveRequests.get(i);

                        model.addRow(new Object[] {
                                        leave.getRequestId(),
                                        leave.getFormattedRequestDate(),
                                        leave.getFormattedFromDate(),
                                        leave.getFormattedToDate(),
                                        leave.getNumberOfDays(),
                                        leave.getStatus()
                        });
                }

                int totalPages = (int) Math.ceil((double) leaveRequests.size() / rowsPerPage);
                lblPageInfo.setText("Page " + currentPage + " of " + totalPages);
                btnPrev.setEnabled(currentPage > 1);
                btnNext.setEnabled(currentPage < totalPages);
        }

        private void displayLeaveRequestDetails(int row) {
                int modelRow = leaveRequestTable.convertRowIndexToModel(row);
                int start = (currentPage - 1) * rowsPerPage;
                LeaveRequest leave = leaveRequests.get(start + modelRow);

                Employee employee = leaveService.getEmployee(leave.getEmployeeNumber());

                if (employee != null) {
                        empIdVal.setText(employee.getEmployeeNumber());
                        empNumVal.setText(employee.getFirstName() + " " + employee.getLastName());
                }

                reqId.setText(leave.getRequestId());
                leaveTypeVal.setText(leave.getLeavetype());
                fromDateVal.setText(leave.getFormattedFromDate());
                toDateVal.setText(leave.getFormattedToDate());
                totalDaysVal.setText(String.valueOf(leave.getNumberOfDays()));
                reasonBox.setText(leave.getReason());

                // disablie buttons if hr already approve/reject the request
                String status = leave.getStatus();
                if ("Approved".equalsIgnoreCase(status) || "Rejected".equalsIgnoreCase(status)) {
                        approveBttn.setEnabled(false);
                        rejectBttn.setEnabled(false);
                }
        }

        // ========== VISIBILITY CONTROL METHODS ==========

        private void hideDetailsLabels() {
                empIdVal.setVisible(false);
                empNumVal.setVisible(false);
                leaveTypeVal.setVisible(false);
                fromDateVal.setVisible(false);
                toDateVal.setVisible(false);
                totalDaysVal.setVisible(false);
                reasonBox.setVisible(false);
                reqId.setVisible(false);
                approveBttn.setEnabled(false);
                rejectBttn.setEnabled(false);
        }

        private void showDetailsLabels() {
                empIdVal.setVisible(true);
                empNumVal.setVisible(true);
                leaveTypeVal.setVisible(true);
                fromDateVal.setVisible(true);
                toDateVal.setVisible(true);
                totalDaysVal.setVisible(true);
                reasonBox.setVisible(true);
                reqId.setVisible(true);
                approveBttn.setEnabled(true);
                rejectBttn.setEnabled(true);
        }

        // ========== UI INITIALIZATION (DO NOT MODIFY) ==========
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
                                new java.awt.Dimension(0, 32767));
                searchButton = new javax.swing.JButton();
                searchBar = new javax.swing.JTextField();
                boarder1 = new javax.swing.JSeparator();
                btnPrev = new javax.swing.JButton();
                btnNext = new javax.swing.JButton();
                lblPageInfo = new javax.swing.JLabel();
                lblSubTitle = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                leaveRequestTable = new javax.swing.JTable();
                emplyeeInfo = new javax.swing.JPanel();
                reqId = new javax.swing.JLabel();
                boarder4 = new javax.swing.JSeparator();
                approveBttn = new javax.swing.JButton();
                rejectBttn = new javax.swing.JButton();
                ReqIDlbl = new javax.swing.JLabel();
                empNumlbl = new javax.swing.JLabel();
                empIdlbl = new javax.swing.JLabel();
                toDatelbl = new javax.swing.JLabel();
                leaveTypelbl = new javax.swing.JLabel();
                fromDatelbl = new javax.swing.JLabel();
                totalDayslbl = new javax.swing.JLabel();
                reasonlbl = new javax.swing.JLabel();
                jScrollPane4 = new javax.swing.JScrollPane();
                reasonBox = new javax.swing.JTextArea();
                empNumVal = new javax.swing.JLabel();
                empIdVal = new javax.swing.JLabel();
                leaveTypeVal = new javax.swing.JLabel();
                fromDateVal = new javax.swing.JLabel();
                toDateVal = new javax.swing.JLabel();
                totalDaysVal = new javax.swing.JLabel();
                titlePagelb = new javax.swing.JLabel();

                setBackground(new java.awt.Color(255, 255, 255));
                setMaximumSize(new java.awt.Dimension(1054, 720));
                setMinimumSize(new java.awt.Dimension(1054, 720));
                setPreferredSize(new java.awt.Dimension(1054, 720));

                searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph/icons/search.png"))); // NOI18N
                searchButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
                searchButton.setBorderPainted(false);
                searchButton.setContentAreaFilled(false);
                searchButton.setFocusPainted(false);
                searchButton.setMaximumSize(new java.awt.Dimension(35, 35));
                searchButton.setMinimumSize(new java.awt.Dimension(0, 0));
                searchButton.setPreferredSize(new java.awt.Dimension(24, 24));
                searchButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                searchButtonActionPerformed(evt);
                        }
                });

                searchBar.setBackground(new java.awt.Color(238, 238, 238));
                searchBar.setForeground(new java.awt.Color(153, 153, 153));
                searchBar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                searchBar.setText("Search Employee by ID");
                searchBar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                searchBarActionPerformed(evt);
                        }
                });
                searchBar.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyPressed(java.awt.event.KeyEvent evt) {
                                searchBarKeyPressed(evt);
                        }
                });

                boarder1.setBackground(new java.awt.Color(0, 66, 102));
                boarder1.setForeground(new java.awt.Color(0, 66, 102));

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

                lblPageInfo.setText("Page 1 of 1");

                lblSubTitle.setFont(CustomFont.getExtendedSemiBold(14f));
                lblSubTitle.setText("My Leave Requests");
                lblSubTitle.setMaximumSize(new java.awt.Dimension(400, 30));

                leaveRequestTable.setAutoCreateRowSorter(true);
                leaveRequestTable.setFont(CustomFont.getExtendedRegular(12f));
                leaveRequestTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null, null, null }
                                },
                                new String[] {
                                                "Request ID", "Reqest Date", "From", "To", "Days", "Status"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                leaveRequestTable.setAutoscrolls(false);
                leaveRequestTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                leaveRequestTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                leaveRequestTable.getTableHeader().setReorderingAllowed(false);
                leaveRequestTable.setUpdateSelectionOnSort(false);
                jScrollPane1.setViewportView(leaveRequestTable);

                emplyeeInfo.setBackground(new java.awt.Color(255, 255, 255));
                emplyeeInfo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED,
                                java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.lightGray,
                                java.awt.Color.lightGray));

                reqId.setFont(CustomFont.getExtendedSemiBold(14f));
                reqId.setText("ID NUMBER");

                boarder4.setBackground(new java.awt.Color(0, 66, 102));
                boarder4.setForeground(new java.awt.Color(0, 66, 102));

                approveBttn.setBackground(new java.awt.Color(0, 153, 51));
                approveBttn.setForeground(new java.awt.Color(255, 255, 255));
                approveBttn.setText("Approve");
                approveBttn.setBorder(null);
                approveBttn.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                approveBttnActionPerformed(evt);
                        }
                });

                rejectBttn.setBackground(new java.awt.Color(153, 0, 51));
                rejectBttn.setForeground(new java.awt.Color(255, 255, 255));
                rejectBttn.setText("Reject");
                rejectBttn.setBorder(null);
                rejectBttn.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                rejectBttnActionPerformed(evt);
                        }
                });

                ReqIDlbl.setFont(CustomFont.getExtendedSemiBold(14f));
                ReqIDlbl.setText("REQUEST ID:");

                empNumlbl.setFont(CustomFont.getExtendedRegular(12f));
                empNumlbl.setText("Employee Name:");

                empIdlbl.setFont(CustomFont.getExtendedRegular(12f));
                empIdlbl.setText("Employee ID:");

                toDatelbl.setFont(CustomFont.getExtendedRegular(12f));
                toDatelbl.setText("To:");

                leaveTypelbl.setFont(CustomFont.getExtendedRegular(12f));
                leaveTypelbl.setText("Leave Type:");

                fromDatelbl.setFont(CustomFont.getExtendedRegular(12f));
                fromDatelbl.setText("From:");

                totalDayslbl.setFont(CustomFont.getExtendedRegular(12f));
                totalDayslbl.setText("Total Days:");

                reasonlbl.setFont(CustomFont.getExtendedRegular(12f));
                reasonlbl.setText("Reason:");

                reasonBox.setEditable(false);
                reasonBox.setColumns(20);
                reasonBox.setFont(CustomFont.getExtendedRegular(12f));
                reasonBox.setRows(5);
                reasonBox.setFocusable(false);
                jScrollPane4.setViewportView(reasonBox);

                empNumVal.setFont(CustomFont.getExtendedRegular(12f));
                empNumVal.setText("Full Name Here");

                empIdVal.setFont(CustomFont.getExtendedRegular(12f));
                empIdVal.setText("#####");

                leaveTypeVal.setFont(CustomFont.getExtendedRegular(12f));
                leaveTypeVal.setText("Sick/Holiday Leave");

                fromDateVal.setFont(CustomFont.getExtendedRegular(12f));
                fromDateVal.setText("Date From");

                toDateVal.setFont(CustomFont.getExtendedRegular(12f));
                toDateVal.setText("Date To");

                totalDaysVal.setFont(CustomFont.getExtendedRegular(12f));
                totalDaysVal.setText("# of Days");

                javax.swing.GroupLayout emplyeeInfoLayout = new javax.swing.GroupLayout(emplyeeInfo);
                emplyeeInfo.setLayout(emplyeeInfoLayout);
                emplyeeInfoLayout.setHorizontalGroup(
                                emplyeeInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(boarder4)
                                                .addGroup(emplyeeInfoLayout.createSequentialGroup()
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jScrollPane4,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                367,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(emplyeeInfoLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGap(178, 178,
                                                                                                                                178)
                                                                                                                .addComponent(rejectBttn,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                92,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                .addComponent(approveBttn,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                102,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGap(16, 16, 16)
                                                                                                                .addComponent(ReqIDlbl)
                                                                                                                .addGap(18, 18, 18)
                                                                                                                .addComponent(reqId))
                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGap(17, 17, 17)
                                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addComponent(empNumlbl,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                110,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(empNumVal,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                110,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(empIdlbl,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(leaveTypelbl,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(fromDatelbl,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(toDatelbl,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(totalDayslbl,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(reasonlbl,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addGroup(emplyeeInfoLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(empIdVal,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(leaveTypeVal,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(fromDateVal,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(toDateVal,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(totalDaysVal,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                110,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                                                .addContainerGap(22, Short.MAX_VALUE)));
                emplyeeInfoLayout.setVerticalGroup(
                                emplyeeInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(emplyeeInfoLayout.createSequentialGroup()
                                                                .addGap(17, 17, 17)
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(reqId,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                22,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(ReqIDlbl,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                22,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(boarder4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                8,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(empNumlbl)
                                                                                .addComponent(empNumVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(
                                                                                emplyeeInfoLayout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(empIdVal,
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                .addComponent(empIdlbl,
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING))
                                                                .addGap(34, 34, 34)
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(leaveTypelbl)
                                                                                .addComponent(leaveTypeVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(fromDatelbl)
                                                                                .addComponent(fromDateVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(toDatelbl)
                                                                                .addComponent(toDateVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(totalDayslbl)
                                                                                .addComponent(totalDaysVal))
                                                                .addGap(33, 33, 33)
                                                                .addComponent(reasonlbl)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                128,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                42,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(emplyeeInfoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(rejectBttn,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(approveBttn,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(45, 45, 45)));

                titlePagelb.setFont(CustomFont.getExtendedSemiBold(20f));
                titlePagelb.setText("EMPLOYEE MANAGEMENT");
                titlePagelb.setMaximumSize(null);
                titlePagelb.setPreferredSize(null);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(boarder1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(39, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(emplyeeInfo,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(38, 38, 38)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jScrollPane1,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                532,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(lblSubTitle,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                452,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(btnPrev)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(lblPageInfo)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(btnNext)))
                                                                .addGap(33, 33, 33))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGap(36, 36, 36)
                                                                .addComponent(titlePagelb,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                528, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(searchBar,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                145,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(searchButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                32,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(37, 37, 37)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(53, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(searchBar,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                28,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(titlePagelb,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                30,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(searchButton,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                28,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(11, 11, 11)
                                                                .addComponent(boarder1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                8,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                14,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(lblSubTitle,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                30,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(jScrollPane1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                475,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(emplyeeInfo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(btnPrev)
                                                                                .addComponent(btnNext)
                                                                                .addComponent(lblPageInfo))
                                                                .addGap(50, 50, 50)));

                // Manually added the FocusListener below the searchBar setup
                searchBar.addFocusListener(new java.awt.event.FocusListener() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                                if (searchBar.getText().equals("Search Employee by ID")) {
                                        searchBar.setText(""); // Clear the text when clicked
                                        searchBar.setForeground(new java.awt.Color(0, 0, 0)); // Set text color to black
                                }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                if (searchBar.getText().isEmpty()) {
                                        searchBar.setText("Search Employee by ID"); // Restore the placeholder text if
                                                                                    // empty
                                        searchBar.setForeground(new java.awt.Color(153, 153, 153)); // Set text color to
                                                                                                    // gray
                                }
                        }
                });

                getAccessibleContext().setAccessibleName("");
        }// </editor-fold>//GEN-END:initComponents

        // ========== SEARCH METHODS ==========

        private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchButtonActionPerformed

                String empNumberInput = searchBar.getText().trim();

                // Check if the search field is empty before processing
                if (empNumberInput.isEmpty() || empNumberInput.equals("Search Employee by ID")) {
                        JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
                        loadLeaveRequestsToTable();
                        return;
                }

                // Make sure the input only has numbers (digits)
                if (!empNumberInput.matches("\\d+")) {
                        JOptionPane.showMessageDialog(this, "Invalid Employee Number. Please enter digits only.");
                        loadLeaveRequestsToTable();
                        return;
                }

                DefaultTableModel model = (DefaultTableModel) leaveRequestTable.getModel();
                model.setRowCount(0); // Clear the existing table rows before adding search results

                boolean found = false;

                // Loop through the leave requests and find ones that match the entered employee
                // ID
                for (LeaveRequest leave : leaveRequests) {
                        if (leave.getEmployeeNumber().equals(empNumberInput)) {
                                model.addRow(new Object[] {
                                                leave.getRequestId(),
                                                leave.getFormattedRequestDate(),
                                                leave.getFormattedFromDate(),
                                                leave.getFormattedToDate(),
                                                leave.getNumberOfDays(),
                                                leave.getStatus()
                                });
                                found = true;
                        }
                }

                // If no leave request is found with the given employee ID
                if (!found) {
                        JOptionPane.showMessageDialog(this, "No pending leave request found for this employee.");
                        loadLeaveRequestsToTable();
                }
        }

        private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchBarActionPerformed
                // TODO add your handling code here:

        }

        private void searchBarKeyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                        searchButtonActionPerformed(null);
                }
        }

        // ========== PAGINATION METHODS ==========

        private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {
                if (currentPage > 1) {
                        currentPage--;
                        updateTablePage();
                }
        }

        private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {
                int totalPages = (int) Math.ceil((double) leaveRequests.size() / rowsPerPage);
                if (currentPage < totalPages) {
                        currentPage++;
                        updateTablePage();
                }
        }

        // ========== LEAVE APPROVAL METHODS ==========

        private void approveBttnActionPerformed(java.awt.event.ActionEvent evt) {
                int selectedRow = leaveRequestTable.getSelectedRow();
                if (selectedRow < 0) {
                        JOptionPane.showMessageDialog(this, "Please select a leave request to approve.", "No Selection",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                int modelRow = leaveRequestTable.convertRowIndexToModel(selectedRow);
                DefaultTableModel model = (DefaultTableModel) leaveRequestTable.getModel();
                String requestId = String.valueOf(model.getValueAt(modelRow, 0));

                try {
                        HRService hrService = new HRService();
                        hrService.approveLeaveRequest(requestId, hrUser.getEmployeeNumber());
                        JOptionPane.showMessageDialog(this, "Leave request approved successfully!", "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                        // Update status in table and cached list
                        model.setValueAt("Approved", modelRow, 5);
                        updateCachedLeaveStatus(requestId, "Approved");
                        clearLeaveDetailsPanel();
                        hideDetailsLabels();
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error approving leave request: " + e.getMessage(), "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private void rejectBttnActionPerformed(java.awt.event.ActionEvent evt) {
                int selectedRow = leaveRequestTable.getSelectedRow();
                if (selectedRow < 0) {
                        JOptionPane.showMessageDialog(this, "Please select a leave request to reject.", "No Selection",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                int modelRow = leaveRequestTable.convertRowIndexToModel(selectedRow);
                DefaultTableModel model = (DefaultTableModel) leaveRequestTable.getModel();
                String requestId = String.valueOf(model.getValueAt(modelRow, 0));

                try {
                        HRService hrService = new HRService();
                        hrService.rejectLeaveRequest(requestId, hrUser.getEmployeeNumber());
                        JOptionPane.showMessageDialog(this, "Leave request rejected successfully!", "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                        // Update status in table and cached list
                        model.setValueAt("Rejected", modelRow, 5);
                        updateCachedLeaveStatus(requestId, "Rejected");
                        clearLeaveDetailsPanel();
                        hideDetailsLabels();
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error rejecting leave request: " + e.getMessage(), "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // ========== UTILITY METHODS ==========

        private void clearLeaveDetailsPanel() {
                reqId.setText("");
                empIdVal.setText("");
                empNumVal.setText("");
                leaveTypeVal.setText("");
                fromDateVal.setText("");
                toDateVal.setText("");
                totalDaysVal.setText("");
                reasonBox.setText("");
        }

        private void updateCachedLeaveStatus(String requestId, String status) {
                for (LeaveRequest request : leaveRequests) {
                        if (request.getRequestId().equals(requestId)) {
                                request.setStatus(status);
                                request.setReviewedBy(hrUser.getEmployeeNumber());
                                request.setDateReviewed(new java.util.Date());
                                break;
                        }
                }
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel ReqIDlbl;
        private javax.swing.JButton approveBttn;
        private javax.swing.JSeparator boarder1;
        private javax.swing.JSeparator boarder4;
        private javax.swing.JButton btnNext;
        private javax.swing.JButton btnPrev;
        private javax.swing.JLabel empIdVal;
        private javax.swing.JLabel empIdlbl;
        private javax.swing.JLabel empNumVal;
        private javax.swing.JLabel empNumlbl;
        private javax.swing.JPanel emplyeeInfo;
        private javax.swing.Box.Filler filler1;
        private javax.swing.JLabel fromDateVal;
        private javax.swing.JLabel fromDatelbl;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JLabel lblPageInfo;
        private javax.swing.JLabel lblSubTitle;
        private javax.swing.JTable leaveRequestTable;
        private javax.swing.JLabel leaveTypeVal;
        private javax.swing.JLabel leaveTypelbl;
        private javax.swing.JTextArea reasonBox;
        private javax.swing.JLabel reasonlbl;
        private javax.swing.JButton rejectBttn;
        private javax.swing.JLabel reqId;
        private javax.swing.JTextField searchBar;
        private javax.swing.JButton searchButton;
        private javax.swing.JLabel titlePagelb;
        private javax.swing.JLabel toDateVal;
        private javax.swing.JLabel toDatelbl;
        private javax.swing.JLabel totalDaysVal;
        private javax.swing.JLabel totalDayslbl;
        // End of variables declaration//GEN-END:variables
}
