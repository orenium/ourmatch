package infra.pages;

import com.esotericsoftware.minlog.Log;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import utils.ActionBot;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


@Listeners(il.co.topq.difido.ReportManagerHook.class)
public abstract class BasePage {

    protected WebDriver driver;
    public HashMap<String, String> leaguesAndLinksMap;
    protected static ReportDispatcher report;
//    public static ActionBot actionBot;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.report = ReportManager.getInstance();
//        this.actionBot = new ActionBot();
    }

    protected void setLeaguesMap() {
        leaguesAndLinksMap = new HashMap();

        leaguesAndLinksMap.put(" Premier League", "https://ourmatch.net/videos/england/premier-league-highlights/");
        leaguesAndLinksMap.put(" La Liga", "https://ourmatch.net/videos/spain/la-liga-highlights/");
        leaguesAndLinksMap.put(" Serie A", "https://ourmatch.net/videos/italy/serie-a-highlights/");
        leaguesAndLinksMap.put(" Bundesliga", "https://ourmatch.net/videos/germany/bundesliga-highlights/");
        leaguesAndLinksMap.put(" Ligue 1", "https://ourmatch.net/videos/france/ligue-1-highlights/");
        leaguesAndLinksMap.put(" Champions League", "https://ourmatch.net/videos/europe/uefa-champions-league-highlights/");
        leaguesAndLinksMap.put(" Europa League", "https://ourmatch.net/videos/europe/uefa-europa-league-highlights/");
    }



    public boolean closeAcceptCookiesDialog() {
        By container = By.cssSelector("div.cc-window.cc-banner.cc-type-info.cc-theme-edgeless.cc-bottom.cc-color-override-1861914146");
        By acceptAndCloseBtn = By.cssSelector("div.cc-window.cc-banner.cc-type-info.cc-theme-edgeless.cc-bottom.cc-color-override-1861914146 div.cc-compliance a");

        boolean isClosed = false;

        try {
            if (ActionBot.isElementDisplayed(container)) {
                ActionBot.clickOnElement(acceptAndCloseBtn, "accept and close Btn");
                isClosed = true;

            } else {
                ActionBot.waitForElementToBeDisplayed(container);
                ActionBot.clickOnElement(acceptAndCloseBtn, "accept and close Btn");
                isClosed = true;
            }

//            if (!actionBot.isElementDisplayed(container)) {
//                report.log("AcceptCookiesDialog was closed");
//            }
        } catch (Exception ex) {
            report.log("Unable to find closeAcceptCookiesDialog: " + ex.getMessage());
        }

        return isClosed;
    }

    public GoalOfTheMonthPage openGoalOfTheMonthLink(){
        By goalOfTheMonthBtn = By.cssSelector("div[id=main-navigation] li a[href='http://ourmatch.net/goal-of-the-month/']");
        ActionBot.clickOnElement(goalOfTheMonthBtn, "Goal of the month link");
        return new GoalOfTheMonthPage(driver);
    }




}







