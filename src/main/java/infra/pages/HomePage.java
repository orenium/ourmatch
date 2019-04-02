package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HomePage extends BasePage {

    public static List<WebElement> iframes;

    public HomePage(WebDriver driver) {
        super(driver);
        closeAcceptCookiesDialog();
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
        report.log("Current Url: " + driver.getCurrentUrl());

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
        if (isValid) {
            report.log("The search term '" + searchTerm + "' is shown at the url ");
        }
        return isValid;
    }

    public GamePage selectRandomItem() {
        By videosOnPage = By.cssSelector(".vidthumb");
        ActionBot.selectRandomItem(videosOnPage);
        return new GamePage(driver);
    }

    public void getViewsData() {
        By viewsLocator = By.cssSelector("span.views i.count");
        List<String> viewsValues = ActionBot.getTextFromElementList(viewsLocator);

        try {
            for (String val : viewsValues) {
                if (val.contains("K")) {
                    val.replace("k", "");
                    val = val.replace("k", "");
                    long num = Long.parseLong(val);
                }
                System.out.println("val: " + val);
            }
        } catch (java.lang.NumberFormatException ex) {
            System.out.println("NumberFormatException");
        }
    }


    public TwitterPopUpPage followOnTwitter() {
        ActionBot.clickOnElementInIFrame(By.cssSelector("div#header iframe[src*='twitter']"), By.cssSelector("a#follow-button"));
        String mainPage = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            System.out.println(window);
            if (!window.equals(mainPage)) {
                driver.switchTo().window(window);
                break;
            }
        }
        By test = By.cssSelector("div#bd");
        System.out.println(ActionBot.isElementDisplayed(test));
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        return new TwitterPopUpPage(driver);
    }


}
