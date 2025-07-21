package com.alakdarbank.incident_paiement.controller;

import com.alakdarbank.incident_paiement.model.CodeErreur;
import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.service.CodeErreurService;
import com.alakdarbank.incident_paiement.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CtrController {

    private final CodeErreurService codeErreurService;
    private final UtilisateurService usrservice;

    @Autowired
    public CtrController(CodeErreurService codeErreurService,
                         UtilisateurService usrservice) {
        this.codeErreurService = codeErreurService;
        this.usrservice = usrservice;
    }

    private void addCommonAttributes(Model model, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("username", authentication.getName());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("users", usrservice.afficher_user());
        model.addAttribute("user", new Utilisateur());
    }

    @GetMapping("/ctr_submit")
    public String ctr(Model model, Authentication authentication) {
        addCommonAttributes(model, authentication);
        return "ctr_admin";
    }

    @PostMapping("/delete_user/{id}")
    public String delete_user(@PathVariable Long id,
                              Authentication authentication,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        try {
            String currentUsername = authentication.getName();
            Long currentUserId = usrservice.chercherparnom(currentUsername).getId();

            usrservice.supprimer_user(id);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur supprimé avec succès");

            if (id.equals(currentUserId)) {
                request.logout();
                return "redirect:/login?logout";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/ctr_submit";
    }

    @PostMapping("/ajouter_user")
    public String ajouter_user(@ModelAttribute("user") Utilisateur user,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            usrservice.ajouter_user(user);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la création: " + e.getMessage());
        }
        return "redirect:/ctr_submit";
    }

    @PostMapping("/edit/{id}")
    public String envoie_edit(@PathVariable Long id,
                              Model model,
                              Authentication authentication) {
        addCommonAttributes(model, authentication);
        Utilisateur user = usrservice.chercherparid(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/update_user/{id}")
    public String update_user(@PathVariable Long id,
                              @ModelAttribute("user") Utilisateur user,
                              RedirectAttributes redirectAttributes) {
        try {
            user.setId(id); // Assure que l'ID est bien défini
            usrservice.modifier_user(user);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur modifié avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la modification: " + e.getMessage());
        }
        return "redirect:/ctr_submit";
    }

    @PostMapping("/uploads")
    public String uploads(@RequestParam("fichier") MultipartFile fichier,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        if (fichier.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez sélectionner un fichier");
            return "redirect:/ctr_submit";
        }

        try {
            List<CodeErreur> lignes = codeErreurService.ListerErreur(fichier);
            redirectAttributes.addFlashAttribute("lignes", lignes);
            redirectAttributes.addFlashAttribute("successMessage", "Fichier traité avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors du traitement du fichier: " + e.getMessage());
        }
        return "redirect:/ctr_submit";
    }
}