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

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "simple-login");
    }

    public void doLogin(String emailtxt, String passwordtxt){
        email.sendKeys(emailtxt);
        password.sendKeys(passwordtxt);
        loginButton.click();

    }

    
}
