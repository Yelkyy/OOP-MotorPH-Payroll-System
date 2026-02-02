# MotorPH Payroll Management System (OOP Version)

**MotorPH Payroll Management System** is a Java desktop application developed using **Object-Oriented Programming (OOP)** principles.  
It is designed to manage employee records, payroll processing, and role-based access for MotorPH.

The system uses **CSV files as its data source** and provides different features depending on the logged-in user’s role.

---

## 🧾 System Overview

This application supports core HR and Payroll operations such as:
- Employee record management  
- Payroll and payslip viewing  
- Attendance tracking  
- Leave request processing  
- Role-based access control  

The system demonstrates key OOP concepts including:
- **Encapsulation**
- **Inheritance**
- **Polymorphism**
- **Abstraction**

---

## 👥 User Roles & Features

### 👨‍💼 Admin (System Administrator)
- View all employee records  
- View attendance records of all employees  
- View payroll and payslips of all employees  
- Configure system access and settings  

---

### 👩‍💼 HR
- Add, update, and delete employee records  
- View attendance records of all employees  
- Approve or reject leave requests  

---

### 💼 Finance
- Generate payroll and pay runs  
- View and print payslips of all employees  

---

### 👤 Employee
- View personal profile information  
- View personal attendance records  
- Submit leave requests  
- View and print personal payslip  

*(Employees do not have access to the system dashboard used by Admin, HR, or Finance.)*

---

## 🔐 Login & Access Control

The system uses **role-based authentication**.

### Automatic Employee Login
Regular employees do not require a separate user account file.

**Employee login format:**
Username: emp{EmployeeNumber}
Password: {EmployeeNumber}

## Default System Login Accounts

The following accounts are predefined in `MotorPH Users.csv` for system access:

| Role     | Username      | Password |
|--------  |-----------    |----------|
| Admin    | admin1        | admin123 |
| HR       | andrea.hr     | hr123    |
| Finance  | rod.finance   | fin123   |

These users log in using their assigned **username and password**.

