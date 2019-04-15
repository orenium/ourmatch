package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class ToggleMatchScoreTest extends BaseTest {

    private static HomePage homePage;
    private static GamePage gamePage;
    private static boolean isScoreShowed;

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
        isScoreShowed = gamePage.toggleMatchScore();
        report.endLevel();
        AssertUtils.assertTrue(isScoreShowed, "Succesfullfy showing match score","Failed to show match score", true);
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
        isScoreShowed = gamePage.toggleMatchScore();
        report.endLevel();
        AssertUtils.assertTrue(!isScoreShowed,"Hide score test pass", "Failed to hide match score",true);
    }



}
