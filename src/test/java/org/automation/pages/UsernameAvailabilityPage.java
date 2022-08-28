package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

@PageObject
public class UsernameAvailabilityPage {
    private static final long MAX_WAIT_TIMEOUT_IN_MS = 500;

    @Autowired
    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    @FindBy(css = "#usernameAvailability > input")
    public WebElement usernameInput;

    @FindBy(css = "#usernameAvailability .invalid-feedback")
    public WebElement invalidMessage;

    @FindBy(css = "#usernameAvailability .valid-feedback")
    public WebElement validMessage;

    private final By spinnerLocator = By.cssSelector(".spinner-grow");

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "username-availability");
    }

    public void typeUsername(CharSequence username) {
        usernameInput.sendKeys(username);
    }

    public void waitForSpinner() {
        waitForSpinnerToShowUp();
        waitForSpinnerToHide();
    }

    public void waitForSpinnerToShowUp() {
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofMillis(MAX_WAIT_TIMEOUT_IN_MS))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(spinnerLocator));
    }

    public void waitForSpinnerToHide() {
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(spinnerLocator));
    }

    public boolean isSpinnerDisplayed(){
        try {
            WebElement spinner = webDriver.findElement(spinnerLocator);
            return spinner.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isInvalidMessageDisplayed() {
        return invalidMessage.isDisplayed();
    }

    public boolean isValidMessageDisplayed() {
        return validMessage.isDisplayed();
    }

    public String getInvalidMessage() {
        return invalidMessage.getText();
    }

    public String getValidMessage() {
        return validMessage.getText();
    }

    public String getUsernameInputValue() {
        return usernameInput.getAttribute("value");
    }
}
