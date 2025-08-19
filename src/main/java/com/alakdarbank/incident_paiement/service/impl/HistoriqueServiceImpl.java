package com.alakdarbank.incident_paiement.service.impl;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.model.Historique;
import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.repository.HistoriqueRepository;
import com.alakdarbank.incident_paiement.service.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HistoriqueServiceImpl implements HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    @Autowired
    public HistoriqueServiceImpl(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    /**
     * Saves the incident history for a given user.
     *
     * @param Historique A map containing account numbers as keys and CodeErreur objects as values.
     * @param user The user for whom the incident history is being saved.
     */
    @Override
    public void enregistement_d_historique(Map<String, CodeErreur> Historique, Utilisateur user, String filename) {
        LocalDate fileDate = extractDateFromFilename(filename);

        System.out.println("DEBUG - Filename: " + filename);
        System.out.println("DEBUG - Extracted date: " + fileDate);

        for (Map.Entry<String, CodeErreur> entry : Historique.entrySet()) {
            if (entry.getValue() != null) {  // Add null check
                Historique historique = new Historique();
                historique.setCodeerreur(entry.getValue());
                historique.setNumerodecompte(entry.getKey());
                historique.setDateerreur(fileDate);
                historique.setUtilisateur(user);
                historiqueRepository.save(historique);
            }
        }
    }

    private LocalDate extractDateFromFilename(String filename) {
        try {
            // Extract the date part from the filename (position 13-20 in the format CIP_CTR_001_001_20250702_730_2.bin)
            String datePart = filename.split("_")[4]; // Gets "20250702"

            // Parse the date string into a LocalDate
            return LocalDate.parse(datePart,
                    java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            // If there's any error parsing the date, return current date as fallback
            return LocalDate.now();
        }
    }

    /**
     * Retrieves the incident history for a given user.
     *
     * @param user The user whose incident history is to be retrieved.
     * @return A list of HistoriqueIncident objects associated with the user.
     */
    @Override
    public List<Historique> affichierHistorique(Utilisateur user) {
        return historiqueRepository.findByUtilisateurId(user.getId());
    }
}
