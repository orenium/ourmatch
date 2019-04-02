package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LeaveCommentTest extends BaseTest  {


    @Test
    public void leaveAComment(){
        HomePage homePage = navigateToHomePage();
        GamePage gamePage = homePage.selectRandomItem();
        Assert.assertTrue(gamePage.leaveComment());
    }

}
