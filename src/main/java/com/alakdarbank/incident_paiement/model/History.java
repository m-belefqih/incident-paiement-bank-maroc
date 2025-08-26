package com.alakdarbank.incident_paiement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;  // "numéroCompte"

    private LocalDate errorDate;   // "dateErreur"

    @ManyToOne
    @JoinColumn(name = "errorCode_id")
    private ErrorCode errorCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}