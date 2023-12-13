package com.automatedtests.demoblaze.steps;

import com.automatedtests.demoblaze.utils.Expect;
import com.automatedtests.demoblaze.pages.BasePage;
import com.automatedtests.demoblaze.pages.CartPage;
import com.automatedtests.demoblaze.pages.HomePage;
import com.automatedtests.demoblaze.pages.ProductPage;
import com.automatedtests.demoblaze.utils.Configuration;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class contains Cucumber steps for adding a product to the shopping cart on a demo e-commerce site.
 */
public class AddProductToCartSteps {
    private static Logger logging;

    private WebDriver driver;

    private Configuration config;

    private Capabilities capabilities;

    public static ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();

    private BasePage basePage;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    public AddProductToCartSteps() {
        logging = Logger.getLogger(AddProductToCartSteps.class);

        driver = Hooks.driver.get();

        config = Hooks.config;

        capabilities = ((RemoteWebDriver) driver).getCapabilities();

        basePage = new BasePage(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Given("the user is browsing the list of available products.")
    public void theUserIsBrowsingProducts() {
        try {
            new Expect(driver.getCurrentUrl()).toBeEqual("https://www.demoblaze.com/");
        } catch (AssertionError e) {
            logging.error(e);
        }
        try {
            byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String browserName = capabilities.getBrowserName();
            String imageName = String.format("the_user_is_browsing_the_list_of_available_products_%s", browserName);
            currentScenario.get().attach(screenshotAs, "image/png", imageName);
        } catch (UnhandledAlertException e) {
            String browserName = capabilities.getBrowserName();
            logging.error(String.format("No screenshot could be taken. (%s)", browserName));
            logging.error(e);
        }
    }

    @When("the user selects a product from the 'laptops' category.")
    public void theUserSelectsProductFromCategory() {
        homePage.clickLaptopsCategoryButton();
        homePage.waitForMacbookButton();
        homePage.clickMacbookButton();
    }

    @When("the user adds the selected product to the shopping cart.")
    public void theUserAddsProductToCart() {
        productPage.waitForMacbookTitle();
        productPage.clickAddToCart();
        String expectedText = "Product added.";
        productPage.verifyAlertSuccessfulAddedToCart(expectedText);
        productPage.submitAlert();
    }

    @Then("the product should be added to the user's shopping cart.")
    public void theProductShouldBeInCart() {
        basePage.clickCartButton();
        cartPage.waitForMacbookTitleInCart();
        String expectedText = "MacBook air";
        cartPage.verifyMacbookTitleInCart(expectedText);
        try {
            byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String browserName = capabilities.getBrowserName();
            String imageName = String.format("the_product_should_be_added_to_the_user_shopping_cart_%s", browserName);
            currentScenario.get().attach(screenshotAs, "image/png", imageName);
        } catch (UnhandledAlertException e) {
            String browserName = capabilities.getBrowserName();
            logging.error(String.format("No screenshot could be taken. (%s)", browserName));
            logging.error(e);
        }
    }
}
