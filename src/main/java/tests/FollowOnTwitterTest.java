package tests;

import infra.pages.HomePage;
import infra.pages.TwitterPopUpPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowOnTwitterTest extends BaseTest {
    HomePage homePage;
    TwitterPopUpPage twitterPopUpPage;


    @Test(priority = 1)
    public void followOnTwitter() {
        homePage = navigateToHomePage();
        twitterPopUpPage = homePage.followOnTwitter();
        Assert.assertTrue(twitterPopUpPage.isPopupShown());
    }
}
