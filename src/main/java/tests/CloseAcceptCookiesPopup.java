package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CloseAcceptCookiesPopup extends BaseTest {

    @Test
    public void CloseAcceptCookiesPopup(){
        HomePage homePage = navigateToHomePage();

        Assert.assertTrue(homePage.closeAcceptCookiesDialog(),
                "failed to closeAcceptCoockiesPopup ");

    }


}
