package com.example.demo.Patient;

import com.example.demo.Messaging.Message;
import com.example.demo.Messaging.MessageRepository;
import com.example.demo.Scheduling.Appointment;
import com.example.demo.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.Scheduling.AppointmentRepository;
import com.example.demo.Scheduling.AppointmentService;
import com.example.demo.security.JwtUtils;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MessageRepository messageRepository;

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

    public PatientProfileResponse getPatientProfile(String token) {
        Long userId = jwtUtils.extractId(token);
        Patient patient = patientRepository.findByUserId(userId);
        if (patient == null) {
            return null;
        }
        List<Appointment> appointmentList = appointmentRepository.findByPatientId(patient.getId());
        List<Message> messageList = messageRepository.findByPatientId(patient.getId());
        PatientProfileResponse patientProfileResponse = new PatientProfileResponse();
        patientProfileResponse.setFirstName(patient.getFirst_name());
        patientProfileResponse.setLastName(patient.getLast_name());
        patientProfileResponse.setHeight(patient.getHeight());
        patientProfileResponse.setWeight(patient.getWeight());
        patientProfileResponse.setAppointments(appointmentList);
        patientProfileResponse.setMessages(messageList);

        return patientProfileResponse;
    }

    public String updatePatientInformation(Long userId, String firstName, String lastName, String gender, Integer height, Integer weight, String insurance_number) {
        Patient patient = patientRepository.findByUserId(userId);
        if (patient == null) {
            return "Patient not found";
        }
        // Only update fields that are not null
        if (firstName != null && !firstName.isBlank()) patient.setFirst_name(firstName);
        if (lastName != null && !lastName.isBlank()) patient.setLast_name(lastName);
        if (gender != null && !gender.isBlank()) patient.setGender(gender);
        if (height != null) patient.setHeight(height);
        if (weight != null) patient.setWeight(weight);
        if (insurance_number != null && !insurance_number.isBlank()) patient.setInsurance_number(insurance_number);

        patientRepository.save(patient);
        return "Patient information updated successfully";
    }



}
