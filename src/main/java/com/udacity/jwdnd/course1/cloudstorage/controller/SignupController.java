package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError;
        Integer userId;
        try {
            if (!userService.isUsernameAvailable(user.getUsername())) {
                signupError = "The username already exists.";
                model.addAttribute("signupError", signupError);
            } else {
                userId = userService.createUser(user);
                log.info("User {} created for {}", userId, user.getUsername());
                return "redirect:/login?signup";
            }
        } catch (Exception e) {
            signupError = "There was an error signing you up. Please try again.";
            model.addAttribute("signupError", signupError);
            log.error("User unsuccessfully created for {}: server", user.getUsername());
        }
        return "signup";
    }
}
