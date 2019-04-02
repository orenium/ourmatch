package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.MainConfig;


public class SearchATeamTest extends BaseTest {

//    private String SEARCH_TERM = MainConfig.searchTerm;

    @Test(priority = 1)
    public void search() {
        HomePage homePage = navigateToHomePage();
        Assert.assertTrue(homePage.search(MainConfig.searchTerm), "fail to search: " + MainConfig.searchTerm);
    }


}

