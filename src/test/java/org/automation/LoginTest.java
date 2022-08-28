package org.automation;

import org.automation.pages.LoginPage;
import org.automation.pages.WelcomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {
    private static final String EMAIL = "admin@gmail.com";
    private static final String PASSWORD = "123";
    private static final String INVALID_MAIL = "xyzabc123@";
    private static final String BLANK_MAIL = "";
    private static final String BLANK_PASSWORD = "";
    private static final String PAGE = "https://automation-in-practice.herokuapp.com/simple-login";
    private static final String INVALID_EMAIL_MESSAGE = "Please enter a valid email";
    private static final String INVALID_PASSWORD_MESSAGE = "Please enter a password";

    @Autowired
    private LoginPage loginPage;
    @Autowired
    private WelcomePage welcomePage;

    @Test
    public void enterAValidEmailAndPassword() {
        loginPage.visit();
        loginPage.doLogin(EMAIL, PASSWORD);

        String welcomeMsg = welcomePage.getWelcomeText();
        assertThat(welcomeMsg).contains(EMAIL);
    }

    //Validate logging into the application using invalid email and valid password
    @Test
    public void enterAnInvalidEmailAndValidPassword() {
        loginPage.visit();
        loginPage.doLogin(INVALID_MAIL, PASSWORD);

        //Verify if validation message are being displayed
        List<String> invalidMessages = loginPage.getInvalidMessages();

        assertThat(invalidMessages).hasSize(1).contains(INVALID_EMAIL_MESSAGE);
    }

    //Validate logging into the application using empty email and empty password
    @Test
    public void enterEmptyEmailAndPassword() {
        loginPage.visit();
        // Validate if inputs are empty
        loginPage.validateEmptyEmailAndPassword(BLANK_MAIL, BLANK_PASSWORD);
        // Verify if validation messages are being displayed
        List<String> invalidMessages = loginPage.getInvalidMessages();
        assertThat(invalidMessages).hasSize(2).contains(INVALID_EMAIL_MESSAGE, INVALID_PASSWORD_MESSAGE);

        //Verify is on current URL
        assertThat(loginPage.getCurrentURL()).isEqualTo(PAGE);
    }
}
