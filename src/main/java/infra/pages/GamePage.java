package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;
import utils.MainConfig;

import java.util.List;

public class GamePage extends BasePage {

    By player = By.id("rmpPlayer");

    By rmp = By.cssSelector("div.rmp-overlay-button.rmp-color-bg");
    By closePopupBtn = By.cssSelector("div.closeButtonIcon");

    By iframe = By.cssSelector("div.embed-code iframe");
    By embededCodeDiv = By.cssSelector("div.embed-code");
    By matchTeams = By.cssSelector("div.match_team_header.clearfix h2");
    By spoilerDiv = By.cssSelector("div.vs.spoiler");
    By videoSourceOptions = By.cssSelector("li[data-pos='top']");

    String homeTeam;
    String awayTeam;
    String author = "";
    String email = MainConfig.email;
    String commentToPost = MainConfig.comment;


    public GamePage(WebDriver driver) {
        super(driver);
        getMatchTeams();
    }


    public boolean playHighlights() {
        boolean isPlayed = false;
        ActionBot.moveToElement(embededCodeDiv);

        List<WebElement> videoSources = ActionBot.getAllElements(videoSourceOptions);

//        for (WebElement source: videoSources) {
//            ActionBot.clickOnElement(source, "selecting match source");


        if (ActionBot.isElementDisplayed(iframe)) {
            String videoSrc = ActionBot.getElementAttribute(iframe, "src");
            report.log("Playing match highlights from source: " + videoSrc);
            if (videoSrc.contains("www.youtube.com")) {
                isPlayed = playViaYoutubePlayer();
            } else if (videoSrc.contains("oms.veuclips.com")) {
                ActionBot.clickOnElementInIFrame(iframe, By.cssSelector("div.video-thumbnail"), "iframe video-thumbnail");
                OmsPage omsPage = playViaInOms(true);
                isPlayed = omsPage.verifyNewTabWasOpen();
                if (isPlayed) {
                    omsPage.clickPlay();
                }
            } else if (videoSrc.contains("oms.videostreamlet.net")) {
                playViaInOms(false);
                By playBtn = By.cssSelector("span.rmp-i.rmp-i-play");
                if (ActionBot.isElementDisplayedInIframe(iframe, playBtn)) {
                    ActionBot.clickOnElementInIFrame(iframe, playBtn, "Play button");
                    isPlayed = true;
                }
            } else if (videoSrc.contains("streamable.com")) {
                isPlayed = playViaStreamable();
            }
        }
//        }
        return isPlayed;
    }

    public OmsPage playViaInOms(boolean isNewTabOpen) {

        if (ActionBot.isElementDisplayed(iframe)) {
            ActionBot.clickOnElement(iframe, "oms iframe");
        }
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {
        }
        if (isNewTabOpen) {
            ActionBot.switchToNewTab();
        }
        return new OmsPage(driver);
    }

    public boolean playViaStreamable() {

        boolean isPlayed = false;
        By playBtn = By.id("play-button");
        ActionBot.clickOnElementInIFrame(iframe, playBtn, "Play button");
        isPlayed = true;
        return isPlayed;
    }


    private boolean playViaYoutubePlayer() {
        By fullScreenBtn = By.cssSelector("button.ytp-fullscreen-button.ytp-button");

        boolean isPlayingInYoutube = false;

        By playBtn = By.cssSelector("button.ytp-large-play-button.ytp-button");
        if (ActionBot.isElementDisplayed(iframe)) {
            ActionBot.clickOnElementInIFrame(iframe, playBtn, "Play in Youtube");
            isPlayingInYoutube = true;
            if (!isErrorMsgShown()) {
                ActionBot.moveToElementInIFrame(iframe, fullScreenBtn);
                ActionBot.clickOnElementInIFrame(iframe, fullScreenBtn, "full screen");
            }
        }
        return isPlayingInYoutube;
    }

    private boolean isErrorMsgShown() {
        By youTubeErrorMsg = By.cssSelector("div.ytp-error-content-wrap-reason span");
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


    public void closePopup() {

        ActionBot.clickOnElementInIFrame(iframe, closePopupBtn, "close Popup button");
//        try {
//        } catch (org.openqa.selenium.TimeoutException ex) {
//            System.out.println("TimeoutException");
//            ActionBot.clickOnIFrameElement(iframe, closePopupBtb, "closePopupBtn");
//            ActionBot.moveToElement(closePopupBtb);
//            ActionBot.clickOnElement(closePopupBtb, "close popup btn");
//            ActionBot.clickOnElement(closeButtonContainer, "close popup btn");
//        }
    }


    /**
     * This method opens the current game in full screen mode
     *
     * @return - True if action succeed, false if not
     */
//    public boolean openVideoFullScreen() {
//        boolean isFullscreen = false;
//
//        try {
//            for (WebElement video : ActionBot.getAllElements(videoHighlightsSrcs)) {
//                ActionBot.clickOnElement(video, "video src");
//                System.out.println("Page URL: " + driver.getCurrentUrl());
//               ActionBot.clickOnElementInIFrame(iframe, fullScreenBtn, "full Screen button");
//               report.log("switching to full screen");
//                isFullscreen = true;
//                break;
//            }
//        } catch (org.openqa.selenium.TimeoutException ex) {
//           ActionBot.clickOnElement(iframe, "iframe");
//           ActionBot.clickOnElementInIFrame(iframe, linkIniFrame, "link in iFrame");
//
//        }
//        return isFullscreen;
//    }
    public void printErrorReason(By errorElement) {
        report.log(ActionBot.getElementText(errorElement));
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

    /**
     * This method hide the match score by the score toggle button
     *
     * @return - True if scores are hide, false if shown
     */
    public boolean hideMatchScore() {
        boolean isScoreHidden = false;
        By matchScoreBtn = By.cssSelector("div.spoilerbtn button");
        if (ActionBot.isElementDisplayed(matchScoreBtn)) {
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

        ActionBot.moveToElement(By.cssSelector("footer"));
        ActionBot.writeToElement(By.cssSelector("form input#author"), author);
        ActionBot.writeToElement(By.cssSelector("form input#email"), email);
        ActionBot.writeToElement(By.cssSelector("form textarea#comment"), commentToPost);
        ActionBot.clickOnElement(By.cssSelector("p input[name='submit']"), "post comment");
        report.log("A comment: " + commentToPost + " was posted");

        return isCommentLeft();
    }

    private boolean isCommentLeft() {
        boolean isCommentLeft = false;
        By commentName = By.cssSelector("div#comments ul li cite");
        String name = ActionBot.getElementText(commentName);

        ActionBot.moveToElement(By.cssSelector("footer"));
        By commentContent = By.cssSelector("div#comments ul li div.comment-content p");
        List<WebElement> comments = ActionBot.getAllElements(commentContent);
        String comment = ActionBot.getElementText(comments.get(1));
        if (comment.equals(comment) && name.equals(author)) {
            isCommentLeft = true;
        }
        return isCommentLeft;
    }


}
