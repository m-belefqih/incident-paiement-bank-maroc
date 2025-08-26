package com.alakdarbank.incident_paiement.service.impl;

import com.alakdarbank.incident_paiement.model.User;
import com.alakdarbank.incident_paiement.repository.UserRepository;
import com.alakdarbank.incident_paiement.security.PasswordHashGenerator;
import com.alakdarbank.incident_paiement.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true) // default: all methods are read-only
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    /**
     * Searches for a user by their ID.
     *
     * @param ID the ID of the user to search for
     * @return the found user or null if no user is found
     */
    @Override
    public User findById(long ID){
        return userRepository.findById(ID).orElse(null);
    }

    /**
     * Searches for a user by their username.
     *
     * @param username the username to search for
     * @return the found user or null if no user is found
     */
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * Searches for a user by their email.
     *
     * @param email the email of the user to search for
     * @return the found user or null if no user is found
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    /**
     * Saves a new user by checking for email uniqueness and hashing the password.
     *
     * @param user the user to save
     * @throws Exception if the email is already in use
     */
    @Override
    @Transactional
    public void save(User user) throws Exception {

        // Check email uniqueness
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email déjà utilisée (User non créé).");
        }

        // Hash password before saving
        user.setPassword(PasswordHashGenerator.generateHash(user.getPassword()));

        // Set default values for role and statue if not provided
        userRepository.save(user);
    }

    /**
     * Updates an existing user by checking for ID existence and re-hashing the password if modified.
     *
     * @param user the user to update
     * @throws RuntimeException if the user with the given ID does not exist
     */
    @Override
    @Transactional
    public void update(User user) {
        User user_db = findById(user.getId());
        if (user_db == null) {
            throw new RuntimeException("User non trouvé avec ID: " + user.getId());
        }

        // Username
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            user_db.setUsername(user.getUsername());
        }

        // Password (always re-hash if modified)
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user_db.setPassword(PasswordHashGenerator.generateHash(user.getPassword()));
        }

        // Role
        if (user.getRole() != null) {
            user_db.setRole(user.getRole());
        }

        // Statue
        if (user.getStatus() != null) {
            user_db.setStatus(user.getStatus());
        }

        // Email
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            user_db.setEmail(user.getEmail());
        }

        userRepository.save(user_db);
    }
}

