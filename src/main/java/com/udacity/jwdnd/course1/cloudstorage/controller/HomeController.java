package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;

    public HomeController(EncryptionService encryptionService, CredentialService credentialService, FileService fileService, NoteService noteService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Credential credential, File file, Model model, Note note) {
        try {
            Integer userId = userService.getUser(authentication.getName()).getUserId();
            model.addAttribute("credentials", credentialService.getCredentials(userId));
            model.addAttribute("encryptionService", encryptionService);
            model.addAttribute("files", fileService.getFiles(userId));
            model.addAttribute("notes", noteService.getNotes(userId));
        } catch (Exception e) {
            log.error("Error: server");
        }
        return "home";
    }
}


