package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(className = "note-row")
    private List<WebElement> notes;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void logout() { click(logoutButton); }

    public void notesTab() { click(notesTab); }

    public WebElement getNote(String noteTitle, String noteDescription) {
            waitForList(notes);
        for (WebElement note : notes) {
            WebElement title = note.findElement(By.className("note-title"));
            WebElement description = note.findElement(By.className("note-description"));
            if (title.getText().equals(noteTitle) && description.getText().equals(noteDescription))
                return note;
        }
        return null;
    }

    public void addNote(String noteTitle, String noteDescription) {
        click(addNoteButton);
        sendKeys(noteTitleField, noteTitle);
        sendKeys(noteDescriptionField, noteDescription);
        click(saveNoteButton);
    }

    public void editNote(String existingNoteTitle, String existingNoteDescription, String newNoteTitle, String newNoteDescription) {
        WebElement note = getNote(existingNoteTitle, existingNoteDescription);
        click(note.findElement(By.cssSelector("button[class='btn btn-success']")));
        sendKeys(noteTitleField, newNoteTitle);
        sendKeys(noteDescriptionField, newNoteDescription);
        click(saveNoteButton);
    }

    public void deleteNote(String noteTitle, String noteDescription) {
        WebElement note = getNote(noteTitle, noteDescription);
        click(note.findElement(By.cssSelector("a[class='btn btn-danger']")));
    }
}
