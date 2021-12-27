package org.automation;

import org.automation.pages.LoginPage;
import org.automation.pages.WelcomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginTest extends BaseTest {
    private static final String EMAIL = "admin@gmail.com";
    private static final String PASSWORD = "123";
    private static final String INVALID_MAIL = "xyzabc123@";
    private static final String BLANK_MAIL = "";
    private static final String BLANK_PASSWORD = "";

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

    //Validate logging into the application using invalid email and valid password
    @Test
    public void useAnInvalidEmailAndAValidPassword() {
        loginPage.visit();
        loginPage.doLogin(INVALID_MAIL, PASSWORD);
        loginPage.fillInvalidEmailAndValidPassword();
    }

    //Validate logging into the application using blank email and blank password
    @Test
    public void useBlankEmailAndPassword() {
        loginPage.visit();
        loginPage.fillBlankEmailandPassword();
    }

    //Validate logging into the application using blank email and a password
    @Test
    public void useAnBlankEmailAndAValidPassword() {
        loginPage.visit();
        loginPage.doLogin(BLANK_MAIL, PASSWORD);
        loginPage.fillBlankEmail();
    }

    //Validate logging into the application using an email and blank password
    @Test
    public void useAnEmailAndBlankPassword() {
        loginPage.visit();
        loginPage.doLogin(EMAIL, BLANK_PASSWORD);
        loginPage.fillBlankPassword();
    }
}
