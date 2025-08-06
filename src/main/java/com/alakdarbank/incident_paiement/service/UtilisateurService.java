package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.repository.UtilisateurRepository;
import com.alakdarbank.incident_paiement.security.PasswordHashGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    /**
     * Récupère tous les utilisateurs.
     *
     * @return une liste de tous les utilisateurs
     */
    public List<Utilisateur> getAllUsers(){
        return utilisateurRepository.findAll();
    }

    /**
     * Cherche un utilisateur par son ID.
     *
     * @param ID l'ID de l'utilisateur à chercher
     * @return l'utilisateur trouvé ou null si aucun utilisateur n'est trouvé
     */
    public Utilisateur chercherparid(long ID){
        return utilisateurRepository.findById(ID).orElse(null);
    }

    /**
     * Cherche un utilisateur par son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur à chercher
     * @return l'utilisateur trouvé ou null si aucun utilisateur n'est trouvé
     */
    public Utilisateur chercherparnom(String username){
        return utilisateurRepository.findByUsername(username);
    }

    /**
     * Cherche un utilisateur par son email.
     *
     * @param email l'email de l'utilisateur à chercher
     * @return l'utilisateur trouvé ou null si aucun utilisateur n'est trouvé
     */
    public Utilisateur chercherparEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public void ajouter_user(Utilisateur user) throws Exception {
        // Vérifier si le username existe déjà
        if (utilisateurRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Nom d'utilisateur déjà pris ( Utilisateur non cree ) ");
        }


        // Hash du mot de passe avant sauvegarde
        user.setPassword(PasswordHashGenerator.generateHash(user.getPassword()));
        utilisateurRepository.save(user);
    }
    public void supprimer_user(Long id){
        utilisateurRepository.deleteById(id);
    }

    @Transactional
    public void modifier_user(Utilisateur user) {
        Utilisateur user_db = chercherparid(user.getId());
        if (user_db == null) {
            throw new RuntimeException("Utilisateur non trouvé avec ID: " + user.getId());
        }

        // Username
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            user_db.setUsername(user.getUsername());
        }

        // Password (toujours re-hasher si modifié)
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user_db.setPassword(PasswordHashGenerator.generateHash(user.getPassword()));
        }

        // Role
        if (user.getRole() != null) {
            user_db.setRole(user.getRole());
        }

        // Statue
        if (user.getStatue() != null) {
            user_db.setStatue(user.getStatue());
        }

        // Email
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            user_db.setEmail(user.getEmail());
        }

        utilisateurRepository.save(user_db);
    }


}

