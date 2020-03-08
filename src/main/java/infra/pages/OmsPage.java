package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import infra.pages.utils.ActionBot;

public class OmsPage extends BasePage {

    private static final By playBtnSpan = By.cssSelector("span.rmp-i.rmp-i-play");
    private static final By notFoundError = By.cssSelector("div#notfound");

    private WebDriverWait wait;
    private By iframeLocator = By.tagName("iframe");
    public static boolean isPlayed = false;


    public OmsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * This method starts playing highlights by clicking the play button
     *
     * @return - True if clicked, false if not
     */
    public boolean clickPlay() {

        if (ActionBot.isElementDisplayed(iframeLocator, true)) {
            try {
                ActionBot.switchToIFrameDriver(iframeLocator);
                if (ActionBot.isElementDisplayed(playBtnSpan, true)) {
                    ActionBot.clickOnElement(playBtnSpan, "Play button");
                    isPlayed = true;
                } else {
                    printErrorMsg();
                }
                ActionBot.switchToDefaultContent();
            } catch (org.openqa.selenium.NoSuchElementException ex) {
                printErrorMsg();
                return false;

            } catch (org.openqa.selenium.TimeoutException ex) {
                printErrorMsg();
                return false;
            }
        } else {
            printErrorMsg();
        }
        return isPlayed;
    }


    /**
     * This methods verified that a new tab was open
     *
     * @return - True if a new tab was open, false if not
     */
    public boolean verifyNewTabWasOpen() {
        boolean isNewTabOpened = false;
        String url = driver.getCurrentUrl();
        report.log("OmsPage was open in browser");
        if (url.contains("oms.veuclips.com/")) {
            isNewTabOpened = true;
        }
        return isNewTabOpened;
    }


    /**
     * This method prints the error msg to log
     */
    private void printErrorMsg() {

        report.log("Unable to play video");
        if (ActionBot.isElementDisplayed(notFoundError, true)) {
            report.log("Error:" + ActionBot.getElementText(By.cssSelector("div#notfound h1")));
            report.log(ActionBot.getElementText(By.cssSelector("div#notfound h2")));
        }
    }
}




