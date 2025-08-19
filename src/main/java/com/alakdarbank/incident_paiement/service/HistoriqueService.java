package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.model.Historique;
import com.alakdarbank.incident_paiement.model.Utilisateur;

import java.util.List;
import java.util.Map;

public interface HistoriqueService {

    void enregistement_d_historique(Map<String, CodeErreur> historiqueMap, Utilisateur user, String filename);

    List<Historique> affichierHistorique(Utilisateur user);
}