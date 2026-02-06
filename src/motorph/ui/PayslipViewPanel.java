package motorph.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import javax.swing.JPanel;
import motorph.model.core.Employee;
import motorph.model.payroll.PayrollCalculator.PayrollResult;
import motorph.model.EmployeeTimeLogs;
import motorph.service.PayrollService;
import motorph.service.EmployeeService;

public class PayslipViewPanel extends JPanel {

        private String employeeId;
        private String payDate;

        private static final DateTimeFormatter PAY_DATE_LONG = DateTimeFormatter.ofPattern("MMMM d, uuuu", Locale.US)
                        .withResolverStyle(ResolverStyle.SMART);

        private static final DateTimeFormatter PAY_DATE_SHORT = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US)
                        .withResolverStyle(ResolverStyle.STRICT);

        private static LocalDate parsePayDate(String raw) {
                String v = raw == null ? "" : raw.trim();
                try {
                        return LocalDate.parse(v, PAY_DATE_LONG);
                } catch (Exception e) {
                        return LocalDate.parse(v, PAY_DATE_SHORT);
                }
        }

        public PayslipViewPanel() {
                initComponents();
                clearAllLabels();
        }

        public void setEmployeeId(String empId) {
                this.employeeId = empId;
                if (this.payDate != null && !this.payDate.isBlank()) {
                        loadEmployeeData();
                }
        }

        public void setPayDate(String payDate) {
                this.payDate = payDate;
                payrollDate.setText(payDate);

                if (this.employeeId != null && !this.employeeId.isBlank()) {
                        loadEmployeeData();
                }
        }

        public void setPayPeriod(String period) {
                dateCovered.setText(period);
        }

        private void loadEmployeeData() {
                Employee emp = EmployeeService.getEmployeeById(employeeId);

                if (emp != null) {
                        empNum.setText(emp.getEmployeeNumber());
                        empFullName.setText(emp.getFullName());
                        empFullName2.setText(emp.getFullName());
                        empPosition.setText(emp.getPosition());
                        empDepartment.setText(emp.getImmediateSupervisor());

                        sssNo.setText(emp.getSssNumber());
                        PhilHealthNo.setText(emp.getPhilhealthNumber());
                        PagibigNo.setText(emp.getPagIbigNumber());
                        tinNo.setText(emp.getTinNumber());

                        basicPay.setText(formatCurrency(emp.getBasicSalary()));
                        RiceSub.setText(formatCurrency(emp.getRiceSubsidy()));
                        PhoneAllw.setText(formatCurrency(emp.getPhoneAllowance()));
                        ClothAllw.setText(formatCurrency(emp.getClothingAllowance()));

                        double totalComp = emp.getBasicSalary()
                                        + emp.getRiceSubsidy()
                                        + emp.getPhoneAllowance()
                                        + emp.getClothingAllowance();
                        totalCompValue.setText(formatCurrency(totalComp));

                        LocalDate cutoff = parsePayDate(payDate);
                        String monthYear = cutoff.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                        int payPeriod = (cutoff.getDayOfMonth() <= 15) ? 1 : 2;

                        // only logs for that cutoff period
                        List<EmployeeTimeLogs> logs = EmployeeService.getTimeLogsForEmployee(employeeId);

                        // Calculate breakdown
                        PayrollResult breakdown = PayrollService.computeDeductions(emp, logs, monthYear,
                                        payPeriod);

                        // Set to labels
                        sssVal.setText(String.format("₱ %,.2f", breakdown.sss));
                        philHealthVal.setText(String.format("₱ %,.2f", breakdown.philhealth));
                        pagIbigVal.setText(String.format("₱ %,.2f", breakdown.pagibig));
                        taxVal.setText(String.format("₱ %,.2f", breakdown.tax));
                        deducSss.setText(String.format("₱ %,.2f", breakdown.sss));
                        deducPhilHealth.setText(String.format("₱ %,.2f", breakdown.philhealth));
                        deducHDMF.setText(String.format("₱ %,.2f", breakdown.pagibig));
                        deducTax.setText(String.format("₱ %,.2f", breakdown.tax));
                        totalDeducValue.setText(String.format("₱ %,.2f", breakdown.totalDeductions));
                        netPayValue.setText(String.format("₱ %,.2f", breakdown.netPay));
                } else {
                        // Clear all labels if employee not found
                        clearAllLabels();
                }
        }

        private void clearAllLabels() {
                empNum.setText("");
                empFullName.setText("");
                empFullName2.setText("");
                empPosition.setText("");
                empDepartment.setText("");
                sssNo.setText("");
                PhilHealthNo.setText("");
                PagibigNo.setText("");
                tinNo.setText("");
                basicPay.setText("");
                RiceSub.setText("");
                PhoneAllw.setText("");
                ClothAllw.setText("");
                totalCompValue.setText("");
                sssVal.setText("");
                philHealthVal.setText("");
                pagIbigVal.setText("");
                taxVal.setText("");
                deducSss.setText("");
                deducPhilHealth.setText("");
                deducHDMF.setText("");
                deducTax.setText("");
                totalDeducValue.setText("");
                netPayValue.setText("");
                absences.setText("");
                deducDeMinimis.setText("");
                lateAndUnder.setText("");
                allowance.setText("");
        }

        private String formatCurrency(double value) {
                return String.format("₱%,.2f", value);
        }

        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                titlebox = new javax.swing.JPanel();
                title = new javax.swing.JLabel();
                logobox = new javax.swing.JPanel();
                logohere = new javax.swing.JLabel();
                fullnameBox = new javax.swing.JPanel();
                empFullName = new javax.swing.JLabel();
                jPanel5 = new javax.swing.JPanel();
                empFullName2 = new javax.swing.JLabel();
                empNum = new javax.swing.JLabel();
                payrollDate = new javax.swing.JLabel();
                dateCovered = new javax.swing.JLabel();
                empDepartment = new javax.swing.JLabel();
                empPosition = new javax.swing.JLabel();
                jPanel6 = new javax.swing.JPanel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                jPanel8 = new javax.swing.JPanel();
                jLabel17 = new javax.swing.JLabel();
                jLabel18 = new javax.swing.JLabel();
                jLabel19 = new javax.swing.JLabel();
                jLabel20 = new javax.swing.JLabel();
                jLabel21 = new javax.swing.JLabel();
                jPanel7 = new javax.swing.JPanel();
                taxStatus = new javax.swing.JLabel();
                tinNo = new javax.swing.JLabel();
                sssNo = new javax.swing.JLabel();
                PhilHealthNo = new javax.swing.JLabel();
                PagibigNo = new javax.swing.JLabel();
                jPanel9 = new javax.swing.JPanel();
                jLabel27 = new javax.swing.JLabel();
                jPanel10 = new javax.swing.JPanel();
                jLabel28 = new javax.swing.JLabel();
                jPanel11 = new javax.swing.JPanel();
                jLabel29 = new javax.swing.JLabel();
                jPanel12 = new javax.swing.JPanel();
                jLabel33 = new javax.swing.JLabel();
                jLabel34 = new javax.swing.JLabel();
                jLabel35 = new javax.swing.JLabel();
                jLabel36 = new javax.swing.JLabel();
                jLabel37 = new javax.swing.JLabel();
                jLabel38 = new javax.swing.JLabel();
                basicPay = new javax.swing.JLabel();
                deMinimisBenefits = new javax.swing.JLabel();
                OrdND = new javax.swing.JLabel();
                RiceSub = new javax.swing.JLabel();
                PhoneAllw = new javax.swing.JLabel();
                ClothAllw = new javax.swing.JLabel();
                jPanel13 = new javax.swing.JPanel();
                jLabel30 = new javax.swing.JLabel();
                jLabel31 = new javax.swing.JLabel();
                jPanel14 = new javax.swing.JPanel();
                totalCompValue = new javax.swing.JLabel();
                jPanel15 = new javax.swing.JPanel();
                jLabel45 = new javax.swing.JLabel();
                jLabel46 = new javax.swing.JLabel();
                jLabel47 = new javax.swing.JLabel();
                jLabel48 = new javax.swing.JLabel();
                jLabel49 = new javax.swing.JLabel();
                jLabel50 = new javax.swing.JLabel();
                jLabel51 = new javax.swing.JLabel();
                jLabel52 = new javax.swing.JLabel();
                absences = new javax.swing.JLabel();
                deducDeMinimis = new javax.swing.JLabel();
                lateAndUnder = new javax.swing.JLabel();
                deducTax = new javax.swing.JLabel();
                deducHDMF = new javax.swing.JLabel();
                deducPhilHealth = new javax.swing.JLabel();
                deducSss = new javax.swing.JLabel();
                allowance = new javax.swing.JLabel();
                jPanel16 = new javax.swing.JPanel();
                jLabel61 = new javax.swing.JLabel();
                jPanel17 = new javax.swing.JPanel();
                totalDeducValue = new javax.swing.JLabel();
                jPanel18 = new javax.swing.JPanel();
                netPayValue = new javax.swing.JLabel();
                jPanel19 = new javax.swing.JPanel();
                jLabel63 = new javax.swing.JLabel();
                jPanel20 = new javax.swing.JPanel();
                jLabel65 = new javax.swing.JLabel();
                taxableGrossVal = new javax.swing.JLabel();
                jLabel67 = new javax.swing.JLabel();
                jLabel68 = new javax.swing.JLabel();
                jLabel69 = new javax.swing.JLabel();
                jLabel70 = new javax.swing.JLabel();
                jLabel71 = new javax.swing.JLabel();
                taxVal = new javax.swing.JLabel();
                nonTaxVal = new javax.swing.JLabel();
                sssVal = new javax.swing.JLabel();
                philHealthVal = new javax.swing.JLabel();
                pagIbigVal = new javax.swing.JLabel();

                setBackground(new java.awt.Color(204, 204, 204));
                setMaximumSize(new java.awt.Dimension(1054, 720));
                setMinimumSize(new java.awt.Dimension(1054, 720));

                jPanel1.setBackground(new java.awt.Color(204, 204, 204));
                jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

                titlebox.setBackground(new java.awt.Color(55, 156, 207));

                title.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                title.setForeground(new java.awt.Color(255, 255, 255));
                title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                title.setText("PAYSLIP");

                javax.swing.GroupLayout titleboxLayout = new javax.swing.GroupLayout(titlebox);
                titlebox.setLayout(titleboxLayout);
                titleboxLayout.setHorizontalGroup(
                                titleboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleboxLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(title,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                titleboxLayout.setVerticalGroup(
                                titleboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleboxLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(title,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                75, Short.MAX_VALUE)
                                                                .addContainerGap()));

                logobox.setBackground(new java.awt.Color(255, 255, 255));

                logohere.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                logohere.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                logohere.setIcon(
                                new javax.swing.ImageIcon(getClass().getResource("/motorph/icons/MotorPH Logo 2.png"))); // NOI18N

                javax.swing.GroupLayout logoboxLayout = new javax.swing.GroupLayout(logobox);
                logobox.setLayout(logoboxLayout);
                logoboxLayout.setHorizontalGroup(
                                logoboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(logohere, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE));
                logoboxLayout.setVerticalGroup(
                                logoboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoboxLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap(31, Short.MAX_VALUE)
                                                                .addComponent(logohere,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                97,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)));

                fullnameBox.setBackground(new java.awt.Color(55, 156, 207));

                empFullName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                empFullName.setForeground(new java.awt.Color(255, 255, 255));
                empFullName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                empFullName.setText("EMPLOYEE'S FULL NAME HERE");

                javax.swing.GroupLayout fullnameBoxLayout = new javax.swing.GroupLayout(fullnameBox);
                fullnameBox.setLayout(fullnameBoxLayout);
                fullnameBoxLayout.setHorizontalGroup(
                                fullnameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fullnameBoxLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(empFullName,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                fullnameBoxLayout.setVerticalGroup(
                                fullnameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fullnameBoxLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(empFullName,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                29, Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel5.setBackground(new java.awt.Color(255, 255, 255));

                empFullName2.setText(": First Name Last Name");

                empNum.setText(": Employee Number");

                payrollDate.setText(": 13 June 2024");

                dateCovered.setText(": Payroll for d/MM/yyyy - d/MM/yyyy");

                empDepartment.setText(": Employee's Department");

                empPosition.setText(": Employee's Position");

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel5Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(dateCovered,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                255,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(empFullName2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(empNum,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(payrollDate,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(empDepartment,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(empPosition,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));
                jPanel5Layout.setVerticalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(empFullName2)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empNum)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(payrollDate)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(dateCovered)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empDepartment)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(empPosition)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel6.setBackground(new java.awt.Color(255, 255, 255));

                jLabel4.setText("EMPLOYEE NAME");

                jLabel5.setText("EMPLOYEE NUMBER");

                jLabel6.setText("PAYROLL DATE");

                jLabel7.setText("DATE COVERED");

                jLabel9.setText("SUPERVISOR");

                jLabel10.setText("POSITION");

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel4,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel5,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel6,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel7,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(186, 186, 186)
                                                                                                .addComponent(jLabel8,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                45,
                                                                                                                Short.MAX_VALUE))
                                                                                .addComponent(jLabel9,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel10,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addGap(23, 23, 23)));
                jPanel6Layout.setVerticalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel5)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel6)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel7)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel9)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel10)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel8)));

                jPanel8.setBackground(new java.awt.Color(255, 255, 255));

                jLabel17.setText("TAX STATUS");

                jLabel18.setText("TIN NUMBER");

                jLabel19.setText("SSS NUMBER");

                jLabel20.setText("PHILHEALTH NUMBER");

                jLabel21.setText("HDMF");

                javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
                jPanel8.setLayout(jPanel8Layout);
                jPanel8Layout.setHorizontalGroup(
                                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel8Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel8Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel18,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel17,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel19,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel20,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                212,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel21,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));
                jPanel8Layout.setVerticalGroup(
                                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel8Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel17)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel18)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel19)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel20)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel21)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel7.setBackground(new java.awt.Color(255, 255, 255));

                taxStatus.setText(": S");

                tinNo.setText(": 442-605-657-000");

                sssNo.setText(": 44-4506057-3");

                PhilHealthNo.setText(": 331736000000");

                PagibigNo.setText(": 171520000000");

                javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
                jPanel7.setLayout(jPanel7Layout);
                jPanel7Layout.setHorizontalGroup(
                                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel7Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(taxStatus,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(tinNo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                254, Short.MAX_VALUE)
                                                                                .addComponent(sssNo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(PhilHealthNo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(PagibigNo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));
                jPanel7Layout.setVerticalGroup(
                                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(taxStatus)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(tinNo)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(sssNo)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(PhilHealthNo)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(PagibigNo)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel9.setBackground(new java.awt.Color(255, 255, 255));

                jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel27.setText("COMPENSATION");

                javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
                jPanel9.setLayout(jPanel9Layout);
                jPanel9Layout.setHorizontalGroup(
                                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel9Layout.createSequentialGroup()
                                                                .addGap(132, 132, 132)
                                                                .addComponent(jLabel27)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel9Layout.setVerticalGroup(
                                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel9Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel27,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                21, Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel10.setBackground(new java.awt.Color(255, 255, 255));

                jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel28.setText("DEDUCTIONS");

                javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
                jPanel10.setLayout(jPanel10Layout);
                jPanel10Layout.setHorizontalGroup(
                                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel10Layout.createSequentialGroup()
                                                                .addGap(125, 125, 125)
                                                                .addComponent(jLabel28)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel10Layout.setVerticalGroup(
                                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel10Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel28,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel11.setBackground(new java.awt.Color(255, 255, 255));

                jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel29.setText("YEAR-TO-DATE");

                javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
                jPanel11.setLayout(jPanel11Layout);
                jPanel11Layout.setHorizontalGroup(
                                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel11Layout.createSequentialGroup()
                                                                .addGap(112, 112, 112)
                                                                .addComponent(jLabel29)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel11Layout.setVerticalGroup(
                                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel11Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel29)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel12.setBackground(new java.awt.Color(255, 255, 255));

                jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel33.setText("DE MINIMIS BENEFITS");

                jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel34.setText("BASIC");

                jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel35.setText("Ord-ND ");

                jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel36.setText("Rice Subsidy");

                jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel37.setText("Clothing Allowance");

                jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel38.setText("Phone Allowance");

                basicPay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                basicPay.setText("0.0");
                basicPay.setToolTipText("");

                deMinimisBenefits.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                deMinimisBenefits.setText("0.0");
                deMinimisBenefits.setToolTipText("");

                OrdND.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                OrdND.setText("0.0");
                OrdND.setToolTipText("");

                RiceSub.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                RiceSub.setText("0.0");
                RiceSub.setToolTipText("");

                PhoneAllw.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                PhoneAllw.setText("0.0");
                PhoneAllw.setToolTipText("");

                ClothAllw.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                ClothAllw.setText("0.0");
                ClothAllw.setToolTipText("");

                javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
                jPanel12.setLayout(jPanel12Layout);
                jPanel12Layout.setHorizontalGroup(
                                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel12Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel12Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel12Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel34,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                194,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(basicPay,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel12Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel37,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                194,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(ClothAllw,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel12Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel33,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                194,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(deMinimisBenefits,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel12Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                194,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(OrdND,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel12Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel36,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                194,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(RiceSub,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel12Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel38,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                194,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(PhoneAllw,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)))
                                                                .addContainerGap()));
                jPanel12Layout.setVerticalGroup(
                                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel12Layout.createSequentialGroup()
                                                                .addGap(30, 30, 30)
                                                                .addGroup(jPanel12Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel34)
                                                                                .addComponent(basicPay))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel12Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel33)
                                                                                .addComponent(deMinimisBenefits))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel12Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel35)
                                                                                .addComponent(OrdND))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel12Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel36)
                                                                                .addComponent(RiceSub))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel12Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel38)
                                                                                .addComponent(PhoneAllw))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel12Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel37)
                                                                                .addComponent(ClothAllw))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel13.setBackground(new java.awt.Color(255, 255, 255));

                jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel30.setText("TOTAL");

                jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel31.setText("COMPENSATION");

                javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
                jPanel13.setLayout(jPanel13Layout);
                jPanel13Layout.setHorizontalGroup(
                                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel13Layout.createSequentialGroup()
                                                                .addContainerGap(60, Short.MAX_VALUE)
                                                                .addGroup(jPanel13Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel13Layout.createSequentialGroup()
                                                                                                                .addComponent(jLabel31)
                                                                                                                .addGap(54, 54, 54))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel13Layout.createSequentialGroup()
                                                                                                                .addComponent(jLabel30)
                                                                                                                .addGap(83, 83, 83)))));
                jPanel13Layout.setVerticalGroup(
                                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel13Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel30)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel31,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                26, Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel14.setBackground(new java.awt.Color(255, 255, 255));

                totalCompValue.setBackground(new java.awt.Color(255, 255, 255));
                totalCompValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                totalCompValue.setText("AMOUNT HERE");

                javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
                jPanel14.setLayout(jPanel14Layout);
                jPanel14Layout.setHorizontalGroup(
                                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(totalCompValue,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                159,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                jPanel14Layout.setVerticalGroup(
                                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel14Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(totalCompValue,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel15.setBackground(new java.awt.Color(255, 255, 255));

                jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel45.setText("DE MINIMIS");

                jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel46.setText("LATE/UNDERTIME");

                jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel47.setText("ALLOWANCE");

                jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel48.setText("ABSENCES");

                jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel49.setText("SSS");

                jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel50.setText("PHILHEALTH");

                jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel51.setText("HDMF");

                jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel52.setText("TAX");

                absences.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                absences.setText("0.0");
                absences.setToolTipText("");

                deducDeMinimis.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                deducDeMinimis.setText("0.0");
                deducDeMinimis.setToolTipText("");

                lateAndUnder.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lateAndUnder.setText("0.0");
                lateAndUnder.setToolTipText("");

                deducTax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                deducTax.setText("0.0");
                deducTax.setToolTipText("");

                deducHDMF.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                deducHDMF.setText("0.0");
                deducHDMF.setToolTipText("");

                deducPhilHealth.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                deducPhilHealth.setText("0.0");
                deducPhilHealth.setToolTipText("");

                deducSss.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                deducSss.setText("0.0");
                deducSss.setToolTipText("");

                allowance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                allowance.setText("0.0");
                allowance.setToolTipText("");

                javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
                jPanel15.setLayout(jPanel15Layout);
                jPanel15Layout.setHorizontalGroup(
                                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel15Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel45,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deducDeMinimis,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel46,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(lateAndUnder,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel48,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(absences,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel52,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deducTax,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel47,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(allowance,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel51,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deducHDMF,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel50,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deducPhilHealth,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel15Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel49,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                166,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deducSss,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                157,
                                                                                                                Short.MAX_VALUE)))
                                                                .addContainerGap()));
                jPanel15Layout.setVerticalGroup(
                                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel15Layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel45)
                                                                                .addComponent(deducDeMinimis))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel46)
                                                                                .addComponent(lateAndUnder))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel48)
                                                                                .addComponent(absences))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel47)
                                                                                .addComponent(allowance))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel49)
                                                                                .addComponent(deducSss,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                16,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel50)
                                                                                .addComponent(deducPhilHealth))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel51)
                                                                                .addComponent(deducHDMF))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel15Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel52)
                                                                                .addComponent(deducTax))
                                                                .addContainerGap(37, Short.MAX_VALUE)));

                jPanel16.setBackground(new java.awt.Color(255, 255, 255));

                jLabel61.setBackground(new java.awt.Color(255, 255, 255));
                jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel61.setText("TOTAL DEDUCTIONS");

                javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
                jPanel16.setLayout(jPanel16Layout);
                jPanel16Layout.setHorizontalGroup(
                                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel16Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel61,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                168, Short.MAX_VALUE)
                                                                .addContainerGap()));
                jPanel16Layout.setVerticalGroup(
                                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel16Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel61,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel17.setBackground(new java.awt.Color(255, 255, 255));

                totalDeducValue.setBackground(new java.awt.Color(255, 255, 255));
                totalDeducValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                totalDeducValue.setText("AMOUNT HERE");

                javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
                jPanel17.setLayout(jPanel17Layout);
                jPanel17Layout.setHorizontalGroup(
                                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel17Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(totalDeducValue,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                jPanel17Layout.setVerticalGroup(
                                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel17Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(totalDeducValue,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel18.setBackground(new java.awt.Color(255, 255, 255));

                netPayValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                netPayValue.setText("AMOUNT HERE");

                javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
                jPanel18.setLayout(jPanel18Layout);
                jPanel18Layout.setHorizontalGroup(
                                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(netPayValue,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                jPanel18Layout.setVerticalGroup(
                                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel18Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(netPayValue,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel19.setBackground(new java.awt.Color(255, 255, 255));

                jLabel63.setBackground(new java.awt.Color(255, 255, 255));
                jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel63.setText("NET PAY");

                javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
                jPanel19.setLayout(jPanel19Layout);
                jPanel19Layout.setHorizontalGroup(
                                jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel19Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel63,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                141,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel19Layout.setVerticalGroup(
                                jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel19Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel63,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel20.setBackground(new java.awt.Color(255, 255, 255));

                jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel65.setText("TAXABLE GROSS");

                taxableGrossVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                taxableGrossVal.setText("0.0");
                taxableGrossVal.setToolTipText("");

                jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel67.setText("TAX");

                jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel68.setText("SSS");

                jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel69.setText("PHIC");

                jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel70.setText("HDMF");

                jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel71.setText("NON TAXABLE");

                taxVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                taxVal.setText("0.0");
                taxVal.setToolTipText("");

                nonTaxVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                nonTaxVal.setText("0.0");
                nonTaxVal.setToolTipText("");

                sssVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                sssVal.setText("0.0");
                sssVal.setToolTipText("");

                philHealthVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                philHealthVal.setText("0.0");
                philHealthVal.setToolTipText("");

                pagIbigVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                pagIbigVal.setText("0.0");
                pagIbigVal.setToolTipText("");

                javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
                jPanel20.setLayout(jPanel20Layout);
                jPanel20Layout.setHorizontalGroup(
                                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel20Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel20Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel20Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel65,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                139,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(taxableGrossVal,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel20Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel67,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                139,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(12, 12, 12)
                                                                                                .addComponent(taxVal,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel20Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel70,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                139,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(12, 12, 12)
                                                                                                .addComponent(pagIbigVal,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel20Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel71,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                139,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(12, 12, 12)
                                                                                                .addComponent(nonTaxVal,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel20Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel68,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                139,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(12, 12, 12)
                                                                                                .addComponent(sssVal,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel20Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel69,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                139,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(12, 12, 12)
                                                                                                .addComponent(philHealthVal,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)))
                                                                .addContainerGap()));
                jPanel20Layout.setVerticalGroup(
                                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel20Layout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addGroup(jPanel20Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel65)
                                                                                .addComponent(taxableGrossVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel20Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel67)
                                                                                .addComponent(taxVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel20Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel68)
                                                                                .addComponent(sssVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel20Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel69)
                                                                                .addComponent(philHealthVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel20Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel70)
                                                                                .addComponent(pagIbigVal))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel20Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel71)
                                                                                .addComponent(nonTaxVal))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(1, 1, 1)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jPanel6,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jPanel5,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jPanel8,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jPanel7,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addComponent(fullnameBox,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(logobox,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(titlebox,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(jPanel12,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(jPanel13,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(jPanel14,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE))
                                                                                                                .addComponent(jPanel9,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                false)
                                                                                                                .addComponent(jPanel10,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jPanel15,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                .addComponent(jPanel16,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(jPanel17,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(jPanel19,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(jPanel18,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE))
                                                                                                                .addComponent(jPanel11,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jPanel20,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))))
                                                                .addGap(2, 2, 2)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(titlebox,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(logobox,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(fullnameBox,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                false)
                                                                                .addComponent(jPanel8,
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel5,
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel6,
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel7,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(jPanel9,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel10,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel11,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel20,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel15,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel12,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel13,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel14,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel16,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel17,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel19,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel18,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
        }// </editor-fold>//GEN-END:initComponents

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel ClothAllw;
        private javax.swing.JLabel OrdND;
        private javax.swing.JLabel PagibigNo;
        private javax.swing.JLabel PhilHealthNo;
        private javax.swing.JLabel PhoneAllw;
        private javax.swing.JLabel RiceSub;
        private javax.swing.JLabel absences;
        private javax.swing.JLabel allowance;
        private javax.swing.JLabel basicPay;
        private javax.swing.JLabel dateCovered;
        private javax.swing.JLabel deMinimisBenefits;
        private javax.swing.JLabel deducDeMinimis;
        private javax.swing.JLabel deducHDMF;
        private javax.swing.JLabel deducPhilHealth;
        private javax.swing.JLabel deducSss;
        private javax.swing.JLabel deducTax;
        private javax.swing.JLabel empDepartment;
        private javax.swing.JLabel empFullName;
        private javax.swing.JLabel empFullName2;
        private javax.swing.JLabel empNum;
        private javax.swing.JLabel empPosition;
        private javax.swing.JPanel fullnameBox;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel17;
        private javax.swing.JLabel jLabel18;
        private javax.swing.JLabel jLabel19;
        private javax.swing.JLabel jLabel20;
        private javax.swing.JLabel jLabel21;
        private javax.swing.JLabel jLabel27;
        private javax.swing.JLabel jLabel28;
        private javax.swing.JLabel jLabel29;
        private javax.swing.JLabel jLabel30;
        private javax.swing.JLabel jLabel31;
        private javax.swing.JLabel jLabel33;
        private javax.swing.JLabel jLabel34;
        private javax.swing.JLabel jLabel35;
        private javax.swing.JLabel jLabel36;
        private javax.swing.JLabel jLabel37;
        private javax.swing.JLabel jLabel38;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel45;
        private javax.swing.JLabel jLabel46;
        private javax.swing.JLabel jLabel47;
        private javax.swing.JLabel jLabel48;
        private javax.swing.JLabel jLabel49;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel50;
        private javax.swing.JLabel jLabel51;
        private javax.swing.JLabel jLabel52;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel61;
        private javax.swing.JLabel jLabel63;
        private javax.swing.JLabel jLabel65;
        private javax.swing.JLabel jLabel67;
        private javax.swing.JLabel jLabel68;
        private javax.swing.JLabel jLabel69;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel70;
        private javax.swing.JLabel jLabel71;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel10;
        private javax.swing.JPanel jPanel11;
        private javax.swing.JPanel jPanel12;
        private javax.swing.JPanel jPanel13;
        private javax.swing.JPanel jPanel14;
        private javax.swing.JPanel jPanel15;
        private javax.swing.JPanel jPanel16;
        private javax.swing.JPanel jPanel17;
        private javax.swing.JPanel jPanel18;
        private javax.swing.JPanel jPanel19;
        private javax.swing.JPanel jPanel20;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
        private javax.swing.JPanel jPanel9;
        private javax.swing.JLabel lateAndUnder;
        private javax.swing.JPanel logobox;
        private javax.swing.JLabel logohere;
        private javax.swing.JLabel netPayValue;
        private javax.swing.JLabel nonTaxVal;
        private javax.swing.JLabel pagIbigVal;
        private javax.swing.JLabel payrollDate;
        private javax.swing.JLabel philHealthVal;
        private javax.swing.JLabel sssNo;
        private javax.swing.JLabel sssVal;
        private javax.swing.JLabel taxStatus;
        private javax.swing.JLabel taxVal;
        private javax.swing.JLabel taxableGrossVal;
        private javax.swing.JLabel tinNo;
        private javax.swing.JLabel title;
        private javax.swing.JPanel titlebox;
        private javax.swing.JLabel totalCompValue;
        private javax.swing.JLabel totalDeducValue;
        // End of variables declaration//GEN-END:variables
}
