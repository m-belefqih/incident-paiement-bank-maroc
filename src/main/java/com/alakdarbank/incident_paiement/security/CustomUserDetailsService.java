package com.alakdarbank.incident_paiement.security;

import com.alakdarbank.incident_paiement.Enum.Statue;
import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        if (user.getStatue() == Statue.Desactiver) {
            throw new DisabledException("Compte désactivé");
        }

        return new CustomUserDetails(user); // important : passe un Utilisateur à jour
    }
}
