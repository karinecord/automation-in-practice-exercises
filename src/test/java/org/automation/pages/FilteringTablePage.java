package org.automation.pages;

import org.automation.architecture.TestProperties;
import org.automation.architecture.annotations.PageObject;
import org.bouncycastle.jcajce.provider.asymmetric.EC;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.testng.Assert;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.*;

@PageObject
public class FilteringTablePage {
    @Autowired
    WebDriver webDriver;

    @Autowired
    TestProperties testProperties;

    @FindBy(xpath = "//input[@id='queryFilter']")
    public WebElement filterInput;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    public WebElement filterButton;

    @FindBy(xpath = "//table[@id='__BVID__18']")
    public WebElement filterTable;

    @FindBy(xpath = "//table[@id='__BVID__13__value_']")
    public WebElement filterToTable;

    @FindBy(xpath = "//label[contains(text(),'Last name')]")
    public WebElement chkLastName;


    @FindBy(xpath = "//div[contains(text(),'First Name')]")
    public WebElement firstNam;

    @FindBy(xpath = "//div[contains(text(),'Last Name')]")
    public WebElement lastName;

    @FindBy(xpath = "//thead/tr[1]/th[3]")
    public WebElement colBirthDate;

    @FindBy(xpath = "//label[contains(text(),'First name')]")
    public WebElement chkFirstName;

    @FindBy(xpath = "//label[@id='birthDateFrom__value_']")
    public WebElement labelBirthDate;

    @FindBy(xpath = "//div[@id='birthDateFrom__dialog_']")
    public WebElement inputBirthDate;

    @FindBy(xpath = "//div[@id='__BVID__13__outer_']")
    public WebElement fromBirthDate;

    @FindBy(css = "div.card-body")
    public WebElement rowDetails;

    @FindBy(xpath = "//button[contains(@class,'btn btn-sm border-0 flex-fill btn-outline-secondary')][contains(@title, 'Previous year')] ")
    public WebElement buttonDatePickerPreviousYear;

    @FindBy(xpath = "//button[contains(@class,'btn btn-sm border-0 flex-fill btn-outline-secondary')][contains(@title, 'Next month')] ")
    public WebElement buttonDatePickerNextMonth;

    @FindBy(xpath = "//span[contains(text(),'13')]")
    public WebElement novemberDay;

    @FindBy(xpath = "//label[contains(text(),'Only active')]")
    public WebElement activeInactiveButton;

    @FindBy(xpath = "//div[contains(text(),'Active')]")
    public WebElement activeTextName;

    @FindAll(@FindBy(xpath = "//table[@id='__BVID__18']/tbody/tr"))
    public List<WebElement> tableRows;

    @FindBy(xpath = "//span[contains(text(),'1')]")
    public WebElement januaryDay;

    @FindBy(xpath = "//span[contains(text(),'11')]")
    public WebElement julyDay;

    @FindBy(xpath = "//button[contains(text(),'Clear')]")
    public WebElement clearFilter;

    @FindAll(@FindBy(tagName = "option"))
    public List<WebElement> paginationOptions;

    @FindBy(xpath = "//button[contains(text(),'Hide Details')]")
    public WebElement hideDetailsButton;

    @FindBy(xpath = "//thead/tr/th")
    public WebElement firstName;

    @FindBy(xpath = "//thead/tr[1]/th[1]")
    public WebElement colFirstName;

    @FindBy(xpath = "//thead/tr[1]/th[2]")
    public WebElement colLastName;

    @FindAll(@FindBy(xpath = "//thead/tr[1]/th[1]"))
    public List<WebElement> tableFirstName;


    @FindBy(xpath = " //div[contains(text(),'There are no records matching your request')]")
    public WebElement msgRecords;


    public void visit() {
        webDriver.get(testProperties.getAppBaseUrl() + "filtering-table");
    }

    public void filterByFirstName(String firstNameTxt) {
        filterInput.sendKeys(firstNameTxt);
        checkFistName();
        clickButtonFilter();
        displaysOnTable();
    }


    public void clickButtonFilter() {
        filterButton.click();
    }

    public boolean displaysOnTable() {
        return filterTable.isDisplayed();
    }

    public void checkLastName() {
        chkLastName.click();
    }

    public void checkFistName() {
        chkFirstName.click();
    }

    public void clickHideDetailsButton() {
        hideDetailsButton.click();
    }


    public void filterByLastName(String lastNameCheck) {
        filterInput.sendKeys(lastNameCheck);
        checkLastName();
        clickButtonFilter();
        displaysOnTable();
    }

    public void filterByFilterButton() {
        clickButtonFilter();
        displaysOnTable();
    }

    public void clearFilterButton() {
        clearFilter.click();
    }

    public void clearQueryFilter(String firstName) {
        filterInput.sendKeys(firstName);
        clearFilter.click();
    }


    public void clearFilters(String lastNameCheck, Integer month, Integer year) {
        filterInput.sendKeys(lastNameCheck);
        chkFirstName.click();
        filterByFromBirthDate(month, year);
        activeInactiveButton.click();
        clearFilter.click();
        //incompleto

    }

    public void clearFilterOnCheckboxes() {
        chkFirstName.click();
        chkLastName.click();
        clearFilterButton();
    }

    public void clearFilterFromTo(Integer month, Integer year) {
        filterByFromToBirthDate(month, year);
        clearFilterButton();
    }

    public void clearOnlyActiveCheckbox() {
        activeInactiveButton.click();
        clearFilterButton();
    }

    public void selectPerPage(int selectIndex) {
        paginationOptions.get(selectIndex).click();
    }

    public Boolean isSelectSelected(int selectIndex) {
        return paginationOptions.get(selectIndex).isSelected();
    }

    public void isSelectCheckbox() {
        activeInactiveButton.click();
        clickButtonFilter();

    }

    public List<String> isUnselectCheckbox() {

        activeInactiveButton.click();
        activeInactiveButton.click();
        clickButtonFilter();

        List<String> showDetails = new ArrayList<>();

        for (WebElement tableRows : tableRows) {
            tableRows.findElement(tagName("button")).click();
        }

        return showDetails;
    }

    public String getActiveText() {
        return activeTextName.getText();

    }

    public List<String> getShowDetails() {

        activeInactiveButton.click();
        clickButtonFilter();

        List<String> showDetails = new ArrayList<>();

        for (WebElement tableRows : tableRows) {
            tableRows.findElement(tagName("button")).click();

            if (rowDetails.findElements(By.tagName("div")).contains("Active")) {
                showDetails.add(activeTextName.getText());
                clickHideDetailsButton();
            }

        }

        return showDetails;
    }
//
//    public void filterByFromBirthDate() {
//
//        labelBirthDate.click();
//
//        int month = YearMonth.now().getMonthValue();
//        int year = YearMonth.now().getYear();
//
//        for (int i = year; i <= year; i--) {
//            if (i <= 2012) {
//                for (int j = month; j <= 11; j++) {
//                    if (j == 11) {
//                        novemberDay.click();
//                        clickButtonFilter();
//                        break;
//                    }
//                    buttonDatePickerNextMonth.click();
//                }
//                break;
//            }
//            buttonDatePickerPreviousYear.click();
//        }
//    }

    public void filterByFromBirthDate(Integer month, Integer year) {

        labelBirthDate.click();

        for (int i = year; i <= year; i--) {
            if (i <= 2012) {
                for (int j = month; j <= 11; j++) {
                    if (j == 11) {
                        novemberDay.click();
                        clickButtonFilter();
                        break;
                    }
                    buttonDatePickerNextMonth.click();
                }
                break;
            }
            buttonDatePickerPreviousYear.click();
        }
    }

    public void filterByFromToBirthDate(Integer month, Integer year) {

        labelBirthDate.click();

        for (int i = year; i <= year; i--) {
            if (i <= 1981) {
                for (int j = month; j >= 1; j--) {
                    if (j == 1) {
                        januaryDay.click();
                        clickButtonFilter();
                        break;
                    }
                    buttonDatePickerNextMonth.click();
                }
                break;
            }
            buttonDatePickerPreviousYear.click();
        }
        fromBirthDate.click();
        for (int i = year; i <= year; i--) {
            if (i <= 1981) {
                for (int j = month; j <= 7; j++) {
                    if (j == 7) {
                        julyDay.click();
                        clickButtonFilter();
                        break;
                    }
                    buttonDatePickerNextMonth.click();
                }
                break;
            }
            buttonDatePickerPreviousYear.click();
        }
    }

    public void clickDescendingButtonFirstName() {

        List<String> beforeFilterFirstNameList = new ArrayList<>();

        beforeFilterFirstNameList.add(colFirstName.getText());

        colFirstName.click();

        List<String> afterFilterFirstNameList = new ArrayList<>();

        afterFilterFirstNameList.add(colFirstName.getText());

        Collections.sort(afterFilterFirstNameList);
        Assert.assertNotEquals(beforeFilterFirstNameList, afterFilterFirstNameList);

    }

    public void clickDescendingButtonLastName() {

        List<String> beforeFilterLastNameList = new ArrayList<>();

        beforeFilterLastNameList.add(colLastName.getText());

        colLastName.click();

        List<String> afterFilterLastNameList = new ArrayList<>();

        afterFilterLastNameList.add(colLastName.getText());

        Collections.sort(afterFilterLastNameList);
        Assert.assertNotEquals(beforeFilterLastNameList, afterFilterLastNameList);

    }

    public void clickDescendingButtonBirthDate() {

        List<String> beforeFilterBirthDateList = new ArrayList<>();

        beforeFilterBirthDateList.add(colBirthDate.getText());

        colBirthDate.click();

        List<String> afterFilterBirthDateList = new ArrayList<>();

        afterFilterBirthDateList.add(colBirthDate.getText());

        Collections.sort(afterFilterBirthDateList);
        Assert.assertNotEquals(beforeFilterBirthDateList, afterFilterBirthDateList);

    }

    public void clickAscendingButtonFirstName() {

        List<String> beforeFilterFirstNameList = new ArrayList<>();

        beforeFilterFirstNameList.add(colFirstName.getText());

        colFirstName.click();

        List<String> afterFilterFirstNameList = new ArrayList<>();

        afterFilterFirstNameList.add(colFirstName.getText());

        Collections.sort(beforeFilterFirstNameList);
        Assert.assertNotEquals(beforeFilterFirstNameList, afterFilterFirstNameList);

    }

    public void clickAscendingButtonLastName() {

        List<String> beforeFilterLastNameList = new ArrayList<>();

        beforeFilterLastNameList.add(colLastName.getText());

        colLastName.click();

        List<String> afterFilterLastNameList = new ArrayList<>();

        afterFilterLastNameList.add(colLastName.getText());

        Collections.sort(beforeFilterLastNameList);
        Assert.assertNotEquals(beforeFilterLastNameList, afterFilterLastNameList);

    }

    public void clickAscendingButtonBirthDate() {

        List<String> beforeFilterBirthList = new ArrayList<>();

        beforeFilterBirthList.add(colBirthDate.getText());

        colBirthDate.click();

        List<String> afterFilterBirthList = new ArrayList<>();

        afterFilterBirthList.add(colBirthDate.getText());

        Collections.sort(beforeFilterBirthList);
        Assert.assertNotEquals(beforeFilterBirthList, afterFilterBirthList);

    }

        public void pagination(){
            String displayedCount = webDriver.findElement(By.xpath("//b[contains(text(),'12')]")).getText().split(" ")[0];
            WebElement nextButton = webDriver.findElement(By.xpath("//button[contains(text(),'›')]"));

            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("window.scrollBy(0,5000);");

            List<WebElement> rows = webDriver.findElements(By.xpath("//table[@id='__BVID__18']"));
            List<String> rowNames = new ArrayList<>();

            for(WebElement lines: rows){
                rowNames.add(lines.getText());
                System.out.println(lines.getText());
            }
            System.out.println("*************************************************************************************");
            while (true){
                nextButton.click();
                js.executeScript("window.scrollBy(0,5000);");
                rows = webDriver.findElements(By.xpath("//table[@id='__BVID__18']"));

                for(WebElement lines: rows){
                    rowNames.add(lines.getText());
                    System.out.println(lines.getText());
                }
                System.out.println("*************************************************************************************");

               try {
                   nextButton = webDriver.findElement(By.xpath("//button[contains(text(),'›')]"));
                }catch (Exception e){
                    System.out.println("NO MORE LINES NOW");
                   break;
                }
            }
            System.out.println("*************************************************************************************");
            System.out.println("Number of lines are:"+rowNames.size());
            System.out.println("Total of rows:"+displayedCount);
        }

    public void filterDataBySpecialCharacters(String character){
        filterInput.sendKeys(character);
        filterButton.click();
    }


    public String returnMessageRecord(){
        return msgRecords.getText();
    }

    public void filterDataByBlankSpace(String blankSpace){
        filterInput.sendKeys(blankSpace);
        filterButton.click();
    }


}






