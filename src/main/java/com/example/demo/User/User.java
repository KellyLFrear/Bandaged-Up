package com.example.demo.User;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password_hashed;
    private String email;
    private Timestamp created_at;
    private LocalDate date_of_birth;

    public User() {
    }

    public User(String username, String password_hashed, String email, Timestamp created_at, LocalDate date_of_birth) {
        this.username = username;
        this.password_hashed = password_hashed;
        this.email = email;
        this.created_at = created_at;
        this.date_of_birth = date_of_birth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return password_hashed;
    }

    public void setHashedPassword(String hashed_password) {
        this.password_hashed = hashed_password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public LocalDate getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(LocalDate date_of_birth) {
        this.date_of_birth = this.date_of_birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", hashedPassword='" + password_hashed + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + created_at +
                ", DateOfBirth=" + date_of_birth +
                '}';
    }
}
