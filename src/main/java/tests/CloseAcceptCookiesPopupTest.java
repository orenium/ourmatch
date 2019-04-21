package tests;

import infra.pages.HomePage;
import org.testng.annotations.Test;
import utils.AssertUtils;

public class CloseAcceptCookiesPopupTest extends BaseTest {

    @Test
    public void CloseAcceptCookiesPopup() throws InterruptedException {
        boolean isClosed;

        report.startLevel("1. Navigate to ourmatch homepage");
        HomePage homePage = navigateToHomePage();
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
