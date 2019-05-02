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
        report.endLevel();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);

        report.startLevel("2. Validate main leagues links");
        homePage.setLeaguesMap();
        isLinksValid = homePage.mainLeaguesLinksValidation(homePage.leaguesAndLinksMap);
        report.endLevel();

        // Verify links and country matched by checking the button's link's value (see expected below)
        AssertUtils.assertTrue(isLinksValid, "Leagues links validation successfully passed", "Leagues links validation failed", true);
    }
/**
 * Expected links results:
 * Premier League -- > https://ourmatch.net/videos/england/premier-league-highlights/
 * La Liga -- > https://ourmatch.net/videos/spain/la-liga-highlights/
 * Serie A -- > https://ourmatch.net/videos/italy/serie-a-highlights/
 * Bundesliga -- > https://ourmatch.net/videos/germany/bundesliga-highlights/
 * Ligue 1 -- > https://ourmatch.net/videos/france/ligue-1-highlights/
 * Champions League -- > https://ourmatch.net/videos/europe/uefa-champions-league-highlights/
 * Europa League -- > https://ourmatch.net/videos/europe/uefa-europa-league-highlights/
 */

}
