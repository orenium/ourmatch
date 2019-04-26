package tests;

import infra.pages.HomePage;
import infra.pages.TwitterPopUpPage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class FollowOnTwitterTest extends BaseTest {

    private static HomePage homePage;
    private static TwitterPopUpPage twitterPopUpPage;


    @Test
    public void followOnTwitter() {

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();
        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);
        report.endLevel();

        report.startLevel("2. Click on the 'Follow on Twitter' button");
        twitterPopUpPage = homePage.followOnTwitter();
        report.endLevel();

        // Verify that the Twitter popup is shown"
        boolean isPopupShown = twitterPopUpPage.isPopupShown();
        AssertUtils.assertTrue(isPopupShown, "Twitter popup was successfully opened", "Failed to open Twitter popup", true);

        // Verify that the presented profile is "Ourmatch.net"
        boolean isValidProfile = twitterPopUpPage.isValidProfile();
        AssertUtils.assertTrue(isValidProfile, "Profile was successfully validated", "Failed to validate profile", true);
    }
}
