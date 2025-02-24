package com.example.demo.Registration;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;


    // Password hashing encoder for safe storage
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Transactional
    public void registerUser(String username, String password, String email, LocalDate dateOfBirth, String role, String firstName, String lastName, String specialtyOrInsurance, String licenseNumberOrInsuranceNumber) {
        // Step 1: Hash the user's password
        String passwordHashed = encoder.encode(password);

        // Step 2: Create User (but only commit if the full process succeeds)
        User user = new User(username, passwordHashed, email, Timestamp.valueOf(LocalDateTime.now()), dateOfBirth);
        user = userRepository.save(user);  // Save and get the generated 'id'

        // Step 3: Create the corresponding Doctor or Patient record
        if (role.equalsIgnoreCase("doctor")) {
            if (specialtyOrInsurance == null || licenseNumberOrInsuranceNumber == null) {
                throw new IllegalArgumentException("Doctor registration requires specialty and license number.");
            }
            Doctor doctor = new Doctor(user, firstName, lastName, specialtyOrInsurance, licenseNumberOrInsuranceNumber);
            doctorRepository.save(doctor);
        } else if (role.equalsIgnoreCase("patient")) {
            if (licenseNumberOrInsuranceNumber == null) {
                throw new IllegalArgumentException("Patient registration requires insurance number.");
            }
            Patient patient = new Patient(user, firstName, lastName, licenseNumberOrInsuranceNumber);
            patientRepository.save(patient);
        } else {
            throw new IllegalArgumentException("Invalid role specified. Must be 'doctor' or 'patient'.");
        }
    }


}
