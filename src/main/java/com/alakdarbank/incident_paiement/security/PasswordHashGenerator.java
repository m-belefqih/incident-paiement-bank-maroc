package com.alakdarbank.incident_paiement.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Méthode statique pour générer le hash d'un mot de passe
    public static String generateHash(String rawPassword) {
        return encoder.encode(rawPassword);
    }


}