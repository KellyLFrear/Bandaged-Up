package com.example.demo.Scheduling;

import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepo, ScheduleRepository scheduleRepo, PatientRepository patientRepo) {
        this.appointmentRepository = appointmentRepo;
        this.scheduleRepository = scheduleRepo;
        this.patientRepository = patientRepo;
    }

    public Appointment bookAppointment(Long patientId, AppointmentRequest request) {
        Schedule corresponding_schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!appointmentRepository.findByScheduleId(request.getScheduleId()).isEmpty()) {
            throw new RuntimeException("Appointment already booked for this schedule");
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setSchedule(corresponding_schedule);
        appointment.setDate(corresponding_schedule.getDate());
        appointment.setStartTime(corresponding_schedule.getStartTime());
        appointment.setEndTime(corresponding_schedule.getEndTime());
        appointment.setStatus("b");
        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getAllAppointmentsForPatient(Long patientId) {
        // Search for list of all appointments made by user
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Schedule> getAvailableSchedules() {
        List<Long> bookedScheduleIds = appointmentRepository.findAll().stream()
                .map(a -> a.getSchedule().getScheduleId())
                .collect(Collectors.toList());

        return scheduleRepository.findAll().stream()
                .filter(s -> !bookedScheduleIds.contains(s.getScheduleId()))
                .collect(Collectors.toList());
    }

    public List<Schedule> getAvailableSchedulesByType(String aptType) {
        return scheduleRepository.findByAptType(aptType);
    }
    public List<Schedule> getAvailableSchedulesByDate(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }
    public List<Schedule> getSchedulesByTypeAndDate(LocalDate date, String aptType) {
        return scheduleRepository.findByAptTypeAndDate(aptType, date);
    }






}
