package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class PlayMostViewedTest extends BaseTest {

    private static HomePage homePage;
    private static GamePage gamePage;
    private static int mostViewedIndex;
    private static boolean isPlayed;
    private static boolean isScoreShowed;

    @Test
    public void playMostViewedTest() throws InterruptedException {

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);

        report.startLevel("2. Track and click on the most viewed match on page");
        mostViewedIndex = homePage.getViewsData();
        if (mostViewedIndex > -1){
            gamePage = homePage.selectMatchByIndex(mostViewedIndex);
        }
        report.endLevel();

        report.startLevel("3. Expose match final score");
        isScoreShowed = gamePage.toggleMatchScore();
        report.endLevel();

     // Verify match score can be shown
        AssertUtils.assertTrue(isScoreShowed,"Match score is shown", " Failed to show match score", true);

        report.startLevel("4. Play match highlights");
        isPlayed = gamePage.playHighlights();
        report.endLevel();

     // Verify match highlights can be played
        AssertUtils.assertTrue(isPlayed,"Most viewed game was successfully played" , " Failed to play match highlights", true);

    }



}
