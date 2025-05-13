package com.example.demo.Messaging;

import com.example.demo.Doctor.Doctor;
import com.example.demo.Doctor.DoctorRepository;
import com.example.demo.Patient.Patient;
import com.example.demo.Patient.PatientRepository;
import com.example.demo.security.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    public Message sendMessage(MessageRequest request, Claims claims) {
        String role = (String) claims.get("role");
        Long senderId = ((Number) claims.get("Id")).longValue();

        Message message;

        if ("DOCTOR".equalsIgnoreCase(role)) {
            Doctor doctor = doctorRepository.findByUserId(senderId);
            Long doctorId = doctor.getId();
            Patient patient = patientRepository.findByUserId(request.getRecipientId());
            Long patientId = patient.getId();
            message = Message.builder()
                    .doctorId(doctorId)
                    .patientId(patientId)
                    .content(request.getContent())
                    .timestamp(LocalDateTime.now())
                    .build();
        } else if ("PATIENT".equalsIgnoreCase(role)) {
            Patient patient = patientRepository.findByUserId(senderId);
            Long patientId = patient.getId();
            Doctor doctor = doctorRepository.findByUserId(request.getRecipientId());
            Long doctorId = doctor.getId();
            message = Message.builder()
                    .patientId(patientId)
                    .doctorId(doctorId)
                    .content(request.getContent())
                    .timestamp(LocalDateTime.now())
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid role in token");
        }

        return messageRepository.save(message);
    }


    public ResponseEntity<List<Message>> getAll(String token) {
        Long userId = jwtUtils.extractId(token);
        String role = jwtUtils.extractRole(token); // assuming you have this method

        List<Message> messages;

        if ("DOCTOR".equals(role)) {
            Doctor doctor = doctorRepository.findByUserId(userId);
            Long doctorId = doctor.getId();
            messages = messageRepository.findByDoctorId(doctorId);
        } else if ("PATIENT".equals(role)) {
            Patient patient = patientRepository.findByUserId(userId);
            Long patientId = patient.getId();
            messages = messageRepository.findByPatientId(patientId);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // or 400 Bad Request
        }

        return ResponseEntity.ok(messages);
    }
}
