package pl.webtest.test.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pl.webtest.page.Page;
import pl.webtest.page.google.SearchPage;
import pl.webtest.page.google.SearchResultsPage;
import pl.webtest.test.SeleniumTestBase;

public class GoogleSearchPageTest extends SeleniumTestBase {

	@Test
	@Parameters({"searchString", "expectedResult"})
	public void googleSearchTest(String searchString, String expectedResult) {
		SearchPage searchPage = Page.getInstance(getWebDriver(),
				"http://www.google.com/webhp?complete=1&hl=en",
				SearchPage.class);

		assertThat(searchPage.getTitle(), equalTo("Google"));

		SearchResultsPage results = searchPage.search(searchString);
		String topResultTitle = results.getTopResultTitle();

		assertThat(topResultTitle, containsString(expectedResult));
	}

}
