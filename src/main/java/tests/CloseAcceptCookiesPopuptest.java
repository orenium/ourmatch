package tests;

import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CloseAcceptCookiesPopuptest extends BaseTest {

    @Test
    public void CloseAcceptCookiesPopup(){
        HomePage homePage = navigateToHomePage();
        Assert.assertTrue(homePage.closeAcceptCookiesDialog(),
                "Fail to close cookies dialog ");
    }


}
