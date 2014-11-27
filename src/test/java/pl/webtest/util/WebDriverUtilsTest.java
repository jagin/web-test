package pl.webtest.util;

import static java.io.File.separatorChar;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import pl.webtest.WebDriverFactory;

public class WebDriverUtilsTest {

	@Test
	public void saveScreenshotFirefoxTest() {
		WebDriverFactory wdf = new WebDriverFactory("firefox");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		wd.get("http://google.com");
		File screenshotFile = WebDriverUtils.saveScreenshot(wd, getScreenshotName("firefox"));
		wd.quit();
		
		assertThat(screenshotFile.exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFile.getAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFile.getAbsolutePath() + "\">Screenshot</a>");
    	}			
	}	
	
	@Test
	public void saveScreenshotChromeTest() {
		if(System.getProperty("webdriver.chrome.driver") == null) {
			throw new SkipException("The path to the driver executable (webdriver.chrome.driver) is not set");
		}
		
		WebDriverFactory wdf = new WebDriverFactory("chrome");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		wd.get("http://google.com");
		File screenshotFile = WebDriverUtils.saveScreenshot(wd, getScreenshotName("chrome"));
		wd.quit();
		
		assertThat(screenshotFile.exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFile.getAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFile.getAbsolutePath() + "\">Screenshot</a>");
    	}	
	}	
	
	@Test
	public void saveScreenshotIETest() {
		if(System.getProperty("webdriver.ie.driver") == null) {
			throw new SkipException("The path to the driver executable (webdriver.ie.driver) is not set");
		}
		
		WebDriverFactory wdf = new WebDriverFactory("internet explorer");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		WebDriver wd = wdf.createDriver(capabilities);
		wd.get("http://google.com");
		File screenshotFile = WebDriverUtils.saveScreenshot(wd, getScreenshotName("ie"));
		wd.quit();
		
		assertThat(screenshotFile.exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFile.getAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFile.getAbsolutePath() + "\">Screenshot</a>");
    	}	
	}		
	
	@Test
	public void saveScreenshotSafariTest() {
		if(System.getProperty("webdriver.safari.driver") == null) {
			throw new SkipException("The path to the driver executable (webdriver.safari.driver) is not set");
		}
		
		WebDriverFactory wdf = new WebDriverFactory("safari");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		wd.get("http://google.com");
		File screenshotFile = WebDriverUtils.saveScreenshot(wd, getScreenshotName("safari"));
		wd.quit();
		
		assertThat(screenshotFile.exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFile.getAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFile.getAbsolutePath() + "\">Screenshot</a>");
    	}		
	}		
		
	private String getScreenshotName(String browser) {
		return this.getClass().getCanonicalName().replace('.', separatorChar) + separatorChar + browser + separatorChar + System.currentTimeMillis();
	}	
	
}
