package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayMostViewedTest extends BaseTest {

    HomePage homePage;
    GamePage gamePage;

    @Test
    public void playMostViewedTest() throws InterruptedException {
        int mostViewedIndex;
        boolean isPlayed;
        boolean isScoreShowed;
        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("2. Track and click on the most viewed match on page");
        mostViewedIndex = homePage.getViewsData();
        if (mostViewedIndex > -1){
            gamePage = homePage.selectMatchByIndex(mostViewedIndex);
        }
        report.endLevel();

        report.startLevel("3. Expose match final score");
        isScoreShowed = gamePage.showMatchScore();
        report.endLevel();
        Assert.assertTrue(isScoreShowed, " Failed to show match score");

        report.startLevel("4. Play match highlights");
        isPlayed = gamePage.playHighlights();
        report.endLevel();
        Assert.assertTrue(isPlayed, " Failed to play match highlights");

    }



}
