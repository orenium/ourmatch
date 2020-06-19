package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import infra.pages.utils.ActionBot;

import java.util.List;

import static infra.pages.utils.ActionBot.*;

public class GoalOfTheMonthPage extends BasePage {

    private static final By goalOptions = By.cssSelector("span.css-answer-input.pds-answer-input input");
    private static final By goalLabels = By.cssSelector("span.css-answer-span.pds-answer-span");

    private static final By scoreDiv = By.cssSelector("div.pds-box-top");
    public static final By pollMsg = By.cssSelector("div.PDS_Poll div.pds-question-top");
    private static final By spanAnswerText = By.cssSelector("span.pds-answer-text");
    private static final By spanAnswerPer = By.cssSelector("span.pds-feedback-per");
    private static final By spanAnswerVotes = By.cssSelector("span.pds-feedback-votes");
    private static final By divTotalVotes = By.cssSelector("div.pds-total-votes");

    private static final By iframe = By.tagName("iframe");
    private static final By voteBtn = By.cssSelector("div.css-box.pds-box span");  // 41

    public static boolean isClosedForVoting = false;

    public GoalOfTheMonthPage(WebDriver driver) {
        super(driver);
    }


    /**
     * This method votes for goal of the month (if available)
     * @return - True if vote was made, false if not
     */
    public boolean vote() {
        boolean isVoted;

        int index = getRandomIndex(getTextFromElementList(goalLabels).size());
        moveToElement(By.cssSelector("div.CSS_Poll.PDS_Poll"));
        executeJavaScript("var list = document.querySelectorAll('span.css-answer-input.pds-answer-input input');\n" +
                "list[" + index + "].click();");
        report.log(getTextFromElementList(goalLabels).get(index) + " was selected");
        moveToElement(By.cssSelector("div.pre-gotm"));
        moveToElement(voteBtn);
        clickOnElement(voteBtn, "Vote button");
        isVoted = true;
        moveToElement(By.cssSelector("div.pds-box-outer"));

        return isVoted;
    }


    /**
     * This method check if voting is possible or the pool is already close until next month
     *
     * @return - True if pool is close, false if not
     */
    public boolean isPollClosed() {
        if (isElementDisplayed(pollMsg,true)) {
            String pollMsgText = getElementText(pollMsg);
            if (!pollMsgText.contains("Poll Closed")) {
                isClosedForVoting = false;
                report.log("Poll is available for voting");
            } else {
                isClosedForVoting = true;
                report.log("Poll is closed for voting");
                List<WebElement> iframes = getAllElements(iframe);
                clickOnElement(iframes.get(5), "iframe");
            }
        }

        return isClosedForVoting;
    }

    /**
     * This method prints the voting scores to logs
     */
    public boolean printScoresToLog() {
        boolean isPrintedToLogs = false;
        if (isElementDisplayed(scoreDiv, true)) {
            report.log(getElementText(pollMsg));
            report.log("Results:");
            List<String> names = getTextFromElementList(spanAnswerText);
            List<String> per = getTextFromElementList(spanAnswerPer);
            List<String> votes = getTextFromElementList(spanAnswerVotes);
            for (int i = 0; i < names.size(); i++) {
                report.log(names.get(i) + "    " + per.get(i) + "    " + votes.get(i));
            }
            isPrintedToLogs = true;
            report.log(getElementText(divTotalVotes));
        }
        return isPrintedToLogs;
    }
}
