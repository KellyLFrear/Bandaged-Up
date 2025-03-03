package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private String secretKey = "TeAm10'sSeCrEtKeY999fffINEEDTOMAKETHIS256bitssoIMjustTYPINGrandomnessNOW";  // Ensure this key is securely stored in production
    private long expirationTime = 1000 * 60 * 60 * 24;  // 1 day expiration time for the token (adjust as needed)

    public String generateToken(String username, String role) {
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
                .setIssuedAt(issuedAt)  // Set the issue time of the token
                .setExpiration(expiration)  // Set the expiration time of the token
                .signWith(key, SignatureAlgorithm.HS256)  // Sign the token using the HMAC SHA-256 algorithm
                .compact();  // Return the compact serialized JWT token
    }

    public Claims parseToken(String token) {
        try {
            // Convert the secret key to a SecretKey instance
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

            JwtParser parser = Jwts.parser()  // Correct method to build a parser
                    .setSigningKey(key)  // Set the signing key
                    .build();

            // Parse the token and retrieve claims
            return parser.parseClaimsJws(token).getBody();  // Extract the claims from the token

        } catch (SignatureException e) {
            // Handle invalid signature exception (token has been tampered with)
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (Exception e) {
            // Handle other exceptions such as expired token
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
}
