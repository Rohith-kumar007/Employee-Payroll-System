package com.example.project.controller;

import com.example.project.dto.AuthRequest;
import com.example.project.dto.AuthResponse;
import com.example.project.entity.Employee;
import com.example.project.repository.EmployeeRepository;
import com.example.project.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/test-users")
    public org.springframework.http.ResponseEntity<?> testUsers() {
        return org.springframework.http.ResponseEntity.ok(employeeRepository.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // Lookup Employee to get role
        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(userDetails.getUsername(), employee.getRole().name());

        return ResponseEntity.ok(new AuthResponse(token, employee.getEmail(), employee.getRole().name()));
    }
}
