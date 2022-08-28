package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.automation.components.DatePickerComponent;
import org.automation.models.Person;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.openqa.selenium.By.*;

@PageObject
public class FilteringTablePage {
    private static final long MAX_WAIT_TIMEOUT_IN_MS = 500;

    private static final int FIRST_NAME_POSITION = 0;
    private static final int LAST_NAME_POSITION = 1;
    private static final int BIRTH_DATE_POSITION = 2;
    private static final int ACTION_BUTTON_POSITION = 4;
    private static final int STATUS_CARD_DETAILS_POSITION = 2;

    private final By spinnerLocator = By.cssSelector(".spinner-border");
    private final By tableLineFinder = By.tagName("tr");
    private final By tableColumnFinder = By.tagName("td");

    @FindBy(xpath = "//input[@id='queryFilter']")
    private WebElement filterByInput;

    @FindBy(css = "label[for='__BVID__7']")
    private WebElement chkFirstName;

    @FindBy(css = "label[for='__BVID__8']")
    private WebElement chkLastName;

    @FindBy(css = "#birthDateFrom__value_")
    private WebElement birthDateFrom;

    @FindBy(css = "#__BVID__13__value_")
    private WebElement birthDateTo;

    @FindBy(css = ".b-table-empty-row")
    public WebElement emptyRowMessage;

    @FindBy(xpath = "//button[contains(text(),'Clear')]")
    public WebElement clearFilterButton;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    public WebElement filterButton;

    @FindBy(xpath = "//label[@id='birthDateFrom__value_']")
    public WebElement labelBirthDate;

    @FindBy(xpath = "//label[@id='__BVID__13__value_']")
    public WebElement labelBirthDateTo;

    @FindBy(css = "label[for='__BVID__17']")
    private WebElement onlyActiveCheckbox;

    @FindBy(css = "#tableListFilter table.table-striped tbody")
    public WebElement dataTableBody;

    @FindBy(xpath = "//button[contains(text(),'›')]")
    private WebElement nextPageButton;

    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    private final DatePickerComponent datePickerFrom;
    private final DatePickerComponent datePickerTo;

    public FilteringTablePage(@Nonnull final WebDriver webDriver) {
        this.webDriver = webDriver;
        datePickerFrom = new DatePickerComponent("birthDateFrom", webDriver);
        datePickerTo = new DatePickerComponent("__BVID__13", webDriver);
    }

    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "filtering-table");
    }

    public void doFilter() {
        filterButton.click();
    }

    public void clearFilter() {
        clearFilterButton.click();
    }

    public void setInputQueryFilter(String txt) {
        filterByInput.sendKeys(txt);
    }

    public void checkFirstName() {
        chkFirstName.click();
    }

    public void checkLastName() {
        chkLastName.click();
    }

    public void checkOnlyActive() {
        onlyActiveCheckbox.click();
    }

    public void setBirthDateFrom(LocalDate birthDateFrom) {
        labelBirthDate.click();
        datePickerFrom.pickDate(birthDateFrom);
    }

    public void setBirthDateTo(LocalDate birthDateTo) {
        labelBirthDateTo.click();
        datePickerTo.pickDate(birthDateTo);
    }

    public void waitForSpinner() {
        waitForSpinnerToHide();
    }

    public void waitForSpinnerToHide() {
        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(spinnerLocator));
    }

    public List<Person> getListRecords() {
        List<Person> personList = new ArrayList<>();

        for (WebElement tableLine: this.dataTableBody.findElements(tableLineFinder)) {
            personList.add(createPersonFrom(tableLine));
        }

        return personList;
    }

    private Person createPersonFrom(WebElement tableLine) {
        List<WebElement> tableLineColumns = tableLine.findElements(tableColumnFinder);
        String firstName = tableLineColumns.get(FIRST_NAME_POSITION).getText();
        String lastName = tableLineColumns.get(LAST_NAME_POSITION).getText();
        LocalDate birthDate = LocalDate.parse(tableLineColumns.get(BIRTH_DATE_POSITION).getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        tableLineColumns.get(ACTION_BUTTON_POSITION).findElement(tagName("button")).click();

        WebElement lineDetailsCard = tableLine.findElement((xpath("//following-sibling::*[@class='b-table-details']")));
        String status = lineDetailsCard.findElements(By.cssSelector(".card-body .row")).get(STATUS_CARD_DETAILS_POSITION).getText();

        return new Person(firstName, lastName, birthDate, status.equalsIgnoreCase("active"));
    }

    //Verifying if the next page is clickable
    public boolean isNextPageClickable() {
        try {
            return nextPageButton.isEnabled();
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }

    public void clickOnNextPage() {
        nextPageButton.click();
    }

    public String getFilterQueryText(){
        return filterByInput.getText();
    }

    public boolean isFilterOnFirstNameChkChecked() {
        return chkFirstName.isSelected();
    }

    public boolean isFilterOnLastNameChkChecked() {
        return chkLastName.isSelected();
    }

    public String birthDateFrom(){
        return birthDateFrom.getText();
    }

    public String birthDateTo(){
        return birthDateTo.getText();
    }

    public boolean isOnlyActiveChecked(){
      return  onlyActiveCheckbox.isSelected();
    }

    public String getEmptyRowMessage(){
        return emptyRowMessage.getText();
    }
}
