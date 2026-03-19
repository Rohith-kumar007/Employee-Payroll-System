-- Create Database if not exists
CREATE DATABASE IF NOT EXISTS employee_attendance_payroll;
USE employee_attendance_payroll;

-- Table: employees
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'EMPLOYEE', -- 'ADMIN', 'EMPLOYEE'
    department VARCHAR(50),
    base_salary DOUBLE NOT NULL DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: attendance
CREATE TABLE IF NOT EXISTS attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20) NOT NULL, -- 'PRESENT', 'ABSENT', 'LATE', 'LEAVE'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    UNIQUE KEY unique_attendance (employee_id, date)
);

-- Table: leaves
CREATE TABLE IF NOT EXISTS leaves (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- 'PENDING', 'APPROVED', 'REJECTED'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- Table: payroll
CREATE TABLE IF NOT EXISTS payroll (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    month VARCHAR(20) NOT NULL, -- 'JANUARY', etc.
    year INT NOT NULL,
    worked_days INT NOT NULL,
    calculated_salary DOUBLE NOT NULL,
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    UNIQUE KEY unique_payroll (employee_id, month, year)
);

-- Sample Data (Password is 'admin123' and 'user123' bcrypt hashed)
-- admin123 -> $2a$10$8.UnVuG9HHgffUDAlk8LH.PsmrvKiLB89F706U1f3vPyHMbeQ7Sze
-- user123  -> $2a$10$R9h/9Ej8vWCSv9wK6eZ7n.wKjD8O8Y/e17uV2p/yZfWv7m9W8Y6C2

INSERT INTO employees (name, email, password, role, department, base_salary) VALUES
('Admin User', 'admin@example.com', '$2a$10$8.UnVuG9HHgffUDAlk8LH.PsmrvKiLB89F706U1f3vPyHMbeQ7Sze', 'ADMIN', 'HR', 75000.0),
('John Doe', 'john@example.com', '$2a$10$R9h/9Ej8vWCSv9wK6eZ7n.wKjD8O8Y/e17uV2p/yZfWv7m9W8Y6C2', 'EMPLOYEE', 'Engineering', 50000.0),
('Jane Smith', 'jane@example.com', '$2a$10$R9h/9Ej8vWCSv9wK6eZ7n.wKjD8O8Y/e17uV2p/yZfWv7m9W8Y6C2', 'EMPLOYEE', 'Marketing', 45000.0);

-- Sample Attendance for John Doe (ID 2)
INSERT INTO attendance (employee_id, date, status) VALUES
(2, '2026-03-01', 'PRESENT'),
(2, '2026-03-02', 'PRESENT'),
(2, '2026-03-03', 'ABSENT'),
(2, '2026-03-04', 'PRESENT');

-- Sample Leave for Jane Smith (ID 3)
INSERT INTO leaves (employee_id, start_date, end_date, reason, status) VALUES
(3, '2026-03-10', '2026-03-12', 'Fever', 'APPROVED');
