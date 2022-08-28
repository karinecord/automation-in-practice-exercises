package org.automation;

import org.automation.pages.UsernameAvailabilityPage;
import org.openqa.selenium.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsernameAvailabilityTest extends BaseTest {

    private static final String ADMIN_USER = "admin";
    private static final String SUPER_ADMIN_USER = "superadmin";
    private static final String REGULAR_USERNAME = "RegularUsername";
    private static final String USERNAME_SUPER_LONG = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the text";

    @Autowired
    private UsernameAvailabilityPage usernamePage;

    @DataProvider(name = "usernameProvider")
    protected Object[][] createUsernamesList() {
        return new Object[][]{
                {ADMIN_USER}, {SUPER_ADMIN_USER}
        };
    }

    @BeforeMethod
    public void visitTodoPage() {
        usernamePage.visit();
    }

    @Test(dataProvider = "usernameProvider")
    public void validateUsername(String username) {
        usernamePage.typeUsername(username);

        usernamePage.waitForSpinner();

        assertThat(usernamePage.isInvalidMessageDisplayed()).isTrue();
        assertThat(usernamePage.getInvalidMessage()).contains("Username not allowed");
    }

    @Test
    public void testMaximumInputCharLimit() {
        usernamePage.typeUsername(USERNAME_SUPER_LONG);

        usernamePage.waitForSpinner();

        assertThat(usernamePage.getUsernameInputValue()).hasSize(100).isNotEqualTo(USERNAME_SUPER_LONG);

        assertThat(usernamePage.isValidMessageDisplayed()).isTrue();
        assertThat(usernamePage.getValidMessage()).contains("Looks good");
    }

    @Test
    public void testUsernameWithSuccess() {
        usernamePage.typeUsername(REGULAR_USERNAME);

        usernamePage.waitForSpinner();

        assertThat(usernamePage.isValidMessageDisplayed()).isTrue();
        assertThat(usernamePage.getValidMessage()).contains("Looks good");
    }

    @Test
    public void testUsernameWithLessThan3Characters() {
        // Enter a character
        usernamePage.typeUsername("A");

        // Validating if spinner doesn't appear
        assertThat(usernamePage.isSpinnerDisplayed()).isFalse();

        // Nothing message should be appeared
        assertThat(usernamePage.isValidMessageDisplayed()).isFalse();
        assertThat(usernamePage.isInvalidMessageDisplayed()).isFalse();

        // Enter two characters
        usernamePage.typeUsername("B");

        // Validating if spinner doesn't appear
        assertThat(usernamePage.isSpinnerDisplayed()).isFalse();

        // Nothing message should be appeared
        assertThat(usernamePage.isValidMessageDisplayed()).isFalse();
        assertThat(usernamePage.isInvalidMessageDisplayed()).isFalse();

        // Enter three characters
        usernamePage.typeUsername("C");

        // Verify if spinner doesn't appear
        usernamePage.waitForSpinnerToShowUp();
        assertThat(usernamePage.isSpinnerDisplayed()).isTrue();
        usernamePage.waitForSpinnerToHide();

        // The message should be appeared
        assertThat(usernamePage.isValidMessageDisplayed()).isTrue();
        assertThat(usernamePage.getValidMessage()).contains("Looks good");
    }

    @Test
    public void testUsernameVerificationInterruption() {
        // Enter three characters
        usernamePage.typeUsername("ABC");

        // Verify if spinner appears
        usernamePage.waitForSpinnerToShowUp();

        //Remove a character
        usernamePage.typeUsername(Keys.BACK_SPACE);

        // Verify if spinner doesn't appear
        assertThat(usernamePage.isSpinnerDisplayed()).isFalse();

        // The messages shouldn't be appeared
        assertThat(usernamePage.isValidMessageDisplayed()).isFalse();
    }
}
