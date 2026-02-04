package motorph.ui.panels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import motorph.model.EmployeeDetails;
import motorph.repository.DataHandler;

public class AddEmployee extends javax.swing.JDialog {

    List<JTextField> newEmployeeAddField;

    public AddEmployee(JFrame parent) {
        super(parent, "New Employee Information", true);

        initComponents();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setupBirthdayField();
        loadSupervisorsDropdown();

        newEmployeeAddField = Arrays.asList(
                empNum);

        for (JTextField field : newEmployeeAddField) {
            field.setEditable(false);
            field.setBackground(new java.awt.Color(240, 240, 240));
            field.setFocusable(false);
        }

        pack();
    }

    private void setupBirthdayField() {
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            birthday.setFormatterFactory(new DefaultFormatterFactory(dateMask));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void loadSupervisorsDropdown() {
        List<EmployeeDetails> allEmployees = DataHandler.readEmployeeDetails();
        javax.swing.DefaultComboBoxModel<String> model = new javax.swing.DefaultComboBoxModel<>();
        model.addElement("Select Supervisor");

        for (EmployeeDetails emp : allEmployees) {
            String fullName = emp.getLastName() + ", " + emp.getFirstName();
            model.addElement(fullName);
        }

        empSupervisor.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        empNum = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lastName = new javax.swing.JTextField();
        firstName = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        phoneNum = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
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
        addEmpButton = new javax.swing.JButton();
        empStatus = new javax.swing.JComboBox<>();
        empPosition = new javax.swing.JComboBox<>();
        empSupervisor = new javax.swing.JComboBox<>();
        birthday = new javax.swing.JFormattedTextField();
        sssNum = new javax.swing.JFormattedTextField();

        setTitle("Add New Employee");
        setMinimumSize(new java.awt.Dimension(993, 564));
        setName("displayEmpFrame"); // NOI18N
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1054, 517));
        jPanel1.setPreferredSize(new java.awt.Dimension(1054, 564));

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

        address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressActionPerformed(evt);
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

        addEmpButton.setBackground(new java.awt.Color(95, 182, 199));
        addEmpButton.setForeground(new java.awt.Color(255, 255, 255));
        addEmpButton.setText("Add Employee");
        addEmpButton.setBorder(null);
        addEmpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpButtonActionPerformed(evt);
            }
        });

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

        birthday.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("MM/dd/yyyy"))));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(empNum, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(empStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(empPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(empSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(phoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(empPhoneAllwc, javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(563, 563, 563)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(empClothingAllwc, javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(tinNum, javax.swing.GroupLayout.PREFERRED_SIZE, 197,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(birthday, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(73, 73, 73)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(empRiceSubsidy, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(empBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        223, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(704, 704, 704)
                                                .addComponent(addEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(pagIbigNum, javax.swing.GroupLayout.PREFERRED_SIZE, 197,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sssNum, javax.swing.GroupLayout.PREFERRED_SIZE, 197,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(philHealthNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(217, 217, 217)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(empHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(empSemiMonthlyRate,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(empNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(empStatus, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel13))))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(empPosition, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel14))))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(empSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel15))))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(empBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jLabel6)
                                                                .addComponent(birthday,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jLabel16))))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(empRiceSubsidy, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel17))))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(phoneNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(empPhoneAllwc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel18))))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jLabel19))
                                        .addComponent(empClothingAllwc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(sssNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jLabel21))
                                        .addComponent(empSemiMonthlyRate, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(jLabel10))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addComponent(philHealthNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jLabel20))
                                        .addComponent(empHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jLabel11))
                                        .addComponent(tinNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jLabel12))
                                        .addComponent(pagIbigNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addComponent(addEmpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 997, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
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

    private void addressActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addressActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_addressActionPerformed

    private void phoneNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_phoneNumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_phoneNumActionPerformed

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

    private void addEmpButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addEmpButtonActionPerformed
        try {
            String newEmployeeNumber = DataHandler.generateNewEmpId();

            String birthdayStr = birthday.getText(); // get as String
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date birthdayDate = null;
            try {
                birthdayDate = sdf.parse(birthdayStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid birthday format. Please use MM/dd/yyyy.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return; // stop adding employee if invalid date
            }

            // Format birthday to string in MM/dd/yyyy format
            String formattedBirthday = sdf.format(birthdayDate);

            EmployeeDetails newEmployee = new EmployeeDetails(
                    newEmployeeNumber,
                    lastName.getText(),
                    firstName.getText(),
                    formattedBirthday, // Use the formatted string here
                    address.getText(),
                    phoneNum.getText(),
                    sssNum.getText(),
                    philHealthNum.getText(),
                    tinNum.getText(),
                    pagIbigNum.getText(),
                    (String) empStatus.getSelectedItem(), // status dropdown
                    (String) empPosition.getSelectedItem(), // position dropdown
                    (String) empSupervisor.getSelectedItem(), // supervisor dropdown
                    Double.parseDouble(empBasicSalary.getText()),
                    Double.parseDouble(empRiceSubsidy.getText()),
                    Double.parseDouble(empPhoneAllwc.getText()),
                    Double.parseDouble(empClothingAllwc.getText()),
                    Double.parseDouble(empSemiMonthlyRate.getText()),
                    Double.parseDouble(empHourlyRate.getText()));

            DataHandler.addEmployeeToCSV(newEmployee);

            JOptionPane.showMessageDialog(this, "Employee added with ID: " + newEmployeeNumber);

            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for salary and allowances.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }// GEN-LAST:event_addEmpButtonActionPerformed

    private void empStatusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empStatusActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empStatusActionPerformed

    private void empPositionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empPositionActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empPositionActionPerformed

    private void empSupervisorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empSupervisorActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empSupervisorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEmpButton;
    private javax.swing.JTextField address;
    private javax.swing.JFormattedTextField birthday;
    private javax.swing.JTextField empBasicSalary;
    private javax.swing.JTextField empClothingAllwc;
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
    private javax.swing.JFormattedTextField sssNum;
    private javax.swing.JTextField tinNum;
    // End of variables declaration//GEN-END:variables
}
