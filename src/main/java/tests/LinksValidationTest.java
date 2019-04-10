package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinksValidationTest extends BaseTest {

    @Test(priority = 1)
    public void validateLeaguesLinks() {
        boolean isLinksValid;

        report.startLevel("1. Navigate to ourmatch homepage");
        HomePage homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("");
        isLinksValid = homePage.mainLeaguesLinksValidation(homePage.leaguesAndLinksMap);
        report.endLevel();
        Assert.assertTrue(isLinksValid, "leagues links validation failed");
    }


}
