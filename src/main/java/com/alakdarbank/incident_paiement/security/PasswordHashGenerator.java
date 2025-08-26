package com.alakdarbank.incident_paiement.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Method static to generate the hash of a password
    public static String generateHash(String rawPassword) {

        return encoder.encode(rawPassword);
    }
}