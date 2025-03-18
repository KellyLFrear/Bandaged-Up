package com.example.demo.Doctor;

import com.example.demo.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByUser(User user);

    Doctor findByUserId(long id);
}
