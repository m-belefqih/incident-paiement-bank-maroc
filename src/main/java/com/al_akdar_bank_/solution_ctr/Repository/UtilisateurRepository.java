package com.al_akdar_bank_.solution_ctr.Repository;

import com.al_akdar_bank_.solution_ctr.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{
    Utilisateur findByUsername(String username);
    boolean existsByUsername(String username);
}
