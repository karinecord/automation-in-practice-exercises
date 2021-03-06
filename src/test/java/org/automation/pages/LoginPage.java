package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public class LoginPage {
    @Autowired
    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    @FindBy(id = "email")
    public WebElement emailInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(xpath = "//button[contains(@class,'btn-primary')]")
    public WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'invalid-feedback') and text() = 'Please enter a valid email']")
    private WebElement validationEmail;

    @FindBy(xpath = "//div[contains(@class, 'invalid-feedback') and text() = 'Please enter a password']")
    private WebElement validationPassword;

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "simple-login");
    }

    public void doLogin(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
    }


    public void validateEmptyEmailAndPassword(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public boolean isEmailValidationMessageVisible() {
        return validationEmail.isDisplayed();
    }

    public boolean isPasswordValidationMessageVisible() {
        return validationPassword.isDisplayed();
    }

    public String getCurrentURL(){
        return webDriver.getCurrentUrl();
    }

}
