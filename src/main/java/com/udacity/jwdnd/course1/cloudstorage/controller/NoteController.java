package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String postNote(Authentication authentication, @ModelAttribute Note newNote, Model model) {
        Integer noteId = newNote.getNoteId();
        boolean result = false;
        Integer userId = null;
        try {
            userId = userService.getUser(authentication.getName()).getUserId();
            if (noteId == null) {
                noteId = noteService.createNote(newNote, userId);
                log.info("User {} created note {}", userId, noteId);
                result = true;
            } else {
                Note note = noteService.getNote(noteId);
                if (note == null) {
                    log.warn("User {} unsuccessfully updated note {}: not found", userId, noteId);
                } else if (!note.getUserId().equals(userId)) {
                    log.warn("User {} unsuccessfully updated note {}: forbidden", userId, noteId);
                } else {
                    noteService.updateNote(newNote, userId);
                    log.info("User {} updated note {}", userId, noteId);
                    result = true;
                }
            }
        } catch (Exception e) {
            if (noteId == null) {
                log.error("User {} unsuccessfully created note: server", userId);
            } else {
                log.error("User {} unsuccessfully updated note {}: server", userId, noteId);
            }
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId, Model model) {
        boolean result = false;
        Integer userId = null;
        try {
            userId = userService.getUser(authentication.getName()).getUserId();
            Note note = noteService.getNote(noteId);
            if (note == null) {
                log.warn("User {} unsuccessfully deleted note {}: not found", userId, noteId);
            } else if (!note.getUserId().equals(userId)) {
                log.warn("User {} unsuccessfully deleted note {}: forbidden", userId, noteId);
            } else {
                noteService.deleteNote(noteId);
                log.info("User {} deleted note {}", userId, noteId);
                result = true;
            }
        } catch (Exception e) {
            log.error("User {} unsuccessfully deleted note {}: server", userId, noteId);
        }
        model.addAttribute("result", result);
        return "result";
    }
}
