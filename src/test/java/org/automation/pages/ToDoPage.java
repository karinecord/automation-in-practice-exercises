package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.NoSuchElementException;
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

    @FindBy(css = "#todoApp .list-group-item.align-items-center")
    public List<WebElement> todoTasksList;

    @FindBy(css = "#todoApp input[type='text']")
    public WebElement todoAddNewTaskInput;

    @FindBy(xpath = "//*[text()='There are no tasks created']")
    public WebElement noTaskCreatedMessage;

    @FindBy(css = "input.form-check-input")
    public WebElement checkboxTask;

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "todo");
    }

    public void addNewTask(String taskName) {
        todoAddNewTaskInput.sendKeys(taskName);
        todoAddNewTaskInput.sendKeys(Keys.ENTER);
    }

    public void removeTask(String taskName) {
        for (WebElement newTaskElement : todoTasksList) {
            if (newTaskElement.getText().equals(taskName)) {
                clickOnRemoveTask(newTaskElement);
            }
        }
    }

    public void checkTask(String taskName) {
        for (WebElement newTaskElement : todoTasksList) {
            if (newTaskElement.getText().equals(taskName)) {
                WebElement checkbox = newTaskElement.findElement(By.cssSelector(".form-check-input"));
                checkbox.click();
            }
        }
    }

    public List<String> getAllTodoTasksText() {
        List<String> listOfAddedTasks = new ArrayList<>();

        for (WebElement newTaskElement : todoTasksList) {
            listOfAddedTasks.add(newTaskElement.getText());
        }

        return listOfAddedTasks;
    }

    public void removeAllTask() {
        for (WebElement taskElement : todoTasksList) {
            clickOnRemoveTask(taskElement);
        }
    }

    public boolean isTaskChecked(String taskName) {
        for (WebElement taskElement : todoTasksList) {
            if (taskElement.getText().equals(taskName)) {
                WebElement labelElement = taskElement.findElement(By.cssSelector("label"));
                String cssClasses = labelElement.getAttribute("class");
                return cssClasses.contains("text-decoration-line-through");
            }
        }

        return false;
    }

    public boolean isNoTaskCreatedMessageVisible() {
        try {
            return noTaskCreatedMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void clickOnRemoveTask(WebElement webElement) {
        WebElement removeElement = webElement.findElement(By.cssSelector("*[title='Remove task']"));
        removeElement.click();
    }

    public String getTodoAddNewTaskInputValue() {
        return todoAddNewTaskInput.getAttribute("value");
    }

    public void typeNewTask(String taskName) {
        todoAddNewTaskInput.sendKeys(taskName);
    }
}
