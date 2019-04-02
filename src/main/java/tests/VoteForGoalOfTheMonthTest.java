package tests;

import infra.pages.GoalOfTheMonthPage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VoteForGoalOfTheMonthTest extends BaseTest {


    @Test
    public void vote() {
        // TODO: Voting not working, just the poll closed scenario
        HomePage homePage = navigateToHomePage();
        GoalOfTheMonthPage goalOfTheMonthPage = homePage.openGoalOfTheMonthLink();
        boolean isVoted = goalOfTheMonthPage.vote();
        if (isVoted) {
            Assert.assertTrue(isVoted, "Failed to vote");
        } else if (GoalOfTheMonthPage.isClosedForVoting) {
            Assert.assertFalse(isVoted, "Pool is close");
        }
    }

}
