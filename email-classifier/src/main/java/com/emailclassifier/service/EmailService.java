//
//package com.emailclassifier.service;
//
//import com.emailclassifier.model.Email;
//import com.emailclassifier.repository.EmailRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private EmailRepository emailRepository;
//
//    @Autowired
//    private MLClientService mlClientService; // NEW
//
//    // Simple rule-based fallback
//    public String classifyEmailRuleBased(String content) {
//        if (content == null) return "Unknown";
//        String s = content.toLowerCase();
//        if (s.contains("free") || s.contains("win")) return "Spam";
//        if (s.contains("project") || s.contains("meeting")) return "Work";
//        return "Personal";
//    }
//
//    // Save classified email - now uses ML first, fallback to rules
//    public Email saveEmail(String content) {
//        // Try ML service
//        String category = mlClientService.classifyWithML(content);
//
//        // Fallback to rule-based if ML fails
//        if (category == null) {
//            category = classifyEmailRuleBased(content);
//        }
//
//        Email email = new Email(content, category);
//        return emailRepository.save(email);
//    }
//
//    // Fetch all
//    public List<Email> getAllEmails() {
//        return emailRepository.findAll();
//    }
//
//    // Fetch by id
//    public Email getEmailById(Long id) {
//        return emailRepository.findById(id).orElse(null);
//    }
//
//    // Delete by id
//    public void deleteEmail(Long id) {
//        emailRepository.deleteById(id);
//    }
//
//    // Delete all
//    public void deleteAllEmails() {
//        emailRepository.deleteAll();
//    }
//}
//
package com.emailclassifier.service;

import com.emailclassifier.model.Email;
import com.emailclassifier.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired(required = false)
    private MLClientService mlClientService; // Optional (in case ML service is down)

    // Simple rule-based fallback
    public String classifyEmailRuleBased(String content) {
        if (content == null || content.isBlank()) return "Unknown";

        String s = content.trim().toLowerCase();
        if (s.contains("free") || s.contains("win")) return "Spam";
        if (s.contains("project") || s.contains("meeting")) return "Work";
        return "Personal";
    }

    // Save classified email - uses ML first, fallback to rules
    public Email saveEmail(String content) {
        String category = null;

        // Try ML classification first
        if (mlClientService != null) {
            try {
                category = mlClientService.classifyWithML(content);
            } catch (Exception e) {
                System.err.println("ML classification failed, using rule-based classification: " + e.getMessage());
            }
        }

        // Fallback to rule-based if ML fails or returns null
        if (category == null) {
            category = classifyEmailRuleBased(content);
        }

        Email email = new Email(content, category);
        return emailRepository.save(email);
    }

    // Fetch all
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    // Fetch by id
    public Email getEmailById(Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    // Delete by id
    public void deleteEmail(Long id) {
        emailRepository.deleteById(id);
    }

    // Delete all
    public void deleteAllEmails() {
        emailRepository.deleteAll();
    }
}
