package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;

import java.util.List;

public class GoalOfTheMonthPage extends BasePage {

    By goalOptions = By.cssSelector("span.css-answer-input.pds-answer-input input");
    By goallabels = By.cssSelector("span.css-answer-span.pds-answer-span");

    By pollClosedMsg = By.cssSelector("div.PDS_Poll div.pds-question-top");
    By iframe = By.tagName("iframe");
    By playBtn = By.cssSelector("div#play-button");


    public GoalOfTheMonthPage(WebDriver driver) {
        super(driver);
    }

    public static boolean isClosedForVoting = false;

    private boolean isPoolClosed() {
        if (ActionBot.isElementDisplayed(pollClosedMsg)) {
            report.log(ActionBot.getElementText(pollClosedMsg));
            isClosedForVoting = true;
        }
        return isClosedForVoting;
    }

    public boolean vote() {
        boolean isVoted = false;
        if (!isPoolClosed()) {
            int index = ActionBot.getRandomIndex(ActionBot.getTextFromElementList(goallabels).size());
            ActionBot.moveToElement(By.cssSelector("#PDI_form10257564"));

            ActionBot.executeJavaScript("var list = document.querySelectorAll('span.css-answer-input.pds-answer-input input');\n" +
                    "list[" + index + "].click();", null);
            report.log(ActionBot.getTextFromElementList(goallabels).get(index) + " was selected");
            isVoted = true;
        } else {
            List<WebElement> iframes = ActionBot.getAllElements(iframe);
            ActionBot.clickOnElement(iframes.get(5), "iframe");
        }
        return isVoted;
    }
}
