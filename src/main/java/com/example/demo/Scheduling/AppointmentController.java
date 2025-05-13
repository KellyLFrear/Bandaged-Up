package com.example.demo.Scheduling;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService service;
    private final JwtUtils jwtUtils;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    public AppointmentController(AppointmentService service, JwtUtils jwtUtils) {
        this.service = service;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@RequestHeader("Authorization") String token, @RequestParam Long scheduleId) {
        Long userId = jwtUtils.extractId(token);
        Patient patient = patientRepository.findByUserId(userId);
        Long patientId = patient.getId();
        return ResponseEntity.ok(service.bookAppointment(patientId, scheduleId));
    }

    @GetMapping("/appointments-booked")
    public ResponseEntity<List<Appointment>> getAppointmentsBooked(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtils.extractId(token);
        Patient patient = patientRepository.findByUserId(userId);
        Long patientId = patient.getId();
        return ResponseEntity.ok(service.getAllAppointmentsForPatient(patientId));
    }
    @GetMapping("/appointments-by-doctor")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtils.extractId(token);
        Doctor doctor = doctorRepository.findByUserId(userId);
        Long doctorId = doctor.getId();
        return ResponseEntity.ok(service.getAppointmentsForDoctor(doctorId));
    }

    @GetMapping("/availability-by-type")
    public ResponseEntity<List<Schedule>> getAvailableAppointmentsByType(@RequestHeader("Authorization") String token, @RequestParam("type") String type) {
        return ResponseEntity.ok(service.getAvailableSchedulesByType(type));
    }
    @GetMapping("availability-by-date")
    public ResponseEntity<List<Schedule>> getAvailableAppointmentsByDate(@RequestHeader("Authorization") String token, @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(service.getAvailableSchedulesByDate(date));
    }
    @GetMapping("availability-by-date-and-type")
    public ResponseEntity<List<Schedule>> getAvailableAppointmentsByDateAndType(@RequestHeader("Authorization") String token, @RequestParam("date") LocalDate date, @RequestParam("type") String type) {
        return ResponseEntity.ok(service.getSchedulesByTypeAndDate(date, type));
    }
    @GetMapping("/full-availability")
    public ResponseEntity<List<Schedule>> getFullAvailability(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.getAvailableSchedules());
    }

}
