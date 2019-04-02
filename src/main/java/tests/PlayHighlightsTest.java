package tests;

import infra.pages.GamePage;
import infra.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayHighlightsTest extends BaseTest {


    HomePage homePage;
    GamePage gamePage;

    @Test(priority = 1)
    public void toggleMatchScore() {
        if (gamePage != null) {
            Assert.assertTrue(gamePage.toggleMatchScore());
        } else {
            homePage = navigateToHomePage();
            gamePage = homePage.selectRandomItem();
            Assert.assertTrue(gamePage.toggleMatchScore());
        }
    }

    @Test(priority = 2)
    public void playRandomHighLights() {
        // TODO: add assertion in page
        if (gamePage != null) {
            gamePage = homePage.selectRandomItem();
            gamePage.playHighlights();
        } else {
            homePage = navigateToHomePage();
            gamePage = homePage.selectRandomItem();
            gamePage.playHighlights();
        }
    }

    @Test(priority = 3)
    public void openVideoFullScreen() {

        if (gamePage != null) {
            Assert.assertTrue(gamePage.openVideoFullScreen());
        } else {
            homePage = navigateToHomePage();
            gamePage = homePage.selectRandomItem();
            gamePage.playHighlights();
            Assert.assertTrue(gamePage.openVideoFullScreen());
        }
    }
}
