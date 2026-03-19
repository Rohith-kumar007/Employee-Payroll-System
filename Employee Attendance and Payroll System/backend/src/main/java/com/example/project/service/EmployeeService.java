package com.example.project.service;

import com.example.project.dto.EmployeeDto;
import com.example.project.entity.Employee;
import com.example.project.entity.Role;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return convertToDto(employee);
    }

    public EmployeeDto createEmployee(EmployeeDto dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists: " + dto.getEmail());
        }

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        // Default password if not provided or hash it
        employee.setPassword(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "user123"));
        employee.setRole(dto.getRole() != null ? Role.valueOf(dto.getRole()) : Role.EMPLOYEE);
        employee.setDepartment(dto.getDepartment());
        employee.setBaseSalary(dto.getBaseSalary());

        Employee saved = employeeRepository.save(employee);
        return convertToDto(saved);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        employee.setBaseSalary(dto.getBaseSalary());
        
        if (dto.getRole() != null) {
            employee.setRole(Role.valueOf(dto.getRole()));
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Employee updated = employeeRepository.save(employee);
        return convertToDto(updated);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setRole(employee.getRole().name());
        dto.setDepartment(employee.getDepartment());
        dto.setBaseSalary(employee.getBaseSalary());
        return dto;
    }
}
