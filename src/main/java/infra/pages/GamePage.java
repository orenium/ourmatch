package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;

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


    public GamePage(WebDriver driver) {
        super(driver);
        getMatchTeams();
    }


    public void playHighlights() {
// TODO: test fail for now
        ActionBot.moveToElement(embededCodeDiv);
        ActionBot.clickOnElement(embededCodeDiv, "embededCodeDiv");

        if (ActionBot.isElementDisplayed(iframe)) {
            report.log("iframe src: " + ActionBot.getElementAttribute(iframe, "src"));
            ActionBot.clickOnElement(iframe, "iframe");
            openVideoFullScreen(iframe);

        }
    }

    public void closePopup() {

        try {
            ActionBot.waitForElementToBeDisplayed(closePopupBtb);
            ActionBot.clickOnElement(closePopupBtb, "close popup btn");
            driver.close();
        } catch (org.openqa.selenium.TimeoutException ex) {
            System.out.println("TimeoutException");
            ActionBot.clickOnIFrameElement(iframe, closePopupBtb, "closePopupBtn");
//            ActionBot.moveToElement(closePopupBtb);
//            ActionBot.clickOnElement(closePopupBtb, "close popup btn");
//            ActionBot.clickOnElement(closeButtonContainer, "close popup btn");
        }
    }


    public void openVideoFullScreen(By byLocator) {
        ActionBot.doubleClick(byLocator);
    }

    public void getMatchTeams() {

        List<WebElement> teams = ActionBot.getAllElements(matchTeams);
        String homeTeam = teams.get(0).getText();
        String awayTeam = teams.get(1).getText();

        report.log("Match: " + homeTeam + " " + " VS " + awayTeam);
    }

    public boolean toggleMatchScore() {
        boolean isScoreShown = false;
        By matchScoreBtn = By.cssSelector("div.spoilerbtn button");
        ActionBot.moveToElement(By.cssSelector("div.score"));
        ActionBot.clickOnElement(matchScoreBtn, "Toggle match score button");
        ActionBot.waitForElementToBeDisplayed(spoilerDiv);
        String vsText = ActionBot.getElementText(spoilerDiv);
        if (vsText.equals("FT")){
            isScoreShown = true;
        }
        return isScoreShown;
    }
}
