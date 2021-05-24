package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public ArrayList<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public Integer createNote(Note note, Integer userId) {
        Note newNote = new Note(null, note.getNoteTitle(), note.getNoteDescription(), userId);
        noteMapper.insert(newNote);
        return newNote.getNoteId();
    }

    public void updateNote(Note note, Integer userId) {
        note.setUserId(userId);
        noteMapper.update(note);
    }

    public void deleteNote(Integer noteId) { noteMapper.delete(noteId); }
}
