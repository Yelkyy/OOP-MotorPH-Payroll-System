package motorph.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import motorph.model.core.Employee;
import motorph.dao.DataHandler;
import motorph.model.EmployeeTimeLogs;
import motorph.service.PayrollService;
import motorph.ui.util.CustomFont;
import motorph.service.EmployeeService;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import motorph.ui.util.MyRender;

public class PayrunsPanel extends javax.swing.JPanel {

    private List<Employee> employees;
    private List<EmployeeTimeLogs> timeLogs;
    private List<LocalDate> payDates;

    private int currentPage = 1;
    private final int rowsPerPage = 15;

    private static final DateTimeFormatter TIMELOG_FMT = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US)
            .withResolverStyle(ResolverStyle.STRICT);

    private static LocalDate parseTimelogDate(String raw) {
        return LocalDate.parse(raw == null ? "" : raw.trim(), TIMELOG_FMT);
    }

    public PayrunsPanel() {
        initComponents();
        initTable();
        loadData();
        setupMouseClickListener();
    }

    private void initTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < payrolList.getColumnCount(); i++) {
            payrolList.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        payrolList.getTableHeader().setReorderingAllowed(false);
        payrolList.getTableHeader().setResizingAllowed(false);

        if (payrolList.getColumnModel().getColumnCount() >= 6) {
            payrolList.getColumnModel().getColumn(0).setPreferredWidth(250);
            payrolList.getColumnModel().getColumn(1).setPreferredWidth(150);
            payrolList.getColumnModel().getColumn(2).setPreferredWidth(80);
            payrolList.getColumnModel().getColumn(3).setPreferredWidth(150);
            payrolList.getColumnModel().getColumn(4).setPreferredWidth(80);
            payrolList.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        payrolList.getColumnModel().getColumn(5).setCellRenderer(new MyRender());
    }

    private void loadData() {
        employees = EmployeeService.getAllEmployees();
        timeLogs = DataHandler.readEmployeeTimeLogs();
        payDates = getPayDatesFromTimeLogs();
        loadPayRuns();
    }

    private void setupMouseClickListener() {
        payrolList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = payrolList.rowAtPoint(evt.getPoint());
                int col = payrolList.columnAtPoint(evt.getPoint());

                if (col == 5) {
                    String action = (String) payrolList.getValueAt(row, col);
                    if ("View".equalsIgnoreCase(action)) {
                        String payPeriod = (String) payrolList.getValueAt(row, 0);
                        String payDate = (String) payrolList.getValueAt(row, 1);
                        openDisplayPayrollList(payPeriod, payDate);
                    }
                }
            }
        });
    }

    private void loadPayRuns() {
        if (payDates == null || payDates.isEmpty()) {
            lblPageInfo.setText("No available pay periods.");
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) payrolList.getModel();
        model.setRowCount(0);

        DateTimeFormatter fullDateFmt = DateTimeFormatter.ofPattern("MMMM d, yyyy");

        int totalPages = (int) Math.ceil((double) payDates.size() / rowsPerPage);
        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, payDates.size());

        List<LocalDate> pagePayDates = payDates.subList(startIndex, endIndex);

        for (LocalDate cutoffDate : pagePayDates) {
            LocalDate startDate = (cutoffDate.getDayOfMonth() == 15)
                    ? cutoffDate.withDayOfMonth(1)
                    : cutoffDate.withDayOfMonth(16);

            String payPeriod = startDate.format(fullDateFmt) + " - " + cutoffDate.format(fullDateFmt);
            String payDate = cutoffDate.format(fullDateFmt);

            int employeeCount = countEmployeesPaidOn(cutoffDate);
            double totalPayment = calculateTotalPaymentOn(cutoffDate);

            String status = (employeeCount > 0) ? "Completed" : "Pending";
            String action = (employeeCount > 0) ? "View" : "Run Payroll";

            model.addRow(new Object[] {
                    payPeriod,
                    payDate,
                    employeeCount,
                    "₱ " + String.format("%,.2f", totalPayment),
                    status,
                    action
            });
        }

        lblPageInfo.setText("Page " + currentPage + " of " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    private List<LocalDate> getPayDatesFromTimeLogs() {
        Set<LocalDate> payDates = new TreeSet<>();
        for (EmployeeTimeLogs log : timeLogs) {
            LocalDate logDate = parseTimelogDate(log.getDate());
            payDates.add(getCutoffForDate(logDate));
        }
        return new ArrayList<>(payDates);
    }

    private LocalDate getCutoffForDate(LocalDate date) {
        return (date.getDayOfMonth() <= 15)
                ? LocalDate.of(date.getYear(), date.getMonth(), 15)
                : YearMonth.of(date.getYear(), date.getMonth()).atEndOfMonth();
    }

    private boolean isInPayPeriod(LocalDate logDate, LocalDate cutoffDate) {
        LocalDate start = (cutoffDate.getDayOfMonth() == 15)
                ? cutoffDate.withDayOfMonth(1)
                : cutoffDate.withDayOfMonth(16);
        return !logDate.isBefore(start) && !logDate.isAfter(cutoffDate);
    }

    private int countEmployeesPaidOn(LocalDate cutoff) {
        return (int) timeLogs.stream()
                .filter(log -> isInPayPeriod(parseTimelogDate(log.getDate()), cutoff))
                .map(EmployeeTimeLogs::getEmployeeNumber)
                .distinct()
                .count();
    }

    private double calculateTotalPaymentOn(LocalDate cutoff) {
        double total = 0.0;

        String monthYear = cutoff.format(DateTimeFormatter.ofPattern("MM-yyyy"));
        int payPeriod = (cutoff.getDayOfMonth() == 15) ? 1 : 2;

        for (Employee emp : employees) {
            List<EmployeeTimeLogs> empLogs = timeLogs.stream()
                    .filter(log -> log.getEmployeeNumber().equals(emp.getEmployeeNumber()))
                    .filter(log -> isInPayPeriod(parseTimelogDate(log.getDate()), cutoff))
                    .toList();

            if (!empLogs.isEmpty()) {
                total += PayrollService.calculateNetPay(emp, empLogs, monthYear, payPeriod);
            }
        }
        return total;
    }

    private void openDisplayPayrollList(String payPeriod, String payDate) {
        DisplayPayruns displayPanel = new DisplayPayruns();

        displayPanel.setPayPeriod(payPeriod);
        displayPanel.setPayDay(payDate);
        displayPanel.setPayrollType("Semi-Monthly");

        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US);
        DateTimeFormatter rawFmt = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US);

        LocalDate cutoffDate = LocalDate.parse(payDate, displayFmt);

        int employeeCount = countEmployeesPaidOn(cutoffDate);
        displayPanel.setTotalEmployees(employeeCount);

        String cutoffRaw = cutoffDate.format(rawFmt);
        displayPanel.setTableData(cutoffRaw);

        MainFrame.showPanel(displayPanel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));
        searchButton = new javax.swing.JButton();
        searchBar = new javax.swing.JTextField();
        boarder1 = new javax.swing.JSeparator();
        lblSubTitle = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblPageInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        payrolList = new javax.swing.JTable();

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

        lblSubTitle.setFont(CustomFont.getExtendedSemiBold(20f));
        lblSubTitle.setText("Payroll List");

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

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        payrolList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        payrolList.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null }
                },
                new String[] {
                        "Pay Period", "Pay Date", "Employee", "Total Payment", "Status", "Actions"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
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
        payrolList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        payrolList.setAutoscrolls(false);
        jScrollPane1.setViewportView(payrolList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boarder1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1003,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSubTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 265,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPrev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPageInfo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext)
                                .addGap(23, 23, 23)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(45, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(searchBar, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchButton, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addComponent(boarder1, javax.swing.GroupLayout.PREFERRED_SIZE, 8,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblSubTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblPageInfo)
                                        .addComponent(btnNext)
                                        .addComponent(btnPrev))
                                .addContainerGap(165, Short.MAX_VALUE)));

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

    }// GEN-LAST:event_searchButtonActionPerformed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_searchBarActionPerformed

    private void searchBarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_searchBarKeyPressed

    }// GEN-LAST:event_searchBarKeyPressed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadPayRuns();
        }
    }// GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNextActionPerformed
        int totalPages = (int) Math.ceil((double) payDates.size() / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            loadPayRuns();
        }
    }// GEN-LAST:event_btnNextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator boarder1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JTable payrolList;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    // End of variables declaration//GEN-END:variables
}
