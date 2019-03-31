package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SearchATeamTest extends BaseTest {

    private final String SEARCH_TERM = "Real Madrid";

    @Test(priority = 1)
    public void search() {
        HomePage homePage = navigateToHomePage();
        Assert.assertTrue(homePage.search(SEARCH_TERM), "fail to search: " + SEARCH_TERM);
    }


}

