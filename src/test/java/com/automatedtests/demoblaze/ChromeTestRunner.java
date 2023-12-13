package com.automatedtests.demoblaze;

import com.automatedtests.demoblaze.steps.Hooks;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * This class serves as the Test Runner for DemoBlaze automated tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/automatedtests/demoblaze/features", // Location of feature files
        glue = {                                                          // Glue between feature files and step definition files
                "com/automatedtests/demoblaze/steps",
                "com/automatedtests/demoblaze/utils"
        },
        plugin = {                                                        // Format and location of reports
                "pretty", "html:target/reports/demoblaze-chrome.html",
                "json:target/reports/cucumber-chrome.json"
        }
)
public class ChromeTestRunner {
        @BeforeClass
        public static void beforeScenario() {
                Hooks.setBrowserName("chrome");
        }
}
