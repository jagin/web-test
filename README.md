# web-test

Selenium automation testing project (Maven based) written in Java with [Selenium](http://www.seleniumhq.org) Webdriver and [TestNG](http://testng.org).

It is a baseline template for new Selenium test projects.

## Installation and running

Prerequisits are Maven 3.x and JDK 1.8 or higher ready to use.

Be sure you have Internet connection :)

First [download](http://www.seleniumhq.org/download/) the browser drivers for required browser and 
configure it in `pom.xml`. Look for:

```xml
    <!-- Set webdrivers location for selenium -->
    <webdriver.gecko.driver>drivers/geckodriver.exe</webdriver.gecko.driver>
    <webdriver.chrome.driver>drivers/chromedriver.exe</webdriver.chrome.driver>
    <webdriver.ie.driver>drivers/IEDriverServer.exe</webdriver.ie.driver>
    <webdriver.edge.driver>drivers/MicrosoftWebDriver.exe</webdriver.edge.driver>
    <!--webdriver.safari.driver></webdriver.safari.driver-->
```

You can create `drivers` directory in the project location and put the driver executable files here.
Comment the one you are not using.

Run all tests with the following command: 

```mvn test```

When the tests are finished we can look at the nice looking report (thanks to [ReportNG](http://reportng.uncommons.org)). Just open `target\surefire-reports\html\index.html` in your browser.

Read the source code. There are a lot of comments.

# Configuration

We have different levels of configuration. First there is a `pom.xml` file with a lot of configuration possibilities if you know [Maven](http://maven.apache.org) well (read the comments included in the file for some help).

The most important setting is `webtest.testng`. By default it is set to `all` indicating the TestNG XML configuration file `all.testng.xml` in the `src\test\resources\` folder.

We can run a separate test suite with the command:

```mvn test -Dwebtest.testng=google-search```

TestNG settings is the next level. TestNG is a testing framework inspired from JUnit and NUnit but introducing some new functionalities that make it more powerful and easier to use.
Some of its features are used in `src\test\java\pl\webtest\test\SeleniumTestBase.java` class file which is the most important part of this project. Every test extending this class will get a configurable and thread-safe webdriver.

We can run our tests on different browsers:

```mvn test -Dwebtest.testng=google-search -Dwebtest.browser=chrome```

```mvn test -Dwebtest.testng=google-search -Dwebtest.browser="internet explorer"```

If you want to run your tests suite with the selected firefox profile just run:

```mvn test -Dwebtest.testng=google-search -Dwebtest.browser=firefox -Dwebtest.firefox.profileName=Selenium```

There is also possibility to run the tests on a selenium grid hub but i was not able to test it.

For other evailable settings read the source of the `SeleniumTestBase.java`.

You can also use all params of TestNG like `group`, `test`:

```mvn test -Dgroups=slow``` (this is only an example as there is no tests grouping in our project but feel free to create some)

```mvn test -Dtest=WebDriverFactoryTest``` - you can run single test class

The last level of settings is defined in `src\main\java\pl\webtest\Config.java`.
Read the comments in the file and experiment. For example we can switch off extensive webdriver events logging and reporting:

```mvn test -Dwebtest.webDriverEventLogging=false```

Spot the test report for the difference.

# Multithreaded tests
TestNG allows you to run your test methods in separate threads. You can configure the size of the thread pool and the time-out and TestNG takes care of the rest.

Look at the `src\test\resources\all.testng.xml` and experiment with `parallel` and `thread-count` (read TestNG documentation for more details):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Test Automation Suite" verbose="1" parallel="tests" thread-count="3">
	<suite-files>
		<suite-file path="./webdriver-test.testng.xml" />
		<suite-file path="./google-search.testng.xml" />
		<suite-file path="./google-translate.testng.xml" />
	</suite-files>
</suite>
```

Be aware that webdriver settings (the one from `SeleniumTestBase.java`) are per suite. So all tests in the suite will use the same settings.

# Links

- [Maven](http://maven.apache.org)
- [Selenium](http://www.seleniumhq.org)
- [TestNG](http://testng.org)
- [ReportNG](http://reportng.uncommons.org)
- [hamcrest](https://code.google.com/p/hamcrest)