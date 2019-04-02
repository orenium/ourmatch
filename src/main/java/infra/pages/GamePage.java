package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;
import utils.MainConfig;

import java.util.List;

public class GamePage extends BasePage {

    By player = By.id("rmpPlayer");
    By playBtn = By.cssSelector("span.rmp-i.rmp-i-play");
    By rmp = By.cssSelector("div.rmp-overlay-button.rmp-color-bg");
    By closePopupBtb = By.cssSelector("div.closeButtonIcon");
    By iframe = By.cssSelector("iframe.contentVencher");
    By embededCodeDiv = By.cssSelector("div.embed-code");
    By matchTeams = By.cssSelector("div.match_team_header.clearfix h2");
    By spoilerDiv = By.cssSelector("div.vs.spoiler");
    By fullScreenBtn = By.cssSelector("div.rmp-fullscreen.rmp-i.rmp-i-resize-full");

    String homeTeam;
    String awayTeam;


    public GamePage(WebDriver driver) {
        super(driver);
        getMatchTeams();
    }


    public void playHighlights() {
        ActionBot.moveToElement(embededCodeDiv);
        ActionBot.clickOnElement(embededCodeDiv, "embededCodeDiv");

        if (ActionBot.isElementDisplayed(iframe)) {
            report.log("iframe src: " + ActionBot.getElementAttribute(iframe, "src"));
        }
    }


    public void closePopup() {

        ActionBot.clickOnElementInIFrame(iframe, closePopupBtb);
//        try {
//        } catch (org.openqa.selenium.TimeoutException ex) {
//            System.out.println("TimeoutException");
//            ActionBot.clickOnIFrameElement(iframe, closePopupBtb, "closePopupBtn");
//            ActionBot.moveToElement(closePopupBtb);
//            ActionBot.clickOnElement(closePopupBtb, "close popup btn");
//            ActionBot.clickOnElement(closeButtonContainer, "close popup btn");
//        }
    }


    public boolean openVideoFullScreen() {
        ActionBot.clickOnElementInIFrame(iframe, fullScreenBtn);
        report.log("switching to full screen");
        return true;
    }

    public void getMatchTeams() {

        List<WebElement> teams = ActionBot.getAllElements(matchTeams);
        homeTeam = teams.get(0).getText();
        awayTeam = teams.get(1).getText();

        report.log("Match: " + homeTeam + " " + " VS " + awayTeam);
    }

    public boolean toggleMatchScore() {
        boolean isScoreShown = false;
        By matchScoreBtn = By.cssSelector("div.spoilerbtn button");
        if (ActionBot.isElementDisplayed(matchScoreBtn)) {
            ActionBot.moveToElement(By.cssSelector("div.score"));
            ActionBot.clickOnElement(matchScoreBtn, "Toggle match score button");
            ActionBot.waitForElementToBeDisplayed(spoilerDiv);
            String vsText = ActionBot.getElementText(spoilerDiv);
            if (vsText.equals("FT")) {
                isScoreShown = true;
            }
        }
        return isScoreShown;
    }

    public boolean leaveComment() {
        boolean isCommentLeft = false;
        String author = homeTeam.toLowerCase() + " fan";
        String email = MainConfig.email;
        String comment = MainConfig.comment;

        ActionBot.moveToElement(By.cssSelector("footer"));
        ActionBot.writeToElement(By.cssSelector("form input#author"), author);
        ActionBot.writeToElement(By.cssSelector("form input#email"), email);
        ActionBot.writeToElement(By.cssSelector("form textarea#comment"), comment);
        ActionBot.clickOnElement(By.cssSelector("p input[name='submit']"), "post comment");
        report.log("A comment: " + comment +" was posted");

        ActionBot.moveToElement(By.cssSelector("footer"));
        if (ActionBot.isElementDisplayed(By.cssSelector("div.comment-inner"))) {
            String commentAuthor = ActionBot.getElementText(By.cssSelector("div.comment-inner > div.comment-meta > span.comment-author"));
            List<WebElement> commentsElements = ActionBot.getAllElements(By.cssSelector("div.comment-inner > div.comment-content > p"));
            for (WebElement commentElement : commentsElements) {
                String commentText = ActionBot.getElementText(commentElement);
                if (commentText.equals(comment) && commentAuthor.equals(author)) {
                    isCommentLeft = true;
                    WebElement parent = commentElement.findElement(By.xpath(".."));
                    System.out.println("parent tag: " + parent.getTagName());
                    break;
                }

            }
        }
        return isCommentLeft;
    }
}
