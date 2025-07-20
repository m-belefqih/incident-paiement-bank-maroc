package com.al_akdar_bank_.solution_ctr.Service;

import com.al_akdar_bank_.solution_ctr.Model.CodeErreur;
import com.al_akdar_bank_.solution_ctr.Repository.CodeErreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public List<CodeErreur> ListerErreur(MultipartFile file) throws IOException {
        List<CodeErreur> list_erreur = new ArrayList<>();
        List<Integer> content_LireFichier_int = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                char firstChar = line.charAt(0);
                if (firstChar == 'H' || firstChar == 'F') {
                    continue;
                }
                if (line.length() >= 199) {
                    String sub = line.substring(194, 199);
                    content_LireFichier_int.add(Integer.parseInt(sub));
                }
            }
        }

        for (Integer code : content_LireFichier_int) {
            list_erreur.add(codeErreurRepository.findByCode(code));
        }

        return list_erreur;
    }


}