package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class ToggleMatchScoreTest extends BaseTest {

    private static HomePage homePage;
    private static GamePage gamePage;
    private static boolean isScoreShowed;

    @Test(priority = 1)
    public void showMatchScore() {
        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);

        report.startLevel("2. Select a random match");
        gamePage = homePage.selectRandomItem();
        report.endLevel();

        //  Verify a random match is selected
        AssertUtils.assertTrue(GamePage.isMatchSelected,
                "A random match was successfully selected",
                "Failed to select a random match", true);

        report.startLevel("3. Click on the 'Click to see the score' button");
        isScoreShowed = gamePage.toggleMatchScore();
        report.endLevel();

//      Expected result: Verify text is "FT" at the score once clicked
        AssertUtils.assertTrue(isScoreShowed, "Successfully showing match score", "Failed to show match score", true);
    }

    @Test(priority = 2)
    public void hideMatchScore() {
        if (gamePage == null) {
            report.startLevel("1. Navigate to ourmatch homepage");
            homePage = navigateToHomePage();
            report.endLevel();

            report.startLevel("2. Select a random match");
            gamePage = homePage.selectRandomItem();
            report.endLevel();
        }
        report.startLevel("3. Click on the 'hide the score' button");
        isScoreShowed = gamePage.toggleMatchScore();
        report.endLevel();

//      Expected result: Verify text is "-" at the score once clicked
        AssertUtils.assertTrue(!isScoreShowed, "Hide score test pass", "Failed to hide match score", true);
    }


}
