package pl.webtest.util;

import static java.io.File.separatorChar;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Path;

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
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("marionette", true);
		WebDriver wd = wdf.createDriver(desiredCapabilities);
		wd.get("http://google.com");
		Path screenshotFilePath = WebDriverUtils.saveScreenshot(wd, getScreenshotName("firefox"));
		wd.quit();
		
		assertThat(screenshotFilePath.toFile().exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFilePath.toAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFilePath.toAbsolutePath() + "\">Screenshot</a>");
    	}			
	}	
	
	@Test
	public void saveScreenshotChromeTest() {
		WebDriverFactory wdf = new WebDriverFactory("chrome");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		wd.get("http://google.com");
		Path screenshotFilePath = WebDriverUtils.saveScreenshot(wd, getScreenshotName("chrome"));
		wd.quit();
		
		assertThat(screenshotFilePath.toFile().exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFilePath.toAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFilePath.toAbsolutePath() + "\">Screenshot</a>");
    	}	
	}	
	
	@Test
	public void saveScreenshotIETest() {
		if (!IS_OS_WINDOWS) {
			throw new SkipException("IE tests can be only run in windows");
		}
		WebDriverFactory wdf = new WebDriverFactory("internet explorer");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		WebDriver wd = wdf.createDriver(capabilities);
		wd.get("http://google.com");
		Path screenshotFilePath = WebDriverUtils.saveScreenshot(wd, getScreenshotName("ie"));
		wd.quit();
		
		assertThat(screenshotFilePath.toFile().exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFilePath.toAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFilePath.toAbsolutePath() + "\">Screenshot</a>");
    	}	
	}		
	
	@Test
	public void saveScreenshotSafariTest() {
		if (!IS_OS_MAC) {
			throw new SkipException("Safari test can be only run in mac");
		}
		WebDriverFactory wdf = new WebDriverFactory("safari");
		WebDriver wd = wdf.createDriver(new DesiredCapabilities());
		wd.get("http://google.com");
		Path screenshotFile = WebDriverUtils.saveScreenshot(wd, getScreenshotName("safari"));
		wd.quit();
		
		assertThat(screenshotFile.toFile().exists(), equalTo(true));
		
    	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
    		Reporter.log("Screenshot: " + screenshotFile.toAbsolutePath());
    	} else {
    		Reporter.log("<a href=\"file:///" + screenshotFile.toAbsolutePath() + "\">Screenshot</a>");
    	}		
	}		
		
	private String getScreenshotName(String browser) {
		return this.getClass().getCanonicalName().replace('.', separatorChar) + separatorChar + browser + separatorChar + System.currentTimeMillis();
	}	
	
}
