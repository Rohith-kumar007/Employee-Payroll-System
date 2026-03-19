package com.example.project.dto;

import java.time.LocalDate;

public class AttendanceDto {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate date;
    private String status; // 'PRESENT', 'ABSENT', 'LATE', 'LEAVE'

    public AttendanceDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
