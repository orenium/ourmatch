package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;

import java.util.List;

public class GoalOfTheMonthPage extends BasePage {

    By goalOptions = By.cssSelector("span.css-answer-input.pds-answer-input input");
    By goallabels = By.cssSelector("span.css-answer-span.pds-answer-span");

    By scoreDiv = By.cssSelector("div.pds-box-top");
    By pollClosedMsg = By.cssSelector("div.PDS_Poll div.pds-question-top");
    By spanAnswerText = By.cssSelector("span.pds-answer-text");
    By spanAnswerPer = By.cssSelector("span.pds-feedback-per");
    By spanAnswerVotes = By.cssSelector("span.pds-feedback-votes");
    By divTotalVotes = By.cssSelector("div.pds-total-votes");

    By iframe = By.tagName("iframe");
    By playBtn = By.cssSelector("div#play-button");

    public static boolean isClosedForVoting = false;

    public GoalOfTheMonthPage(WebDriver driver) {
        super(driver);
    }

    public boolean vote() {
        boolean isVoted = false;
        if (!isPoolClosed()) {
            int index = ActionBot.getRandomIndex(ActionBot.getTextFromElementList(goallabels).size());
            ActionBot.moveToElement(By.cssSelector("#PDI_form10257564"));
            ActionBot.executeJavaScript("var list = document.querySelectorAll('span.css-answer-input.pds-answer-input input');\n" +
                    "list[" + index + "].click();");
            report.log(ActionBot.getTextFromElementList(goallabels).get(index) + " was selected");
            isVoted = true;
        } else {
            List<WebElement> iframes = ActionBot.getAllElements(iframe);
            ActionBot.clickOnElement(iframes.get(5), "iframe");
        }
        return isVoted;
    }


    private boolean isPoolClosed() {
        if (ActionBot.isElementDisplayed(pollClosedMsg)) {
            isClosedForVoting = true;
            printScoresToLog();
        }
        return isClosedForVoting;
    }

    private void printScoresToLog() {
        if (ActionBot.isElementDisplayed(scoreDiv)) {
            report.log(ActionBot.getElementText(pollClosedMsg));
            report.log("Results:");
            List<String> names = ActionBot.getTextFromElementList(spanAnswerText);
            List<String> per = ActionBot.getTextFromElementList(spanAnswerPer);
            List<String> votes = ActionBot.getTextFromElementList(spanAnswerVotes);
            for (int i = 0; i < names.size(); i++) {
                report.log(names.get(i) + "    " + per.get(i) + "    " + votes.get(i));
            }
            report.log(ActionBot.getElementText(divTotalVotes));
        }
    }
}
