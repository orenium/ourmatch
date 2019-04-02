package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

public class TwitterPopUpPage extends BasePage {



    public TwitterPopUpPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPopupShown() {
        By mainDiv = By.cssSelector("div#bd");
        return ActionBot.isElementDisplayed(mainDiv);
    }
}
