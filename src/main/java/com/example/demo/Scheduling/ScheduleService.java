package com.example.demo.Scheduling;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    // Helper function to check if a new schedule being added overlaps with an already existing schedule
    public boolean isOverlapping(LocalTime requestStartTime, LocalTime requestEndTime, LocalTime existingStartTime, LocalTime existingEndTime) {
        return !(requestEndTime.isBefore(existingStartTime) || requestStartTime.isAfter(existingEndTime));
    }

    public void addAvailability(Long doctorId, ScheduleRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Check for overlaps
        List<Schedule> existing = scheduleRepository.findByDoctorIdAndDate(doctorId, request.getDate());

        for (Schedule s : existing) {
            if (isOverlapping(request.getStartTime(), request.getEndTime(), s.getStartTime(), s.getEndTime())) {
                throw new RuntimeException("Overlapping schedule exists for this time slot.");
            }
        }

        Schedule schedule = new Schedule(doctor, request.getDate(), request.getStartTime(), request.getEndTime(), request.getAppointmentType());

        scheduleRepository.save(schedule);
    }

    public List<Schedule> getDoctorSchedule(Long doctorId) {
        return scheduleRepository.findByDoctorId(doctorId);
    }

    public void removeAvailability(Long doctorId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        if (!schedule.getDoctor().getId().equals(doctorId)) {
            throw new RuntimeException("Unauthorized attempt to remove schedule");
        }

        scheduleRepository.delete(schedule);
    }
}
