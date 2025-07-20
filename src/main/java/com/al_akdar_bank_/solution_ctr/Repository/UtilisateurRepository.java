package com.al_akdar_bank_.solution_ctr.Repository;

import com.al_akdar_bank_.solution_ctr.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{

    boolean existsByUsername(String username);
    Utilisateur findByUsername(String username);
}
