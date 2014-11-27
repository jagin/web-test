package pl.webtest.listener;

import static java.io.File.separatorChar;

import java.io.File;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.reporters.ExitCodeListener;

import pl.webtest.Config;
import pl.webtest.test.SeleniumTestBase;
import pl.webtest.util.WebDriverUtils;

/**
 *  TestNG listener for screenshot savings
 */
public class ScreenshotListener extends ExitCodeListener {
	
    @Override
    public void onTestFailure(ITestResult result) {
    	super.onTestFailure(result);
    	
        if (Config.SCREENSHOT_ON_FAILURE) {
            String screenshotName = getScreenshotName(
            		result.getMethod().getTestClass().getName(), 
            		result.getMethod().getMethodName());
        	// Save the browser screenshot if there was an error
        	File screenshotFile = WebDriverUtils.saveScreenshot(SeleniumTestBase.getWebDriver(), screenshotName);
        	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
        		Reporter.log("Failure screenshot: " + screenshotFile.getAbsolutePath());
        	} else {
        		Reporter.log("<a href=\"file:///" + screenshotFile.getAbsolutePath() + "\">Failure screenshot</a>");
        	}
        }
    }
    
	@Override
	public void onTestSuccess(ITestResult result) {
		super.onTestSuccess(result);

		if (Config.SCREENSHOT_ON_SUCCESS) {
            String screenshotName = getScreenshotName(
            		result.getMethod().getTestClass().getName(), 
            		result.getMethod().getMethodName());
			// Save the browser screenshot
            File screenshotFile = WebDriverUtils.saveScreenshot(SeleniumTestBase.getWebDriver(), screenshotName);
        	if(Boolean.parseBoolean(System.getProperty("org.uncommons.reportng.escape-output", "true"))) {
        		Reporter.log("Success screenshot: " + screenshotFile.getAbsolutePath());
        	} else {
        		Reporter.log("<a href=\"file:///" + screenshotFile.getAbsolutePath() + "\">Success screenshot</a>");
        	}
		}
	} 
	
	private String getScreenshotName(String className, String methodName) {
		return className.replace('.', separatorChar) + separatorChar + methodName + separatorChar + System.currentTimeMillis();
	}
}