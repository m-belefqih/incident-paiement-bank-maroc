package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

    // Add a custom method to find all Historique entries by Utilisateur ID
    List<Historique> findByUtilisateurId(Long utilisateurId);
}
