package com.alakdarbank.incident_paiement.service;

import com.alakdarbank.incident_paiement.model.ErrorCode;
import com.alakdarbank.incident_paiement.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ErrorCodeService {
    List<Map<String, ErrorCode>> listErrors(MultipartFile file, User user) throws IOException;
}