package tests;

import infra.pages.GoogleHomePage;
import infra.pages.GoogleSearchResultsPage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AssertUtils;


public class GoogleSearchTest extends BaseTest {

    private static GoogleHomePage googleHomePage;
    private static GoogleSearchResultsPage resultsPage = null;
    private static HomePage homePage = null;

    private static final String searchTerm = "ourmatch";
    private static final String siteUrl = "https://ourmatch.net/videos/";
    private static final String siteTitle = "Latest Football Highlights";

    @Test
    public void verifyShowsInGoogleResults() {

        report.startLevel("1. Navigate to google.com");
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openGoogleHomePage();
        report.endLevel();

        report.startLevel("2. At the search area, enter 'ourmatch' and click the ENTER key");
        resultsPage = new GoogleSearchResultsPage(driver);
        resultsPage = googleHomePage.runGoogleSearch(searchTerm);
        report.endLevel();

        if (resultsPage != null) {
            // Verify that the 1st title is : Latest Highlights | OurMatch - Latest Football Highlights
            AssertUtils.assertTrue(resultsPage.getFirstTitle().contains(siteTitle),
                    "1st title at results was verified","Failed to verify 1st title", true);

            // Verify that the 1st URL is: https://ourmatch.net/videos/
            AssertUtils.assertTrue(resultsPage.getFirstLink().contains(siteUrl),
                    "1st link at results was verified",
                    "Failed to verify 1st link",
                    true);
        }

        report.startLevel("3. Click on first link");
        homePage = resultsPage.clickFirstLink();
        report.endLevel();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);
    }
}
