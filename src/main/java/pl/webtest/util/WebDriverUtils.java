package pl.webtest.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public static Path saveScreenshot(WebDriver webDriver, String screenshotName) {
    	Path dest = null;
        try {
        	WebDriver returned;
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
                    Files.createDirectories(dest.getParent());
                    Files.copy(src.toPath(), dest);
                    logger.debug("Screenshot saved to " + dest.toAbsolutePath());
                } catch (IOException e) {
                    logger.error("Error saving screenshot", e);
                }
            }
        } catch (ScreenshotException e) {
        	logger.error("Error taking screenshot", e);
        }
        
        return dest;
    }

    private static Path getScreenshotFile(String screenshotName) {
        return new File(new File(Config.SCREENSHOT_FOLDER), screenshotName + ".png").toPath();
    }


}
