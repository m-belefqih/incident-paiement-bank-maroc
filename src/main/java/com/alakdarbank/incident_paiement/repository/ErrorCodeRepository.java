package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ErrorCodeRepository extends JpaRepository<ErrorCode,Long> {

    // Add a custom query to find ErrorCode by code
    @Query("SELECT c FROM ErrorCode c WHERE c.code = :code")
    ErrorCode findByCode(int code);
}
