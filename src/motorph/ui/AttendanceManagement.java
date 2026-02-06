package motorph.ui;

import motorph.model.EmployeeTimeLogs;
import motorph.dao.DataHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import motorph.ui.util.CustomFont;

public class AttendanceManagement extends javax.swing.JPanel {

    private int currentPage = 1;
    private int rowsPerPage = 17;
    private List<EmployeeTimeLogs> timeLogs;

    private List<EmployeeTimeLogs> filteredLogs = null;

    public AttendanceManagement() {
        initComponents();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < attendanceLogTable.getColumnCount(); i++) {
            attendanceLogTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        loadTimeLogsToTable();
    }

    public void loadTimeLogsToTable() {
        timeLogs = DataHandler.readEmployeeTimeLogs(); // Load from CSV or DB
        currentPage = 1;
        updateTablePage();
    }

    private void updateTablePage(List<EmployeeTimeLogs> logsToDisplay) {
        DefaultTableModel model = (DefaultTableModel) attendanceLogTable.getModel();
        model.setRowCount(0);

        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, logsToDisplay.size());

        for (int i = start; i < end; i++) {
            EmployeeTimeLogs log = logsToDisplay.get(i);
            String fullName = log.getFirstName() + " " + log.getLastName();
            String totalHours = calculateHoursWorked(log.getLogIn(), log.getLogOut());

            model.addRow(new Object[] {
                    log.getEmployeeNumber(),
                    fullName,
                    log.getDate(),
                    log.getLogIn(),
                    log.getLogOut(),
                    totalHours
            });
        }

        int totalPages = (int) Math.ceil((double) logsToDisplay.size() / rowsPerPage);
        lblPageInfo.setText("Page " + currentPage + " of " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    private void updateTablePage() {
        updateTablePage(timeLogs);
    }

    private String calculateHoursWorked(String inTime, String outTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime in = LocalTime.parse(inTime.trim(), formatter);
            LocalTime out = LocalTime.parse(outTime.trim(), formatter);
            Duration duration = Duration.between(in, out);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            return String.format("%02d:%02d", hours, minutes);
        } catch (Exception e) {
            System.out.println("Invalid time format: IN=" + inTime + ", OUT" + outTime);
            e.printStackTrace();
            return "Invalid";
        }
    }

    private void updateFilteredTablePage() {
        if (filteredLogs != null) {
            updateTablePage(filteredLogs);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));
        jScrollPane1 = new javax.swing.JScrollPane();
        attendanceLogTable = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        searchBar = new javax.swing.JTextField();
        boarder1 = new javax.swing.JSeparator();
        lblSubTitle = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblPageInfo = new javax.swing.JLabel();
        sortBttn = new javax.swing.JButton();
        refreshButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1054, 720));
        setMinimumSize(new java.awt.Dimension(1054, 720));
        setRequestFocusEnabled(false);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        attendanceLogTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        attendanceLogTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null }
                },
                new String[] {
                        "Employee #", "Employee Name", "Attendance Date", "In Time", "Out Time", "Total Worked Time"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        attendanceLogTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(attendanceLogTable);
        if (attendanceLogTable.getColumnModel().getColumnCount() > 0) {
            attendanceLogTable.getColumnModel().getColumn(0).setResizable(false);
            attendanceLogTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            attendanceLogTable.getColumnModel().getColumn(1).setResizable(false);
            attendanceLogTable.getColumnModel().getColumn(2).setResizable(false);
            attendanceLogTable.getColumnModel().getColumn(3).setResizable(false);
            attendanceLogTable.getColumnModel().getColumn(4).setResizable(false);
        }

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph/icons/search.png"))); // NOI18N
        searchButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        searchButton.setBorderPainted(false);
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

        lblSubTitle.setFont(CustomFont.getExtendedSemiBold(20f));
        lblSubTitle.setText("Attendance History");

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

        sortBttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph/icons/filter.png"))); // NOI18N
        sortBttn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        sortBttn.setBorderPainted(false);
        sortBttn.setFocusPainted(false);
        sortBttn.setMaximumSize(new java.awt.Dimension(35, 35));
        sortBttn.setMinimumSize(new java.awt.Dimension(0, 0));
        sortBttn.setPreferredSize(new java.awt.Dimension(24, 24));
        sortBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortBttnActionPerformed(evt);
            }
        });

        refreshButton1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/motorph/icons/refresh_20dp_000000_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        refreshButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        refreshButton1.setBorderPainted(false);
        refreshButton1.setFocusPainted(false);
        refreshButton1.setMaximumSize(new java.awt.Dimension(35, 35));
        refreshButton1.setMinimumSize(new java.awt.Dimension(0, 0));
        refreshButton1.setPreferredSize(new java.awt.Dimension(24, 24));
        refreshButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boarder1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnPrev)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPageInfo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnNext))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(searchButton,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                false)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout
                                                                .createSequentialGroup()
                                                                .addComponent(lblSubTitle,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 265,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(sortBttn,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 48,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(refreshButton1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 983,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(36, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(52, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(searchBar, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchButton, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(boarder1, javax.swing.GroupLayout.PREFERRED_SIZE, 8,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addComponent(lblSubTitle,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(refreshButton1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(sortBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPrev)
                                        .addComponent(btnNext)
                                        .addComponent(lblPageInfo))
                                .addContainerGap(111, Short.MAX_VALUE)));

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
                    searchBar.setText("Search Employee by ID"); // Restore the placeholder text if empty
                    searchBar.setForeground(new java.awt.Color(153, 153, 153)); // Set text color to gray
                }
            }
        });

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchButtonActionPerformed

        String empNumberInput = searchBar.getText().trim();

        if (empNumberInput.isEmpty() || empNumberInput.equals("Search Employee by ID")) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
            filteredLogs = null;
            loadTimeLogsToTable();
            return;
        }

        if (!empNumberInput.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid Employee Number. Please enter digits only.");
            filteredLogs = null;
            loadTimeLogsToTable();
            return;
        }

        // Filter logs once
        filteredLogs = new ArrayList<>();
        for (EmployeeTimeLogs log : timeLogs) {
            if (log.getEmployeeNumber().equals(empNumberInput)) {
                filteredLogs.add(log);
            }
        }

        if (filteredLogs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            filteredLogs = null;
            loadTimeLogsToTable();
        } else {
            currentPage = 1;
            updateFilteredTablePage();
        }
    }// GEN-LAST:event_searchButtonActionPerformed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_searchBarActionPerformed

    private void searchBarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_searchBarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            if (searchBar.getText().trim().isEmpty()) {
                filteredLogs = null;
                loadTimeLogsToTable();
            } else {
                searchButtonActionPerformed(null);
            }
        }
    }// GEN-LAST:event_searchBarKeyPressed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrevActionPerformed
        currentPage--;
        if (filteredLogs != null) {
            updateFilteredTablePage();
        } else {
            updateTablePage(timeLogs);
        }
    }// GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNextActionPerformed
        currentPage++;
        if (filteredLogs != null) {
            updateFilteredTablePage();
        } else {
            updateTablePage(timeLogs);
        }
    }// GEN-LAST:event_btnNextActionPerformed

    private void sortBttnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sortBttnActionPerformed
        JPopupMenu sortMenu = new JPopupMenu();

        JMenuItem latestItem = new JMenuItem("Sort Date: Latest");
        JMenuItem oldestItem = new JMenuItem("Sort Date: Oldest");

        latestItem.addActionListener(e -> {
            timeLogs.sort((a, b) -> b.getDate().compareTo(a.getDate()));
            currentPage = 1;
            updateTablePage();
        });

        oldestItem.addActionListener(e -> {
            timeLogs.sort((a, b) -> a.getDate().compareTo(b.getDate()));
            currentPage = 1;
            updateTablePage();
        });

        sortMenu.add(latestItem);
        sortMenu.add(oldestItem);
        sortMenu.show(sortBttn, 0, sortBttn.getHeight());
    }// GEN-LAST:event_sortBttnActionPerformed

    private void refreshButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_refreshButton1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_refreshButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable attendanceLogTable;
    private javax.swing.JSeparator boarder1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JButton refreshButton1;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton sortBttn;
    // End of variables declaration//GEN-END:variables
}
