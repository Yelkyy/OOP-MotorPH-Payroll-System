package motorph.ui;

import motorph.model.EmployeeDetails;
import motorph.service.EmployeeService;
import motorph.ui.components.CustomFont;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class DisplayEmployeeInfo extends javax.swing.JDialog {

    List<JTextComponent> empInfoFieldList;
    private final EmployeePanel employeeListRefresher;

    public DisplayEmployeeInfo(JFrame parent, String employeeNumber, EmployeePanel employeeListRefresher) {
        super(parent, "Employee Information", true);
        initComponents();

        this.employeeListRefresher = employeeListRefresher;
        prepareFormUI();
        loadSupervisorsDropdown(employeeNumber);
        loadEmployeeDetails(employeeNumber);
    }

    private void prepareFormUI() {
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        saveEmpButton.setVisible(false);
        cancelEmpButton.setVisible(false);
        applyCustomFont();

        empInfoFieldList = Arrays.asList(
                empNum, firstName, lastName, birthday, address2, phoneNum,
                sssNum, philHealthNum, tinNum, pagIbigNum,
                empBasicSalary, empRiceSubsidy, empPhoneAllwc,
                empClothingAllwc, empSemiMonthlyRate, empHourlyRate

        );

        for (JTextComponent field : empInfoFieldList) {
            boolean isEmpNum = field == empNum;
            field.setEditable(false);
            field.setFocusable(false);
            field.setBackground(new java.awt.Color(240, 240, 240));
            field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }

        empStatus.setEnabled(false);
        empPosition.setEnabled(false);
        empSupervisor.setEnabled(false);
    }

    private void loadEmployeeDetails(String employeeNumber) {
        EmployeeDetails emp = EmployeeService.getEmployeeById(employeeNumber);

        if (emp == null)
            return;
        // Personal Info
        empNum.setText(emp.getEmployeeNumber());
        firstName.setText(emp.getFirstName());
        lastName.setText(emp.getLastName());

        birthday.setText(formatDate(emp.getBirthday()));
        address2.setText(emp.getAddress());
        phoneNum.setText(emp.getPhoneNumber());
        sssNum.setText(emp.getSssNumber());
        philHealthNum.setText(emp.getPhilhealthNumber());
        tinNum.setText(emp.getTinNumber());
        pagIbigNum.setText(emp.getPagIbigNumber());
        // Job Info
        empStatus.setSelectedItem(emp.getStatus());
        empPosition.setSelectedItem(emp.getPosition());
        empSupervisor.setSelectedItem(emp.getImmediateSupervisor());
        // Salary Info
        empBasicSalary.setText(String.format("%.2f", emp.getBasicSalary()));
        empRiceSubsidy.setText(String.format("%.2f", emp.getRiceSubsidy()));
        empPhoneAllwc.setText(String.format("%.2f", emp.getPhoneAllowance()));
        empClothingAllwc.setText(String.format("%.2f", emp.getClothingAllowance()));
        empSemiMonthlyRate.setText(String.format("%.2f", emp.getGrossSemiMonthlyRate()));
        empHourlyRate.setText(String.format("%.2f", emp.getHourlyRate()));

        empFullName.setText(emp.getFullName());
    }

    private String formatDate(String rawBirthday) {
        try {
            Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(rawBirthday);
            return new SimpleDateFormat("MM/dd/yyyy").format(parsedDate);
        } catch (Exception e) {
            return rawBirthday;
        }
    }

    private String parseFormattedNumber(String value, DecimalFormat df) {
        try {
            return df.format(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return value;
        }
    }

    private void applyCustomFont() {
        empFullName.setFont(CustomFont.getExtendedSemiBold(14f));
    }

    private void setEditMode(boolean isEditing) {
        for (JTextComponent field : empInfoFieldList) {
            boolean isEmpNum = field == empNum;
            field.setEditable(isEditing && !isEmpNum);
            field.setFocusable(isEditing && !isEmpNum);
            field.setCursor(
                    new java.awt.Cursor(isEditing ? java.awt.Cursor.TEXT_CURSOR : java.awt.Cursor.DEFAULT_CURSOR));
            field.setBackground(isEmpNum ? new java.awt.Color(240, 240, 240)
                    : (isEditing ? Color.WHITE : new java.awt.Color(240, 240, 240)));
        }

        empStatus.setEnabled(isEditing);
        empPosition.setEnabled(isEditing);
        empSupervisor.setEnabled(isEditing);

        saveEmpButton.setVisible(isEditing);
        cancelEmpButton.setVisible(isEditing);
        editEmpButton.setVisible(!isEditing);
        deleteEmpButton.setVisible(!isEditing);
    }

    private void loadSupervisorsDropdown(String currentEmpNumber) {
        List<EmployeeDetails> allEmployees = EmployeeService.getAllEmployees();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Select Supervisor");

        String currentSupervisor = null;

        // Grab current supervisor first (for restoring selected item)
        for (EmployeeDetails emp : allEmployees) {
            if (emp.getEmployeeNumber().equals(currentEmpNumber)) {
                currentSupervisor = emp.getImmediateSupervisor();
                break;
            }
        }

        // Add all other employees to dropdown
        for (EmployeeDetails emp : allEmployees) {
            if (!emp.getEmployeeNumber().equals(currentEmpNumber)) {
                String fullName = emp.getLastName() + ", " + emp.getFirstName();
                model.addElement(fullName);
            }
        }

        // 🛠️ If current supervisor is not yet in dropdown (e.g. no longer in list),
        // add it
        if (currentSupervisor != null &&
                !modelContains(model, currentSupervisor)) {
            model.addElement(currentSupervisor);
        }

        empSupervisor.setModel(model);
    }

    private boolean modelContains(DefaultComboBoxModel<String> model, String item) {
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).equals(item))
                return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cancelEmpButton = new javax.swing.JButton();
        saveEmpButton = new javax.swing.JButton();
        empFullName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        empNum = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lastName = new javax.swing.JTextField();
        firstName = new javax.swing.JTextField();
        phoneNum = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        sssNum = new javax.swing.JTextField();
        philHealthNum = new javax.swing.JTextField();
        tinNum = new javax.swing.JTextField();
        pagIbigNum = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        empBasicSalary = new javax.swing.JTextField();
        empRiceSubsidy = new javax.swing.JTextField();
        empPhoneAllwc = new javax.swing.JTextField();
        empClothingAllwc = new javax.swing.JTextField();
        empSemiMonthlyRate = new javax.swing.JTextField();
        empHourlyRate = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        deleteEmpButton = new javax.swing.JButton();
        editEmpButton = new javax.swing.JButton();
        birthday = new javax.swing.JFormattedTextField();
        empStatus = new javax.swing.JComboBox<>();
        empPosition = new javax.swing.JComboBox<>();
        empSupervisor = new javax.swing.JComboBox<>();
        address1 = new javax.swing.JScrollPane();
        address2 = new javax.swing.JTextArea();

        setTitle("Employee Information");
        setMinimumSize(new java.awt.Dimension(993, 720));
        setName("displayEmpFrame"); // NOI18N
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1054, 720));
        jPanel1.setMinimumSize(new java.awt.Dimension(1054, 720));
        jPanel1.setPreferredSize(new java.awt.Dimension(1054, 720));

        cancelEmpButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Grey"));
        cancelEmpButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelEmpButton.setText("Cancel");
        cancelEmpButton.setBorder(null);
        cancelEmpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEmpButtonActionPerformed(evt);
            }
        });

        saveEmpButton.setBackground(new java.awt.Color(0, 153, 0));
        saveEmpButton.setForeground(new java.awt.Color(255, 255, 255));
        saveEmpButton.setText("Save");
        saveEmpButton.setBorder(null);
        saveEmpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveEmpButtonActionPerformed(evt);
            }
        });

        empFullName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        empFullName.setText("Fist Name Last Name");

        jLabel3.setText("Employee Number:");

        empNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empNumActionPerformed(evt);
            }
        });

        jLabel4.setText("First Name:");

        jLabel5.setText("Last Name:");

        jLabel6.setText("Date of Birth:");

        jLabel7.setText("Address:");

        jLabel8.setText("Phone Number:");

        lastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameActionPerformed(evt);
            }
        });

        firstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameActionPerformed(evt);
            }
        });

        phoneNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumActionPerformed(evt);
            }
        });

        jLabel9.setText("SSS:");

        jLabel10.setText("PhilHealth:");

        jLabel11.setText("TIN:");

        jLabel12.setText("Pag-IBIG:");

        sssNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sssNumActionPerformed(evt);
            }
        });

        philHealthNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                philHealthNumActionPerformed(evt);
            }
        });

        tinNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tinNumActionPerformed(evt);
            }
        });

        pagIbigNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagIbigNumActionPerformed(evt);
            }
        });

        jLabel13.setText("Status:");

        jLabel14.setText("Position:");

        jLabel15.setText("Supervisor:");

        jLabel16.setText("Basic Sallary:");

        jLabel17.setText("Rice Subsidy:");

        jLabel18.setText("Phone Allowance:");

        jLabel19.setText("Clothing Allowance:");

        jLabel20.setText("Hourly Rate:");

        empBasicSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empBasicSalaryActionPerformed(evt);
            }
        });

        empRiceSubsidy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empRiceSubsidyActionPerformed(evt);
            }
        });

        empPhoneAllwc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empPhoneAllwcActionPerformed(evt);
            }
        });

        empClothingAllwc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empClothingAllwcActionPerformed(evt);
            }
        });

        empSemiMonthlyRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empSemiMonthlyRateActionPerformed(evt);
            }
        });

        empHourlyRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empHourlyRateActionPerformed(evt);
            }
        });

        jLabel21.setText("Semi-Monthly Rate:");

        deleteEmpButton.setBackground(new java.awt.Color(153, 0, 0));
        deleteEmpButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteEmpButton.setText("Delete Employee");
        deleteEmpButton.setBorder(null);
        deleteEmpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmpButtonActionPerformed(evt);
            }
        });

        editEmpButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        editEmpButton.setForeground(new java.awt.Color(255, 255, 255));
        editEmpButton.setText("Edit Employee");
        editEmpButton.setBorder(null);
        editEmpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editEmpButtonActionPerformed(evt);
            }
        });

        birthday.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("MM/dd/yyyy"))));

        empStatus.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Status", "Probationary", "Regular" }));
        empStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empStatusActionPerformed(evt);
            }
        });

        empPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Position",
                "Chief Executive Officer", "Chief Finance Officer", "IT Operations and Systems", "HR Manager",
                "HR Rank and File", "Accounting Head", "Payroll Manager", "Payroll Team Leader",
                "Payroll Rank and File", "Account Manager", "Account Team Leader", "Account Rank and File",
                "Sales & Marketing", "Supply Chain and Logistics", "Customer Service and Relations" }));
        empPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empPositionActionPerformed(evt);
            }
        });

        empSupervisor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Supervisor" }));
        empSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empSupervisorActionPerformed(evt);
            }
        });

        address1.setBackground(new java.awt.Color(255, 255, 255));
        address1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        address1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        address2.setEditable(false);
        address2.setBackground(new java.awt.Color(255, 255, 255));
        address2.setColumns(2);
        address2.setLineWrap(true);
        address2.setRows(5);
        address2.setWrapStyleWord(true);
        address1.setViewportView(address2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel7,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(address1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                340,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel6,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(birthday,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                341,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel5,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lastName))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(firstName))
                                                                .addComponent(empFullName,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 268,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(empNum)))
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel9,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(sssNum))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel10,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(philHealthNum))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel11,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(tinNum))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel12,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(pagIbigNum,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                197,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel8,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(phoneNum,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(73, 73, 73)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel15,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empSupervisor, 0,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel14,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empPosition, 0,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel13,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empStatus, 0,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel19,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empClothingAllwc))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel16,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empBasicSalary))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel17,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empRiceSubsidy))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel18,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empPhoneAllwc))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel21,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel20,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                110,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(empHourlyRate)
                                                                        .addComponent(empSemiMonthlyRate)))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cancelEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(saveEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(67, 67, 67)))
                                .addGap(95, 95, 95))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(empFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(empNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13)
                                        .addComponent(empStatus, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14)
                                        .addComponent(empPosition, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15)
                                        .addComponent(empSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel16)
                                        .addComponent(empBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(birthday, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel17)
                                                        .addComponent(empRiceSubsidy,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel18)
                                                        .addComponent(empPhoneAllwc,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel19)
                                                        .addComponent(empClothingAllwc,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(address1, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(phoneNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel8))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(sssNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel10)
                                                        .addComponent(philHealthNum,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel11)
                                                        .addComponent(tinNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel12)
                                                        .addComponent(pagIbigNum,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(empSemiMonthlyRate,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel21))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(empHourlyRate,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel20))))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(deleteEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cancelEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 993, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    private void empNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empNumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empNumActionPerformed

    private void lastNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_lastNameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_lastNameActionPerformed

    private void firstNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_firstNameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_firstNameActionPerformed

    private void phoneNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_phoneNumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_phoneNumActionPerformed

    private void sssNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sssNumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_sssNumActionPerformed

    private void philHealthNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_philHealthNumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_philHealthNumActionPerformed

    private void tinNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tinNumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_tinNumActionPerformed

    private void pagIbigNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_pagIbigNumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_pagIbigNumActionPerformed

    private void empBasicSalaryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empBasicSalaryActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empBasicSalaryActionPerformed

    private void empRiceSubsidyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empRiceSubsidyActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empRiceSubsidyActionPerformed

    private void empPhoneAllwcActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empPhoneAllwcActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empPhoneAllwcActionPerformed

    private void empClothingAllwcActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empClothingAllwcActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empClothingAllwcActionPerformed

    private void empSemiMonthlyRateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empSemiMonthlyRateActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empSemiMonthlyRateActionPerformed

    private void empHourlyRateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empHourlyRateActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empHourlyRateActionPerformed

    private void deleteEmpButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteEmpButtonActionPerformed
        String employeeNumber = empNum.getText().trim();

        if (employeeNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee Number is missing.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EmployeeDetails emp = EmployeeService.getEmployeeById(employeeNumber);
        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Employee #" + employeeNumber + " not found.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete Employee #" + employeeNumber + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                EmployeeService employeeService = new EmployeeService();
                employeeService.deleteEmployee(employeeNumber);

                employeeListRefresher.loadEmployeesToTable();
                JOptionPane.showMessageDialog(this,
                        "Employee #" + employeeNumber + " deleted successfully.",
                        "Deleted",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Delete Not Allowed",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }// GEN-LAST:event_deleteEmpButtonActionPerformed

    private void editEmpButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editEmpButtonActionPerformed
        setEditMode(true);
    }// GEN-LAST:event_editEmpButtonActionPerformed

    private void empStatusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empStatusActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empStatusActionPerformed

    private void empPositionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empPositionActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empPositionActionPerformed

    private void empSupervisorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empSupervisorActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empSupervisorActionPerformed

    private void saveEmpButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveEmpButtonActionPerformed
        try {
            EmployeeDetails updated = new EmployeeDetails(
                    empNum.getText().trim(),
                    lastName.getText().trim(),
                    firstName.getText().trim(),
                    birthday.getText().trim(),
                    address2.getText().trim(),
                    phoneNum.getText().trim(),
                    sssNum.getText().trim(),
                    philHealthNum.getText().trim(),
                    tinNum.getText().trim(),
                    pagIbigNum.getText().trim(),
                    empStatus.getSelectedItem().toString(),
                    empPosition.getSelectedItem().toString(),
                    empSupervisor.getSelectedItem().toString(),
                    Double.parseDouble(empBasicSalary.getText().trim()),
                    Double.parseDouble(empRiceSubsidy.getText().trim()),
                    Double.parseDouble(empPhoneAllwc.getText().trim()),
                    Double.parseDouble(empClothingAllwc.getText().trim()),
                    Double.parseDouble(empSemiMonthlyRate.getText().trim()),
                    Double.parseDouble(empHourlyRate.getText().trim()));

            EmployeeService employeeService = new EmployeeService();
            employeeService.updateEmployee(updated);

            JOptionPane.showMessageDialog(this,
                    "Employee information saved successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            if (employeeListRefresher != null) {
                employeeListRefresher.loadEmployeesToTable();
            }

            this.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for salary fields.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "An error occurred while saving the employee information:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }// GEN-LAST:event_saveEmpButtonActionPerformed

    private void cancelEmpButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelEmpButtonActionPerformed
        setEditMode(false);
        loadEmployeeDetails(empNum.getText().trim());
    }// GEN-LAST:event_cancelEmpButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane address1;
    private javax.swing.JTextArea address2;
    private javax.swing.JFormattedTextField birthday;
    private javax.swing.JButton cancelEmpButton;
    private javax.swing.JButton deleteEmpButton;
    private javax.swing.JButton editEmpButton;
    private javax.swing.JTextField empBasicSalary;
    private javax.swing.JTextField empClothingAllwc;
    private javax.swing.JLabel empFullName;
    private javax.swing.JTextField empHourlyRate;
    private javax.swing.JTextField empNum;
    private javax.swing.JTextField empPhoneAllwc;
    private javax.swing.JComboBox<String> empPosition;
    private javax.swing.JTextField empRiceSubsidy;
    private javax.swing.JTextField empSemiMonthlyRate;
    private javax.swing.JComboBox<String> empStatus;
    private javax.swing.JComboBox<String> empSupervisor;
    private javax.swing.JTextField firstName;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lastName;
    private javax.swing.JTextField pagIbigNum;
    private javax.swing.JTextField philHealthNum;
    private javax.swing.JTextField phoneNum;
    private javax.swing.JButton saveEmpButton;
    private javax.swing.JTextField sssNum;
    private javax.swing.JTextField tinNum;
    // End of variables declaration//GEN-END:variables
}
