package com.example.demo.Patient;

import com.example.demo.Messaging.Message;
import com.example.demo.Scheduling.Appointment;

import java.util.List;

public class PatientProfileResponse {
    private String firstName;
    private String lastName;
    private Integer height;
    private Integer weight;
    private List<Appointment> appointments;
    private List<Message> messages;

    public PatientProfileResponse(String firstName, String lastName, Integer height, Integer weight, List<Appointment> appointments, List<Message> messages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.appointments = appointments;
        this.messages = messages;
    }

    public PatientProfileResponse() {}

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
    public Integer getHeight() {return height;}
    public void setHeight(Integer height) {this.height = height;}
    public Integer getWeight() {return weight;}
    public void setWeight(Integer weight) {this.weight = weight;}
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
}
