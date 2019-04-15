package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ActionBot;

public class OmsPage extends BasePage {

    private static final By skipBtn = By.cssSelector("div.videoAdUiSkipIcon");
    private static final By playBtnSpan = By.cssSelector("span.rmp-i.rmp-i-play");
    private static final By notFoundError = By.cssSelector("div#notfound");

    private WebDriverWait wait;
    //    By iframeLocator = By.cssSelector("div.rmp-content iframe");
    By iframeLocator = By.tagName("iframe");
    public static boolean isPlayed = false;


    public OmsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 30);
    }

    public void skipAd() {

        WebElement iFrame = driver.findElement(iframeLocator);
        driver.switchTo().frame(iFrame);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(skipBtn)).click();
            report.log("Skipp ad button was clicked");
            ActionBot.switchToDefaultContent();
        } catch (org.openqa.selenium.TimeoutException ex) {
            ActionBot.switchToDefaultContent();
            clickPlay();
        }
    }


    public boolean clickPlay() {

        if (ActionBot.isElementDisplayed(iframeLocator, true)) {
            try {
                ActionBot.switchToIFrameDriver(iframeLocator);
                if (ActionBot.isElementDisplayed(playBtnSpan, true)){
                    ActionBot.clickOnElement(playBtnSpan, "Play button");
                    isPlayed = true;
                }
//                wait.until(ExpectedConditions.visibilityOfElementLocated(playBtnSpan)).click();
//                report.log("Play button was clicked");
                ActionBot.switchToDefaultContent();
            } catch(org.openqa.selenium.NoSuchElementException ex){
                printErrorMsg();
            }
        } else {
            printErrorMsg();
        }
        return isPlayed;
    }

    public boolean verifyNewTabWasOpen() {
        boolean isNewTabOpened = false;
        String url = driver.getCurrentUrl();
        report.log("OmsPage was open in browser");
        if (url.contains("oms.veuclips.com/")) {
            isNewTabOpened =  true;
        }
        return isNewTabOpened;
    }

    private void printErrorMsg(){

        report.log("Unable to play video");
        if (ActionBot.isElementDisplayed(notFoundError, true)){
            report.log("Error:" + ActionBot.getElementText(By.cssSelector("div#notfound h1")));
            report.log(ActionBot.getElementText(By.cssSelector("div#notfound h2")));
        }
    }
}




