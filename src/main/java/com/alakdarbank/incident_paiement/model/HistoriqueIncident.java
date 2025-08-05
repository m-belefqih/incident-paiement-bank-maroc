package com.alakdarbank.incident_paiement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueIncident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numerodecompte;
    private LocalDate dateerreur;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codeerreur_id")
    private CodeErreur codeerreur;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private Utilisateur utilisateur;

}
