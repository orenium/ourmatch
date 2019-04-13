package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LeaveCommentTest extends BaseTest {


    @Test
    public void leaveAComment() {
        report.startLevel("1. Navigate to ourmatch homepage");
        HomePage homePage = navigateToHomePage();
        report.endLevel();

        report.startLevel("2. Select a random match");
        GamePage gamePage = homePage.selectRandomItem();
        report.endLevel();

        report.startLevel("3. Leave a comment");
        boolean commentLeft = gamePage.leaveComment();
        report.endLevel();

        // Verify that comment was left by comment's author and comment's content
        Assert.assertTrue(commentLeft, "Failed to leave comment");
    }

}
