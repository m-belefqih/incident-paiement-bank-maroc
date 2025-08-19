package com.alakdarbank.incident_paiement.controller;

import com.alakdarbank.incident_paiement.model.Utilisateur;
import com.alakdarbank.incident_paiement.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users") // This is the base URL for URL requests handled by this controller
public class UserController {

    private final UtilisateurService utilisateurservice;

    /**
     * Constructor to inject the UtilisateurService dependency.
     *
     * @param utilisateurservice Service for handling user operations.
     */
    public UserController(UtilisateurService utilisateurservice) {
        this.utilisateurservice = utilisateurservice;
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
        Utilisateur user = utilisateurservice.findByEmail(email);
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
        model.addAttribute("users", utilisateurservice.findAll());
        model.addAttribute("user", new Utilisateur());
    }

    /**
     * This method Handles GET requests for the /users endpoint.
     * It adds common attributes to the model
     *
     * @param model the model to which attributes are added
     * @param authentication the authentication object containing the currently authenticated user details
     * @return the name of the template for rendering the users page
     */
    @GetMapping("/list")
    public String listUsers(Model model, Authentication authentication) {

        // Add common attributes to the models
        addCommonAttributes(model, authentication);

        return "list-users";  // This assumes you have a list-users.html template
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                              Authentication authentication,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        try {
            String currentUsername = authentication.getName(); // it returns the email of the authenticated user

            Long currentUserId = utilisateurservice.findByEmail(currentUsername).getId();

            utilisateurservice.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur supprimé avec succès");

            if (id.equals(currentUserId)) {
                request.logout();
                return "redirect:/login?logout";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression !!");
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/users/list";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") Utilisateur user,
                               RedirectAttributes redirectAttributes) {
        try {
            utilisateurservice.save(user);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email déja utilisée !!");
            System.out.println("Erreur lors de la création: " + e.getMessage());
        }
        return "redirect:/users/list";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                              Model model,
                              Authentication authentication) {
        addCommonAttributes(model, authentication);
        Utilisateur user = utilisateurservice.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                              @ModelAttribute("user") Utilisateur user,
                              RedirectAttributes redirectAttributes) {
        try {
            user.setId(id); // Assure que l'ID est bien défini
            utilisateurservice.update(user);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur modifié avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la modification: ");
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
        return "redirect:/users/list";
    }
}
