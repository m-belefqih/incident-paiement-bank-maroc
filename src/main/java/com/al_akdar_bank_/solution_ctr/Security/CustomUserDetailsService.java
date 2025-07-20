package com.al_akdar_bank_.solution_ctr.Security;

import com.al_akdar_bank_.solution_ctr.Enum.Statue;
import com.al_akdar_bank_.solution_ctr.Model.Utilisateur;
import com.al_akdar_bank_.solution_ctr.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        if (user.getStatue() == Statue.Desactiver) {
            throw new DisabledException("Compte désactivé");
        }

        return new CustomUserDetails(user); // important : passe un Utilisateur à jour
    }


}
