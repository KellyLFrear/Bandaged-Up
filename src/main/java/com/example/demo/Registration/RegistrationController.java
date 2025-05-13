package com.example.demo.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam LocalDate date_of_birth,
            @RequestParam String role,
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String license_number,
            @RequestParam(required = false) String insurance_number
    ) {
        if (role.equalsIgnoreCase("doctor")) {
            registrationService.registerUser(username, password, email, date_of_birth, role, first_name, last_name, specialty, license_number, null);
        } else if (role.equalsIgnoreCase("patient")) {
            if (insurance_number != null) {
                registrationService.registerUser(username, password, email, date_of_birth, role, first_name, last_name, null, null, insurance_number);
            } else {
                registrationService.registerUser(username, password, email, date_of_birth, role, first_name, last_name, null, null, null);
            }
        } else {
            return "Invalid role specified!";
        }
        // In reality, probably need to return the JWT or something here
        return "User registered successfully!";
    }
}
