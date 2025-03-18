package com.example.demo.Prescription;
import com.example.demo.Prescription.Prescription;
import java.time.LocalDate;

public class PrescriptionDTO {
    private String medicationName;
    private String dosage;
    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;

    public PrescriptionDTO(String medicationName, String dosage, String frequency, LocalDate startDate, LocalDate endDate, String notes) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public static PrescriptionDTO fromEntity(Prescription prescription) {
        return new PrescriptionDTO(
                prescription.getMedication_name(),
                prescription.getDosage(),
                prescription.getFrequency(),
                prescription.getStart_date(),
                prescription.getEnd_date(),
                prescription.getNotes()
        );
    }

    // Getters and Setters

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
