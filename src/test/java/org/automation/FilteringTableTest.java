package org.automation;

import org.assertj.core.api.Assertions;
import org.automation.pages.FilteringTablePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;


public class FilteringTableTest extends BaseTest {
    private static final String FIRSTNAME = "Geneva";
    private static final String LASTNAME = "Wilson";
    private static final String DATEPICKE = "11132012";
    private static final Integer YEAR = 2022;
    private static final Integer MONTH = 3;
    private final static int OPTION_5 = 0;
    private final static int OPTION_10 = 1;
    private final static int OPTION_15 = 2;

    private static final String SpecialCharacter = ".";
    private static final String  BlankSpace = "  ";


    @Autowired
    private FilteringTablePage filterPage;

    @Test
    public void filterByFirstName() {
        filterPage.visit();
        filterPage.filterByFirstName(FIRSTNAME);
        assertThat(filterPage.displaysOnTable()).isTrue();

    }

    @Test
    public void filterByLastName() {
        filterPage.visit();
        filterPage.filterByLastName(LASTNAME);
        assertThat(filterPage.displaysOnTable()).isTrue();

    }

    @Test
    public void filterByFilterbutton() {
        filterPage.visit();
        filterPage.filterByFilterButton();
        assertThat(filterPage.displaysOnTable()).isTrue();

    }

    @Test
    public void filterByBirthDate() {
        filterPage.visit();
        filterPage.filterByFromBirthDate(MONTH, YEAR);

        assertThat(filterPage.displaysOnTable()).isTrue();

    }

    @Test
    public void filterFromToBirthDate() {
        filterPage.visit();
        filterPage.filterByFromToBirthDate(MONTH, YEAR);

        assertThat(filterPage.displaysOnTable()).isTrue();

    }

    @Test
    public void clearFitler() {
        filterPage.visit();
        filterPage.clearFilters(FIRSTNAME, MONTH, YEAR);

//incompleto


    }

    @Test
    public void clearFilterOnCheckboxes() {
        filterPage.visit();
        filterPage.clearFilterOnCheckboxes();
        assertThat(filterPage.chkFirstName.equals("Null"));
        assertThat(filterPage.chkLastName.equals("Null"));
    }

    @Test
    public void clearFilterBirthDateRange() {
        filterPage.visit();
        filterPage.clearFilterFromTo(MONTH, YEAR);
        assertThat(filterPage.labelBirthDate.equals("Null"));

    }

    @Test
    public void clearOnlyActiveCheckbox() {
        filterPage.visit();
        filterPage.clearOnlyActiveCheckbox();
        assertThat(filterPage.activeInactiveButton.equals("Null"));

    }

    @Test
    public void selectOptions() {
        filterPage.visit();
        filterPage.selectPerPage(OPTION_5);
        assertThat(filterPage.isSelectSelected(OPTION_5)).isTrue();

        filterPage.selectPerPage(OPTION_10);
        assertThat(filterPage.isSelectSelected(OPTION_10)).isTrue();

        filterPage.selectPerPage(OPTION_15);
        assertThat(filterPage.isSelectSelected(OPTION_15)).isTrue();

    }

    @Test
    public void selectActiveCheckbox() {
        filterPage.visit();
        filterPage.isSelectCheckbox();
        assertThat(filterPage.activeInactiveButton.isDisplayed());
    }

    @Test
    public void unselectActiveCheckbox() {
        filterPage.visit();
        filterPage.isUnselectCheckbox();
        assertThat(filterPage.activeInactiveButton.equals("Null"));

    }


    @Test
    public void hideDetails() {
        filterPage.visit();
        filterPage.getShowDetails();


        assertThat(filterPage.rowDetails.isDisplayed());
    }


    @Test
    public void showDetails() {
        filterPage.visit();
        filterPage.isUnselectCheckbox();

        assertThat(filterPage.rowDetails.isDisplayed());
    }

    @Test
    public void clickDescendingButtonFirstName() {
        filterPage.visit();
        filterPage.clickDescendingButtonFirstName();

    }

    @Test
    public void clickDescendingButtonLastName() {
        filterPage.visit();
        filterPage.clickDescendingButtonLastName();

    }

    @Test
    public void clickDescendingButtonBirthDate() {
        filterPage.visit();
        filterPage.clickDescendingButtonBirthDate();

    }


    @Test
    public void clickAscendingButtonFirstName() {
        filterPage.visit();
        filterPage.clickAscendingButtonFirstName();

    }

    @Test
    public void clickAscendingButtonLastName() {
        filterPage.visit();
        filterPage.clickAscendingButtonLastName();

    }

    @Test
    public void clickAscendingButtonBirthDate() {
        filterPage.visit();
        filterPage.clickAscendingButtonBirthDate();

    }

    @Test
    public void paginationFitlering() {
        filterPage.visit();
        filterPage.pagination();

    }

    @Test
    public void filterDataBySpecialCharacters() {
        filterPage.visit();
        filterPage.filterDataBySpecialCharacters(SpecialCharacter);
        assertThat(filterPage.returnMessageRecord().contains("There are no records matching your request"));

    }

    @Test
    public void filterDataByBlankSpace() {
        filterPage.visit();
        filterPage.filterDataByBlankSpace(BlankSpace);

    }

    @Test
    public void clearQueryFilter() {
        filterPage.visit();
        filterPage.clearQueryFilter(FIRSTNAME);
        assertThat(filterPage.filterInput.getText().contains(""));
     //   System.out.println(filterPage.filterInput.getText().contains(""));
    }

    @Test
    public void clearFilters() {
        filterPage.visit();
        filterPage.clearFilters(LASTNAME,MONTH,YEAR);
        assertThat(filterPage.lastName.getText().contains(""));
        System.out.println(filterPage.lastName.getText().contains(""));
        assertThat(filterPage.labelBirthDate.getText().contains(""));
        System.out.println(filterPage.inputBirthDate.getText().contains(""));
        assertThat(filterPage.chkFirstName.getText().contains(""));
        System.out.println(filterPage.chkFirstName.getText().contains(""));
        assertThat(filterPage.activeInactiveButton.getText().contains(""));
        System.out.println(filterPage.activeInactiveButton.getText().contains(""));
    }

}
