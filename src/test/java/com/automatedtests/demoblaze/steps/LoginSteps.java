package com.automatedtests.demoblaze.steps;

import com.automatedtests.demoblaze.pages.BasePage;
import com.automatedtests.demoblaze.utils.Configuration;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class contains Cucumber steps for user login functionality on the application.
 */
public class LoginSteps {
    private static Logger logging;

    private WebDriver driver;

    private Configuration config;

    private String username;
    private String password;

    private Capabilities capabilities;

    public static ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();

    private BasePage basePage;

    public LoginSteps() {
        logging = Logger.getLogger(LoginSteps.class);

        driver = Hooks.driver.get();

        config = Hooks.config;

        capabilities = ((RemoteWebDriver) driver).getCapabilities();

        basePage = new BasePage(driver);
    }

    @Given("the user has signed up with credentials: {string}, {string}.")
    public void theUserHasSignedUpWithCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Given("the user is on the Login Page.")
    public void theUserIsOnLoginPage() {
        driver.get(config.getUrl());
        basePage.clickLoginButton();
        basePage.waitForLoginTitle();
        basePage.verifyLoginTitle();
        try {
            byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String browserName = capabilities.getBrowserName();
            String imageName = String.format("the_user_is_on_the_Login_Page_%s", browserName);
            currentScenario.get().attach(screenshotAs, "image/png", imageName);
        } catch (UnhandledAlertException e) {
            String browserName = capabilities.getBrowserName();
            logging.error(String.format("No screenshot could be taken. (%s)", browserName));
            logging.error(e);
        }
    }

    @When("the user inputs their username and password into the form.")
    public void theUserInputsUsernameAndPassword() {
        basePage.enterLoginUsername(username);
        basePage.enterLoginPassword(password);
    }

    @When("the user clicks on the Submit button.")
    public void theUserClicksSubmitButton() {
        basePage.submitLogin();
    }

    @Then("the user should be logged in.")
    public void theUserShouldBeLoggedIn() {
        String expectedText = String.format("Welcome %s", username);
        basePage.waitForLoggedInUsername(expectedText);
        basePage.verifyLoggedInUsername(expectedText);
        try {
            byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String browserName = capabilities.getBrowserName();
            String imageName = String.format("the_user_should_be_logged_in_%s", browserName);
            currentScenario.get().attach(screenshotAs, "image/png", imageName);
        } catch (UnhandledAlertException e) {
            String browserName = capabilities.getBrowserName();
            logging.error(String.format("No screenshot could be taken. (%s)", browserName));
            logging.error(e);
        }
    }
}
