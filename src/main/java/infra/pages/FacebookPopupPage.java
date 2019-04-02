package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

public class FacebookPopupPage extends BasePage {

    By loginForm = By.cssSelector("div#loginform");

    public FacebookPopupPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPopupShown() {

        return ActionBot.isElementDisplayed(loginForm);
    }
}


