package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodeErreurRepository extends JpaRepository<CodeErreur,Long> {
    @Query("SELECT c FROM CodeErreur c WHERE c.code = :code")
    CodeErreur findByCode(int code);
}
