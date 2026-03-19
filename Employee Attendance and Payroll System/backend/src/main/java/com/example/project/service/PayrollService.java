package com.example.project.service;

import com.example.project.dto.PayrollDto;
import com.example.project.entity.Employee;
import com.example.project.entity.Payroll;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.AttendanceRepository;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public PayrollDto generatePayroll(Long employeeId, String monthName, int year) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Month month = Month.valueOf(monthName.toUpperCase());
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // Count 'PRESENT' days
        long workedDays = attendanceRepository.countByEmployeeIdAndStatusAndDateBetween(
                employeeId, "PRESENT", startDate, endDate);

        double baseSalary = employee.getBaseSalary();
        int totalDaysInMonth = yearMonth.lengthOfMonth();
        double calculatedSalary = (baseSalary / totalDaysInMonth) * workedDays;

        // Save or Update Payroll record
        Payroll payroll = payrollRepository.findByEmployeeIdAndMonthAndYear(employeeId, monthName, year)
                .orElse(new Payroll());

        payroll.setEmployee(employee);
        payroll.setMonth(monthName);
        payroll.setYear(year);
        payroll.setWorkedDays((int) workedDays);
        payroll.setCalculatedSalary(calculatedSalary);

        Payroll saved = payrollRepository.save(payroll);
        return convertToDto(saved);
    }

    public List<PayrollDto> getPayrollByEmployee(Long employeeId) {
        return payrollRepository.findAll().stream()
                .filter(p -> p.getEmployee().getId().equals(employeeId))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PayrollDto> getAllPayrolls() {
        return payrollRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PayrollDto convertToDto(Payroll payroll) {
        PayrollDto dto = new PayrollDto();
        dto.setId(payroll.getId());
        dto.setEmployeeId(payroll.getEmployee().getId());
        dto.setEmployeeName(payroll.getEmployee().getName());
        dto.setMonth(payroll.getMonth());
        dto.setYear(payroll.getYear());
        dto.setWorkedDays(payroll.getWorkedDays());
        dto.setCalculatedSalary(payroll.getCalculatedSalary());
        return dto;
    }
}
