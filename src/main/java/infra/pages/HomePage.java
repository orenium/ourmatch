package infra.pages;

import com.esotericsoftware.minlog.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        setLeaguesMap();
    }

    public boolean mainLeaguesLinksValidation(HashMap<String, String> leaguesAndLinks) {
        boolean isValid = false;
        int validLinks = 0;

        try {
            List<WebElement> leaguesTitles = driver.findElements(By.cssSelector("li.popular-leagues-list ul li.hover-tp a"));
            List<WebElement> leaguesLinks = driver.findElements(By.cssSelector("li.popular-leagues-list ul li a"));

            for (int i = 0; i < leaguesAndLinks.size(); i++) {

                if (leaguesAndLinks.get(" " + leaguesTitles.get(i).getText()).equals(
                        (leaguesLinks.get(i).getAttribute("href")))) {
                    Log.info(leaguesTitles.get(i).getText() + " link is valid");
                    validLinks++;
                } else {
                    Log.warn(leaguesTitles.get(i).getText() + " link is NOT valid");
                }

            }
            if (validLinks == leaguesAndLinks.size()) {
                isValid = true;
                Log.info("All leagues links are valid");
            } else {
                Log.warn("Not all links are valid");
            }

        } catch (Exception ex) {
            Log.error(ex.getMessage());
        }
        return isValid;
    }


}
