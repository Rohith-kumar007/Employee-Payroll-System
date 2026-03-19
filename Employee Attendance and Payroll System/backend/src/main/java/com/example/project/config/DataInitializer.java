package com.example.project.config;

import com.example.project.entity.Employee;
import com.example.project.entity.Role;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@example.com";
        Optional<Employee> adminOpt = employeeRepository.findByEmail(adminEmail);

        if (adminOpt.isPresent()) {
            Employee admin = adminOpt.get();
            // Force reset password to 'admin123' just in case hash matches issues
            admin.setPassword(passwordEncoder.encode("admin123"));
            employeeRepository.save(admin);
            System.out.println("✅ Admin User password verified & updated successfully.");
        } else {
            // Create if missing
            Employee admin = new Employee();
            admin.setName("Admin User");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setDepartment("HR");
            admin.setBaseSalary(75000.0);
            employeeRepository.save(admin);
            System.out.println("✅ Core Admin User created successfully.");
        }

        // --- Also update sample employees so their passwords work ---
        String[] empEmails = { "john@example.com", "jane@example.com" };
        for (String email : empEmails) {
            Optional<Employee> empOpt = employeeRepository.findByEmail(email);
            if (empOpt.isPresent()) {
                Employee emp = empOpt.get();
                emp.setPassword(passwordEncoder.encode("admin123"));
                employeeRepository.save(emp);
                System.out.println("✅ Employee password reset for: " + email);
            }
        }
    }
}
