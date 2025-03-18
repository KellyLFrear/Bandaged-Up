package com.example.demo.Login;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.User.UserService;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

// This will be a service to log the user in
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private JwtUtils jwtUtils = new JwtUtils();

    public String loginUser(String username, String password) {
        // Step 1: Find user by username
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isEmpty()) {
            return "User not found";
        }

        // Extract user info
        User user = foundUser.get();
        String role = user.getRole();

        // Step 2: Verify password using BCrypt
        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            return "Wrong password";
        }

        // Step 3: Generate a token based on the user's role
        if ("PATIENT".equalsIgnoreCase(role)) {
            Patient patientData = patientRepository.findByUserId(user.getId());
            if (patientData == null) {
                return "Patient record not found";
            }
            Long patientId = patientData.getId();
            return jwtUtils.generatePatientToken(username, role, patientId);
        } else if ("DOCTOR".equalsIgnoreCase(role)) {
            Doctor doctorData = doctorRepository.findByUserId(user.getId());
            if (doctorData == null) {
                return "Doctor record not found";
            }
            Long doctorId = doctorData.getId();
            return jwtUtils.generateDoctorToken(username, role, doctorId);
        }

        // Step 4: Handle unknown roles
        return "Invalid role selection";
    }

}
