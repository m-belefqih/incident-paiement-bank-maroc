package com.alakdarbank.incident_paiement.model;

import com.alakdarbank.incident_paiement.Enum.Role;
import com.alakdarbank.incident_paiement.Enum.Statue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String username;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    Statue statue=Statue.Activer;
    @Enumerated(EnumType.STRING)
    Role role;

}
