package com.al_akdar_bank_.solution_ctr.Model;

import com.al_akdar_bank_.solution_ctr.Enum.Role;
import com.al_akdar_bank_.solution_ctr.Enum.Statue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String username;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    Statue statue=Statue.Activer;
    @Enumerated(EnumType.STRING)
    Role role;

}
