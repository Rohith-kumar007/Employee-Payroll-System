package com.example.project.controller;

import com.example.project.dto.AttendanceDto;
import com.example.project.dto.LeaveDto;
import com.example.project.dto.PayrollDto;
import com.example.project.entity.Employee;
import com.example.project.repository.EmployeeRepository;
import com.example.project.service.AttendanceService;
import com.example.project.service.LeaveService;
import com.example.project.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee getAuthenticatedEmployee() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    // --- Employee Profile Details ---
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Employee employee = getAuthenticatedEmployee();
        return ResponseEntity.ok(employee); // You could convert to DTO but okay for now
    }

    // --- Attendance Reporting ---
    @PostMapping("/attendance")
    public ResponseEntity<AttendanceDto> markAttendance(@RequestBody AttendanceDto dto) {
        Employee employee = getAuthenticatedEmployee();
        dto.setEmployeeId(employee.getId()); // Force authenticated user ID
        return ResponseEntity.ok(attendanceService.markAttendance(dto));
    }

    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceDto>> getMyAttendance() {
        Employee employee = getAuthenticatedEmployee();
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(employee.getId()));
    }

    // --- Leave Management ---
    @PostMapping("/leaves")
    public ResponseEntity<LeaveDto> applyLeave(@RequestBody LeaveDto dto) {
        Employee employee = getAuthenticatedEmployee();
        dto.setEmployeeId(employee.getId()); // Force authenticated user ID
        return ResponseEntity.ok(leaveService.applyLeave(dto));
    }

    @GetMapping("/leaves")
    public ResponseEntity<List<LeaveDto>> getMyLeaves() {
        Employee employee = getAuthenticatedEmployee();
        return ResponseEntity.ok(leaveService.getLeavesByEmployee(employee.getId()));
    }

    // --- Payroll View ---
    @GetMapping("/payroll")
    public ResponseEntity<List<PayrollDto>> getMyPayroll() {
        Employee employee = getAuthenticatedEmployee();
        return ResponseEntity.ok(payrollService.getPayrollByEmployee(employee.getId()));
    }
}
