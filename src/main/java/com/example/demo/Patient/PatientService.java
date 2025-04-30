package com.example.demo.Patient;

import com.example.demo.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;

    public String updatePatientInsuranceInformation(Long patientId, String InsuranceInformation) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        // Check if patient is present
        if (patient.isPresent()) {
            Patient p = patient.get();
            p.setInsurance_number(InsuranceInformation);
            patientRepository.save(p);
        } else {
            return "Patient not found";
        }
        return "Insurance information updated successfully";
    }



}
