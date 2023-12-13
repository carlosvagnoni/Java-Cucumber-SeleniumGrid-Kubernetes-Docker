# Automated Web Testing with Java, Selenium, and Cucumber for Parallel Execution with Selenium Grid and Docker
![Static Badge](https://img.shields.io/badge/Java-logo?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=rgb(229%2C%2031%2C%2036)&color=rgb(22%2C%2027%2C%2034))
![Static Badge](https://img.shields.io/badge/Selenium-logo?style=for-the-badge&logo=selenium&logoColor=white&labelColor=rgb(0%2C%20174%2C%200)&color=rgb(22%2C%2027%2C%2034))
![Static Badge](https://img.shields.io/badge/Cucumber-logo?style=for-the-badge&logo=cucumber&logoColor=black&labelColor=rgb(35%2C%20217%2C%20108)&color=rgb(22%2C%2027%2C%2034))
![Static Badge](https://img.shields.io/badge/Selenium%20Grid-logo?style=for-the-badge&logo=selenium&logoColor=white&labelColor=rgb(0%2C%20174%2C%200)&color=rgb(22%2C%2027%2C%2034))
![Static Badge](https://img.shields.io/badge/docker-logo?style=for-the-badge&logo=docker&logoColor=white&labelColor=rgb(28%2C%20140%2C%20237)&color=rgb(22%2C%2027%2C%2034))

This project is an enhanced version of the [original automated web testing framework](https://github.com/carlosvagnoni/JavaSeleniumCucumber), now adapted for parallel execution across various browsers using Selenium Grid and Docker. It maintains the core features of automated testing with Java, Selenium, and Cucumber, following Behavior-Driven Development (BDD) best practices and implementing the Page Object Model design pattern.

This adaptation leverages the capabilities of Selenium Grid Hub and associated nodes for different browsers (Chrome, Edge, and Firefox), enabling remote execution of WebDriver tests.

To facilitate remote test execution, a Docker infrastructure has been implemented, consisting of the following images:

- [Selenium Grid Hub](https://hub.docker.com/r/selenium/hub): This image provides a hub for Selenium Grid, orchestrating remote execution of WebDriver tests when used in conjunction with one or more Selenium Grid nodes.

- [Selenium Grid Node with Chrome](https://hub.docker.com/r/selenium/node-chrome): This image provides a Selenium Grid node with Chrome support. When combined with the Selenium Grid Hub, it enables the execution of WebDriver tests remotely in environments with this specific browser.

- [Selenium Grid Node with Edge](https://hub.docker.com/r/selenium/node-edge): Similar to the former, this image provides a Selenium Grid node with Edge support, working in collaboration with the Selenium Grid Hub to remotely execute WebDriver tests in Microsoft Edge environments.

- [Selenium Grid Node with Firefox](https://hub.docker.com/r/selenium/node-firefox): Another Selenium Grid node image, this time compatible with Firefox. When used in conjunction with the Selenium Grid Hub, it enables remote execution of WebDriver tests in environments utilizing this browser.

This architecture maximizes the efficiency of automated tests by running them simultaneously across multiple browsers, providing a controlled and scalable environment through the use of Docker and Selenium Grid.

## Testing demoblaze.com Features 🧪

This suite of tests is specifically designed to validate and test features on the [demoblaze.com](https://www.demoblaze.com) website. You'll find feature files under the `tests/features` directory related to signup, login and adding products to the cart.

![JavaSeleniumCucumber](https://github.com/carlosvagnoni/JavaSeleniumCucumber/assets/106275103/73d6bb26-c86a-4ddc-8e1b-a9c376de3796)

## Table of Contents 📑
- [Requirements](#requirements)
- [Folder Structure](#folder-structure)
- [Installation](#installation)
- [Configuration](#configuration)
- [Test Execution](#test-execution)
- [Contact](#contact)

## <a id="requirements">Requirements 📋</a>

- JDK 21
- Lombok 1.18.30
- Selenium 4.15.0
- Cucumber 7.14.0
- Webdriver Manager 5.6.2

## <a id="folder-structure">Folder Structure 📂</a>

- **config.json:** Configuration file for variable data.
- **pom.xml:** Maven configuration file specifying project dependencies.
- **run.bat:** Script file for Windows environment execution.

### Directory "src/test/java/com/automatedtests/demoblaze"

- **ChromeTestRunner.java:** Cucumber test runner class for Chrome.
- **EdgeTestRunner.java:** Cucumber test runner class for Edge.
- **FirefoxTestRunner.java:** Cucumber test runner class for Firefox.
  
#### Directory "features"

- **001_Signup.feature:** Specification file for signup feature.
- **002_Login.feature:** Specification file for login feature.
- **003_AddProductToCart.feature:** Specification file for adding products to cart feature.

#### Directory "pages"

- **BasePage.java:** Page class for header and footer.
- **CartPage.java:** Page class for cart functionality.
- **HomePage.java:** Page class for home page functionality.
- **ProductPage.java:** Page class for product-related functionality.

#### Directory "steps"

- **AddProductToCartSteps.java:** Step definitions for adding products to cart.
- **Hooks.java:** Cucumber hooks for setup and teardown.
- **LoginSteps.java:** Step definitions for login functionality.
- **SignupSteps.java:** Step definitions for signup functionality.

#### Directory "utils"

- **Configuration.java:** Utility class for configuration settings.
- **Expect.java:** Custom assertion functions.
- **PageObject.java:** Definition of the base structure of the Page Object Model.

### Directory "resources"

- **log4j.properties:** Logging configuration file using Log4j.

## <a id="installation">Installation 🛠️</a>

1. Clone this repository:

    ```bash
    git clone https://github.com/carlosvagnoni/JavaSeleniumCucumberSeleniumGridDocker.git
    cd JavaSeleniumCucumberSeleniumGridDocker
    ```

2. Compile the project:

    ```bash
    mvn clean compile
    ```

## <a id="configuration">Configuration ⚙️</a>

To set up the Selenium Grid infrastructure for parallel execution, follow these steps:

### Create a Docker Network

Before starting the Selenium Hub and Nodes, create a Docker network:

```bash
docker network create grid
```

### Start the Hub

Launch the Selenium Hub using the created network:

```bash
docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub:latest
```

### Start the Nodes

Start the individual Selenium Grid Nodes for Chrome, Edge, and Firefox using the created network:

```bash
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \
    --shm-size="2g" \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-chrome:latest

docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \
    --shm-size="2g" \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-edge:latest

docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \
    --shm-size="2g" \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-firefox:latest
```

The Hub and Nodes will be created within the same network ('grid'), allowing them to recognize each other by their container names. Adjust any additional configurations or environmental variables as needed for your specific setup.

Remember to ensure that Docker is installed and properly configured before executing these commands. Once the Hub and Nodes are running, you can proceed with executing tests via the provided instructions.

## <a id="test-execution">Test Execution ▶️</a>

Run all the tests:

```bash
mvn test
```

Open reports:

```bash
start "" "target\reports\demoblaze-chrome.html"
start "" "target\reports\demoblaze-edge.html"
start "" "target\reports\demoblaze-firefox.html"
```

**NOTE:**

- Set up the respective environment variables beforehand.
- On Windows environments, you can directly execute the `run.bat` file.

## <a id="contact">Contact 📧</a> 

If you have any questions or suggestions, feel free to contact me through my social media accounts.

Thank you for your interest in this project!
