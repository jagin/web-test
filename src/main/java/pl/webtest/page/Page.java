package pl.webtest.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object pattern.
 * Abstract class representation of a Page in the UI.
 * 
 * (https://code.google.com/p/selenium/wiki/PageObjects)
 * (https://code.google.com/p/selenium/wiki/PageFactory)
 */
public abstract class Page {

	protected WebDriver webDriver;

	/**
	 * Constructor injecting the WebDriver interface
	 * 
	 * @param webDriver
	 */
	public Page(WebDriver webDriver) {
        this.webDriver = webDriver;
	}
	
	public String getTitle() {
		return webDriver.getTitle();
	}	

	public static <T extends Page> T getInstance(WebDriver webDriver, String url, Class<T> type) {
		webDriver.get(url);
	    return getInstance(webDriver, type);
	}	

	public static <T extends Page> T getInstance(WebDriver webDriver, Class<T> type) {
	    return PageFactory.initElements(webDriver, type);
	}
}