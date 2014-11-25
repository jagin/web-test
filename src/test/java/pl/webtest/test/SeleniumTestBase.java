package pl.webtest.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import pl.webtest.WebDriverFactory;
import pl.webtest.listener.ScreenshotListener;
import pl.webtest.Config;

/*
 * Base class for all selenium test classes
 */
@Listeners(ScreenshotListener.class)
public abstract class SeleniumTestBase {
    private static List<WebDriver> webDriverPool = Collections.synchronizedList(new ArrayList<WebDriver>());
    private static ThreadLocal<DesiredCapabilities> desiredCapabilities = null;
    private static ThreadLocal<WebDriver> webDriver = null;
    
    @BeforeSuite(alwaysRun = true)
    @Parameters({"webtest.browser", "webtest.browserPlatform", "webtest.browserVersion", "webtest.gridHubUrl"})
    public static void setupWebDriver(
    		@Optional("firefox") final String browser, 
    		@Optional final String browserPlatform, 
    		@Optional final String browserVersion, 
    		@Optional final String gridHubUrl) {
    	
    	desiredCapabilities = new ThreadLocal<DesiredCapabilities>() {
            @Override
            protected DesiredCapabilities initialValue() {
            	// Default desire capability
            	DesiredCapabilities capabilities = new DesiredCapabilities();

            	return capabilities;
            }
        };    
        
    	webDriver = new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                WebDriverFactory wdf = new WebDriverFactory(browser, browserPlatform, browserVersion, gridHubUrl);
                WebDriver webDriver = wdf.createDriver(desiredCapabilities.get());

                // Specifies the amount of time the driver should wait when
                // searching for an element if it is not immediately present.
                webDriver.manage().timeouts().implicitlyWait(
                        Config.WEBDRIVER_TIMEOUTS_IMPLICITY_WAIT,
                        TimeUnit.SECONDS);
                //Maximize the browser window
                webDriver.manage().window().maximize();
                
                // Add webdriver to the pool (for clean it up later)
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
		for (WebDriver driver : webDriverPool) {
			driver.quit();
		}
	}   
    
    public static WebDriver getWebDriver() {
        return webDriver.get();
    }
    
    public static DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities.get();
    }    
}
