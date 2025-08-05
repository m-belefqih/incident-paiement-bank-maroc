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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * This method adds common attributes to the model for all views.
     * It retrieves the user's email, username, admin status, and initials,
     * and adds them to the model.
     *
     * @param model The model to which attributes are added.
     * @param authentication The authentication object containing user details.
     */
    private void addCommonAttributes(Model model, Authentication authentication) {
        // Get email from authentication
        String email = authentication.getName();

        // Default value
        String username = "";

        // To test username, you can uncomment the line below
        // String username = "Admin User";

        // Find user by email (since that's what authentication.getName() returns)
        Utilisateur user = usrservice.chercherparEmail(email);
        if (user != null) {
            username = user.getUsername();
        }

        // Check if the user has an admin role
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        // Generate initials from username
        String initials = Arrays.stream(username.split(" "))
                .filter(s -> !s.isEmpty())
                .map(s -> s.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("initials", initials);
        model.addAttribute("users", usrservice.getAllUsers());
        model.addAttribute("user", new Utilisateur());
    }

    @GetMapping("/import")
    public String ctr(Model model, Authentication authentication) {
        addCommonAttributes(model, authentication);
//        return "ctr_admin";
        return "import"; // This assumes you have a import.html template
    }

    @GetMapping("/history")
    public String history(Model model, Authentication authentication) {
        addCommonAttributes(model, authentication);
        return "history";  // This assumes you have a history.html template
    }

    @GetMapping("/users")
    public String users(Model model, Authentication authentication) {
        addCommonAttributes(model, authentication);
        return "users";  // This assumes you have a users.html template
    }

    @PostMapping("/delete_user/{id}")
    public String delete_user(@PathVariable Long id,
                              Authentication authentication,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        try {
            String currentUsername = authentication.getName(); // it returns the email of the authenticated user

            Long currentUserId = usrservice.chercherparEmail(currentUsername).getId();

            usrservice.supprimer_user(id);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur supprimé avec succès");

            if (id.equals(currentUserId)) {
                request.logout();
                return "redirect:/login?logout";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/users";
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
        return "redirect:/users";
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
        return "redirect:/users";
    }

    @PostMapping("/uploads")
    public String uploads(@RequestParam("fichier") MultipartFile fichier,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        if (fichier.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez sélectionner un fichier");
            return "redirect:/import";
        }

        try {
            List<Map<String, CodeErreur>> lignes = codeErreurService.ListerErreur(fichier);
            redirectAttributes.addFlashAttribute("lignes", lignes);
            redirectAttributes.addFlashAttribute("successMessage", "Fichier traité avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors du traitement du fichier: " + e.getMessage());
        }
        return "redirect:/import";
    }
}