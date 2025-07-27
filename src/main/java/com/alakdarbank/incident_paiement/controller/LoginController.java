package com.alakdarbank.incident_paiement.controller;

import com.alakdarbank.incident_paiement.security.PasswordHashGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        System.out.println(PasswordHashGenerator.generateHash("admin"));
        return "login";
    }
}