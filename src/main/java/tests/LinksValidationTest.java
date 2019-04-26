package tests;

import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class LinksValidationTest extends BaseTest {

    private static HomePage homePage;

    @Test
    public void validateLeaguesLinks() {
        boolean isLinksValid;

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);
        report.endLevel();

        report.startLevel("2. Validate main leagues links");
        homePage.setLeaguesMap();
        isLinksValid = homePage.mainLeaguesLinksValidation(homePage.leaguesAndLinksMap);
        report.endLevel();
        AssertUtils.assertTrue(isLinksValid, "Leagues links validation successfully passed", "Leagues links validation failed", true);
    }


}
