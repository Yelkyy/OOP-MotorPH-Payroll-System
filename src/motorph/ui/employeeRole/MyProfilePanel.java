package motorph.ui.employeeRole;

import motorph.model.core.Employee;
import motorph.ui.util.CustomFont;

public class MyProfilePanel extends javax.swing.JPanel {

    public MyProfilePanel(Employee employee) {
        initComponents();
        displayEmployee(employee);
    }

    private void displayEmployee(Employee e) {
        if (e == null)
            return;

        empFullName.setText(e.getFullName());
        empNumVal.setText(e.getEmployeeNumber());

        firstNameVal.setText(e.getFirstName());
        lastNameVal.setText(e.getLastName());
        birthdayVal.setText(e.getBirthday());
        phoneNumVal.setText(e.getPhoneNumber());
        addressVal.setText(e.getAddress());

        empStatusVal.setText(e.getStatus());
        empPositionVal.setText(e.getPosition());
        empSupervisorVal.setText(e.getImmediateSupervisor());

        // optional: lock editing for viewing
        empNumVal.setEditable(false);
        firstNameVal.setEditable(false);
        lastNameVal.setEditable(false);
        birthdayVal.setEditable(false);
        phoneNumVal.setEditable(false);
        addressVal.setEditable(false);
        empStatusVal.setEditable(false);
        empPositionVal.setEditable(false);
        empSupervisorVal.setEditable(false);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        empNum = new javax.swing.JLabel();
        empNumVal = new javax.swing.JTextField();
        empFullName = new javax.swing.JLabel();
        lastName = new javax.swing.JLabel();
        firstName = new javax.swing.JLabel();
        birthday = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        phoneNum = new javax.swing.JLabel();
        phoneNumVal = new javax.swing.JTextField();
        birthdayVal = new javax.swing.JTextField();
        lastNameVal = new javax.swing.JTextField();
        addressVal = new javax.swing.JTextField();
        firstNameVal = new javax.swing.JTextField();
        empStatusVal = new javax.swing.JTextField();
        empSupervisorVal = new javax.swing.JTextField();
        empStatus = new javax.swing.JLabel();
        empPosition = new javax.swing.JLabel();
        empSupervisor = new javax.swing.JLabel();
        empPositionVal = new javax.swing.JTextField();
        editProfileButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1054, 720));
        setMinimumSize(new java.awt.Dimension(1054, 720));

        empNum.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        empNum.setText("Employee ID:");

        empNumVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empNumValActionPerformed(evt);
            }
        });

        empFullName.setFont(CustomFont.getExtendedSemiBold(20f));
        empFullName.setText("Full Name");

        lastName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lastName.setText("Last Name:");

        firstName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        firstName.setText("First Name:");

        birthday.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        birthday.setText("Birthday:");

        address.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        address.setText("Address:");

        phoneNum.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        phoneNum.setText("Phone Number:");

        phoneNumVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumValActionPerformed(evt);
            }
        });

        birthdayVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthdayValActionPerformed(evt);
            }
        });

        lastNameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameValActionPerformed(evt);
            }
        });

        addressVal.setColumns(2);
        addressVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressValActionPerformed(evt);
            }
        });

        firstNameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameValActionPerformed(evt);
            }
        });

        empStatusVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empStatusValActionPerformed(evt);
            }
        });

        empSupervisorVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empSupervisorValActionPerformed(evt);
            }
        });

        empStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        empStatus.setText("Status:");

        empPosition.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        empPosition.setText("Position:");

        empSupervisor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        empSupervisor.setText("Supervisor:");

        empPositionVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empPositionValActionPerformed(evt);
            }
        });

        editProfileButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        editProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        editProfileButton.setText("Edit My Profile");
        editProfileButton.setBorder(null);
        editProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(empFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 1014,
                                                        Short.MAX_VALUE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout
                                                                .createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(empStatus)
                                                                        .addComponent(empPosition)
                                                                        .addComponent(empSupervisor))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(empStatusVal)
                                                                        .addComponent(empPositionVal)
                                                                        .addComponent(empSupervisorVal)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(empNum)
                                                                .addGap(27, 27, 27)
                                                                .addComponent(empNumVal))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(firstName)
                                                                .addGap(40, 40, 40)
                                                                .addComponent(firstNameVal))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lastName)
                                                                .addGap(41, 41, 41)
                                                                .addComponent(lastNameVal))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(birthday)
                                                                .addGap(56, 56, 56)
                                                                .addComponent(birthdayVal))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(phoneNum)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(phoneNumVal))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(address)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        56, Short.MAX_VALUE)
                                                                .addComponent(addressVal,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 423,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(493, 493, 493))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(empFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(empNum)
                                        .addComponent(empNumVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(firstName)
                                        .addComponent(firstNameVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lastName)
                                        .addComponent(lastNameVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(birthday)
                                        .addComponent(birthdayVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(phoneNum)
                                        .addComponent(phoneNumVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(address)
                                        .addComponent(addressVal, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(empStatus)
                                        .addComponent(empStatusVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(empPosition)
                                        .addComponent(empPositionVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(empSupervisor)
                                        .addComponent(empSupervisorVal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65)
                                .addComponent(editProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(207, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void empNumValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empNumValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empNumValActionPerformed

    private void phoneNumValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_phoneNumValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_phoneNumValActionPerformed

    private void birthdayValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_birthdayValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_birthdayValActionPerformed

    private void lastNameValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_lastNameValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_lastNameValActionPerformed

    private void addressValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addressValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_addressValActionPerformed

    private void firstNameValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_firstNameValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_firstNameValActionPerformed

    private void empStatusValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empStatusValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empStatusValActionPerformed

    private void empSupervisorValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empSupervisorValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empSupervisorValActionPerformed

    private void empPositionValActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_empPositionValActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_empPositionValActionPerformed

    private void editProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editProfileButtonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_editProfileButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel address;
    private javax.swing.JTextField addressVal;
    private javax.swing.JLabel birthday;
    private javax.swing.JTextField birthdayVal;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JLabel empFullName;
    private javax.swing.JLabel empNum;
    private javax.swing.JTextField empNumVal;
    private javax.swing.JLabel empPosition;
    private javax.swing.JTextField empPositionVal;
    private javax.swing.JLabel empStatus;
    private javax.swing.JTextField empStatusVal;
    private javax.swing.JLabel empSupervisor;
    private javax.swing.JTextField empSupervisorVal;
    private javax.swing.JLabel firstName;
    private javax.swing.JTextField firstNameVal;
    private javax.swing.JLabel lastName;
    private javax.swing.JTextField lastNameVal;
    private javax.swing.JLabel phoneNum;
    private javax.swing.JTextField phoneNumVal;
    // End of variables declaration//GEN-END:variables
}
