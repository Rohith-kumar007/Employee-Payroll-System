package com.example.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"employee_id", "month", "year"})
})
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private String month; // 'JANUARY', etc.

    @Column(nullable = false)
    private Integer year;

    @Column(name = "worked_days", nullable = false)
    private Integer workedDays;

    @Column(name = "calculated_salary", nullable = false)
    private Double calculatedSalary;

    @Column(name = "generated_at", updatable = false)
    private LocalDateTime generatedAt = LocalDateTime.now();

    public Payroll() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getWorkedDays() { return workedDays; }
    public void setWorkedDays(Integer workedDays) { this.workedDays = workedDays; }

    public Double getCalculatedSalary() { return calculatedSalary; }
    public void setCalculatedSalary(Double calculatedSalary) { this.calculatedSalary = calculatedSalary; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
