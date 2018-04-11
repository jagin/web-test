package pl.webtest;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Factory class to instantiate a WebDriver object. 
 * 
 * It returns an instance of the driver (local invocation) or an instance of RemoteWebDriver
 */
public class WebDriverFactory {
	private String browser;

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
     * @param capabilities desired capabilities
     * @return @code WebDriver
     */
    public WebDriver createDriver(DesiredCapabilities capabilities) {
		WebDriver webDriver = null;
		
		if (StringUtils.startsWith(browser, "http://") || StringUtils.startsWith(browser, "https://")) {
			// Create Remote WebDriver
			try {
				webDriver = new RemoteWebDriver(new URL(browser), capabilities);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		} else {
			// Create local webdriver
			if (BrowserType.CHROME.equalsIgnoreCase(browser)) {
				ChromeOptions options = new ChromeOptions();
				options.merge(capabilities);
				webDriver = new ChromeDriver(options);
			} else if (BrowserType.FIREFOX.equalsIgnoreCase(browser)) {
				FirefoxOptions options = new FirefoxOptions();
				options.merge(capabilities);
				webDriver = new FirefoxDriver(options);
			} else if (BrowserType.IE.equalsIgnoreCase(browser)) {
				InternetExplorerOptions options = new InternetExplorerOptions();
				options.merge(capabilities);
				webDriver = new InternetExplorerDriver(options);
	        } else if (BrowserType.EDGE.equalsIgnoreCase(browser)) {
				EdgeOptions options = new EdgeOptions();
				options.merge(capabilities);
				webDriver = new EdgeDriver(options);
			} else if (BrowserType.SAFARI.equalsIgnoreCase(browser)) {
				SafariOptions options = new SafariOptions();
				options.merge(capabilities);
	            webDriver = new SafariDriver(options);
			} else {
				throw new RuntimeException("Unsupported browser: " + browser);
			}						
		}
		
		if(Config.WEBDRIVER_EVENT_LOGGING) {
			// Wrap the webdriver with logging event listener
			webDriver = new EventFiringWebDriver(webDriver).register(new LoggingWebDriverEventListener());
		}

		return webDriver;
	}

}
