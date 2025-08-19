package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.model.Utilisateur;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CodeErreurService {
    List<Map<String, CodeErreur>> ListerErreur(MultipartFile file, Utilisateur user) throws IOException;
}