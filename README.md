# 🚀 Employee Attendance & Payroll System

## 📌 Overview

This project is a **full-stack Employee Attendance & Payroll Management System** designed to simulate an **ERP-based HR module (similar to SAP)**. It automates employee management, attendance tracking, leave processing, and salary calculation using a role-based system.

---

## 🎯 Features

### 🔐 Authentication

* JWT-based login system
* Role-based access control (Admin / Employee)
* Secure password storage using BCrypt

---

### 👥 Employee Management (Admin)

* Add, update, delete employees
* View all employee records
* Manage departments and salaries

---

### 🕒 Attendance Management

* Mark attendance (Present / Absent / Leave)
* Track daily attendance records
* View attendance history

---

### 📆 Leave Management

* Employees can apply for leave
* Admin can approve/reject leave requests
* Track leave status

---

### 💰 Payroll System

* Automatic salary calculation
* Based on attendance and absences

**Salary Formula:**

```
Per Day Salary = Basic Salary / 30  
Deduction = Absent Days × Per Day Salary  
Net Salary = Basic Salary - Deductions
```

---

### 📄 Payslip Generation (Bonus)

* Generate PDF payslips
* Includes employee details, attendance summary, and salary breakdown

---

## 🏗️ Tech Stack

### Backend:

* Java Spring Boot
* Spring Security + JWT
* Spring Data JPA

### Frontend:

* HTML, CSS, JavaScript

### Database:

* MySQL

---

## 🗄️ Database Schema

Tables:

* employees
* attendance
* leaves
* salary

---

## 📂 Project Structure

```
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── dto/
 ├── security/
 ├── config/
```

---

## 🔗 API Endpoints (Sample)

### Authentication

* POST `/api/auth/login`

### Employee

* GET `/api/employees`
* POST `/api/employees`
* PUT `/api/employees/{id}`
* DELETE `/api/employees/{id}`

### Attendance

* POST `/api/attendance`
* GET `/api/attendance/{empId}`

### Leave

* POST `/api/leaves`
* PUT `/api/leaves/{id}/approve`

### Payroll

* GET `/api/salary/{empId}`

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository

```
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

---

### 2️⃣ Configure Database

* Create MySQL database:

```
CREATE DATABASE payroll_system;
```

* Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/payroll_system
spring.datasource.username=root
spring.datasource.password=your_password
```

---

### 3️⃣ Run Backend

```
mvn spring-boot:run
```

---

### 4️⃣ Run Frontend

* Open `index.html` in browser

---

## 🧪 API Testing

Use:

* Postman
* Thunder Client

---

## 📊 Future Enhancements

* Email notifications
* Dashboard with analytics
* Role-based UI rendering
* Docker deployment

---

## 🎯 Learning Outcomes

* MVC architecture
* REST API development
* JWT authentication
* Database design (SQL)
* Real-world ERP system simulation

---

## 👨‍💻 Author

Rohith Kumar P

---

## ⭐ Acknowledgment

This project is inspired by real-world ERP systems like SAP HR modules.

---
