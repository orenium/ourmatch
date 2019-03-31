package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayHighlightsTest extends BaseTest {


    HomePage homePage;
    GamePage gamePage;


    @Test(priority = 1)
    public void playRandomHighLights() {
        homePage = navigateToHomePage();
        homePage.closeAcceptCookiesDialog();
        gamePage = homePage.selectRandomItem();
        gamePage.playHighlights();
    }


    @Test(priority = 2)
    public void toggleMatchScore() {
        if (gamePage != null) {
            Assert.assertTrue(gamePage.toggleMatchScore());
        } else {
            homePage = navigateToHomePage();
            homePage.closeAcceptCookiesDialog();
            gamePage = homePage.selectRandomItem();
            Assert.assertTrue(gamePage.toggleMatchScore());
        }
    }
}
