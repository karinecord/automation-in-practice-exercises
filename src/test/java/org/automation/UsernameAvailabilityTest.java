package org.automation;

import org.automation.pages.ToDoPage;
import org.automation.pages.UsernameAvailabilityFunctionaliltyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsernameAvailabilityTest extends BaseTest {

    private static final String INVALID_USERNAME_1 = "admin";
    private static final String INVALID_USERNAME_2 = "superadmin";
    private static final String INVALID_USERNAME_3 = "Tes";
    private static final String INVALID_USERNAME_4 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been thet";
    private static final String INVALID_USERNAME_5 = "ka";

    @Autowired
    private UsernameAvailabilityFunctionaliltyPage usernamePage;

    @Test
    public void enterAnInvalidUsernameAdmin() {
        usernamePage.visit();
        usernamePage.enterUsername(INVALID_USERNAME_1);
        assertThat(usernamePage.isInvalidUsernameValidationMessageVisible(500)).isTrue();

    }

    @Test
    public void enterAnInvalidUsernameSuperAdmin() {
        usernamePage.visit();
        usernamePage.enterUsername(INVALID_USERNAME_2);
        assertThat(usernamePage.isInvalidUsernameValidationMessageVisible(500)).isTrue();

    }

    @Test
    public void enterMax3Characters() {
        usernamePage.visit();
        usernamePage.verifyMaxCharLimitOfAnInput(INVALID_USERNAME_3);
        assertThat(usernamePage.isValidUsernameValidationMessageVisible(500)).isTrue();

    }

    @Test
    public void enter100characters() {
        usernamePage.visit();
        usernamePage.verifyMaxCharLimitOfAnInput(INVALID_USERNAME_4);
        assertThat(usernamePage.isValidUsernameValidationMessageVisible(500)).isTrue();
    }

    @Test
    public void enterMin3characters() {
        usernamePage.visit();
        usernamePage.verifyMaxCharLimitOfAnInput(INVALID_USERNAME_5);
    }
}
