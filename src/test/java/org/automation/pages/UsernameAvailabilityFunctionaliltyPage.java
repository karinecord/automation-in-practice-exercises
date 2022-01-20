package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@PageObject
public class UsernameAvailabilityFunctionaliltyPage {

    @Autowired
    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    @FindBy(css = "#usernameAvailability > input")
    public WebElement usernameInput;

    @FindBy(xpath = "//div[contains(text(),'Looks good!')]")
    public WebElement usernameValid;

    @FindBy(xpath = "//div[contains(text(),'Username not allowed.')]")
    public WebElement usernameInvalid;

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "username-availability");
    }

    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public boolean isInvalidUsernameValidationMessageVisible(int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 500);
            wait.until(ExpectedConditions.visibilityOf(usernameInvalid));
            return usernameInvalid.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isValidUsernameValidationMessageVisible(int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 500);
            wait.until(ExpectedConditions.visibilityOf(usernameValid));
            return usernameValid.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void verifyMaxCharLimitOfAnInput(String username) {
        usernameInput.sendKeys(username);
        String typeValue = usernameInput.getAttribute("value");
        int size = typeValue.length();
        if(size == 100){
            isValidUsernameValidationMessageVisible(500);
        }else if(size >= 3){
            isValidUsernameValidationMessageVisible(500);
        }
    }
}



