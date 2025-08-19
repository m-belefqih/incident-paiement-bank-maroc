package com.alakdarbank.incident_paiement.service.impl;

import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.repository.UtilisateurRepository;
import com.alakdarbank.incident_paiement.security.PasswordHashGenerator;
import com.alakdarbank.incident_paiement.service.UtilisateurService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true) // default: all methods are read-only
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Override
    public List<Utilisateur> findAll(){
        return utilisateurRepository.findAll();
    }

    /**
     * Searches for a user by their ID.
     *
     * @param ID the ID of the user to search for
     * @return the found user or null if no user is found
     */
    @Override
    public Utilisateur findById(long ID){
        return utilisateurRepository.findById(ID).orElse(null);
    }

    /**
     * Searches for a user by their username.
     *
     * @param username the username to search for
     * @return the found user or null if no user is found
     */
    @Override
    public Utilisateur findByUsername(String username){
        return utilisateurRepository.findByUsername(username);
    }

    /**
     * Searches for a user by their email.
     *
     * @param email the email of the user to search for
     * @return the found user or null if no user is found
     */
    @Override
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    @Transactional
    public void deleteById(Long id){
        utilisateurRepository.deleteById(id);
    }

    /**
     * Saves a new user by checking for email uniqueness and hashing the password.
     *
     * @param user the user to save
     * @throws Exception if the email is already in use
     */
    @Override
    @Transactional
    public void save(Utilisateur user) throws Exception {

        // Check email uniqueness
        if (utilisateurRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email déjà utilisée (Utilisateur non créé).");
        }

        // Hash password before saving
        user.setPassword(PasswordHashGenerator.generateHash(user.getPassword()));

        // Set default values for role and statue if not provided
        utilisateurRepository.save(user);
    }

    /**
     * Updates an existing user by checking for ID existence and re-hashing the password if modified.
     *
     * @param user the user to update
     * @throws RuntimeException if the user with the given ID does not exist
     */
    @Override
    @Transactional
    public void update(Utilisateur user) {
        Utilisateur user_db = findById(user.getId());
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

