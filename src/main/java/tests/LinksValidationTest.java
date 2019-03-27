package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinksValidationTest extends BaseTest {

    @Test(priority = 1)
    public void validateLeaguesLinks() {

        HomePage homePage = navigateToHomePage();

        Assert.assertTrue(homePage.mainLeaguesLinksValidation(homePage.getLeaguesAndCountriesMap()),
                "leagues links validation failed");
    }




}
