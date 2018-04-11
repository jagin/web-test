package pl.webtest.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmentable;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.webtest.Config;

/**
 * Webdriver utilities
 */
public class WebDriverUtils {
	private static final Logger logger = LoggerFactory.getLogger(WebDriverUtils.class);
	
    private WebDriverUtils() {}
	
    /**
     *  Save screen shot method
     * @param webDriver
     * @param screenshotName name of the screenshot. It will be saved under @code Config.SCREENSHOT_FOLDER location.
     * @return created screenshot file. null will be returned if the were some problems with creating the screenshot file.
     */
    public static File saveScreenshot(WebDriver webDriver, String screenshotName) {
    	File dest = null;
        try {
        	WebDriver returned = null;
			if (webDriver instanceof EventFiringWebDriver) {
				// Unwrap real webdriver
				EventFiringWebDriver eventFiringWebDriver = (EventFiringWebDriver)webDriver;
				webDriver = eventFiringWebDriver.getWrappedDriver();
			}       	
        	if(webDriver.getClass().isAnnotationPresent(Augmentable.class)) {
        		//Augmenter can only be called on RemoteWebDriver and none of the child classes. See https://code.google.com/p/selenium/issues/detail?id=5087
        		returned = new Augmenter().augment(webDriver);        		
        	} else {
        		returned = webDriver;
        	}
        	
            if (returned != null) {
                File src = ((TakesScreenshot) returned).getScreenshotAs(OutputType.FILE);
                try {
                	dest = getScreenshotFile(screenshotName);
                    FileUtils.copyFile(src, dest);
                    logger.debug("Screenshot saved to " + dest.getAbsolutePath());
                } catch (IOException e) {
                    logger.error("Error saving screenshot", e);
                }
            }
        } catch (ScreenshotException e) {
        	logger.error("Error taking screenshot", e);
        }
        
        return dest;
    }

    private static File getScreenshotFile(String screenshotName) {
        File screenshotFolderFile = new File(Config.SCREENSHOT_FOLDER);
        screenshotFolderFile.mkdirs();

        return new File(screenshotFolderFile, screenshotName + ".png");
    }

    /*
    public static void killProcess(String processName) throws IOException {
        // Process names for browsers: "firefox", "iexplore", "chrome", "MicrosoftEdge"
        if (SystemUtils.IS_OS_LINUX) {
            Runtime.getRuntime().exec("/bin/sh -c ps -ef | grep -w " + processName + " | grep -v grep | awk '/[0-9]/{print $2}' | xargs kill -9 ");
        } else if (SystemUtils.IS_OS_WINDOWS) {
            Runtime.getRuntime().exec("taskkill /F /IM " + processName + ".exe");
        }
    }
    */
}
