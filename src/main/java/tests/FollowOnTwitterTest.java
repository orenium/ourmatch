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

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        report.endLevel();


        report.startLevel("2. Click on the 'Follow on Twitter' button");
        twitterPopUpPage = homePage.followOnTwitter();
        report.endLevel();

        // Verify that the Twitter popup is shown"
        boolean isPopupShown = twitterPopUpPage.isPopupShown();
        Assert.assertTrue(isPopupShown, "Failed to open Twitter popup");

        // Verify that the presented profile is "Ourmatch.net"
        boolean isValidProfile = twitterPopUpPage.isValidProfile();
        Assert.assertTrue(isValidProfile, "Failed to open Twitter popup");
    }
}
