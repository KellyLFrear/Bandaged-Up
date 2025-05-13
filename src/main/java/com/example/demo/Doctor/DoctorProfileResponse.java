package com.example.demo.Doctor;

import com.example.demo.Scheduling.Appointment;

import java.util.List;

public class DoctorProfileResponse {
    private String firstName;
    private String lastName;
    private List<Appointment> appointments;

    public DoctorProfileResponse() {
    }
    public DoctorProfileResponse(String firstName, String lastName, List<Appointment> appointments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.appointments = appointments;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
