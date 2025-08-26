package com.alakdarbank.incident_paiement.service.impl;

import com.alakdarbank.incident_paiement.model.ErrorCode;
import com.alakdarbank.incident_paiement.model.User;
import com.alakdarbank.incident_paiement.repository.ErrorCodeRepository;
import com.alakdarbank.incident_paiement.service.ErrorCodeService;
import com.alakdarbank.incident_paiement.service.HistoryService;
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
public class ErrorCodeServiceImpl implements ErrorCodeService {
    private final ErrorCodeRepository errorCodeRepository;
    private final HistoryService historyService;

    @Autowired
    public ErrorCodeServiceImpl(ErrorCodeRepository errorCodeRepository, HistoryService historyService) {
        this.errorCodeRepository = errorCodeRepository;
        this.historyService = historyService;
    }

    @Override
    public List<Map<String, ErrorCode>> listErrors(MultipartFile file, User user) throws IOException {
        List<Map<String, ErrorCode>> list_erreur_client = new ArrayList<>();
        String filename = file.getOriginalFilename();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = br.readLine()) != null) {
                char firstChar = line.charAt(0);

                if (firstChar == 'H' || firstChar == 'F') {
                    continue;
                }

                if (line.length() >= 199) {
                    Map<String, ErrorCode> incidents = new HashMap<>();

                    String sub = line.substring(194, 199).trim();  // code erreur
                    String sub2 = line.substring(43, 54).trim();  // numéro de compte

                    // Debugging output
                    System.out.println(sub + "," + sub2);

                    try {
                        ErrorCode errorCode = errorCodeRepository.findByCode(Integer.parseInt(sub));
                        if (errorCode != null) {
                            incidents.put(sub2, errorCode);
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                    historyService.saveHistory(incidents, user, filename);
                    list_erreur_client.add(incidents);
                }
            }
        }
        return list_erreur_client;
    }
}