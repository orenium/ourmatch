package tests;

import infra.pages.GoalOfTheMonthPage;
import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.ActionBot;
import utils.AssertUtils;

public class VoteForGoalOfTheMonthTest extends BaseTest {

    private static HomePage homePage;
    private static GoalOfTheMonthPage goalOfTheMonthPage;


    @Test
    public void vote() {
        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);

        report.startLevel("2. Navigate to the 'Goal of the month' page");
        goalOfTheMonthPage = homePage.openGoalOfTheMonthLink();
        report.endLevel();

        // Verify 'Goal of the month' page is shown
        AssertUtils.assertTrue(
                ActionBot.isElementDisplayed(GoalOfTheMonthPage.pollMsg, false),
                "Goal of the month page was verified",
                "Failed to verify goal of the match page",
                true);

        report.startLevel("3. Vote (if voting is available)");
//        The expected results depend on the period of the month:
        if (!goalOfTheMonthPage.isPollClosed()) {  // Scenario A: Poll is open for voting
            boolean isVoted = goalOfTheMonthPage.vote();

            //      Expected result - A random option is selected and the "vote" button is clicked
            AssertUtils.assertTrue(isVoted, "Vote test pass", "Failed to vote", true);

        } else {   // Scenario B: Poll is closed for voting
            boolean isPrintedToLogs = goalOfTheMonthPage.printScoresToLog();
            //      Expected result - A msg printed to logs in addition to the poll results
            AssertUtils.assertTrue(isPrintedToLogs, "Voting results was successfully printed", "Failed to print poll results to log", true);
        }
        report.endLevel();
    }

}
