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
    private String originalValue;

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {
        logger.trace("WebDriver attempt to accept alert");
    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {
        logger.debug("WebDriver accepted the alert");
    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {
        logger.trace("WebDriver dismissed the alert");
    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {
        logger.debug("WebDriver attempt to dismiss alert");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver webDriver) {
        logger.trace("WebDriver navigating to '{}'", url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver webDriver) {
        String message = String.format("WebDriver navigated to '%s'", url);
        logger.info(message);
        Reporter.log(message);
    }

    @Override
    public void beforeNavigateBack(WebDriver webDriver) {
        logger.trace("WebDriver navigating back from '{}'", webDriver.getCurrentUrl());
    }

    @Override
    public void afterNavigateBack(WebDriver webDriver) {
        logger.debug("WebDriver navigated back");
    }

    @Override
    public void beforeNavigateForward(WebDriver webDriver) {
        logger.trace("WebDriver navigating forward");
    }

    @Override
    public void afterNavigateForward(WebDriver webDriver) {
        logger.debug("WebDriver navigated forward");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {
        logger.trace("WebDriver navigating refresh");
    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {
        logger.debug("WebDriver navigated refresh");
    }

    @Override
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        lastFindBy = by;
        logger.trace("WebDriver finding element '{}'", by);
    }

    @Override
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
        String message = String.format("WebDriver found element '%s'", by);
        logger.info(message);
        Reporter.log(message);
    }

    @Override
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
        logger.trace("WebDriver clicking on element found by '{}'", getElementLocator(webElement));
    }

    @Override
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        String message = String.format("WebDriver clicked on element found by '%s'", getElementLocator(webElement));
        logger.info(message);
        Reporter.log(message);
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        originalValue = webElement.getAttribute("value");
        logger.trace("WebDriver changing value in element found by '{}' from '{}'",
                getElementLocator(webElement) /*lastFindBy*/, originalValue);
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        String message = String.format(
                "WebDriver changed value in element found by '%s' from '%s' to '%s'",
                getElementLocator(webElement) /*lastFindBy*/, originalValue, webElement.getAttribute("value"))
                .toString();
        // Log the message to the logger
        logger.info(message);
        // Log the message to the reporter
        Reporter.log(message);
    }

    @Override
    public void beforeScript(String script, WebDriver webDriver) {
        logger.trace("WebDriver running script '{}'", script);
    }

    @Override
    public void afterScript(String script, WebDriver webDriver) {
        logger.debug("WebDriver run script '{}'", script);
    }

    @Override
    public void beforeSwitchToWindow(String window, WebDriver webDriver) {
        logger.trace("WebDriver switching to window '{}'", window);
    }

    @Override
    public void afterSwitchToWindow(String window, WebDriver webDriver) {
        logger.debug("WebDriver switched to window '{}'", window);
    }

    @Override
    public void onException(Throwable error, WebDriver webDriver) {
        if (error.getClass().equals(NoSuchElementException.class)){
            logger.error("WebDriver error: Element not found '{}'", lastFindBy);
        } else {
            logger.error("WebDriver error: ", error);
        }
    }

    private String getElementLocator(WebElement webElement) {
        return webElement.toString().substring(webElement.toString().indexOf(">") + 2, webElement.toString().lastIndexOf("]"));
    }
}