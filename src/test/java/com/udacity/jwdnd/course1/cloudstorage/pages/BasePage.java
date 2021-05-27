package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

public class BasePage {

    private final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void click(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public String getFilePath(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        return file.getAbsolutePath();
    }

    public void sendKeys(WebElement element, String value) {
        waitFor(element);
        click(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + value + "';", element);
    }

    public void sendFileKeys(WebElement element, String value) {
        driver.findElement(By.id(element.getAttribute("id"))).sendKeys(value);
    }

    public void waitFor(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            // Ignore exception as element may not exist
        }
    }

    public void waitForList(List<WebElement> elements) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        } catch (Exception e) {
            // Ignore exception as elements may not exist
        }
    }

}
