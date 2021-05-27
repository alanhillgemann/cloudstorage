package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(className = "credential-row")
    private List<WebElement> credentialRows;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(className = "file-row")
    private List<WebElement> fileRows;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadField;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(className = "note-row")
    private List<WebElement> noteRows;

    @FindBy(id = "save-credential-button")
    private WebElement saveCredentialButton;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "upload-file-button")
    private WebElement uploadFileButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void addCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
        click(addCredentialButton);
        sendKeys(credentialUrlField, credentialUrl);
        sendKeys(credentialUsernameField, credentialUsername);
        sendKeys(credentialPasswordField, credentialPassword);
        click(saveCredentialButton);
    }

    public void addNote(String noteTitle, String noteDescription) {
        click(addNoteButton);
        sendKeys(noteTitleField, noteTitle);
        sendKeys(noteDescriptionField, noteDescription);
        click(saveNoteButton);
    }

    public void credentialsTab() { click(credentialsTab); }

    public void deleteCredential(String credentialUrl, String credentialUsername) {
        WebElement credentialRow = getCredential(credentialUrl, credentialUsername, null);
        click(credentialRow.findElement(By.cssSelector("a[class='btn btn-danger']")));
    }

    public void deleteFile(String filename) {
        WebElement fileRow = getFile(filename);
        click(fileRow.findElement(By.cssSelector("a[class='btn btn-danger']")));
    }

    public void deleteNote(String noteTitle, String noteDescription) {
        WebElement noteRow = getNote(noteTitle, noteDescription);
        click(noteRow.findElement(By.cssSelector("a[class='btn btn-danger']")));
    }

    public void downloadFile(String filename) {
        WebElement fileRow = getFile(filename);
        click(fileRow.findElement(By.cssSelector("a[class='btn btn-success']")));
    }

    public void editCredential(String existingCredentialUrl, String existingCredentialUsername) {
        WebElement credentialRow = getCredential(existingCredentialUrl, existingCredentialUsername, null);
        click(credentialRow.findElement(By.cssSelector("button[class='btn btn-success']")));
    }

    public void editNote(String existingNoteTitle, String existingNoteDescription, String newNoteTitle, String newNoteDescription) {
        WebElement noteRow = getNote(existingNoteTitle, existingNoteDescription);
        click(noteRow.findElement(By.cssSelector("button[class='btn btn-success']")));
        sendKeys(noteTitleField, newNoteTitle);
        sendKeys(noteDescriptionField, newNoteDescription);
        click(saveNoteButton);
    }

    public WebElement getCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
        waitForList(credentialRows);
        for (WebElement credentialRow : credentialRows) {
            WebElement credentialRowUrl = credentialRow.findElement(By.className("credential-url"));
            WebElement credentialRowUsername = credentialRow.findElement(By.className("credential-username"));
            if (credentialPassword != null) {
                WebElement credentialRowPassword = credentialRow.findElement(By.className("credential-password"));
                if (credentialRowUrl.getText().equals(credentialUrl) &&
                        credentialRowUsername.getText().equals(credentialUsername) &&
                        credentialRowPassword.getText().equals(credentialPassword))
                    return credentialRow;
            } else {
                if (credentialRowUrl.getText().equals(credentialUrl) &&
                        credentialRowUsername.getText().equals(credentialUsername))
                    return credentialRow;
            }
        }
        return null;
    }

    public WebElement getFile(String filename) {
        waitForList(fileRows);
        for (WebElement fileRow : fileRows) {
            WebElement fileRowName = fileRow.findElement(By.className("filename"));
            if (fileRowName.getText().equals(filename))
                return fileRow;
        }
        return null;
    }

    public WebElement getNote(String noteTitle, String noteDescription) {
        waitForList(noteRows);
        for (WebElement noteRow : noteRows) {
            WebElement noteRowTitle = noteRow.findElement(By.className("note-title"));
            WebElement noteRowDescription = noteRow.findElement(By.className("note-description"));
            if (noteRowTitle.getText().equals(noteTitle) && noteRowDescription.getText().equals(noteDescription))
                return noteRow;
        }
        return null;
    }

    public boolean isPasswordDecrypted(String credentialPassword) {
        return credentialPasswordField.getAttribute("value").equals(credentialPassword);
    }

    public void logout() { click(logoutButton); }

    public void notesTab() { click(notesTab); }

    public void saveCredential(String newCredentialUrl, String newCredentialUsername, String newCredentialPassword) {
        sendKeys(credentialUrlField, newCredentialUrl);
        sendKeys(credentialUsernameField, newCredentialUsername);
        sendKeys(credentialPasswordField, newCredentialPassword);
        click(saveCredentialButton);
    }

    public void uploadFile(String filename) {
        sendFileKeys(fileUploadField, getFilePath(filename));
        click(uploadFileButton);
    }
}
