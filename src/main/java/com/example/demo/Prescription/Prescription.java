package com.example.demo.Prescription;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Patient.Patient;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates ID
    @Column(name = "prescription_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = true)
    private Doctor prescribing_doctor;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    private String medication_name;
    private String dosage;
    private String frequency;
    private LocalDate start_date;
    private LocalDate end_date;
    private String notes;


    // Constructor without a prescribing docctor (from before they signed up with bandaged up)
    public Prescription(Patient patient, String medication_name, String dosage, String frequency, LocalDate start_date, LocalDate end_date, String notes) {
        this.patient = patient;
        this.medication_name = medication_name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.start_date = start_date;
        this.end_date = end_date;
        this.notes = notes;
    }

    // Constructor for doctors only
    public Prescription(Patient patient, String medication_name, String dosage, String frequency, LocalDate start_date, LocalDate end_date, String notes, Doctor prescribing_doctor) {
        this(patient, medication_name, dosage, frequency, start_date, end_date, notes);
        this.prescribing_doctor = prescribing_doctor;
    }

    public Prescription() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getMedication_name() {
        return medication_name;
    }

    public void setMedication_name(String medication_name) {
        this.medication_name = medication_name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Doctor getPrescribing_doctor() {
        return prescribing_doctor;
    }

    public void setPrescribing_doctor(Doctor prescribing_doctor) {
        this.prescribing_doctor = prescribing_doctor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
