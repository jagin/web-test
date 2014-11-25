package pl.webtest.test.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pl.webtest.test.SeleniumTestBase;

public class GoogleTranslateSeleniumTest extends SeleniumTestBase {
	
    @Test
    @Parameters({"fromLang", "toLang", "translateString", "expectedResult"})
    public void googleTranslateTest(String fromLang, String toLang, String translateString, String expectedResult) {
        
        // Go to the Google Translate page
    	getWebDriver().get("https://translate.google.pl/#" + fromLang + "/" + toLang);
        
        // Enter the string to translate
        WebElement source = getWebDriver().findElement(By.id("source"));
        source.sendKeys("selenium");
        
        getWebDriver().findElement(By.id("gt-submit")).click();
        
        // Sleep until the div we want is visible or 5 seconds is over
        // We need to wait as div with translation results is loaded dynamically on every key input
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result_box")));

        // And now list the suggestions
        WebElement result = getWebDriver().findElement(By.id("result_box"));
        
        assertThat(result.getText(), equalTo(expectedResult));
        Reporter.log("Google translation successful");
    }

}
