package com.example.project.service;

import com.example.project.dto.AttendanceDto;
import com.example.project.entity.Attendance;
import com.example.project.entity.Employee;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.AttendanceRepository;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public AttendanceDto markAttendance(AttendanceDto dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LocalDate date = dto.getDate() != null ? dto.getDate() : LocalDate.now();

        // Check if already marked
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employee.getId(), date)
                .orElse(new Attendance());

        attendance.setEmployee(employee);
        attendance.setDate(date);
        attendance.setStatus(dto.getStatus() != null ? dto.getStatus() : "PRESENT");

        Attendance saved = attendanceRepository.save(attendance);
        return convertToDto(saved);
    }

    public List<AttendanceDto> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceDto> getAllAttendance() {
        return attendanceRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AttendanceDto convertToDto(Attendance attendance) {
        AttendanceDto dto = new AttendanceDto();
        dto.setId(attendance.getId());
        dto.setEmployeeId(attendance.getEmployee().getId());
        dto.setEmployeeName(attendance.getEmployee().getName());
        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus());
        return dto;
    }
}
