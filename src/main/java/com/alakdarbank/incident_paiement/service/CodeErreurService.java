package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.repository.CodeErreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.thymeleaf.util.StringUtils.substring;

@Service
public class CodeErreurService {
    @Autowired
    private CodeErreurRepository codeErreurRepository;

    public List<Map<String, CodeErreur>> ListerErreur(MultipartFile file) throws IOException {
        List<Map<String, CodeErreur>> list_erreur_client = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                char firstChar = line.charAt(0);
                if (firstChar == 'H' || firstChar == 'F') {
                    continue;
                }
                if (line.length() >= 199) {
                    Map<String, CodeErreur> incident = new HashMap<>();

                    String sub = line.substring(194, 199).trim();  // code erreur
                    String sub2 = line.substring(43, 54).trim();  // numéro de compte
                    System.out.println(sub+","+sub2);
                    try {
                        incident.put(sub2,codeErreurRepository.findByCode(Integer.parseInt(sub)) );
                    }catch (Exception e){
                        continue;
                    }
                    list_erreur_client.add(incident);
                }
            }
        }
        return list_erreur_client;
    }


}