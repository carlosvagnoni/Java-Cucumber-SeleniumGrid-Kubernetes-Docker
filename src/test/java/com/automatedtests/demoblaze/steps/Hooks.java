package com.automatedtests.demoblaze.steps;

import com.automatedtests.demoblaze.utils.Configuration;
import io.cucumber.java.*;
import org.openqa.selenium.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class contains setup and teardown methods (hooks) for scenarios in a test suite.
 * It initializes the WebDriver and handles actions before and after scenario execution.
 */
public class Hooks {
    private static final Logger logging = Logger.getLogger(Hooks.class);
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ThreadLocal<String> browserName = new ThreadLocal<>();
    public static Configuration config;

    public static void setBrowserName(String name) {
        browserName.set(name);
    }

    @Before
    public void beforeScenarioChrome(Scenario scenario) throws MalformedURLException {
        logging.info(String.format("Running SCENARIO: %s (%s)", scenario.getName(), browserName.get()));
        try {
            config = Configuration.loadFromFile("config.json");
        } catch (IOException e) {
            logging.error(e);
        }

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(browserName.get());
        driver.set(new RemoteWebDriver(new URL("http://localhost:4444"), desiredCapabilities));
        driver.get().manage().window().maximize();

        switch (scenario.getName()) {
            case "User signs up successfully":
                SignupSteps.currentScenario.set(scenario);
            case "User logs in successfully":
                LoginSteps.currentScenario.set(scenario);
            case "User adds a laptop product to the cart":
                LoginSteps.currentScenario.set(scenario);
                AddProductToCartSteps.currentScenario.set(scenario);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        logging.info(String.format("Finished SCENARIO: %s (%s)", scenario.getName(), browserName.get()));
        switch (scenario.getStatus()) {
            case SKIPPED -> logging.info(String.format("One or more steps of this scenario was passed over during testing. (%s)", browserName.get()));
            case PASSED -> logging.info(String.format("The scenario was tested successfully. (%s)", browserName.get()));
            case FAILED -> {
                logging.error(String.format("One or more steps of this scenario failed. (%s)", browserName.get()));
                try {
                    byte[] screenshotAs = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshotAs, "image/png", String.format("%s_%s", scenario.getName(), browserName.get()));
                } catch (UnhandledAlertException e) {
                    logging.error(String.format("No screenshot could be taken. (%s)", browserName.get()));
                    logging.error(e);
                } finally {
                    driver.get().quit();
                }

            }
        }
        if (driver.get() != null) {
            driver.get().quit();
        }
    }
}
