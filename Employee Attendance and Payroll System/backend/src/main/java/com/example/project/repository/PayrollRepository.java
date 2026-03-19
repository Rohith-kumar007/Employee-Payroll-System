package com.example.project.repository;

import com.example.project.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    Optional<Payroll> findByEmployeeIdAndMonthAndYear(Long employeeId, String month, Integer year);
}
