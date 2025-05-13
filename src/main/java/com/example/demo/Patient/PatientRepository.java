package com.example.demo.Patient;

import com.example.demo.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByUser(User user);

    Patient findByUserId(long userId);

    Patient findById(long id);

    long user(User user);
}
