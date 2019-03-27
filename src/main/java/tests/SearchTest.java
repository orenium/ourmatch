package tests;

import infra.pages.HomePage;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test
    public void search(){
        String searchTerm = "Barcelona";

        HomePage homePage = navigateToHomePage();
        if (homePage != null){
            homePage.search(searchTerm);
        }

    }
}
