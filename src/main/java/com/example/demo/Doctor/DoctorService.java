package com.example.demo.Doctor;

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
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public String updateDoctorInformation(String firstName, String lastName, String specialty, String licenseNumber) {
        Optional<Doctor> doctor = doctorRepository.findByFirstNameAndLastName(firstName, lastName);


        // Check if doctor exists
        if (doctor.isPresent()) {
            Doctor d = doctor.get();
            d.setSpecialty(specialty);
            d.setLicense_number(licenseNumber);
            doctorRepository.save(d);
            return "Doctor information updated successfully";
        } else {
            return "Doctor not found";
        }
    }
    public DoctorProfileResponse getDoctorProfile(String token) {
        Long userId = jwtUtils.extractId(token);
        Doctor doctor = doctorRepository.findByUserId(userId);

        if (doctor == null) {
            return null;
        }

        List<Appointment> appointmentList = appointmentRepository.findByDoctorId(doctor.getId());
        DoctorProfileResponse doctorProfileResponse = new DoctorProfileResponse();
        doctorProfileResponse.setFirstName(doctor.getFirstName());
        doctorProfileResponse.setLastName(doctor.getLastName());
        doctorProfileResponse.setAppointments(appointmentList);

        return doctorProfileResponse;
    }
}
