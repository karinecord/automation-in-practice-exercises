package org.automation;

import org.automation.models.Person;
import org.automation.pages.FilteringTablePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilteringTableTest extends BaseTest {
    private static final String FIRST_NAME_GENEVA = "Geneva";
    private static final String LAST_NAME_WILSON = "Wilson";

    @Autowired
    private FilteringTablePage filterPage;

    @BeforeMethod
    public void visitTodoPage() {
        filterPage.visit();
        filterPage.waitForSpinner();
    }

    @Test
    public void filterByBirthDate() {
        LocalDate birthDate = LocalDate.parse("2012-11-13");

        //Select birthday date
        filterPage.setBirthDateFrom(birthDate);

        //Click on filter button
        filterPage.doFilter();
        filterPage.waitForSpinner();

        List<Person> listRecords = filterPage.getListRecords();

        for (Person person: listRecords) {
            //Checking if the birthday listed in the table is equal to the added date.
            assertThat(person.getBirthDate()).isEqualTo(birthDate);
        }
    }

    @Test
    public void filterFromToBirthDate() {
        LocalDate birthDateFrom = LocalDate.parse("1981-01-01");
        LocalDate birthDateTo = LocalDate.parse("1981-07-11");

        filterPage.setBirthDateFrom(birthDateFrom);
        filterPage.setBirthDateTo(birthDateTo);

        filterPage.doFilter();
        filterPage.waitForSpinner();

        //Record added to table
        List<Person> listRecords = filterPage.getListRecords();
        for (Person person: listRecords) {
            assertThat(person.getBirthDate()).isBetween(birthDateFrom, birthDateTo);
        }
    }

    @Test
    public void testOnlyActive() {
        //Selecting the checkbox "Only active"
        filterPage.checkOnlyActive();

        //Clicking on "Filter" button
        filterPage.doFilter();
        filterPage.waitForSpinner();

        List<Person> onlyActivePersonList = filterPage.getListRecords();
        for (Person person : onlyActivePersonList) {
            assertThat(person.getIsActive()).isTrue();
        }
    }

    @Test
    public void listAllStatusWhenVisitingPage() {
        filterPage.waitForSpinnerToHide();

        while (true) {
            List<Person> listRecords = filterPage.getListRecords();

            for (Person person : listRecords) {
                assertThat(person.getIsActive()).isIn(true, false); // Check all status: Active and Inactive
            }

            if (filterPage.isNextPageClickable()) {
                filterPage.clickOnNextPage();
                filterPage.waitForSpinner();
            } else {
                break;
            }
        }
    }

    @Test
    public void filterByFirstName() {
        //Enter first name
        filterPage.setInputQueryFilter(FIRST_NAME_GENEVA);

        // Click on first name check
        filterPage.checkFirstName();

        // Click on Filter button
        filterPage.doFilter();
        filterPage.waitForSpinner();

        List<Person> listRecords = filterPage.getListRecords();
        for (Person person: listRecords) {
            assertThat(person.getFirstName()).isEqualTo(FIRST_NAME_GENEVA);
        }
    }

    @Test
    public void filterByLastName() {
        //Enter first name
        filterPage.setInputQueryFilter(LAST_NAME_WILSON);

        // Click on first name check
        filterPage.checkLastName();

        // Click on Filter button
        filterPage.doFilter();
        filterPage.waitForSpinner();

        // Verifying if first name display on table
        List<Person> listRecords = filterPage.getListRecords();
        for (Person person: listRecords) {
            assertThat(person.getLastName()).isEqualTo(LAST_NAME_WILSON);
        }
    }

    @Test
    public void enterInvalidTextOnFilter() {
        //Enter first name
        filterPage.setInputQueryFilter(" ' ' ");

        // Click on Filter button
        filterPage.doFilter();
        filterPage.waitForSpinner();

        // Verifying if name displays on the table
        assertThat(filterPage.getEmptyRowMessage()).contains("There are no records matching your request");
    }

    @Test
    public void testClearFilters() {
        // Added some filters
        filterPage.setInputQueryFilter(FIRST_NAME_GENEVA);
        filterPage.checkFirstName();
        filterPage.checkLastName();
        filterPage.setBirthDateFrom(LocalDate.now());
        filterPage.setBirthDateTo(LocalDate.now());

        //Clear filters
        filterPage.clearFilter();

        // Verifying if filters were cleaned
        assertThat(filterPage.getFilterQueryText()).isEmpty();
        assertThat(filterPage.isFilterOnFirstNameChkChecked()).isFalse();
        assertThat(filterPage.isFilterOnLastNameChkChecked()).isFalse();
        assertThat(filterPage.birthDateFrom()).isEqualTo("From");
        assertThat(filterPage.birthDateTo()).isEqualTo("To");
        assertThat(filterPage.isOnlyActiveChecked()).isFalse();
    }
}
