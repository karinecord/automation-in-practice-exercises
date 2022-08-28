package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    @FindAll(@FindBy(css = ".invalid-feedback"))
    private List<WebElement> invalidMessages;

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

    public List<String> getInvalidMessages() {
        List<String> messages = new ArrayList<>();

        for (WebElement element: invalidMessages) {
            if(element.isDisplayed()) {
                messages.add(element.getText());
            }
        }

        return messages;
    }

    public String getCurrentURL(){
        return webDriver.getCurrentUrl();
    }
}
