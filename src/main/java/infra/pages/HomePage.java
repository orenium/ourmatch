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

        By leaguesTitles = By.cssSelector("li.popular-leagues-list ul li.hover-tp a");
        By leaguesLinks = By.cssSelector("li.popular-leagues-list ul li a");

        List<WebElement> leaguesTitlesList;
        List<WebElement> leaguesLinksList;

        boolean isValid = false;
        int validLinks = 0;

        try {
            leaguesTitlesList = actionBot.getAllElements(leaguesTitles);
            leaguesLinksList = actionBot.getAllElements(leaguesLinks);

            for (int i = 0; i < leaguesAndLinks.size(); i++) {

                if (leaguesAndLinks.get(" " + leaguesTitlesList.get(i).getText()).equals(
                        (leaguesLinksList.get(i).getAttribute("href")))) {
                    report.log(leaguesTitlesList.get(i).getText() + " link is valid");
                    validLinks++;
                } else {
                    report.log(leaguesTitlesList.get(i).getText() + " link is NOT valid");
                }

            }
            if (validLinks == leaguesAndLinks.size()) {
                isValid = true;
                report.log("All leagues links are valid");
            } else {
                report.log("Not all links are valid");
            }

        } catch (Exception ex) {
             report.log(ex.getMessage());
        }
        return isValid;
    }


    public void search(String searchTerm) {

        By searchInput = By.cssSelector("input.search-text");
        By searchBtn = By.className("search-submit");

        actionBot.writeToElement(searchInput, searchTerm);
        actionBot.clickOnElement(searchBtn);

    }


    public boolean closeAcceptCookiesDialog() {
        By container = By.cssSelector("div.cc-window.cc-banner.cc-type-info.cc-theme-edgeless.cc-bottom.cc-color-override-1861914146");
        By acceptAndCloseBtn = By.cssSelector("div.cc-window.cc-banner.cc-type-info.cc-theme-edgeless.cc-bottom.cc-color-override-1861914146 div.cc-compliance a");

        boolean isClosed = false;

        try {
            if (actionBot.isElementDisplayed(container)) {
                actionBot.clickOnElement(acceptAndCloseBtn);

            } else {
                actionBot.waitForElementToBeDisplayed(container);
                actionBot.clickOnElement(acceptAndCloseBtn);
            }

            if (!actionBot.isElementDisplayed(container)) {
                report.log("AcceptCookiesDialog was closed");
                isClosed = true;

            }
        } catch (Exception ex) {
            report.log("Unable to find closeAcceptCookiesDialog: " + ex.getMessage());
        }

        return isClosed;
    }
}
