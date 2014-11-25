package pl.webtest;

public final class Config {
	public static final String SCREENSHOT_FOLDER = System.getProperty("webtest.screenshotFolder", "target/surefire-reports/screenshots"); 
    public static final boolean SCREENSHOT_ON_FAILURE = Boolean.parseBoolean(System.getProperty("webtest.screenshotOnFailure", "true"));
    public static final boolean SCREENSHOT_ON_SUCCESS = Boolean.parseBoolean(System.getProperty("webtest.screenshotOnSuccess", "true"));
    
    public static final long WEBDRIVER_TIMEOUTS_IMPLICITY_WAIT = Long.parseLong(System.getProperty("webtest.webDriverTimeoutsImplicitlyWait", "10"));
    public static final boolean WEBDRIVER_LOGGING = Boolean.parseBoolean(System.getProperty("webtest.webDriverLogging", "true"));
}