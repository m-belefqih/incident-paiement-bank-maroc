package com.al_akdar_bank_.solution_ctr.Controlleur;
import com.al_akdar_bank_.solution_ctr.Model.CodeErreur;
import com.al_akdar_bank_.solution_ctr.Service.CodeErreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Controller
public class Testcontroller {
    @Autowired
    private CodeErreurService codeErreurService;

    @GetMapping("/test-file")
    public String afficherFichier(@RequestParam String path, Model model) throws IOException {
        List<CodeErreur> lignes = codeErreurService.ListerErreur(path);
        model.addAttribute("lignes", lignes);
        return "affichage"; // affichage.html
    }
}
