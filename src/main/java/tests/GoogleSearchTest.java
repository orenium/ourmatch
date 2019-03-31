package tests;

import infra.pages.GoogleHomePage;
import infra.pages.GoogleSearchResultsPage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class GoogleSearchTest extends BaseTest {

    private GoogleSearchResultsPage resultsPage = null;
    private HomePage homePage = null;

    @Test(priority = 1)
    public void verifyShowsInGoogleResults() {

        String searchTerm = "ourmatch";
        String siteUrl = "https://ourmatch.net/videos/";
        String siteTitle = "Latest Highlights | OurMatch - Latest Football Highlights";

        GoogleHomePage googleHomePage = new GoogleHomePage(driver);
        resultsPage = new GoogleSearchResultsPage(driver);

        resultsPage = googleHomePage.runGoogleSearch(searchTerm);
        if (resultsPage != null) {
            Assert.assertEquals(resultsPage.getFirstTitle(), siteTitle);
            Assert.assertEquals(resultsPage.getFirstLink(), siteUrl);
        }

    }

    @Test(priority = 2)
    public void verifyLinkIsValid() {

        homePage = resultsPage.clickFirstLink();

        Assert.assertNotNull(homePage, "link validation test failed");

    }

    @Test(priority = 3)
    public void closeCookiesDialog() {
        Assert.assertTrue(homePage.closeAcceptCookiesDialog(), "Fail to close cookies dialog");
    }

}
