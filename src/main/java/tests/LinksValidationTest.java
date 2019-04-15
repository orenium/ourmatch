package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class LinksValidationTest extends BaseTest {

    @Test(priority = 1)
    public void validateLeaguesLinks() {
        boolean isLinksValid;

        report.startLevel("1. Navigate to ourmatch homepage");
        HomePage homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("2. Validate main leagues links");
        homePage.setLeaguesMap();
        isLinksValid = homePage.mainLeaguesLinksValidation(homePage.leaguesAndLinksMap);
        report.endLevel();
        AssertUtils.assertTrue(isLinksValid,"Leagues links validation successfully passed" ,"Leagues links validation failed", true);
    }


}
