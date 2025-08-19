package com.alakdarbank.incident_paiement.service.impl;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.repository.CodeErreurRepository;
import com.alakdarbank.incident_paiement.service.CodeErreurService;
import com.alakdarbank.incident_paiement.service.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeErreurServiceImpl implements CodeErreurService {
    private final CodeErreurRepository codeErreurRepository;
    private final HistoriqueService historiqueService;

    @Autowired
    public CodeErreurServiceImpl(CodeErreurRepository codeErreurRepository, HistoriqueService historiqueService) {
        this.codeErreurRepository = codeErreurRepository;
        this.historiqueService = historiqueService;
    }

    @Override
    public List<Map<String, CodeErreur>> ListerErreur(MultipartFile file, Utilisateur user) throws IOException {
        List<Map<String, CodeErreur>> list_erreur_client = new ArrayList<>();
        String filename = file.getOriginalFilename();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = br.readLine()) != null) {
                char firstChar = line.charAt(0);

                if (firstChar == 'H' || firstChar == 'F') {
                    continue;
                }

                if (line.length() >= 199) {
                    Map<String, CodeErreur> incidents = new HashMap<>();

                    String sub = line.substring(194, 199).trim();  // code erreur
                    String sub2 = line.substring(43, 54).trim();  // numéro de compte

                    // Debugging output
                    System.out.println(sub + "," + sub2);

                    try {
                        CodeErreur codeErreur = codeErreurRepository.findByCode(Integer.parseInt(sub));
                        if (codeErreur != null) {
                            incidents.put(sub2, codeErreur);
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                    historiqueService.enregistement_d_historique(incidents, user, filename);
                    list_erreur_client.add(incidents);
                }
            }
        }
        return list_erreur_client;
    }
}