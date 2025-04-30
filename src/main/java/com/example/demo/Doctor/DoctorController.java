package com.example.demo.Doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PutMapping("/update")
    public String updateDoctorProfile(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String specialty,
            @RequestParam String licenseNumber) {
        return doctorService.updateDoctorInformation(firstName, lastName, specialty, licenseNumber);
    }
}
