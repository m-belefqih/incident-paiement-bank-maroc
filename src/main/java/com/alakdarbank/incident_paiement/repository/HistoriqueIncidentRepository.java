package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.HistoriqueIncident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueIncidentRepository extends JpaRepository<HistoriqueIncident, Long> {
    List<HistoriqueIncident> findByUtilisateurId(Long utilisateurId);
}
