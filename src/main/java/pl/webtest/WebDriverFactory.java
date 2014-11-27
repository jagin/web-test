package pl.webtest;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
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
     * @param capability desired capability
     * @return @code WebDriver
     */
    public WebDriver createDriver(DesiredCapabilities capability) {   
		WebDriver webDriver = null;
		
		if (StringUtils.startsWith(browser, "http://") || StringUtils.startsWith(browser, "https://")) {
			// Create Remote WebDriver
			try {
				webDriver = new RemoteWebDriver(new URL(browser), capability);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		} else {
			// Create local webdriver
			if (BrowserType.CHROME.equalsIgnoreCase(browser)) {
				webDriver = new ChromeDriver(capability);
			} else if (BrowserType.FIREFOX.equalsIgnoreCase(browser)) {
				webDriver = new FirefoxDriver(capability);
			} else if (BrowserType.IE.equalsIgnoreCase(browser)) {
				webDriver = new InternetExplorerDriver(capability);
	        } else if (BrowserType.SAFARI.equalsIgnoreCase(browser)) {
	            webDriver = new SafariDriver(capability);
	        } else if (BrowserType.HTMLUNIT.equalsIgnoreCase(browser)) {
	            webDriver = new HtmlUnitDriver(capability);
	        /*
	        	IPhoneDriver nad AndroidDriver are deprecated
	        	If you are looking to use WebDriver with iOS mobile Safari and are currently testing only on simulators 
	        	please have a look at ios-driver (http://ios-driver.github.io/ios-driver/) or appium (http://appium.io/)
	        } else if (BrowserType.IPHONE.equals(browserName)) {
				webDriver = new IPhoneDriver(capability);
			} else if (BrowserType.ANDROID.equals(browserName)) {
				webDriver = new AndroidDriver(capability);
			*/
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
