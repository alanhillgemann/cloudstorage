package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String postCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        boolean result = false;
        User user = userService.getUser(authentication.getName());
        Integer credentialId = credential.getCredentialId();
        if (credentialId == null) {
            try {
                credential.setUserId(user.getUserId());
                credentialService.createCredential(credential);
                result = true;
            } catch (Exception e) {
                System.out.println("Error creating credential");
            }
        } else {
            Credential existingCredential = credentialService.getCredential(credentialId);
            if (existingCredential != null && existingCredential.getUserId().equals(user.getUserId())) {
                try {
                    credential.setUserId(user.getUserId());
                    credentialService.updateCredential(credential);
                    result = true;
                } catch (Exception e) {
                    System.out.println("Error updating credential " + credentialId);
                }
            } else {
                System.out.println("Error updating credential " + credentialId);
            }
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, Credential credential, Model model) {
        boolean result = false;
        User user = userService.getUser(authentication.getName());
        Credential existingCredential = credentialService.getCredential(credentialId);
        if (existingCredential != null && existingCredential.getUserId().equals(user.getUserId())) {
            try {
                credentialService.deleteCredential(credentialId);
                result = true;
            } catch (Exception e) {
                System.out.println("Error deleting credential " + credentialId);
            }
        } else {
            System.out.println("Error deleting credential " + credentialId);
        }
        model.addAttribute("result", result);
        return "result";
    }
}
