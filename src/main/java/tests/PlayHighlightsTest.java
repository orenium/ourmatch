package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayHighlightsTest extends BaseTest {


    HomePage homePage;
    GamePage gamePage;

    @Test
    public void playRandomHighLights() throws InterruptedException {

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("2. Select a random match");
        gamePage = homePage.selectRandomItem();
        report.endLevel();

        report.startLevel("3. Click on the 'Click to see the score' button");
        boolean isScoreShowed = gamePage.showMatchScore();
        Assert.assertTrue(isScoreShowed, "Failed to show match score");
        report.endLevel();

        report.startLevel("4. Play highlights");
//        When playing a random match there are 3 possible videou sources
//        - Scenario A - Youtube is the video source
//        Expected result:  Verify video is played. In case of an error msg, verify error msg is printed to logs.
//        - Scenario B - Oms is the video source (2 scenarios):
//          1. Video source contains 'oms.veuclips.com' - Verify video opens in a new tab once clicked
//          2. Video source contains 'oms.videostreamlet.net' - Verify video is playing
//        - Scenario C - Streamable is the video source
//        Expected result: Verify video is played.
        boolean isPlayed = gamePage.playHighlights();
        report.endLevel();
        Assert.assertTrue(isPlayed, "playRandomHighLights");
    }


}
