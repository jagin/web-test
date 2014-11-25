package pl.webtest;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/*
 * Factory to instantiate a WebDriver object. It returns an instance of the driver (local invocation) or an instance of RemoteWebDriver
 */
public class WebDriverFactory {

	/* Browsers constants */
	public static final String CHROME_BROWSER = "chrome";
	public static final String FIREFOX_BROWSER = "firefox";
	public static final String INTERNET_EXPLORER_BROWSER = "ie";
    public static final String SAFARI_BROWSER = "safari";
	public static final String IPHONE_BROWSER = "iphone";
	public static final String ANDROID_BROWSER = "android";
	public static final String HTML_UNIT_BROWSER = "htmlunit";
	
	/* Platform constants */
	public static final String WINDOWS_PLATFORM = "windows";
	public static final String ANDROID_PLATFORM = "android";
	public static final String MAC_PLATFORM = "mac";
	public static final String LINUX_PLATFORM = "linux";	
	
	private String browser;
	private String browserPlatform;
	private String browserVersion;
	private String gridHubUrl;

    public WebDriverFactory(String browser) {
        this.browser = browser;
    }
    
    public WebDriverFactory(String browser, String browserPlatform, String browserVersion, String gridHubUrl) {
        this.browser = browser;
        this.browserPlatform = browserPlatform;
        this.browserVersion = browserVersion;
        this.gridHubUrl = gridHubUrl;
    }
    
    public WebDriver createDriver(DesiredCapabilities capability) {   
		WebDriver webDriver = null;
		
		if (StringUtils.isEmpty(gridHubUrl)) {
			// Create local webdriver
			if (CHROME_BROWSER.equals(browser)) {
				webDriver = new ChromeDriver(capability);
			} else if (FIREFOX_BROWSER.equals(browser)) {
				webDriver = new FirefoxDriver(capability);
			} else if (INTERNET_EXPLORER_BROWSER.equals(browser)) {
				capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				webDriver = new InternetExplorerDriver(capability);
	        } else if (SAFARI_BROWSER.equals(browser)) {
	            webDriver = new SafariDriver(capability);
	        /*
	        	IPhoneDriver nad AndroidDriver are deprecated
	        } else if (IPHONE_BROWSER.equals(browserName)) {
				webDriver = new IPhoneDriver(capability);
			} else if (ANDROID_BROWSER.equals(browserName)) {
				webDriver = new AndroidDriver(capability);
			*/
			} else {
				throw new RuntimeException("Unsupported browser: " + browser);
			}
		} else {
			// Create remote webdriver
			
			// Set browser name
			if (CHROME_BROWSER.equals(browser)) {
				capability = DesiredCapabilities.chrome();
			} else if (FIREFOX_BROWSER.equals(browser)) {
				capability = DesiredCapabilities.firefox();
			} else if (INTERNET_EXPLORER_BROWSER.equals(browser)) {
				capability = DesiredCapabilities.internetExplorer();
	        } else if (SAFARI_BROWSER.equals(browser)) {
	            capability = DesiredCapabilities.safari();
	        /*
			} else if (ANDROID_BROWSER.equals(browserName)) {
				capability = DesiredCapabilities.android();
			} else if (IPHONE_BROWSER.equals(browserName)) {
				capability = DesiredCapabilities.iphone();
			*/
			} else {
				throw new RuntimeException("Unsupported browser: " + browser);
			}
			// Set browser platform
			if (MAC_PLATFORM.equalsIgnoreCase(browserPlatform)) {
				capability.setPlatform(Platform.MAC);
			} else if (LINUX_PLATFORM.equalsIgnoreCase(browserPlatform)) {
				capability.setPlatform(Platform.LINUX);
			} else if (WINDOWS_PLATFORM.equalsIgnoreCase(browserPlatform)) {
				capability.setPlatform(Platform.WINDOWS);
			} else if (ANDROID_PLATFORM.equalsIgnoreCase(browserPlatform)) {
				capability.setPlatform(Platform.ANDROID);
			} else {
				capability.setPlatform(Platform.ANY);
			}
			// Set browser version
			if (browserVersion != null) {
				capability.setVersion(browserVersion);
			}
			
			// Create Remote WebDriver
			try {
				webDriver = new RemoteWebDriver(new URL(gridHubUrl), capability);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}			
		}
		
		if(Config.SCREENSHOT_ON_FAILURE) {
			webDriver = new EventFiringWebDriver(webDriver).register(new LoggingWebDriverEventListener());
		}

		return webDriver;
	}

}
