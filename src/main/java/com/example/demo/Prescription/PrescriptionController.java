package com.example.demo.Prescription;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
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
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

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



        Long userId = jwtUtils.extractId(token);
        Patient patient = patientRepository.findByUserId(userId);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
        Long patientId = patient.getId();
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

        Long userId = jwtUtils.extractId(token);
        Doctor doctor = doctorRepository.findByUserId(userId);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);
            LocalDate parsedEndDate = LocalDate.parse(endDate, formatter);

            prescriptionService.addDoctorPrescription(patientId, medicationName, dosage, frequency, parsedStartDate, parsedEndDate, notes, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Prescription added successfully");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get prescriptions for the patient
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patients/prescriptions/list")
    public ResponseEntity<List<PrescriptionDTO>> getPatientPrescriptions(
            @RequestHeader("Authorization") String token) {
        System.out.println("Patient id from token: " + jwtUtils.extractId(token));
        System.out.println("Token recieved: " + token);
        Long userId = jwtUtils.extractId(token);
        if (userId == null) {
            System.out.println("PatientId is null");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }
        Patient patient = patientRepository.findByUserId(userId);
        Long patientId = patient.getId();

        // Fetch prescriptions for this patient
        List<Prescription> prescriptions = prescriptionService.getPrescriptions(patientId);

        // Map prescriptions to DTO (to exclude sensitive information)
        List<PrescriptionDTO> prescriptionDTOs = prescriptions.stream()
                .map(PrescriptionDTO::fromEntity)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(prescriptionDTOs);
    }
}

















