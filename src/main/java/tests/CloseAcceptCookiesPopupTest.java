package tests;

import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class CloseAcceptCookiesPopupTest extends BaseTest {

    private static HomePage homePage;

    @Test
    public void CloseAcceptCookiesPopup() throws InterruptedException {
        boolean isClosed;

        report.startLevel("1. Navigate to ourmatch homepage");
        homePage = navigateToHomePage();

        // Verify ourmatch homepage is shown
        AssertUtils.assertTrue(
                homePage.isElementInPage(),
                "Ourmatch home page was verified",
                "Failed to validate ourmatch homepage",
                true);
        report.endLevel();

        report.startLevel("2. Close (accept) the 'Accept cookies popup'");
        isClosed = homePage.closeAcceptCookiesDialog();
        report.endLevel();

        // Verify popup closed and no longer visible
        AssertUtils.assertTrue(isClosed,
                "Close Accept Cookies Popup was successfully closed",
                "Fail to close cookies dialog",
                true);
    }


}
