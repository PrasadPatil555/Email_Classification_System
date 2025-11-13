//
//
//package com.emailclassifier.controller;
//
//import com.emailclassifier.model.Email;
//import com.emailclassifier.model.EmailRequest;
//import com.emailclassifier.model.EmailResponse;
//import com.emailclassifier.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/email")
//@CrossOrigin(origins = "*")
//public class EmailController {
//
//    @Autowired
//    private EmailService emailService;
//
//    // Classify and save email
//    @PostMapping("/classify")
//    public EmailResponse classify(@RequestBody EmailRequest request) {
//        Email savedEmail = emailService.saveEmail(request.getContent());
//        return new EmailResponse(savedEmail.getCategory());
//    }
//
//    // Fetch all emails
//    @GetMapping("/all")
//    public List<Email> getAllEmails() {
//        return emailService.getAllEmails();
//    }
//
//    // Fetch one by id
//    @GetMapping("/{id}")
//    public Email getEmailById(@PathVariable Long id) {
//        return emailService.getEmailById(id);
//    }
//
//    // Delete one by id
//    @DeleteMapping("/{id}")
//    public String deleteEmail(@PathVariable Long id) {
//        emailService.deleteEmail(id);
//        return "Email with ID " + id + " deleted successfully!";
//    }
//
//    // Delete all
//    @DeleteMapping("/all")
//    public String deleteAllEmails() {
//        emailService.deleteAllEmails();
//        return "All emails deleted successfully!";
//    }
//}
//
package com.emailclassifier.controller;

import com.emailclassifier.model.Email;
import com.emailclassifier.model.EmailRequest;
import com.emailclassifier.model.EmailResponse;
import com.emailclassifier.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // ✅ DEBUG endpoint to check if request body is parsed correctly
    @PostMapping("/debug")
    public Map<String, Object> debug(@RequestBody EmailRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("receivedContent", request.getContent());
        response.put("length", request.getContent() != null ? request.getContent().length() : 0);
        return response;
    }

    @PostMapping("/classify")
    public EmailResponse classify(@RequestBody EmailRequest request) {
        Email savedEmail = emailService.saveEmail(request.getContent());
        return new EmailResponse(savedEmail.getCategory());
    }

    @GetMapping("/all")
    public List<Email> getAllEmails() {
        return emailService.getAllEmails();
    }

    @GetMapping("/{id}")
    public Email getEmailById(@PathVariable Long id) {
        return emailService.getEmailById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteEmail(@PathVariable Long id) {
        emailService.deleteEmail(id);
        return "Email with ID " + id + " deleted successfully!";
    }

    @DeleteMapping("/all")
    public String deleteAllEmails() {
        emailService.deleteAllEmails();
        return "All emails deleted successfully!";
    }
}
