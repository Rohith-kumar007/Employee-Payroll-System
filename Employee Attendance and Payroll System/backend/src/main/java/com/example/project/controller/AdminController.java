package com.example.project.controller;

import com.example.project.dto.AttendanceDto;
import com.example.project.dto.EmployeeDto;
import com.example.project.dto.LeaveDto;
import com.example.project.dto.PayrollDto;
import com.example.project.service.AttendanceService;
import com.example.project.service.EmployeeService;
import com.example.project.service.LeaveService;
import com.example.project.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private PayrollService payrollService;

    // --- Employee Management ---
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // --- Attendance & Leaves View ---
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/leaves")
    public ResponseEntity<List<LeaveDto>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    @PutMapping("/leaves/{id}/status")
    public ResponseEntity<LeaveDto> updateLeaveStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return ResponseEntity.ok(leaveService.updateLeaveStatus(id, status));
    }

    // --- Payroll ---
    @PostMapping("/payroll/generate")
    public ResponseEntity<PayrollDto> generatePayroll(@RequestBody Map<String, Object> body) {
        Long employeeId = Long.valueOf(body.get("employeeId").toString());
        String month = body.get("month").toString();
        int year = Integer.parseInt(body.get("year").toString());
        return ResponseEntity.ok(payrollService.generatePayroll(employeeId, month, year));
    }

    @GetMapping("/payroll")
    public ResponseEntity<List<PayrollDto>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllPayrolls());
    }
}
