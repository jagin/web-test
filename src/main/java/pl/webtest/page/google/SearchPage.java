package pl.webtest.page.google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pl.webtest.page.Page;

/**
 * Google search page
 */
public class SearchPage extends Page {

	// The element is looked up using the name attribute
	@FindBy(name = "q")
	private WebElement searchBox;
	
    public SearchPage(WebDriver webDriver) {
        super(webDriver);
    }

	public SearchResultsPage search(String text) {
		searchBox.sendKeys(text);
		searchBox.submit();
		
        // Sleep until the div we want is visible or 5 seconds is over
        // We need to wait as div with search results is loaded dynamically on every key input
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ires")));
		
		return getInstance(webDriver, SearchResultsPage.class);
	}

}