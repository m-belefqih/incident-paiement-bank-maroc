package com.al_akdar_bank_.solution_ctr.Repository;

import com.al_akdar_bank_.solution_ctr.Model.CodeErreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodeErreurRepository extends JpaRepository<CodeErreur,Long> {
    @Query("SELECT c FROM CodeErreur c WHERE c.code = :code")
    CodeErreur findByCode(int code);
}
