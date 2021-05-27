package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage extends BasePage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public SignupPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void login() {
        click(loginLink);
    }

    public void signup(String firstName, String lastName, String username, String password) {
        sendKeys(firstNameField, firstName);
        sendKeys(lastNameField, lastName);
        sendKeys(usernameField, username);
        sendKeys(passwordField, password);
        click(submitButton);
    }
}
