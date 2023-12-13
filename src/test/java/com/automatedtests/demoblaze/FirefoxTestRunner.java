package com.automatedtests.demoblaze;

import com.automatedtests.demoblaze.steps.Hooks;
import org.junit.Before;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
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
                "pretty", "html:target/reports/demoblaze-firefox.html",
                "json:target/reports/cucumber-firefox.json"
        }
)
public class FirefoxTestRunner {
        @BeforeClass
        public static void beforeScenario() {
                Hooks.setBrowserName("firefox");
        }
}
