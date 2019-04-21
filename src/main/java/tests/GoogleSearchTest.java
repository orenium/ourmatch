package tests;

import infra.pages.GoogleHomePage;
import infra.pages.GoogleSearchResultsPage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ActionBot;
import utils.AssertUtils;


public class GoogleSearchTest extends BaseTest {

    private GoogleSearchResultsPage resultsPage = null;
    private HomePage homePage = null;

    @Test
    public void verifyShowsInGoogleResults() {

        String searchTerm = "ourmatch";
        String siteUrl = "https://ourmatch.net/videos/";
        String siteTitle = "Latest Highlights | OurMatch - Latest Football Highlights";

        GoogleHomePage googleHomePage = new GoogleHomePage(driver);

        report.startLevel("1. Navigate to google.com");
        googleHomePage.openGoogleHomePage();
        report.endLevel();

        report.startLevel("2. At the search area, enter 'ourmatch' and click the ENTER key");
        resultsPage = new GoogleSearchResultsPage(driver);
        resultsPage = googleHomePage.runGoogleSearch(searchTerm);
        report.endLevel();

        if (resultsPage != null) {
            // Verify that the 1st title is : Latest Highlights | OurMatch - Latest Football Highlights
            Assert.assertEquals(resultsPage.getFirstTitle(), siteTitle);

            // Verify that the 1st URL is: https://ourmatch.net/videos/
            Assert.assertEquals(resultsPage.getFirstLink(), siteUrl);
        }

        report.startLevel("3. Click on first link");
        homePage = resultsPage.clickFirstLink();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                ActionBot.isElementDisplayed(HomePage.searchBtn, false),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);
        report.endLevel();
    }
}
