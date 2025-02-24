package com.example.demo.Doctor;

import com.example.demo.User.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)     // Foreign key reference to 'users' table
    private User user;

    private String firstName;
    private String lastName;
    private String specialty;
    private String license_number;

    public Doctor() {
    }

    public Doctor(User user, String firstName, String lastName, String specialty, String license_number) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.license_number = license_number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialty='" + specialty + '\'' +
                ", license_number='" + license_number + '\'' +
                '}';
    }
}
