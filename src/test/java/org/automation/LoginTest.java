package org.automation;

import org.automation.pages.LoginPage;
import org.automation.pages.WelcomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginTest extends BaseTest {
    private static final String EMAIL = "admin@gmail.com";
    private static final String PASSWORD = "123";

    @Autowired
    private LoginPage loginPage;
    @Autowired
    private WelcomePage welcomePage;

    @Test
    public void doLogin() {
        loginPage.visit();
        loginPage.doLogin(EMAIL, PASSWORD);

        String welcomeMsg = welcomePage.getWelcomeText();
        assertThat(welcomeMsg).contains(EMAIL);
        welcomePage.clickOnReturnToForm();
    }
}
