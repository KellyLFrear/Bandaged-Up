package com.example.demo.home;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientHomeController {

    @GetMapping
    public ResponseEntity<String> patientHome() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Welcome to the patient home, Mr. " + username);
    }
}
