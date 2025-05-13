package com.example.demo.Scheduling;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
import com.example.demo.security.JwtUtils;
import com.sun.tools.javac.Main;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;


    @GetMapping("/scheduling-list")
    public ResponseEntity<List<Schedule>> getSchedule(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtils.extractId(token);
        Doctor doctor = doctorRepository.findByUserId(userId);
        Long doctorId = doctor.getId();
        return ResponseEntity.ok(scheduleService.getDoctorSchedule(doctorId));
    }

    @GetMapping("/by-date-and-time")
    public ResponseEntity<List<Schedule>> getSchedulesByDateAndTime(
            @RequestHeader("Authorization") String token,
            @RequestParam String date,
            @RequestParam String time) {
        Long userId = jwtUtils.extractId(token);
        Patient patient = patientRepository.findByUserId(userId);
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        return ResponseEntity.ok(scheduleService.findByDateAndTime(date, time));
    }


    @PostMapping("/add-schedule")
    public ResponseEntity<String> addSchedule(@RequestBody ScheduleRequest request, @RequestHeader("Authorization") String token) {
        System.out.println("Received schedule: " + request);
        Long userId = jwtUtils.extractId(token);
        Doctor doctor = doctorRepository.findByUserId(userId);
        Long doctorId = doctor.getId();
        scheduleService.addAvailability(doctorId, request);
        return ResponseEntity.ok("Schedule added successfully");
    }

    @DeleteMapping("/scheduling-delete/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@RequestHeader("Authorization") String token, @PathVariable Long scheduleId) {
        Long userId = jwtUtils.extractId(token);
        Doctor doctor = doctorRepository.findByUserId(userId);
        Long doctorId = doctor.getId();
        scheduleService.removeAvailability(doctorId, scheduleId);
        return ResponseEntity.ok("Schedule deleted successfully");
    }

}
