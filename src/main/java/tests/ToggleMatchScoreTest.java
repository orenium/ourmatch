package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ToggleMatchScoreTest extends BaseTest {

    private HomePage homePage;
    private GamePage gamePage;

    @Test (priority = 1)
    public void showMatchScore(){
        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("2. Select a random match");
        gamePage = homePage.selectRandomItem();
        report.endLevel();

        report.startLevel("3. Click on the 'Click to see the score' button");
//      Expected result: Verify text is "FT" at the score once clicked
        boolean isScoreShowed = gamePage.showMatchScore();
        report.endLevel();
        Assert.assertTrue(isScoreShowed, "Failed to show match score");
    }

    @Test(priority = 2)
    public void hideMatchScore(){
        if (gamePage == null) {
            report.startLevel("1. Navigate to ourmatch homepage");
            homePage = navigateToHomePage();
            report.endLevel();

            report.startLevel("2. Select a random match");
            gamePage = homePage.selectRandomItem();
            report.endLevel();
        }
        report.startLevel("3. Click on the 'hide the score' button");
//      Expected result: Verify text is "-" at the score once clicked
        boolean isScoreShowed = gamePage.hideMatchScore();
        report.endLevel();
        Assert.assertFalse(isScoreShowed, "Failed to hide match score");
    }



}
