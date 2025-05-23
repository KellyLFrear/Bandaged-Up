package com.example.demo.Prescription;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Patient uploads their past prescription (No doctor involved from bandaged up)
    public Prescription addPatientPrescription(Long patientId, String medication_name, String dosage, String frequency, LocalDate start_date, LocalDate end_date, String notes) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        Patient pat = patient.get();

        Prescription prescription = new Prescription(pat, medication_name, dosage, frequency, start_date, end_date, notes);
        return prescriptionRepository.save(prescription);
    }

    // Doctors upload prescriptions for their patients
    public Prescription addDoctorPrescription(Long patientId, String medication_name, String dosage, String frequency, LocalDate start_date, LocalDate end_date, String notes, Long doctorId) {
        Doctor doctor = doctorRepository.findByUserId(doctorId);
        Patient patient = patientRepository.findByUserId(patientId);


        Prescription prescription = new Prescription(patient, medication_name, dosage, frequency, start_date, end_date, notes, doctor);
        return prescriptionRepository.save(prescription);
    }

    // Retrieve prescriptions for a patient
    public List<Prescription> getPrescriptions(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }


}
