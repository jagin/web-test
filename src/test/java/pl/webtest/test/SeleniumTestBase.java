package pl.webtest.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import pl.webtest.Config;
import pl.webtest.WebDriverFactory;
import pl.webtest.listener.ScreenshotListener;

/**
 * Base class for all selenium test classes
 */
@Listeners(ScreenshotListener.class)
public abstract class SeleniumTestBase {
	private static final Logger logger = LoggerFactory.getLogger(SeleniumTestBase.class);
    private static List<WebDriver> webDriverPool = Collections.synchronizedList(new ArrayList<WebDriver>());
    private static ThreadLocal<WebDriver> webDriver = null;
    
    /**
     * Webdriver setup for a test suite.
     * 
	 * @param browser (firefox by default) name of the browser
	 * @param gridHubUrl selenium grid hub URL. Leave it empty to create a local browser @code WebDriver
	 * @param browserPlatform desired browser platform
	 * @param browserVersion desired browser version
	 * @param firefoxProfileName firefox profile name
	 * @param firefoxProfilePath firefox profile path
	 * @param javascriptEnabled enable javascript on the browser (true by default)
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"webtest.browser", "webtest.gridHubUrl", 
    	"webtest.browserPlatform", "webtest.browserVersion",
    	"webtest.firefox.profileName", "webtest.firefox.profilePath",
    	"webtest.javascriptEnabled"} /* add additional capabilities as needed */)
    public static void setupWebDriver(
    		@Optional("firefox") final String browser, 
    		@Optional final String gridHubUrl,
    		@Optional final String browserPlatform, 
    		@Optional final String browserVersion, 
    		@Optional final String firefoxProfileName,
    		@Optional final String firefoxProfilePath,
    		@Optional("true") final boolean javascriptEnabled) {

    	webDriver = new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
            	DesiredCapabilities capabilities;
            	WebDriverFactory wdf;
            	
            	String message = String.format("Webdriver configuration ["
            			+ "browser: %s, "
            			+ "grid: %s, "
            			+ "platform: %s, "
            			+ "version: %s, "
            			+ "firefox profile name: %s, "
            			+ "firefox profile path: %s "
            			+ "javascript: %b]", 
            			browser, 
            			gridHubUrl,
            			browserPlatform,
            			browserVersion,
            			firefoxProfileName,
            			firefoxProfilePath,
            			javascriptEnabled);
            	Reporter.log(message);
            	logger.info(message);
            	
            	if(StringUtils.isEmpty(gridHubUrl)) {
            		capabilities = new DesiredCapabilities();
            		wdf = new WebDriverFactory(browser);
            	} else {
            		// Desired capability for remote driver
        			// Set browser name
        			if (BrowserType.CHROME.equalsIgnoreCase(browser)) {
        				capabilities = DesiredCapabilities.chrome();
        			} else if (BrowserType.FIREFOX.equalsIgnoreCase(browser)) {
        				capabilities = DesiredCapabilities.firefox();
        			} else if (BrowserType.IE.equalsIgnoreCase(browser)) {
        				capabilities = DesiredCapabilities.internetExplorer();
        	        } else if (BrowserType.SAFARI.equalsIgnoreCase(browser)) {
        	        	capabilities = DesiredCapabilities.safari();
        	        } else if (BrowserType.HTMLUNIT.equalsIgnoreCase(browser)) {
        	        	capabilities = DesiredCapabilities.htmlUnit();
        	        /*
        			} else if (BrowserType.ANDROID.equals(browserName)) {
        				capability = DesiredCapabilities.android();
        			} else if (BrowserType.IPHONE.equals(browserName)) {
        				capability = DesiredCapabilities.iphone();
        			*/
        			} else {
        				throw new RuntimeException("Unsupported browser: " + browser);
        			}
        			
        			// Set browser platform
        			if (StringUtils.isNotEmpty(browserPlatform)) {
        				capabilities.setPlatform(Platform.valueOf(browserPlatform.toUpperCase()));
        			} else {
        				capabilities.setPlatform(Platform.ANY);
        			}
        			
        			// Set browser version
        			if (browserVersion != null) {
        				capabilities.setVersion(browserVersion);
        			}   
        			
        			wdf = new WebDriverFactory(gridHubUrl);
            	}            	
            	
            	// Custom capabilities for chrome
            	if (BrowserType.CHROME.equalsIgnoreCase(browser)) {
	                String[] switches={"--ignore-certificate-errors", "--disable-popup-blocking", "--disable-translate"};
	                capabilities.setCapability("chrome.switches",Arrays.asList(switches));
            	}
                
            	// Custom capabilities for ie
            	if (BrowserType.IE.equalsIgnoreCase(browser)) {
            		// Capability that defines to ignore browser protected mode settings during starting by IEDriverServer.
            		// See: http://jimevansmusic.blogspot.com/2012/08/youre-doing-it-wrong-protected-mode-and.html
            		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            	}
            	
            	// Custom capabilities for firefox
            	if (BrowserType.FIREFOX.equalsIgnoreCase(browser)) {
                	// Set firefox profile if any
            		FirefoxProfile profile = null;
            		if(StringUtils.isNotEmpty(firefoxProfileName)) {
                		ProfilesIni profilesIni = new ProfilesIni();
                		profile = profilesIni.getProfile(firefoxProfileName);
            		} else if (StringUtils.isNotEmpty(firefoxProfilePath)) {
            			profile = new FirefoxProfile(new File(firefoxProfilePath));
            		}
            		if (profile != null) {
                		// You can modify the profile here like:
                		// profile.setAssumeUntrustedCertificateIssuer(false);
                		// ...
                		
                		capabilities.setCapability(FirefoxDriver.PROFILE, profile);            			
            		}
            	}  
            	
            	// Enable/disable javascript
            	capabilities.setJavascriptEnabled(javascriptEnabled);
            	
            	// Create webdriver with the configured capabilities
                WebDriver webDriver = wdf.createDriver(capabilities);

                // Specifies the amount of time the driver should wait when
                // searching for an element if it is not immediately present.
                webDriver.manage().timeouts().implicitlyWait(
                        Config.WEBDRIVER_TIMEOUTS_IMPLICITY_WAIT,
                        TimeUnit.SECONDS);
                //Maximize the browser window
                webDriver.manage().window().maximize();
                
                // Add webdriver to the pool (for cleaning it up later)
                webDriverPool.add(webDriver);
                
                return webDriver;
            }
        };    	
    }
    
    @AfterMethod
    public static void clearCookies() {
    	getWebDriver().manage().deleteAllCookies();
    }     

	@AfterSuite(alwaysRun = true)
	public static void quitWebDriver() {
		for (WebDriver webDriver : webDriverPool) {
			webDriver.quit();
		}
	}   
    
    public static WebDriver getWebDriver() {
        return webDriver.get();
    }
}
