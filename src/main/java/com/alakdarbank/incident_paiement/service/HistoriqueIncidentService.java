package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.model.HistoriqueIncident;
import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.repository.HistoriqueIncidentRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HistoriqueIncidentService {
    @Autowired
    private HistoriqueIncidentRepository historiqueIncidentRepository;
    public void enregistement_d_historique(Map<String, CodeErreur> Historique, Utilisateur user) {
        for (Map.Entry<String, CodeErreur> entry : Historique.entrySet()) {
            if (entry.getValue() != null) {  // Add null check
                HistoriqueIncident historiqueIncident = new HistoriqueIncident();
                historiqueIncident.setCodeerreur(entry.getValue());
                historiqueIncident.setNumerodecompte(entry.getKey());
                historiqueIncident.setDateerreur(LocalDate.now());
                historiqueIncident.setUtilisateur(user);
                historiqueIncidentRepository.save(historiqueIncident);
            }
        }
    }
    public List<HistoriqueIncident> affichierHistorique(Utilisateur user) {
        return historiqueIncidentRepository.findByUtilisateurId(user.getId());
    }
}
