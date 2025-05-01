package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Locale;

@Component
public class JwtUtils {
    private String secretKey = "TeAm10'sSeCrEtKeY999fffINEEDTOMAKETHIS256bitssoIMjustTYPINGrandomnessNOW";  // Ensure this key is securely stored in production
    private long expirationTime = 1000 * 60 * 60 * 24;  // 1 day expiration time for the token (adjust as needed)

    // Method with patientId
    public String generatePatientToken(String username, String role, Long patientId) {
        // Create the signing key from the secret key
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // Set the current time and expiration time
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + expirationTime);  // Set expiration time for the JWT

        // Build the JWT token
        return Jwts.builder()
                .setSubject(username)  // Store the username as the subject of the JWT
                .claim("role", role)  // Add the role as a claim
                .claim("Id", patientId)  // Add the patientId as a claim
                .setIssuedAt(issuedAt)  // Set the issue time of the token
                .setExpiration(expiration)  // Set the expiration time of the token
                .signWith(key, SignatureAlgorithm.HS256)  // Sign the token using the HMAC SHA-256 algorithm
                .compact();  // Return the compact serialized JWT token
    }

    // Method with patientId
    public String generateDoctorToken(String username, String role, Long doctorId) {
        // Create the signing key from the secret key
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        role = role.toUpperCase(Locale.ROOT);

        // Set the current time and expiration time
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + expirationTime);  // Set expiration time for the JWT

        // Build the JWT token
        return Jwts.builder()
                .setSubject(username)  // Store the username as the subject of the JWT
                .claim("role", role)  // Add the role as a claim
                .claim("Id", doctorId)  // Add the patientId as a claim
                .setIssuedAt(issuedAt)  // Set the issue time of the token
                .setExpiration(expiration)  // Set the expiration time of the token
                .signWith(key, SignatureAlgorithm.HS256)  // Sign the token using the HMAC SHA-256 algorithm
                .compact();  // Return the compact serialized JWT token
    }


    public Claims parseToken(String token) {
        try {
            token = token.trim();

            // Check if the token starts with "Bearer " and remove it
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // Remove "Bearer " (7 characters)
            }

            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

            JwtParser parser = Jwts.parser()
                    .setSigningKey(key)
                    .build();

            return parser.parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    // Helper method to extract username from jwt
    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    // Helper method to extract the role from jwt
    public String extractRole(String token) {
        return parseToken(token).get("role", String.class);
    }

    // Helper method to extract the id from jwt
    public Long extractId(String token) {
        return parseToken(token).get("Id", Long.class);
    }

}
