package com.example.demo.Scheduling;

import com.example.demo.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService service;
    private final JwtUtils jwtUtils;

    public AppointmentController(AppointmentService service, JwtUtils jwtUtils) {
        this.service = service;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@RequestHeader("Authorization") String token, @RequestBody AppointmentRequest request) {
        Long patientId = jwtUtils.extractId(token);
        return ResponseEntity.ok(service.bookAppointment(patientId, request));
    }

    @GetMapping("/appointments-booked")
    public ResponseEntity<List<Appointment>> getAppointmentsBooked(@RequestHeader("Authorization") String token) {
        Long patientId = jwtUtils.extractId(token);
        return ResponseEntity.ok(service.getAllAppointmentsForPatient(patientId));
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
