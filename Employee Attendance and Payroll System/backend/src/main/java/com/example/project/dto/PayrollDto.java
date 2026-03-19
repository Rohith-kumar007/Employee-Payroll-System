package com.example.project.dto;

public class PayrollDto {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String month;
    private Integer year;
    private Integer workedDays;
    private Double calculatedSalary;

    public PayrollDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getWorkedDays() { return workedDays; }
    public void setWorkedDays(Integer workedDays) { this.workedDays = workedDays; }

    public Double getCalculatedSalary() { return calculatedSalary; }
    public void setCalculatedSalary(Double calculatedSalary) { this.calculatedSalary = calculatedSalary; }
}
