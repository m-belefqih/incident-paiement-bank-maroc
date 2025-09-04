package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    // Add a custom method to find all History entries by User ID
    List<History> findByUserId(Long userId);
}
