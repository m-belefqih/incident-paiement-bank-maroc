package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.Utilisateur;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> findAll();

    Utilisateur findById(long ID);

    Utilisateur findByUsername(String username);

    Utilisateur findByEmail(String email);

     void deleteById(Long id);

     void save(Utilisateur user) throws Exception;

     void update(Utilisateur user);
}

