package com.alakdarbank.incident_paiement.controller;

import com.alakdarbank.incident_paiement.security.PasswordHashGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    /**
     * This method is used to display the login page.
     * It generates a password hash for the default password "admin"
     * and prints it to the console for testing purposes.
     * The objective is to provide a simple login interface
     * while also demonstrating how to generate a password hash for a new user.
     */
    @GetMapping("/login")
    public String login() {

        // Generate and print the password hash for the default password "admin"
        System.out.println(PasswordHashGenerator.generateHash("admin"));

        return "login"; // Return the name of the login view (e.g., login.html)
    }
}