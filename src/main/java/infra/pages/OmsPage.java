package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ActionBot;

public class OmsPage extends BasePage {

    private WebDriverWait wait;
    //    By iframeLocator = By.cssSelector("div.rmp-content iframe");
    By iframeLocator = By.tagName("iframe");
    public static boolean isPlayed = false;


    public OmsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 30);
    }

    public void skipAd() {
        By skipBtn = By.cssSelector("div.videoAdUiSkipIcon");
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
        By playBtnSpan = By.cssSelector("span.rmp-i.rmp-i-play");
        if (ActionBot.isElementDisplayed(iframeLocator, false)) {
            try {
                ActionBot.switchToIFrameDriver(iframeLocator);
                wait.until(ExpectedConditions.visibilityOfElementLocated(playBtnSpan)).click();
                report.log("Play button was clicked");
                isPlayed = true;
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
        String url = driver.getCurrentUrl();
        report.log("OmsPage was open in browser");
        if (url.contains("http://oms.veuclips.com/"))
            return true;
        else return false;
    }

    private void printErrorMsg(){
        By notFoundError = By.cssSelector("div#notfound");
        report.log("Unable to play video");
        if (ActionBot.isElementDisplayed(notFoundError, true)){
            report.log("Error:" + ActionBot.getElementText(By.cssSelector("div#notfound h1")));
            report.log(ActionBot.getElementText(By.cssSelector("div#notfound h2")));
        }
    }
}




