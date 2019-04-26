package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class NoSearchResultsTest extends BaseTest {

    private static HomePage homePage;

    @Test
    public void noSearchResultsHandling() {

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);
        report.endLevel();

        report.startLevel("2. At the search area, enter some jibrish search term and click the search button");
        homePage.search("randomSearchTerm");
        String errorMsg = homePage.getErrorMsg();
        report.endLevel();

//          Verify that in case of no results, a popper msg is shown ("Apologies, but no results were found.")
        AssertUtils.assertEquals(errorMsg, "Apologies, but no results were found.", "Search results tests passed", true);
    }

}
