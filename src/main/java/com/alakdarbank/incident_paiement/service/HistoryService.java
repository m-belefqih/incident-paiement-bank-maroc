package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.ErrorCode;
import com.alakdarbank.incident_paiement.model.History;
import com.alakdarbank.incident_paiement.model.User;

import java.util.List;
import java.util.Map;

public interface HistoryService {

    void saveHistory(Map<String, ErrorCode> historyMap, User user, String filename);

    List<History> getHistory(User user);
}