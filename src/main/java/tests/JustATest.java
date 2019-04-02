package tests;

import infra.pages.HomePage;
import org.testng.annotations.Test;

public class JustATest extends BaseTest {



    @Test
    public void getViews(){
        HomePage homePage = navigateToHomePage();
        homePage.getViewsData();
    }
}
