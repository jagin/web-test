package pl.webtest;

/**
 * Default test configuration.
 * 
 * You can overwrite this settings calling your tests with -D param like
 * <code>
 * mvn test -Dwebtest.webDriverEventLogging=false
 * </code>
 * if for example you don't need extensive logging.
 */
public final class Config {
	/**
	 *  Screenshot files location
	 */
	public static final String SCREENSHOT_FOLDER = System.getProperty("webtest.screenshotFolder", "target/surefire-reports/screenshots"); 
	/**
	 *  Take screenshot on test failure
	 */
    public static final boolean SCREENSHOT_ON_FAILURE = Boolean.parseBoolean(System.getProperty("webtest.screenshotOnFailure", "true"));
	/**
	 *  Take screenshot on test success
	 */
    public static final boolean SCREENSHOT_ON_SUCCESS = Boolean.parseBoolean(System.getProperty("webtest.screenshotOnSuccess", "true"));
    
    /**
     *  Specifies the amount of time the driver should wait when searching for an element if it is not immediately present.
     */
    public static final long WEBDRIVER_TIMEOUTS_IMPLICITY_WAIT = Long.parseLong(System.getProperty("webtest.webDriverTimeoutsImplicitlyWait", "10"));
    
    /**
     *  Extensive web driver logging and reporting
     */
    public static final boolean WEBDRIVER_EVENT_LOGGING = Boolean.parseBoolean(System.getProperty("webtest.webDriverEventLogging", "true"));
}