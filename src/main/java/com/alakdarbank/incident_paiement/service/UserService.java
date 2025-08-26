package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long ID);

    User findByUsername(String username);

    User findByEmail(String email);

     void deleteById(Long id);

     void save(User user) throws Exception;

     void update(User user);
}

