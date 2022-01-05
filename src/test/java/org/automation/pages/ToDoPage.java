package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@PageObject
public class ToDoPage {
    @Autowired
    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    @FindAll(@FindBy(xpath = "//*[@id='todoApp']/li[1]/input"))
    public List<WebElement> todo;

    @FindBy(xpath = "//*[@id='todoApp']/li[1]/input")
    public WebElement todokey;

    @FindBy(xpath = "//a[@class='ms-auto p-1']")
    private WebElement removeTaskTitle;

    @FindBy(css = "input.form-check-input")
    public WebElement checkboxTask;

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "todo");
    }

    public void pressEnter() {

        todokey.sendKeys(Keys.ENTER);

    }

    public void addOneTask(String TASK_1) {
        todokey.sendKeys(TASK_1);
        pressEnter();
    }

    public List<String> addMultipleTasks(String TASK_1, String TASK_2, String TASK_3, String TASK_4, String TASK_5, String TASK_6) {
        List<String> elementsTask = new ArrayList<>();
        for (WebElement element : todo) {
            elementsTask.add(TASK_1);
            element.sendKeys(TASK_1);
            pressEnter();

            elementsTask.add(TASK_2);
            element.sendKeys(TASK_2);
            pressEnter();

            elementsTask.add(TASK_3);
            element.sendKeys(TASK_3);
            pressEnter();

            elementsTask.add(TASK_4);
            element.sendKeys(TASK_4);
            pressEnter();

            elementsTask.add(TASK_5);
            element.sendKeys(TASK_5);
            pressEnter();

            elementsTask.add(TASK_6);
            element.sendKeys(TASK_6);
            pressEnter();

        }
        return elementsTask;
    }

    public void removeAllTask() {
        for (int i = 0; i <= 5; i++) {
            removeTaskTitle.click();
        }
    }

    public void removeOneTask() {
        removeTaskTitle.click();
    }

    public void checkOneTask() {
        checkboxTask.click();
    }

    public void checkAllTasks() {
        for (int i = 0; i <= 5; i++) {
            checkboxTask.click();
        }
    }

}
