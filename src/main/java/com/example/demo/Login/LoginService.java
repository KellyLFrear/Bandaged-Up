package com.example.demo.Login;

import com.example.demo.Doctor.DoctorRepository;
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
            return ("User not found");
        }


        User user = foundUser.get();

        // Step 2: Verify password using BCrypt
        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            return ("Wrong password");
        }

        String jwtToken = jwtUtils.generateToken(username, user.getRole());
        return jwtToken;

        // Step 3: Check the user's role
        /*
        if (doctorRepository.existsByUser(user)) {
            return ResponseEntity.ok("Welcome, Doctor " + user.getUsername() + "!");
        } else if (patientRepository.existsByUser(user)) {
            return ResponseEntity.ok("Welcome, Patient " + user.getUsername() + "!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid role selection.");
        }
         */


    }

}
