package infra.pages;

import il.co.topq.difido.model.Enums;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;
import utils.MainConfig;

import java.util.List;

public class GamePage extends BasePage {

    private static final By iframe = By.cssSelector("div.embed-code iframe");
    private static final By embededCodeDiv = By.cssSelector("div.embed-code");
    private static final By matchTeams = By.cssSelector("div.match_team_header.clearfix h2");
    private static final By spoilerDiv = By.cssSelector("div.vs.spoiler");
    private static final By youTubeErrorMsg = By.cssSelector("div.ytp-error-content-wrap-reason span");
    private static final By streamablePlayBtn = By.id("play-button");
    private static final By youtubePlayBtn = By.cssSelector("button.ytp-large-play-button.ytp-button");
    private static final By matchScoreBtn = By.cssSelector("div.spoilerbtn button");
    private static final By commentName = By.cssSelector("div#comments ul li cite");
    private static final By commentContent = By.cssSelector("div#comments ul li div.comment-content p");
    private static final By playBtn = By.cssSelector("span.rmp-i.rmp-i-play");

    private static String homeTeam;
    private static String awayTeam;
    private static String author = "";
    private static String email = MainConfig.email;
    private static String commentToPost = MainConfig.comment;


    public GamePage(WebDriver driver) {
        super(driver);
        getMatchTeams();
    }


    /**
     * This method moves to the video (match highlights) and plays the video
     *
     * @return True if plays, false if not
     * NOTE: In case of 3rd party sites, if new tab was open once clicked, test will still pass
     */
    public boolean playHighlights() throws InterruptedException {
        boolean isPlayed = false;
        OmsPage omsPage;
        ActionBot.moveToElement(embededCodeDiv);

        if (ActionBot.isElementDisplayed(iframe, true)) {
            String videoSrc = ActionBot.getElementAttribute(iframe, "src");
            report.log("Playing match highlights from source: " + videoSrc);
            if (videoSrc.contains("www.youtube.com")) {
                isPlayed = playViaYoutubePlayer();
            } else if (videoSrc.contains("oms.veuclips.com")) {
                ActionBot.clickOnElementInIFrame(iframe, By.cssSelector("div.video-thumbnail"), "iframe video-thumbnail");
                omsPage = playViaOms(true);
                Thread.sleep(2000);
                if (omsPage.verifyNewTabWasOpen()) {
                    isPlayed = omsPage.clickPlay();
                }
            } else if (videoSrc.contains("oms.videostreamlet.net")) {
                omsPage = playViaOms(false);
                isPlayed = omsPage.clickPlay();
                if (ActionBot.isElementDisplayedInIframe(iframe, playBtn)) {
                    ActionBot.clickOnElementInIFrame(iframe, playBtn, "Play button");
                    isPlayed = true;
                }
            } else if (videoSrc.contains("streamable.com")) {
                isPlayed = playViaStreamable();
            }
        }
        return isPlayed;
    }


    /**
     * This method plays a video from oms site
     *
     * @param isNewTabOpen - Is should be opened in a new tab - true is so, false if not
     * @return - New OmsPage
     * @throws InterruptedException
     */
    private OmsPage playViaOms(boolean isNewTabOpen) throws InterruptedException {

        if (ActionBot.isElementDisplayed(iframe, true)) {
            ActionBot.clickOnElement(iframe, "oms iframe");
        }
        Thread.sleep(2000);
        if (isNewTabOpen) {
            ActionBot.switchToNewTab();
        }
        return new OmsPage(driver);
    }


    /**
     * This method plays a video from Streamable source
     *
     * @return - true if played, false if not
     */
    private boolean playViaStreamable() {

        boolean isPlayed;
        ActionBot.clickOnElementInIFrame(iframe, streamablePlayBtn, "Play button");
        isPlayed = true;
        return isPlayed;
    }

    /**
     * This method plays a video from Youtube source
     *
     * @return - true if played, false if not
     */
    private boolean playViaYoutubePlayer() {
        boolean isPlayingInYoutube = false;

        if (ActionBot.isElementDisplayed(iframe, true)) {
            ActionBot.clickOnElementInIFrame(iframe, youtubePlayBtn, "Play in Youtube");
            return true;
        }
        ActionBot.switchToIFrameDriver(iframe);
        if (ActionBot.isElementDisplayed(youTubeErrorMsg, false)) {
            //TODO: still not handled
            getErrorMsgIfShown();
        }
        return isPlayingInYoutube;
    }


    /**
     * This method checks if an error msg is shown when playing a Youtube video
     *
     * @return - True if error msg is shown, false if not
     */
    private boolean getErrorMsgIfShown() {

        By subReason = By.cssSelector("div.ytp-error-content-wrap-subreason span");
        boolean isShown = false;
        ActionBot.moveToElementInIFrame(iframe, youTubeErrorMsg);
        if (ActionBot.isElementDisplayedInIframe(iframe, youTubeErrorMsg)) {
            report.log("Error: " + ActionBot.getTextFromElementInIFrame(iframe, youTubeErrorMsg));
            report.log(ActionBot.getTextFromElementInIFrame(iframe, subReason));
            isShown = true;
        }
        return isShown;
    }


    /**
     * This method prints to the log the current match teams
     */
    public void getMatchTeams() {

        List<WebElement> teams = ActionBot.getAllElements(matchTeams);
        homeTeam = teams.get(0).getText();
        awayTeam = teams.get(1).getText();

        report.log("Match: " + homeTeam + " " + " VS " + awayTeam);
    }


    /**
     * This method clicks the "show match score" button
     *
     * @return - True if scores are shown, false if not
     */
    public boolean showMatchScore() {
        boolean isScoreShown = false;

        if (ActionBot.isElementDisplayed(matchScoreBtn, true)) {
            ActionBot.moveToElement(By.cssSelector("div.score"));
            ActionBot.clickOnElement(matchScoreBtn, "Toggle match score button");
            ActionBot.waitForElementToBeDisplayed(spoilerDiv);
            String vsText = ActionBot.getElementText(spoilerDiv);
            if (vsText.equals("FT")) {
                isScoreShown = true;
                report.log("Match score: " + ActionBot.getElementText(By.cssSelector("div.home-score.spoiler")) + " : " + ActionBot.getElementText(By.cssSelector("div.away-score.spoiler")));
            }
        }
        return isScoreShown;
    }

    /**
     * This method hide the match score by the score toggle button
     *
     * @return - True if scores are hide, false if shown
     */
    public boolean hideMatchScore() {
        boolean isScoreHidden = false;

        if (ActionBot.isElementDisplayed(matchScoreBtn, true)) {
            ActionBot.moveToElement(By.cssSelector("div.score"));
            ActionBot.clickOnElement(matchScoreBtn, "Toggle match score button");
            ActionBot.waitForElementToBeDisplayed(spoilerDiv);
            String vsText = ActionBot.getElementText(spoilerDiv);
            if (vsText.equals("-")) {
                isScoreHidden = true;
            }
        }
        return isScoreHidden;
    }


    /**
     * This method leaves a comment at the current match page
     *
     * @return - True if comment was left, false if action failed
     */
    public boolean leaveComment() {
        author = homeTeam.toLowerCase() + " fan";

        ActionBot.moveToElement(By.cssSelector("#footer"));
        ActionBot.writeToElement(By.cssSelector("form input#author"), author);
        ActionBot.writeToElement(By.cssSelector("form input#email"), email);
        ActionBot.writeToElement(By.cssSelector("form textarea#comment"), commentToPost);
        ActionBot.clickOnElement(By.cssSelector("p input[name='submit']"), "post comment");
        report.log("A comment: " + commentToPost + " was posted");

        return isCommentLeft();
    }


    /**
     * This method validate that a comment was posted
     *
     * @return - True if a comment was left, false if not
     */
    private boolean isCommentLeft() {
        boolean isCommentLeft = false;
        try {
            ActionBot.moveToElement(By.cssSelector("#footer"));
            String name = ActionBot.getElementText(commentName);

            ActionBot.moveToElement(By.cssSelector("footer"));
            List<WebElement> comments = ActionBot.getAllElements(commentContent);
            String comment = ActionBot.getElementText(comments.get(1));
            if (comment.equals(comment) && name.equals(author)) {
                isCommentLeft = true;
            }
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            CommentSubmissionFailurePage commentSubmissionFailurePage = new CommentSubmissionFailurePage(driver);
            String errorMsg = commentSubmissionFailurePage.getErrorMsg();
            report.log(errorMsg, Enums.Status.error);
            if (errorMsg.equals("Duplicate comment detected; it looks as though you’ve already said that!")) {
                report.log("It seems like they have found us!", Enums.Status.warning);
                isCommentLeft = true;
            }
            commentSubmissionFailurePage.clickBack();
        } catch (org.openqa.selenium.TimeoutException ex){
            report.log("No comment found!", Enums.Status.error);
        }
        return isCommentLeft;
    }


}
