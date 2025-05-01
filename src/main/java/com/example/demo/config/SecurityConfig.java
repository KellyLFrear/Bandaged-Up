package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for now (optional)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll()  // Allow public access to register & login
                        .requestMatchers("/home/doctor").hasRole("DOCTOR")
                        .requestMatchers("/home/patient").hasRole("PATIENT")
                        .requestMatchers("/patients/upload").hasRole("PATIENT")
                        .requestMatchers("/doctors/upload").hasRole("DOCTOR")
                        .requestMatchers("/prescriptions/patients/prescriptions/list").hasRole("PATIENT")
                        .anyRequest().authenticated()  // All other endpoints require authentication
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before Spring Security's auth filter
                .formLogin(form -> form.disable()) // Disable default form-based login
                .httpBasic(basic -> basic.disable()); // Disable basic authentication

        return http.build();
    }
}
