package pl.webtest.page.google;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pl.webtest.page.Page;

/**
 * Google search results page
 */
public class SearchResultsPage extends Page {

	@FindBy(name = "ires")
	private WebElement results;
	
    public SearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

	public String getTopResultTitle() {
		String topResultTitle = "";
		List<WebElement> results = webDriver.findElements(By.cssSelector("#ires h3.r a"));
		if(results.size() > 0 ) {
			topResultTitle =  results.get(0).getText();
		}
		
		return topResultTitle;
	}
}