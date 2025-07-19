package com.al_akdar_bank_.solution_ctr.Controlleur;
import com.al_akdar_bank_.solution_ctr.Model.CodeErreur;
import com.al_akdar_bank_.solution_ctr.Model.Utilisateur;
import com.al_akdar_bank_.solution_ctr.Service.CodeErreurService;
import com.al_akdar_bank_.solution_ctr.Service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Controller
public class CtrController {
    @Autowired
    private CodeErreurService codeErreurService;
    @Autowired
    private UtilisateurService usrservice;

    @GetMapping("/ctr_submit")
    public String ctr(Model model ,Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("username", authentication.getName());
        System.out.println(authentication.getName());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", new Utilisateur());
        try{model.addAttribute("users", usrservice.afficher_user());}
        catch (Exception e){
            model.addAttribute("users","");
        }

        return "ctr_admin";
    }

    @PostMapping("/delete_user/{id}")
    public String delete_user(@PathVariable Long id, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String currentUsername = authentication.getName();
            Long currentUserId = usrservice.chercherparnom(currentUsername).getId();

            usrservice.supprimer_user(id);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur supprimé");

            if (id.equals(currentUserId)) {
                request.logout();
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Une erreur est survenue");
            return "redirect:/ctr_submit"; // ou vers une autre page selon ton flux
        }

        return "redirect:/ctr_submit";
    }

    @PostMapping("/ajouter_user")
    public String ajouter_user(@ModelAttribute("user") Utilisateur user, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            usrservice.ajouter_user(user);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé");

        } catch (Exception e) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

            model.addAttribute("username", authentication.getName());
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("users", usrservice.afficher_user());
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", e.getMessage());
            return "ctr_admin";
        }


        return "redirect:/ctr_submit";
    }


    @PostMapping("/edit/{id}")
    public String envoie_edit(@PathVariable Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        Utilisateur user = usrservice.chercherparid(id);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("user", user);
        return "edit";

    }
    @PostMapping("/update_user/{id}")
    public String update_user(@PathVariable Long id, @ModelAttribute("user") Utilisateur user, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        try
        {
            usrservice.modifier_user(user);
            redirectAttributes.addFlashAttribute("successMessage","Utlisateur modifier");
        }
        catch (Exception e){
            model.addAttribute("errorMessage", "Un erreur est survenue");

        }

        return "redirect:/ctr_submit";
    }



}
