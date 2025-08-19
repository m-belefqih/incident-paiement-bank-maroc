package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{

    // Adding custom methods

    Utilisateur findByUsername(String username);

    Utilisateur findByEmail(String email);
}
