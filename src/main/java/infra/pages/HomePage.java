package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;

import java.util.HashMap;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
//        setLeaguesMap();
    }

    public boolean mainLeaguesLinksValidation(HashMap<String, String> leaguesAndLinks) {

        By leaguesTitles = By.cssSelector("li.popular-leagues-list ul li.hover-tp a");
        By leaguesLinks = By.cssSelector("li.popular-leagues-list ul li a");

        List<WebElement> leaguesTitlesList;
        List<WebElement> leaguesLinksList;

        boolean isValid = false;
        int validLinks = 0;

        try {
            leaguesTitlesList = ActionBot.getAllElements(leaguesTitles);
            leaguesLinksList = ActionBot.getAllElements(leaguesLinks);

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


    public boolean search(String searchTerm) {
        By searchInput = By.cssSelector("input.search-text");
        By searchBtn = By.className("search-submit");

        ActionBot.writeToElement(searchInput, searchTerm);
        ActionBot.clickOnElement(searchBtn, "searchBtn");
        System.out.println("getCurrentUrl: "+ driver.getCurrentUrl());

        return validateSearchInURL(searchTerm);
    }

    public HashMap getLeaguesAndCountriesMap() {

        if (leaguesAndLinksMap != null) {
            return leaguesAndLinksMap;
        } else {
            report.log("Unable to get leaguesAndCountriesMap");
            return null;
        }
    }

    private boolean validateSearchInURL(String searchTerm) {

        boolean isValid = false;

        String url = driver.getCurrentUrl();
        if (url.contains(searchTerm)) {
            report.log("url contains search term" + searchTerm);
            isValid = true;
        } else {
            if (searchTerm.length() > 0) {
                String[] splited = searchTerm.split(" ");
                for (String singleWord : splited) {
                    if (url.contains(singleWord)) {
                        isValid = true;
                    } else {
                        isValid = false;
                    }
                }
            }
        }
        if (isValid){
            report.log("The search term '" + searchTerm + "' is shown at the url ");
        }
        return isValid;
    }

    public GamePage selectRandomItem() {
        By videosOnPage = By.cssSelector(".vidthumb");
        ActionBot.selectRandomItem(videosOnPage);
        return new GamePage(driver);
    }


}
