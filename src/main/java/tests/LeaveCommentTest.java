package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class LeaveCommentTest extends BaseTest {

    private static HomePage homePage;
    private static GamePage gamePage;

    @Test
    public void leaveAComment() {
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
        AssertUtils.assertTrue(GamePage.isMatchSelected,
                "A random match was successfully selected",
                "Failed to select a random match", true);
        report.endLevel();

        report.startLevel("3. Leave a comment");
        boolean isCommentVerified = gamePage.leaveComment();
        report.endLevel();

        // Verify that comment was left by comment's author and comment's content
        AssertUtils.assertTrue(isCommentVerified, "Comment was left successfully", "Failed to leave a comment", true);
    }

}
