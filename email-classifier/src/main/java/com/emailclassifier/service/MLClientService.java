package com.emailclassifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class MLClientService {

    @Autowired
    private RestTemplate restTemplate;

    // where your Flask service is running
    private final String ML_URL = "http://127.0.0.1:5000/predict";

    /**
     * Call the Flask ML API. Returns predicted category or null if ML call failed.
     */
    public String classifyWithML(String content) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Collections.singletonMap("content", content);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> resp = restTemplate.postForEntity(ML_URL, entity, Map.class);
            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                Object cat = resp.getBody().get("category");
                return cat == null ? null : cat.toString();
            }
        } catch (Exception ex) {
            // Log the error to console (or your logger)
            System.err.println("ML service error: " + ex.getMessage());
        }
        return null;
    }
}
