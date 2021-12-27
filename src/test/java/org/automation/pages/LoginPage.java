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
    public WebElement email;

    @FindBy(id = "password")
    public WebElement password;

    @FindBy(xpath = "//button[contains(@class,'btn-primary')]")
    public WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'invalid-feedback') and text() = 'Please enter a valid email']")
    private WebElement validationEmail;

    @FindBy(xpath = "//div[contains(@class, 'invalid-feedback') and text() = 'Please enter a password']")
    private WebElement validationPassword;

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "simple-login");
    }

    public void doLogin(String emailtxt, String passwordtxt) {
        email.sendKeys(emailtxt);
        password.sendKeys(passwordtxt);
        loginButton.click();
    }

    public void fillBlankEmailandPassword() {
        loginButton.click();
        getValidateTextEmail();
        getValidateTextPassword();
    }

    public String getValidateTextEmail() {
        return validationEmail.getText();
    }

    public String getValidateTextPassword() {
        return validationPassword.getText();
    }

    public void fillInvalidEmailAndValidPassword() {
        getValidateTextEmail();
    }

    public void fillBlankEmail() {
        getValidateTextEmail();

    }

    public void fillBlankPassword() {
        getValidateTextPassword();

    }
}
