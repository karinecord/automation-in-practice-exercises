package org.automation.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.YearMonth;

public class DatePickerComponent {
    private final String dialogId;
    private final WebDriver webDriver;

    public DatePickerComponent(String dialogId, WebDriver webDriver) {
        this.dialogId = dialogId;
        this.webDriver = webDriver;
    }

    public void pickDate(LocalDate date) {
        int currentYear = YearMonth.now().getYear();
        int currentMonth = YearMonth.now().getMonthValue();

        // Selecting a year
        if (currentYear != date.getYear()) {
            int unaryOperator = currentYear > date.getYear() ? -1 : 1;
            int yearCount = currentYear;
            while (yearCount != date.getYear()) {
                if (unaryOperator == 1) {
                    getNextYearButton().click();
                } else {
                    getPreviousYearButton().click();
                }

                yearCount += unaryOperator;
            }
        }

        // Selecting a month
        if (currentMonth != date.getMonthValue()) {
            int unaryOperator = currentMonth > date.getMonthValue() ? -1 : 1;
            int monthCount = currentMonth;
            while (monthCount != date.getMonthValue()) {
                if (unaryOperator == 1) {
                    getNextMonthButton().click();
                } else {
                    getPreviousMonthButton().click();
                }

                monthCount += unaryOperator;
            }
        }

        // Selecting a day
        String fullDate = date.getYear() + "-" + String.format("%02d", date.getMonthValue()) + "-" + String.format("%02d", date.getDayOfMonth());
        webDriver.findElement(By.cssSelector("[data-date='" + fullDate + "']")).click();
    }

    private WebElement getCalenderDialog() {
        return webDriver.findElement(By.cssSelector("#" + dialogId + "__dialog_"));
    }

    private WebElement getNextYearButton() {
        return getCalenderDialog().findElement(By.xpath("//button[contains(@title, 'Next year')] "));
    }

    private WebElement getPreviousYearButton() {
        return getCalenderDialog().findElement(By.xpath("//button[contains(@title, 'Previous year')] "));
    }

    private WebElement getNextMonthButton() {
        return getCalenderDialog().findElement(By.xpath("//button[contains(@title, 'Next month')] "));
    }

    private WebElement getPreviousMonthButton() {
        return getCalenderDialog().findElement(By.xpath("//button[contains(@title, 'Previous month')] "));
    }
}
