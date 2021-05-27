package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage extends BasePage {

    @FindBy(id = "error-alert")
    private WebElement errorAlert;

    @FindBy(className = "home-link")
    private WebElement homeLink;

    @FindBy(id = "success-alert")
    private WebElement successAlert;

    public ResultPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void home() {
        click(homeLink);
    }

    public boolean isErrorAlert() {
        try {
            waitFor(errorAlert);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isSuccessAlert() {
        try {
            waitFor(successAlert);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
