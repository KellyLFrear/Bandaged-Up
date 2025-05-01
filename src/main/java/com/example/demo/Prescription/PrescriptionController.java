package com.example.demo.Prescription;

import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private JwtUtils jwtUtils;

    // Patient uploads their past prescriptions
    @PostMapping("/patients/upload")
    public ResponseEntity<String> uploadPatientPrescription(
            @RequestHeader("Authorization") String token,
            @RequestParam String medicationName,
            @RequestParam String dosage,
            @RequestParam String frequency,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String notes) {



        Long patientId = jwtUtils.extractId(token);
        if (patientId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }


        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);
            LocalDate parsedEndDate = LocalDate.parse(endDate, formatter);


            prescriptionService.addPatientPrescription(patientId, medicationName, dosage, frequency, parsedStartDate, parsedEndDate, notes);
            return ResponseEntity.status(HttpStatus.CREATED).body("Prescription added successfully");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Doctor uploads prescriptions for a patient
    @PostMapping("/doctors/upload")
    public ResponseEntity<String> uploadDoctorPrescription(
            @RequestHeader("Authorization") String token,
            @RequestParam Long patientId,
            @RequestParam String medicationName,
            @RequestParam String dosage,
            @RequestParam String frequency,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String notes) {

        Long doctorId = jwtUtils.extractId(token);
        if (doctorId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);
            LocalDate parsedEndDate = LocalDate.parse(endDate, formatter);

            prescriptionService.addDoctorPrescription(patientId, medicationName, dosage, frequency, parsedStartDate, parsedEndDate, notes, doctorId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Prescription added successfully");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get prescriptions for the patient
    @GetMapping("/patients/prescriptions/list")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<PrescriptionDTO>> getPatientPrescriptions(
            @RequestHeader("Authorization") String token) {

        // Print all headers and body that came from request
        System.out.println("Incoming request: " + token);
        Long patientId = jwtUtils.extractId(token);
        if (patientId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }

        // Fetch prescriptions for this patient
        List<Prescription> prescriptions = prescriptionService.getPrescriptions(patientId);

        // Map prescriptions to DTO (to exclude sensitive information)
        List<PrescriptionDTO> prescriptionDTOs = prescriptions.stream()
                .map(PrescriptionDTO::fromEntity)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(prescriptionDTOs);
    }
}

















