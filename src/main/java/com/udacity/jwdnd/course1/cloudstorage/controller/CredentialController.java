package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        Integer credentialId = credential.getCredentialId();
        boolean result = false;
        Integer userId = null;
        try {
            userId = userService.getUser(authentication.getName()).getUserId();
            if (credentialId == null) {
                credentialId = credentialService.createCredential(credential, userId);
                log.info("User {} created credential {}", userId, credentialId);
                result = true;
            } else {
                Credential existingCredential = credentialService.getCredential(credentialId);
                if (existingCredential == null) {
                    log.warn("User {} unsuccessfully updated credential {}: not found", userId, credentialId);
                } else if (!existingCredential.getUserId().equals(userId)) {
                    log.warn("User {} unsuccessfully updated credential {}: forbidden", userId, credentialId);
                } else {
                    credentialService.updateCredential(credential, userId);
                    log.info("User {} updated credential {}", userId, credentialId);
                    result = true;
                }
            }
        } catch (Exception e) {
            if (credentialId == null) {
                log.error("User {} unsuccessfully created credential: server", userId);
            } else {
                log.error("User {} unsuccessfully updated credential {}: server", userId, credentialId);
            }
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, Model model) {
        boolean result = false;
        Integer userId = null;
        try {
            userId = userService.getUser(authentication.getName()).getUserId();
            Credential credential = credentialService.getCredential(credentialId);
            if (credential == null) {
                log.warn("User {} unsuccessfully deleted credential {}: not found", userId, credentialId);
            } else if (!credential.getUserId().equals(userId)) {
                log.warn("User {} unsuccessfully deleted credential {}: forbidden", userId, credentialId);
            } else {
                credentialService.deleteCredential(credentialId);
                log.info("User {} deleted credential {}", userId, credentialId);
                result = true;
            }
        } catch (Exception e) {
            log.error("User {} unsuccessfully deleted credential {}: server", userId, credentialId);
        }
        model.addAttribute("result", result);
        return "result";
    }
}
