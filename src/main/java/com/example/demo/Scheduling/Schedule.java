package com.example.demo.Scheduling;

import ch.qos.logback.core.status.Status;
import com.example.demo.Doctor.Doctor;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="Schedule", uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id", "date", "start_time", "end_time"}))
public class Schedule {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name="date")
    private LocalDate date;

    @Column(name="start_time")
    private LocalTime startTime;

    @Column(name="end_time")
    private LocalTime endTime;

    @Column(name="apt_type")
    private String aptType;

    public Schedule(Doctor doctor, LocalDate date, LocalTime startTime, LocalTime endTime, String appointment_type) {
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.aptType = appointment_type;
    }

    public Schedule() {

    }

    public String getAppointment_type() {
        return aptType;
    }

    public void setAppointment_type(String appointment_type) {
        this.aptType = appointment_type;
    }

    public long getScheduleId() {
        return id;
    }

    public void setScheduleId(long scheduleId) {
        this.id = scheduleId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
