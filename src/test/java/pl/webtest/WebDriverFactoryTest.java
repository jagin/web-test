package pl.webtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for @code WebDriverFactory
 * <p>
 * This test will check the availability of browser drivers and proper configuration
 */
public class WebDriverFactoryTest {

    @Test
    public void createFirefoxDriverTest() {
        WebDriverFactory wdf = new WebDriverFactory("firefox");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("marionette", true);
        WebDriver wd = wdf.createDriver(desiredCapabilities);
        assertThat(wd, is(notNullValue()));
        wd.quit();
    }

    @Test
    public void createChromeDriverTest() {
        WebDriverFactory wdf = new WebDriverFactory("chrome");
        WebDriver wd = wdf.createDriver(new DesiredCapabilities());
        assertThat(wd, is(notNullValue()));
        wd.quit();
    }


    @Test
    public void createIEDriverTest() {
        if (!IS_OS_WINDOWS) {
            throw new SkipException("IE tests can be only run in windows");
        }
        WebDriverFactory wdf = new WebDriverFactory("internet explorer");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        WebDriver wd = wdf.createDriver(capabilities);
        assertThat(wd, is(notNullValue()));
        wd.quit();
    }

    @Test
    public void createSafariDriverTest() {
        if (!IS_OS_MAC) {
            throw new SkipException("Safari test can be only run in mac");
        }
        WebDriverFactory wdf = new WebDriverFactory("safari");
        WebDriver wd = wdf.createDriver(new DesiredCapabilities());
        assertThat(wd, is(notNullValue()));
        wd.quit();


    }
}
