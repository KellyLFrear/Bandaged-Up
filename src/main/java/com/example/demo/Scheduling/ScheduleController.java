package com.example.demo.Scheduling;

import com.example.demo.security.JwtUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping("/scheduling-list")
    public ResponseEntity<List<Schedule>> getSchedule(@RequestHeader("Authorization") String token) {
        Long doctorId = jwtUtils.extractId(token);
        return ResponseEntity.ok(scheduleService.getDoctorSchedule(doctorId));
    }


    @PostMapping
    public ResponseEntity<String> addSchedule(@RequestBody ScheduleRequest request, @RequestHeader("Authorization") String token) {
        Long doctorId = jwtUtils.extractId(token);
        scheduleService.addAvailability(doctorId, request);
        return ResponseEntity.ok("Schedule added successfully");
    }

    @DeleteMapping("/scheduling-delete/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@RequestHeader("Authorization") String token, @PathVariable Long scheduleId) {
        Long doctorId = jwtUtils.extractId(token);
        scheduleService.removeAvailability(doctorId, scheduleId);
        return ResponseEntity.ok("Schedule deleted successfully");
    }

}
