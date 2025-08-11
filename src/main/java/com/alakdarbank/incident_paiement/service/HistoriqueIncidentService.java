package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.model.Historique;
import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.repository.HistoriqueIncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HistoriqueIncidentService {

    private final HistoriqueIncidentRepository historiqueIncidentRepository;

    @Autowired
    public HistoriqueIncidentService(HistoriqueIncidentRepository historiqueIncidentRepository) {
        this.historiqueIncidentRepository = historiqueIncidentRepository;
    }

    /**
     * Saves the incident history for a given user.
     *
     * @param Historique A map containing account numbers as keys and CodeErreur objects as values.
     * @param user The user for whom the incident history is being saved.
     */
    public void enregistement_d_historique(Map<String, CodeErreur> Historique, Utilisateur user) {
        for (Map.Entry<String, CodeErreur> entry : Historique.entrySet()) {
            if (entry.getValue() != null) {  // Add null check
                Historique historique = new Historique();
                historique.setCodeerreur(entry.getValue());
                historique.setNumerodecompte(entry.getKey());
                historique.setDateerreur(LocalDate.now());
                historique.setUtilisateur(user);
                historiqueIncidentRepository.save(historique);
            }
        }
    }
    /**
     * Retrieves the incident history for a given user.
     *
     * @param user The user whose incident history is to be retrieved.
     * @return A list of HistoriqueIncident objects associated with the user.
     */
    public List<Historique> affichierHistorique(Utilisateur user) {
        return historiqueIncidentRepository.findByUtilisateurId(user.getId());
    }
}
