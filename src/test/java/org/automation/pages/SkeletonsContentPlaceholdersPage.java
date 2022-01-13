package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@PageObject
public class SkeletonsContentPlaceholdersPage {
    @Autowired
    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    @FindBy(id = "skeletonsApp")
    public WebElement skeletonsContent;

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "skeletons");
    }


    public boolean isElementDisplayed(WebElement skeletonsContent, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 1000);
            wait.until(ExpectedConditions.visibilityOf(skeletonsContent));
            return skeletonsContent.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException
                | org.openqa.selenium.StaleElementReferenceException
                | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

}
