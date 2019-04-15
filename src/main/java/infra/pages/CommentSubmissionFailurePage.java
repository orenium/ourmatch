package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

public class CommentSubmissionFailurePage extends BasePage {

    public CommentSubmissionFailurePage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMsg() {
        return ActionBot.getElementText(
                ActionBot.getAllElements(By.cssSelector("#error-page p")).get(1));
    }

    public GamePage clickBack() {
        if (ActionBot.isElementDisplayed(By.tagName("a"), false)) {
            ActionBot.clickOnElement(By.tagName("a"), "Back");
        }
        return new GamePage(driver);
    }
}
