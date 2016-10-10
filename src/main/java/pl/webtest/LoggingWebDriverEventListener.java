package pl.webtest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

/**
 * @code WebDriver event listener
 */
public class LoggingWebDriverEventListener implements WebDriverEventListener {
    private static final Logger logger = LoggerFactory.getLogger(LoggingWebDriverEventListener.class);

    private By lastFindBy;

    public void afterClickOn(WebElement element, WebDriver driver) {
        String message = String.format("WebDriver clicked on element found by '%s'", getElementLocator(element));
        logger.info(message);
        Reporter.log(message);
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        String message = String.format("WebDriver found element '%s'", by);
        logger.info(message);
        Reporter.log(message);
    }

    public void afterNavigateBack(WebDriver driver) {
        logger.debug("WebDriver navigated back");
    }

    public void afterNavigateForward(WebDriver driver) {
        logger.debug("WebDriver navigated forward");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {

    }

    public void afterNavigateTo(String url, WebDriver driver) {
        String message = String.format("WebDriver navigated to '%s'", url);
        logger.info(message);
        Reporter.log(message);
    }

    public void afterScript(String script, WebDriver driver) {
        logger.debug("WebDriver run script '{}'", script);
    }


    public void beforeClickOn(WebElement element, WebDriver driver) {
        logger.trace("WebDriver clicking on element found by '{}'", getElementLocator(element));
    }

    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        lastFindBy = by;
        logger.trace("WebDriver finding element '{}'", by);
    }

    public void beforeNavigateBack(WebDriver driver) {
        logger.trace("WebDriver navigating back from '{}'", driver.getCurrentUrl());
    }

    public void beforeNavigateForward(WebDriver driver) {
        logger.trace("WebDriver navigating forward");
    }

    public void beforeNavigateTo(String url, WebDriver driver) {
        logger.trace("WebDriver navigating to '{}'", url);
    }

    public void beforeScript(String script, WebDriver driver) {
        logger.trace("WebDriver running script '{}'", script);
    }

    public void onException(Throwable error, WebDriver driver) {
        if (error.getClass().equals(NoSuchElementException.class)) {
            logger.error("WebDriver error: Element not found '{}'", lastFindBy);
        } else {
            logger.error("WebDriver error: ", error);
        }
    }

    private String getElementLocator(WebElement element) {
        return element.toString().substring(element.toString().indexOf(">") + 2, element.toString().lastIndexOf("]"));
    }

}