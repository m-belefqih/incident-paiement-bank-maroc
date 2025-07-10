package com.al_akdar_bank_.solution_ctr.Service;

import com.al_akdar_bank_.solution_ctr.Model.CodeErreur;
import com.al_akdar_bank_.solution_ctr.Repository.CodeErreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.thymeleaf.util.StringUtils.substring;

@Service
public class CodeErreurService {
    @Autowired
    private CodeErreurRepository codeErreurRepository;
    public List<String> LireFichier(String filePath) throws IOException {
        List<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                char firstChar = line.charAt(0);
                if (firstChar == 'H' || firstChar == 'F') {
                    continue;
                }
                if (line.length() >= 6) {
                    String sub = line.substring(194, 199);
                    content.add(sub);
                }
            }
        }
        return content;
    }
    public List<CodeErreur> ListerErreur(String filePath) throws IOException {
        List<CodeErreur> list_erreur = new ArrayList<>();
        List<Integer> content_LireFichier_int = new ArrayList<>();
        List<String> content = LireFichier(filePath);

        for (String s : content) {

            content_LireFichier_int.add(Integer.parseInt(s));
        }

        for(int i=0;i<content_LireFichier_int.size();i++){

            list_erreur.add(codeErreurRepository.findByCode(content_LireFichier_int.get(i)));
        }
        return list_erreur;
    }

}