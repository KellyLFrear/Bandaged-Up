package com.example.demo.Doctor;

import com.example.demo.Scheduling.Appointment;
import com.example.demo.Scheduling.AppointmentRepository;
import com.example.demo.Scheduling.AppointmentService;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    @PutMapping("/update")
    public String updateDoctorProfile(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String licenseNumber) {
        return doctorService.updateDoctorInformation(firstName, lastName, specialty, licenseNumber);
    }

    @GetMapping("/info")
    public ResponseEntity<DoctorProfileResponse> getDoctorProfile(
            @RequestHeader("Authorization") String token) {
        DoctorProfileResponse doctorProfileResponse = doctorService.getDoctorProfile(token);

        if (doctorProfileResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(doctorProfileResponse);

    }
}
