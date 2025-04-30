package com.example.demo.Doctor;

import com.example.demo.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserService userService;

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
}
