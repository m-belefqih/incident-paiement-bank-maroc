package com.alakdarbank.incident_paiement.model;

import com.alakdarbank.incident_paiement.Enum.Role;
import com.alakdarbank.incident_paiement.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // avoid reserved keyword "user" in postgres
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire.")
    @Column(nullable = false)
    private String username;

    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@(alakhdarbank\\.ma|gmail\\.com)$",
        message = "L'adresse e-mail doit appartenir à alakhdarbank.ma ou gmail.com."
    )
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> history = new ArrayList<>();
}