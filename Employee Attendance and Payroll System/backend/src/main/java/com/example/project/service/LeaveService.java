package com.example.project.service;

import com.example.project.dto.LeaveDto;
import com.example.project.entity.Employee;
import com.example.project.entity.Leave;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public LeaveDto applyLeave(LeaveDto dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setReason(dto.getReason());
        leave.setStatus("PENDING");

        Leave saved = leaveRepository.save(leave);
        return convertToDto(saved);
    }

    public List<LeaveDto> getLeavesByEmployee(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<LeaveDto> getAllLeaves() {
        return leaveRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public LeaveDto updateLeaveStatus(Long leaveId, String status) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found"));
        leave.setStatus(status);
        Leave saved = leaveRepository.save(leave);
        return convertToDto(saved);
    }

    private LeaveDto convertToDto(Leave leave) {
        LeaveDto dto = new LeaveDto();
        dto.setId(leave.getId());
        dto.setEmployeeId(leave.getEmployee().getId());
        dto.setEmployeeName(leave.getEmployee().getName());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setReason(leave.getReason());
        dto.setStatus(leave.getStatus());
        return dto;
    }
}
