package com.alakdarbank.incident_paiement.model;

import com.alakdarbank.incident_paiement.Enum.Role;
import com.alakdarbank.incident_paiement.Enum.Statue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false, unique = true)
    String email;

    String password;

    @Enumerated(EnumType.STRING)
    Statue statue=Statue.Activer;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Historique> Historique = new ArrayList<>();
}
