package com.example.project.repository;

import com.example.project.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeId(Long employeeId);
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    
    // Count worked days in a month/year
    long countByEmployeeIdAndStatusAndDateBetween(Long employeeId, String status, LocalDate startDate, LocalDate endDate);
}
