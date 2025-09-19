package com.alakdarbank.incident_paiement.controller;

import com.alakdarbank.incident_paiement.model.ErrorCode;
import com.alakdarbank.incident_paiement.model.User;
import com.alakdarbank.incident_paiement.service.ErrorCodeService;
import com.alakdarbank.incident_paiement.service.HistoryService;
import com.alakdarbank.incident_paiement.service.UserService;
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

    private final ErrorCodeService errorCodeService;
    private final UserService userService;
    private final HistoryService historyService;

    /**
     * This constructor is used to inject the services dependency into this controller.
     *
     * @param errorCodeService Service for handling code errors.
     * @param userService Service for handling user operations.
     * @param historyService Service for handling incident history.
     */
    @Autowired
    public CtrController(ErrorCodeService errorCodeService,
                         UserService userService, HistoryService historyService) {
        this.errorCodeService = errorCodeService;
        this.userService = userService;
        this.historyService = historyService;
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
        User user = userService.findByEmail(email);
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
        model.addAttribute("user", new User());
    }

    /**
     * This method handles the root URL and redirects to the import page.
     * It also adds common attributes to the model for the view.
     *
     * @param model The model to which attributes are added.
     * @param authentication The authentication object containing user details.
     * @return A redirect to the import page.
     */
    @GetMapping("/import")
    public String importFile(Model model, Authentication authentication) {

        // Add common attributes to the models
        addCommonAttributes(model, authentication);

        return "import"; // This assumes you have a import.html template
    }

    /**
     * This method handles the file upload and processes the uploaded file.
     * It retrieves the user's email from the authentication object,
     * processes the file, and adds the results to the redirect attributes.
     *
     * @param fichier The uploaded file.
     * @param authentication The authentication object containing user details.
     * @param redirectAttributes The redirect attributes to pass messages.
     * @return A redirect to the import page with success or error messages.
     */
    @PostMapping("/uploads")
    public String uploads(@RequestParam("fichier") MultipartFile fichier,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes) {

        if (fichier.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez sélectionner un fichier, s'il vous plaît !!");
            return "redirect:/import";
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        try {
            List<Map<String, ErrorCode>> lignes = errorCodeService.listErrors(fichier, user);

            redirectAttributes.addFlashAttribute("lignes", lignes);
            redirectAttributes.addFlashAttribute("successMessage", "Fichier traité avec succès !!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors du traitement du fichier. Veuillez sélectionner un fichier de type .bin");
            System.out.println("Erreur lors du traitement du fichier: " + e.getMessage());
        }

        return "redirect:/import";
    }

    /**
     * This method handles the history page request.
     * It adds common attributes to the model and retrieves the user's incident history.
     *
     * @param model The model to which attributes are added.
     * @param authentication The authentication object containing user details.
     * @return The name of the history view template.
     */
    @GetMapping("/history")
    public String history(Model model, Authentication authentication) {

        // Add common attributes to the models
        addCommonAttributes(model, authentication);

        // Get email from authentication
        String email = authentication.getName();

        // Find user by email (since that's what authentication.getName() returns)
        User user = userService.findByEmail(email);

        // Add the user's history to the model
        model.addAttribute("History", historyService.getHistory(user));
        return "history";  // This assumes you have a history.html template
    }
}