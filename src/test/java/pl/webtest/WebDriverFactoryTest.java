package pl.webtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * Tests for @code WebDriverFactory
 * 
 * This test will check the availability of browser drivers and proper configuration
 */
public class WebDriverFactoryTest {
	
	@Test
	public void createFirefoxDriverTest() {
		WebDriverFactory wdf = new WebDriverFactory("firefox");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		assertThat(wd, is(notNullValue()));
		wd.quit();
	}
	
	@Test
	public void createChromeDriverTest() {
		if(System.getProperty("webdriver.chrome.driver") == null) {
			throw new SkipException("The path to the driver executable (webdriver.chrome.driver) is not set");
		}
		
		WebDriverFactory wdf = new WebDriverFactory("chrome");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		assertThat(wd, is(notNullValue()));
		wd.quit();
	}	
	
	
	@Test
	public void createIEDriverTest() {
		if(System.getProperty("webdriver.ie.driver") == null) {
			throw new SkipException("The path to the driver executable (webdriver.ie.driver) is not set");
		}	
		
		WebDriverFactory wdf = new WebDriverFactory("internet explorer");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		WebDriver wd = wdf.createDriver(capabilities);
		assertThat(wd, is(notNullValue()));
		wd.quit();
	}
	
	@Test
	public void createSafariDriverTest() {
		if(System.getProperty("webdriver.safari.driver") == null) {
			throw new SkipException("The path to the driver executable (webdriver.safari.driver) is not set");
		}	
		
		WebDriverFactory wdf = new WebDriverFactory("safari");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		assertThat(wd, is(notNullValue()));
		wd.quit();
	}	
}
