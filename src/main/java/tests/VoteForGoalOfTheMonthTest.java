package tests;

import infra.pages.GoalOfTheMonthPage;
import infra.pages.HomePage;
import org.testng.annotations.Test;

public class VoteForGoalOfTheMonthTest extends BaseTest {


    @Test
    public void vote(){

        HomePage homePage = navigateToHomePage();
        GoalOfTheMonthPage goalOfTheMonthPage = homePage.openGoalOfTheMonthLink();
        goalOfTheMonthPage.vote();

    }

}
