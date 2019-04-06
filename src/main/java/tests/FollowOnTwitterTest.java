package tests;

import infra.pages.HomePage;
import infra.pages.TwitterPopUpPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowOnTwitterTest extends BaseTest {
    HomePage homePage;
    TwitterPopUpPage twitterPopUpPage;


    @Test
    public void followOnTwitter() {
        homePage = navigateToHomePage();
        twitterPopUpPage = homePage.followOnTwitter();
        Assert.assertTrue(twitterPopUpPage.isPopupShown());
    }
}
