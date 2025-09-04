package com.alakdarbank.incident_paiement.service.impl;

import com.alakdarbank.incident_paiement.model.ErrorCode;
import com.alakdarbank.incident_paiement.model.History;
import com.alakdarbank.incident_paiement.model.User;
import com.alakdarbank.incident_paiement.repository.HistoryRepository;
import com.alakdarbank.incident_paiement.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    /**
     * Saves the incident history for a given user.
     *
     * @param Historique A map containing account numbers as keys and ErrorCode objects as values.
     * @param user The user for whom the incident history is being saved.
     */
    @Override
    public void saveHistory(Map<String, ErrorCode> Historique, User user, String filename) {
        LocalDate fileDate = extractDateFromFilename(filename);

        System.out.println("DEBUG - Filename: " + filename);
        System.out.println("DEBUG - Extracted date: " + fileDate);

        for (Map.Entry<String, ErrorCode> entry : Historique.entrySet()) {
            if (entry.getValue() != null) {  // Add null check
                History history = new History();
                history.setErrorCode(entry.getValue());
                history.setAccountNumber(entry.getKey());
                history.setErrorDate(fileDate);
                history.setUser(user);
                historyRepository.save(history);
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
    public List<History> getHistory(User user) {
        return historyRepository.findByUserId(user.getId());
    }
}
