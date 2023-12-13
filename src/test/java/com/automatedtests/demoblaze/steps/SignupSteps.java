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
 * This class contains Cucumber steps for user registration on the application.
 */
public class SignupSteps {
    private static Logger logging;

    private WebDriver driver;

    private Configuration config;

    private String username;
    private String password;

    private Capabilities capabilities;

    public static ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();

    private BasePage basePage;

    public SignupSteps() {
        logging = Logger.getLogger(SignupSteps.class);

        driver = Hooks.driver.get();

        config = Hooks.config;

        capabilities = ((RemoteWebDriver) driver).getCapabilities();

        basePage = new BasePage(driver);
    }

    @Given("the user is on the Registration Page.")
    public void theUserIsOnRegistrationPage() {
        driver.get(config.getUrl());
        basePage.clickSignupButton();
        basePage.waitForSignupTitle();
        basePage.verifySignupTitle();
        try {
            byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String browserName = capabilities.getBrowserName();
            String imageName = String.format("the_user_is_on_the_Registration_Page_%s", browserName);
            currentScenario.get().attach(screenshotAs, "image/png", imageName);
        } catch (UnhandledAlertException e) {
            String browserName = capabilities.getBrowserName();
            logging.error(String.format("No screenshot could be taken. (%s)", browserName));
            logging.error(e);
        }
    }

    @When("the user provides the following registration details: {string}, {string}.")
    public void theUserProvidesRegistrationDetails(String username, String password) {
        this.username = username;
        this.password = password;
        basePage.enterSignupUsername(username);
        basePage.enterSignupPassword(password);
        basePage.verifyEnteredCredentials(username, password);
        try {
            byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String browserName = capabilities.getBrowserName();
            String imageName = String.format("the_user_provides_the_following_registration_details_%s", browserName);
            currentScenario.get().attach(screenshotAs, "image/png", imageName);
        } catch (UnhandledAlertException e) {
            String browserName = capabilities.getBrowserName();
            logging.error(String.format("No screenshot could be taken. (%s)", browserName));
            logging.error(e);
        }
    }

    @When("the user clicks on the Sign Up button.")
    public void theUserClicksSignUpButton() {
        basePage.submitSignup();
    }

    @Then("the user should be registered successfully.")
    public void theUserShouldBeRegisteredSuccessfully() {
        String expectedText = "Sign up successful.";
        basePage.switchToAlert();
        basePage.verifyAlertSuccessfulSignup(expectedText);
        basePage.submitAlert();
    }

}
