package pl.webtest;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class to instantiate a WebDriver object.
 * <p>
 * It returns an instance of the driver (local invocation) or an instance of RemoteWebDriver
 */
public class WebDriverFactory {
    private String browser;
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    /**
     * WebDriver constructor
     *
     * @param browser (required) name of the browser or the grid hub URL
     */
    public WebDriverFactory(String browser) {
        this.browser = browser;
    }

    /**
     * Create @code WebDriver
     *
     * @param capability desired capability
     * @return @code WebDriver
     */
    public WebDriver createDriver(DesiredCapabilities capability) {
        WebDriver webDriver;

        if (browser.startsWith("http://") || browser.startsWith("https://")) {
            // Create Remote WebDriver
            logger.info("Trying to start remote webdriver session");
            try {
                webDriver = new RemoteWebDriver(new URL(browser), capability);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Create local webdriver
            if (BrowserType.CHROME.equalsIgnoreCase(browser)) {
                logger.info("Starting local Chrome session");
                webDriver = new ChromeDriver(capability);
            } else if (BrowserType.FIREFOX.equalsIgnoreCase(browser)) {
                logger.info("Starting local Firefox session");
                webDriver = new FirefoxDriver(capability);
            } else if (BrowserType.IE.equalsIgnoreCase(browser)) {
                logger.info("Starting local IE session");
                webDriver = new InternetExplorerDriver(capability);
            } else if (BrowserType.SAFARI.equalsIgnoreCase(browser)) {
                logger.info("Starting local Safari session");
                webDriver = new SafariDriver(capability);
            } else {
                throw new RuntimeException("Unsupported browser: " + browser);
            }
        }
        if (Config.WEBDRIVER_EVENT_LOGGING) {
            // Wrap the webdriver with logging event listener
            webDriver = new EventFiringWebDriver(webDriver).register(new LoggingWebDriverEventListener());
        }

        return webDriver;
    }

}
