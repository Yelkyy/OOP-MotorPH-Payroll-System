package motorph.ui;

import motorph.ui.components.CustomFont;
import motorph.model.EmployeeDetails;
import motorph.model.EmployeeTimeLogs;
import motorph.repository.DataHandler;
import motorph.service.PayrollService;
import motorph.ui.PayslipViewPanel;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import motorph.ui.components.MyRender;

public class DisplayPayruns extends javax.swing.JPanel {  
    private int currentPage = 1;
    private final int rowsPerPage = 15;
    private int totalPages = 1;
    private String currentPayDate;

    private static final DateTimeFormatter FULL_DATE_FMT =
            DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US).withResolverStyle(ResolverStyle.SMART);

    private static final DateTimeFormatter TIMELOG_FMT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT);

    private static LocalDate parsePayDate(String payDate) {
        String v = payDate == null ? "" : payDate.trim();
        try {
            return LocalDate.parse(v, FULL_DATE_FMT);
        } catch (Exception e) {
            return LocalDate.parse(v, TIMELOG_FMT);
        }
    }

    private static LocalDate parseTimelogDate(String raw) {
        return LocalDate.parse(raw == null ? "" : raw.trim(), TIMELOG_FMT);
    }

    public DisplayPayruns() {
        initComponents();
        applyCustomFontAndSizes();

        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < empListTable.getColumnCount(); i++) {
            empListTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        empListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = empListTable.rowAtPoint(evt.getPoint());
                int col = empListTable.columnAtPoint(evt.getPoint());

                if (col == 4 && row >= 0) {
                    String empId = empListTable.getValueAt(row, 0).toString();

                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(DisplayPayruns.this);

                    PayslipViewPanel panel = new PayslipViewPanel();
                    
                    
                    panel.setPayDate(currentPayDate); // for payrollDate
                    panel.setEmployeeId(empId);
                    panel.setPayPeriod(displayPayPeriod.getText());

                    // Show panel inside a modal dialog
                    JDialog dialog = new JDialog(parentFrame, "Payslip", true);
                    dialog.setContentPane(panel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(parentFrame);
                    dialog.setVisible(true);

            }
            }
        });

    }
    
    private void applyCustomFontAndSizes() {
        title.setFont(CustomFont.getExtendedSemiBold(14f));
        totalEmp.setFont(CustomFont.getExtendedSemiBold(10f));
        payPeriod.setFont(CustomFont.getExtendedSemiBold(10f));
        payDay.setFont(CustomFont.getExtendedSemiBold(10f));
        payrollType.setFont(CustomFont.getExtendedSemiBold(10f));
        displayPayPeriod.setFont(CustomFont.getExtendedRegular(12f));
        displayDate.setFont(CustomFont.getExtendedRegular(12f));
        displayPayrollType.setFont(CustomFont.getExtendedRegular(12f));
        noOfEmp.setFont(CustomFont.getExtendedRegular(12f));    
    }
    
    public void setPayPeriod(String period) {
        displayPayPeriod.setText(period);
    }

    public void setPayDay(String payDay) {
        displayDate.setText(payDay);
    }

    public void setTotalEmployees(int count) {
        noOfEmp.setText(String.valueOf(count));
    }

    public void setPayrollType(String type) {
        displayPayrollType.setText(type);
    }

    public void setTableData(String payDate) {
        this.currentPayDate = payDate;
        
        List<EmployeeDetails> employees = DataHandler.readEmployeeDetails();
        List<EmployeeTimeLogs> timeLogs = DataHandler.readEmployeeTimeLogs();

        DefaultTableModel model = (DefaultTableModel) empListTable.getModel();
        model.setRowCount(0);

        int totalRecords = employees.size();
        totalPages = (int) Math.ceil((double) totalRecords / rowsPerPage);
        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, totalRecords);

        LocalDate cutoffDate = parsePayDate(payDate);
        int payPeriod = (cutoffDate.getDayOfMonth() <= 15) ? 1 : 2;
        String monthYear = cutoffDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));

        for (int i = startIndex; i < endIndex; i++) {
            EmployeeDetails emp = employees.get(i);
            
            List<EmployeeTimeLogs> empLogs = timeLogs.stream()
                .filter(log -> log.getEmployeeNumber().equals(emp.getEmployeeNumber()))
                .filter(log -> {
                    LocalDate logDate = parseTimelogDate(log.getDate());
                    return isInPayPeriod(logDate, cutoffDate);
                })
                .toList();

            double netPay = PayrollService.calculateNetPay(emp, empLogs, monthYear, payPeriod);

            
            String paymentStr = String.format("₱%,.2f", netPay);

            model.addRow(new Object[]{
                emp.getEmployeeId(),
                emp.getFullName(),
                paymentStr,
                emp.getPosition(),
                "View Payslip"
            });
        }  

        lblPageInfo.setText("Page " + currentPage + " of " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
        
        empListTable.getColumnModel().getColumn(4).setCellRenderer(new MyRender());
    }

    
    private int extractPayPeriodFromDate(String payDate) {
        try {
            LocalDate d = parsePayDate(payDate);
            return (d.getDayOfMonth() <= 15) ? 1 : 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 1; // fallback to 1
        }
    }
    
    private boolean isInPayPeriod(LocalDate logDate, LocalDate cutoffDate) {
        LocalDate startDate;
        if (cutoffDate.getDayOfMonth() == 15) {
            startDate = cutoffDate.withDayOfMonth(1);
        } else {
            startDate = cutoffDate.withDayOfMonth(16);
        }
        return !logDate.isBefore(startDate) && !logDate.isAfter(cutoffDate);
    }
    
    
    
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPageInfo = new javax.swing.JLabel();
        boarder1 = new javax.swing.JSeparator();
        title = new javax.swing.JLabel();
        payrollRepBox = new javax.swing.JPanel();
        totalEmp = new javax.swing.JLabel();
        displayPayPeriod = new javax.swing.JLabel();
        payPeriod = new javax.swing.JLabel();
        noOfEmp = new javax.swing.JLabel();
        payDay = new javax.swing.JLabel();
        payrollType = new javax.swing.JLabel();
        displayDate = new javax.swing.JLabel();
        displayPayrollType = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        empListTable = new javax.swing.JTable();
        searchBar = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        backIcon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1070, 720));
        setMinimumSize(new java.awt.Dimension(1054, 720));

        lblPageInfo.setText("Page 1 of 1");

        boarder1.setBackground(new java.awt.Color(0, 66, 102));
        boarder1.setForeground(new java.awt.Color(0, 66, 102));

        title.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        title.setText("Payroll Summary Report");

        payrollRepBox.setBackground(new java.awt.Color(0, 66, 102));
        payrollRepBox.setForeground(new java.awt.Color(255, 255, 255));
        payrollRepBox.setMaximumSize(new java.awt.Dimension(995, 127));
        payrollRepBox.setMinimumSize(new java.awt.Dimension(911, 127));
        payrollRepBox.setName(""); // NOI18N

        totalEmp.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        totalEmp.setForeground(new java.awt.Color(255, 255, 255));
        totalEmp.setText("Total Employee");

        displayPayPeriod.setForeground(new java.awt.Color(255, 255, 255));
        displayPayPeriod.setText("April 1st - 15th, 2025");

        payPeriod.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        payPeriod.setForeground(new java.awt.Color(255, 255, 255));
        payPeriod.setText("Pay Period");

        noOfEmp.setForeground(new java.awt.Color(255, 255, 255));
        noOfEmp.setText("34");

        payDay.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        payDay.setForeground(new java.awt.Color(255, 255, 255));
        payDay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        payDay.setText("Pay Day");

        payrollType.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        payrollType.setForeground(new java.awt.Color(255, 255, 255));
        payrollType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        payrollType.setText("Payroll Type");

        displayDate.setForeground(new java.awt.Color(255, 255, 255));
        displayDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        displayDate.setText("April 15, 2025");

        displayPayrollType.setForeground(new java.awt.Color(255, 255, 255));
        displayPayrollType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        displayPayrollType.setText("Regular");

        javax.swing.GroupLayout payrollRepBoxLayout = new javax.swing.GroupLayout(payrollRepBox);
        payrollRepBox.setLayout(payrollRepBoxLayout);
        payrollRepBoxLayout.setHorizontalGroup(
            payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payrollRepBoxLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(payPeriod)
                    .addComponent(totalEmp)
                    .addComponent(noOfEmp)
                    .addComponent(displayPayPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 367, Short.MAX_VALUE)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(payDay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(payrollType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(displayPayrollType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(displayDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        payrollRepBoxLayout.setVerticalGroup(
            payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payrollRepBoxLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payPeriod)
                    .addComponent(payDay))
                .addGap(3, 3, 3)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayPayPeriod)
                    .addComponent(displayDate))
                .addGap(22, 22, 22)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalEmp)
                    .addComponent(payrollType))
                .addGap(3, 3, 3)
                .addGroup(payrollRepBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noOfEmp)
                    .addComponent(displayPayrollType))
                .addContainerGap(24, Short.MAX_VALUE))
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

        empListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Employee #", "Employee Name", "Payment Amount", "Job Title", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(empListTable);

        searchBar.setBackground(new java.awt.Color(238, 238, 238));
        searchBar.setForeground(new java.awt.Color(153, 153, 153));
        searchBar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchBar.setText("Search Employee by ID");
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });

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

        backIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/motorph/icons/arrow-long-left.png"))); // NOI18N
        backIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boarder1, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPageInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addGap(83, 83, 83))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(payrollRepBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backIcon)
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(searchBar))
                    .addComponent(backIcon))
                .addGap(18, 18, 18)
                .addComponent(boarder1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payrollRepBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPageInfo)
                    .addComponent(btnPrev)
                    .addComponent(btnNext))
                .addGap(81, 81, 81))
        );

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
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            setTableData(currentPayDate);
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (currentPage < totalPages) {
            currentPage++;
            setTableData(currentPayDate); 
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        searchButtonActionPerformed(null);
    }//GEN-LAST:event_searchBarActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String empNumberInput = searchBar.getText().trim();

        if (empNumberInput.isEmpty() || empNumberInput.equals("Search Employee by ID")) {
            // Reset to page 1 if empty search
            currentPage = 1;
            setTableData(currentPayDate);
            return;
        } 

        if (!empNumberInput.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid Employee Number. Use digits only.");
            return;
        }

        // Find index in full employee list
        List<EmployeeDetails> employees = DataHandler.readEmployeeDetails();
        int index = -1;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeNumber().equals(empNumberInput)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            return;
        }

        // Set page and reload
        currentPage = (index / rowsPerPage) + 1;
        setTableData(currentPayDate);

        // Select row in table
        int rowIndexOnPage = index % rowsPerPage;
        empListTable.setRowSelectionInterval(rowIndexOnPage, rowIndexOnPage);
        empListTable.scrollRectToVisible(empListTable.getCellRect(rowIndexOnPage, 0, true));
    }//GEN-LAST:event_searchButtonActionPerformed

    private void backIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backIconMouseClicked
        Dashboard.showPanel(new PayrollVer2());
    }//GEN-LAST:event_backIconMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backIcon;
    private javax.swing.JSeparator boarder1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JLabel displayDate;
    private javax.swing.JLabel displayPayPeriod;
    private javax.swing.JLabel displayPayrollType;
    private javax.swing.JTable empListTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JLabel noOfEmp;
    private javax.swing.JLabel payDay;
    private javax.swing.JLabel payPeriod;
    private javax.swing.JPanel payrollRepBox;
    private javax.swing.JLabel payrollType;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel title;
    private javax.swing.JLabel totalEmp;
    // End of variables declaration//GEN-END:variables
}
