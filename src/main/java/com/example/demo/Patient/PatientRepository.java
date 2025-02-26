package com.example.demo.Patient;

import com.example.demo.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByUser(User user);
}
