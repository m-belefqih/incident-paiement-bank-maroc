package com.alakdarbank.incident_paiement.repository;

import com.alakdarbank.incident_paiement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{

    // Adding custom methods

    User findByUsername(String username);

    User findByEmail(String email);
}
