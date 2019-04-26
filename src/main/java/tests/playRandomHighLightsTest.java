package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class playRandomHighLightsTest extends BaseTest {


    private static HomePage homePage;
    private static GamePage gamePage;
    private static boolean isScoreShowed;
    private static boolean isPlayed;

    @Test
    public void playRandomHighLights() throws InterruptedException {

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);

        report.endLevel();

        report.startLevel("2. Select a random match");
        gamePage = homePage.selectRandomItem();
//        gamePage = homePage.selectMatchByIndex(16);
        report.endLevel();

        report.startLevel("3. Click on the 'Click to see the score' button");
        isScoreShowed = gamePage.toggleMatchScore();
        AssertUtils.assertTrue(isScoreShowed, "Match score shown", "Failed to toggle match Score", true);
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
        isPlayed = gamePage.playHighlights();
        report.endLevel();
        AssertUtils.assertTrue(isPlayed, "Play highlights test passed successfully", "Play highlights test failed", true);
    }


}
