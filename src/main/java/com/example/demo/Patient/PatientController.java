package com.example.demo.Patient;

import com.example.demo.Doctor.DoctorService;
import com.example.demo.Scheduling.Appointment;
import com.example.demo.Scheduling.AppointmentRepository;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/info")
    public ResponseEntity<PatientProfileResponse> getPatientProfile(
            @RequestHeader("Authorization") String token) {
        PatientProfileResponse response = patientService.getPatientProfile(token);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePatient(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer height,
            @RequestParam(required = false) Integer weight,
            @RequestParam(required = false) String insurance_number) {
        Long userId = jwtUtils.extractId(token);
        String result = patientService.updatePatientInformation(userId, firstName, lastName, gender, height, weight, insurance_number);
        if (result.equals("Patient not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

}
