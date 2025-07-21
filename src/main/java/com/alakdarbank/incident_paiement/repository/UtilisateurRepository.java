package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{

    boolean existsByUsername(String username);
    Utilisateur findByUsername(String username);
}
