package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public class WelcomePage {

    @Autowired
    WebDriver webDriver;

    @FindBy(xpath = "//p[contains(text(),'Welcome')]")
    public WebElement welcomeMessage;

    @FindBy(xpath = "//a[contains(text(),'Return to form')]")
    private WebElement returnToFormLink;

    public String getWelcomeText() {
        return welcomeMessage.getText();
    }

    public void clickOnReturnToForm() {
          returnToFormLink.click();
    }


}
