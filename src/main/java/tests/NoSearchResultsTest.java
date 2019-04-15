package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NoSearchResultsTest extends BaseTest {

    private HomePage homePage;

    @Test
    public void noSearchResultsHandling() {

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("2. At the search area, enter some jibrish search term and click the search button");
        homePage.search("randomSearchTerm");
        String errorMsg = homePage.getErrorMsg();
        report.endLevel();

//          Verify that in case of no results, a popper msg is shown ("Apologies, but no results were found.")
        Assert.assertEquals(errorMsg, "Apologies, but no results were found.");
    }

}
