package motorph.ui;

import motorph.ui.panels.AddEmployee;
import motorph.model.core.Employee;
import motorph.ui.DisplayEmployeeInfo;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import motorph.ui.util.CustomFont;
import motorph.ui.util.CustomFont;
import motorph.ui.util.MyRender;
import motorph.service.EmployeeService;

public class EmployeePanel extends javax.swing.JPanel {

    // Pagination Variables and Employee List Setup
    private int currentPage = 1;
    private int rowsPerPage = 18; // Number of employees to display per page in the table
    private List<Employee> employees;

    public EmployeePanel(String name) {
        initComponents();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < employeeListTable.getColumnCount(); i++) {
            employeeListTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        employeeListTable.getColumnModel().getColumn(7).setCellRenderer(new MyRender());

        loadEmployeesToTable();

        employeeListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = employeeListTable.rowAtPoint(evt.getPoint());
                int col = employeeListTable.columnAtPoint(evt.getPoint());

                if (col == 7 && row >= 0) {
                    javax.swing.JFrame parentFrame = (javax.swing.JFrame) javax.swing.SwingUtilities
                            .getWindowAncestor(EmployeePanel.this);
                    String employeeNumber = employeeListTable.getValueAt(row, 0).toString();
                    DisplayEmployeeInfo displayDialog = new DisplayEmployeeInfo(parentFrame, employeeNumber,
                            EmployeePanel.this);

                    displayDialog.setVisible(true);
                }
            }
        });

    }

    /**
     * Loads all employee records from the data source and displays the first page
     * of results in the table.
     */
    public void loadEmployeesToTable() {
        employees = EmployeeService.getAllEmployees(); // Save the full list only once
        currentPage = 1; // Reset to page 1
        updateTablePage(); // Show first page
    }

    /**
     * Updates the table to show employees for the current page.
     * This handles pagination logic and updates the navigation buttons.
     */
    private void updateTablePage() {
        DefaultTableModel model = (DefaultTableModel) employeeListTable.getModel();
        model.setRowCount(0); // Clear the table to remove any previously displayed employee records before
                              // adding new ones.

        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, employees.size());

        for (int i = start; i < end; i++) {
            Employee emp = employees.get(i);

            String philhealth = formatNumberString(emp.getPhilhealthNumber());
            String pagibig = formatNumberString(emp.getPagIbigNumber());

            model.addRow(new Object[] {
                    emp.getEmployeeNumber(),
                    emp.getLastName(),
                    emp.getFirstName(),
                    emp.getSssNumber(),
                    philhealth,
                    emp.getTinNumber(),
                    pagibig,
                    "View"
            });
        }

        // Update page label and enable/disable buttons
        int totalPages = (int) Math.ceil((double) employees.size() / rowsPerPage);
        lblPageInfo.setText("Page " + currentPage + " of " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);

    }

    private String formatNumberString(String number) {
        try {
            // If it's in scientific form, convert it back to plain number
            double num = Double.parseDouble(number);
            java.text.DecimalFormat df = new java.text.DecimalFormat("0");
            df.setMaximumFractionDigits(0);
            df.setGroupingUsed(false);
            return df.format(num);
        } catch (NumberFormatException e) {
            // If not a number, return as-is
            return number;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));
        jScrollPane1 = new javax.swing.JScrollPane();
        employeeListTable = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        searchBar = new javax.swing.JTextField();
        boarder1 = new javax.swing.JSeparator();
        addEmpButton = new javax.swing.JButton();
        lblSubTitle = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblPageInfo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1054, 720));
        setMinimumSize(new java.awt.Dimension(1054, 720));
        setPreferredSize(new java.awt.Dimension(1054, 720));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        employeeListTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        employeeListTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null }
                },
                new String[] {
                        "Employee #", "Last Name", "First Name", "SSS #", "PhilHealth #", "TIN #", "Pag-IBIG #", ""
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        employeeListTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(employeeListTable);
        if (employeeListTable.getColumnModel().getColumnCount() > 0) {
            employeeListTable.getColumnModel().getColumn(0).setResizable(false);
            employeeListTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            employeeListTable.getColumnModel().getColumn(1).setResizable(false);
            employeeListTable.getColumnModel().getColumn(2).setResizable(false);
            employeeListTable.getColumnModel().getColumn(3).setResizable(false);
            employeeListTable.getColumnModel().getColumn(4).setResizable(false);
            employeeListTable.getColumnModel().getColumn(5).setResizable(false);
            employeeListTable.getColumnModel().getColumn(6).setResizable(false);
        }

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

        addEmpButton.setBackground(new java.awt.Color(95, 182, 199));
        addEmpButton.setForeground(new java.awt.Color(255, 255, 255));
        addEmpButton.setText("Add Employee");
        addEmpButton.setBorder(null);
        addEmpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpButtonActionPerformed(evt);
            }
        });

        lblSubTitle.setFont(CustomFont.getExtendedSemiBold(20f));
        lblSubTitle.setText("Active Employees");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boarder1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnPrev)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPageInfo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnNext))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1003,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                        .createSequentialGroup()
                                                        .addComponent(lblSubTitle,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 265,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(addEmpButton,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 114,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(searchButton,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(820, 820, 820))))
                                .addContainerGap(24, Short.MAX_VALUE)));
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
                                .addComponent(boarder1, javax.swing.GroupLayout.PREFERRED_SIZE, 8,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSubTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPrev)
                                        .addComponent(btnNext)
                                        .addComponent(lblPageInfo))
                                .addContainerGap(105, Short.MAX_VALUE)));

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

        // Check if the search field is empty before processing
        if (empNumberInput.isEmpty() || empNumberInput.equals("Search Employee by ID")) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
            loadEmployeesToTable();
            return;
        }

        // Make sure the input only has numbers (digits)
        if (!empNumberInput.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid Employee Number. Please enter digits only.");
            loadEmployeesToTable();
            return;
        }

        // Load employee data from the DataHandler
        if (employees == null || employees.isEmpty()) {
            employees = EmployeeService.getAllEmployees();
        }
        DefaultTableModel model = (DefaultTableModel) employeeListTable.getModel();
        model.setRowCount(0); // Clear the existing table rows before adding search results

        boolean found = false;

        // Loop through the employees and find the one that matches the entered employee
        // ID
        for (Employee emp : employees) {
            if (emp.getEmployeeNumber().equals(empNumberInput)) {

                model.addRow(new Object[] {
                        emp.getEmployeeNumber(),
                        emp.getLastName(),
                        emp.getFirstName(),
                        emp.getSssNumber(),
                        emp.getPhilhealthNumber(),
                        emp.getTinNumber(),
                        emp.getPagIbigNumber(),
                        "View"
                });
                found = true;
                break; // Stop searching once the matching employee ID is found and displayed.
            }
        }

        // If no employee is found with the given ID
        if (!found) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            loadEmployeesToTable();
        }
    }// GEN-LAST:event_searchButtonActionPerformed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_searchBarActionPerformed

    private void addEmpButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addEmpButtonActionPerformed
        JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        AddEmployee addEmpDialog = new AddEmployee(parentFrame);
        addEmpDialog.setVisible(true);

        loadEmployeesToTable();
    }// GEN-LAST:event_addEmpButtonActionPerformed

    private void searchBarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_searchBarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            searchButtonActionPerformed(null);
        }
    }// GEN-LAST:event_searchBarKeyPressed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            updateTablePage();
        }
    }// GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNextActionPerformed
        int totalPages = (int) Math.ceil((double) employees.size() / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            updateTablePage();
        }
    }// GEN-LAST:event_btnNextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEmpButton;
    private javax.swing.JSeparator boarder1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JTable employeeListTable;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    // End of variables declaration//GEN-END:variables
}
