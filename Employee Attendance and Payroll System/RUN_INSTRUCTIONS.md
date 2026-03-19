# Employee Attendance & Payroll System - Run Instructions

This guide explains how to set up and run the full-stack application.

## 1. Database Setup (MySQL)

1.  **Start MySQL Server**: Ensure your MySQL service is running.
2.  **Run Schema Script**: Execute the provided `schema.sql` file to create the database and tables.
    *   Command Line:
        ```bash
        mysql -u root -p < schema.sql
        ```
    *   Or use a GUI (Workbench/phpMyAdmin) and run the script inside an SQL editor.
3.  **Default Data Admin**:
    *   **Email**: `admin@example.com`
    *   **Password**: `admin123`

---

## 2. Backend Setup (Spring Boot)

### Prerequisites:
*   Java 17 (JDK) installed and `JAVA_HOME` set.
*   Maven installed (or use included `mvnw` if available, otherwise just use workspace setup).

### Configuration:
Verify database credentials in:
`backend/src/main/resources/application.properties`
```properties
spring.datasource.username=root
spring.datasource.password=root
```

### Steps to Run:
1.  Navigate to the backend directory:
    ```bash
    cd backend
    ```
2.  Build and Run using Maven:
    ```bash
    mvn spring-boot:run
    ```
    The server will start at `http://localhost:8080`.

---

## 3. Frontend Setup

The frontend uses Vanilla HTML/CSS/JS. No build step is required.

### Steps to View:
1.  Navigate to the `frontend` directory.
2.  Simply **double-click** `index.html` to open it in a browser.
3.  *Recommended for routing robustness*: Use a local server like `Live Server` in VS Code, or:
    ```bash
    # using python
    python -m http.server 8000
    ```
    Then visit `http://localhost:8000`.

---

# API Endpoints Documentation

### Authentication `/api/auth`
| Method | Endpoint | Request Body | Response |
| :--- | :--- | :--- | :--- |
| **POST** | `/login` | `{ email, password }` | `{ token, email, role }` |

### Admin Operations `/api/admin`
| Method | Endpoint | Request Body | Response | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/employees` | None | `List<EmployeeDto>` | Get all staff records |
| **POST** | `/employees` | `{ name, email, password, role, department, baseSalary }` | `EmployeeDto` | Add new employee |
| **PUT** | `/employees/{id}`| `{ name, department, baseSalary }` | `EmployeeDto` | Edit employee details |
| **DELETE**| `/employees/{id}`| None | Void (204) | Remove employee |
| **GET** | `/attendance` | None | `List<AttendanceDto>` | View all check-ins |
| **GET** | `/leaves` | None | `List<LeaveDto>` | View all requests |
| **PUT** | `/leaves/{id}/status`| `{ status: "APPROVED"/"REJECTED" }` | `LeaveDto` | Approve/Reject leave |
| **POST** | `/payroll/generate`| `{ employeeId, month, year }` | `PayrollDto` | Calculate/Save Salary |
| **GET** | `/payroll` | None | `List<PayrollDto>` | View all generated payrolls |

### Employee Profiles `/api/employee`
| Method | Endpoint | Request Body | Response | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/profile` | None | `Employee` (Entity) | View self particulars |
| **POST** | `/attendance` | `{ date, status }` | `AttendanceDto` | Clock in |
| **GET** | `/attendance` | None | `List<AttendanceDto>` | Personal check-in history |
| **POST** | `/leaves` | `{ startDate, endDate, reason }` | `LeaveDto` | Request time off |
| **GET** | `/leaves` | None | `List<LeaveDto>` | My requests history |
| **GET** | `/payroll` | None | `List<PayrollDto>` | My generated payslips |
