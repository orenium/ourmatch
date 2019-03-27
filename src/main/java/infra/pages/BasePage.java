package infra.pages;

import com.esotericsoftware.minlog.Log;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import utils.ActionBot;

import java.util.HashMap;


@Listeners(il.co.topq.difido.ReportManagerHook.class)
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public HashMap<String, String> leaguesAndLinksMap;
    protected ActionBot actionBot;
    protected ReportDispatcher report = ReportManager.getInstance();


    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        actionBot = ActionBot.getInstance(driver);

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
        report.log("leaguesAndLinksMap was set successfully");
    }

    public HashMap getLeaguesAndCountriesMap() {

        if (leaguesAndLinksMap != null) {
            return leaguesAndLinksMap;
        } else {
            Log.info("Unable to get leaguesAndCountriesMap");
            return null;
        }
    }

}





