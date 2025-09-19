package com.alakdarbank.incident_paiement.security;

import com.alakdarbank.incident_paiement.Enum.Status;
import com.alakdarbank.incident_paiement.model.User;
import com.alakdarbank.incident_paiement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        if (user.getStatus() == Status.Inactif) {
            throw new DisabledException("Compte désactivé");
        }

        return new CustomUserDetails(user);
    }
}
