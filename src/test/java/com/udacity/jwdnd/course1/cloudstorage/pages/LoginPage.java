package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    private static WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login(String username, String password) {
        sendKeys(usernameField, username);
        sendKeys(passwordField, password);
        click(submitButton);
    }
}
