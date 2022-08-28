package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

@PageObject
public class SkeletonsContentPlaceholdersPage {
    private static final long MAX_WAIT_TIMEOUT_IN_MS = 5000;

    @Autowired
    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    private final By skeletonSelector = By.cssSelector("#skeletonsApp .b-skeleton-wrapper");

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "skeletons");
    }

    public boolean isSkeletonVisible() {
        try {
            return webDriver.findElement(skeletonSelector).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }

    public void waitSkeletonToBeInvisible() {
        FluentWait<WebDriver> wait = new FluentWait<>(webDriver);
        wait.withTimeout(Duration.ofMillis(MAX_WAIT_TIMEOUT_IN_MS));
        wait.until(ExpectedConditions.invisibilityOf(webDriver.findElement(skeletonSelector)));
    }
}
