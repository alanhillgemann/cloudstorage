package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final NoteService noteService;
    private final UserService userService;

    public HomeController(EncryptionService encryptionService, CredentialService credentialService, NoteService noteService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Credential credential, Note note, Model model) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("notes", noteService.getNotes(user.getUserId()));
        return "home";
    }
}


