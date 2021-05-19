package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String postNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        boolean result = false;
        User user = userService.getUser(authentication.getName());
        Integer noteId = note.getNoteId();
        if (noteId == null) {
            try {
                note.setUserId(user.getUserId());
                noteService.createNote(note);
                result = true;
            } catch (Exception e) {
                System.out.println("Error creating note");
            }
        } else {
            Note existingNote = noteService.getNote(noteId);
            if (existingNote != null && existingNote.getUserId().equals(user.getUserId())) {
                try {
                    note.setUserId(user.getUserId());
                    noteService.updateNote(note);
                    result = true;
                } catch (Exception e) {
                    System.out.println("Error updating note " + noteId);
                }
            } else {
                System.out.println("Error updating note " + noteId);
            }
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId, Note note, Model model) {
        boolean result = false;
        User user = userService.getUser(authentication.getName());
        Note existingNote = noteService.getNote(noteId);
        if (existingNote != null && existingNote.getUserId().equals(user.getUserId())) {
            try {
                noteService.deleteNote(noteId);
                result = true;
            } catch (Exception e) {
                System.out.println("Error deleting note " + noteId);
            }
        } else {
            System.out.println("Error deleting note " + noteId);
        }
        model.addAttribute("result", result);
        return "result";
    }
}
