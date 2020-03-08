package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import infra.pages.utils.ActionBot;

public class CommentSubmissionFailurePage extends BasePage {

    public CommentSubmissionFailurePage(WebDriver driver) {
        super(driver);
    }

    /**
     * This method returns the error msg after trying to leave a comment
     * @return - The error
     */
    public String getErrorMsg() {
        return ActionBot.getElementText(
                ActionBot.getAllElements(By.cssSelector("#error-page p")).get(1));
    }

    /**
     * This method clicks on the back button
     * @return - new (prev) GamePage
     */
    public GamePage clickBack() {
        if (ActionBot.isElementDisplayed(By.tagName("a"), false)) {
            ActionBot.clickOnElement(By.tagName("a"), "Back");
        }
        return new GamePage(driver);
    }
}
