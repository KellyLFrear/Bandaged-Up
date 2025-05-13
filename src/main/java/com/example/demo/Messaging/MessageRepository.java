package com.example.demo.Messaging;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByDoctorIdAndPatientId(Long doctorId, Long patientId);
    List<Message> findByPatientId(Long patientId);
    List<Message> findByDoctorId(Long doctorId);
}
