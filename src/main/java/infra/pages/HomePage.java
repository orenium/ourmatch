package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HomePage extends BasePage {

    //    public static List<WebElement> iframes;
    public static HashMap<String, String> leaguesAndLinksMap;

    public HomePage(WebDriver driver) {
        super(driver);
        setLeaguesMap();
    }

    private void setLeaguesMap() {
        leaguesAndLinksMap = new HashMap();

        leaguesAndLinksMap.put(" Premier League", "https://ourmatch.net/videos/england/premier-league-highlights/");
        leaguesAndLinksMap.put(" La Liga", "https://ourmatch.net/videos/spain/la-liga-highlights/");
        leaguesAndLinksMap.put(" Serie A", "https://ourmatch.net/videos/italy/serie-a-highlights/");
        leaguesAndLinksMap.put(" Bundesliga", "https://ourmatch.net/videos/germany/bundesliga-highlights/");
        leaguesAndLinksMap.put(" Ligue 1", "https://ourmatch.net/videos/france/ligue-1-highlights/");
        leaguesAndLinksMap.put(" Champions League", "https://ourmatch.net/videos/europe/uefa-champions-league-highlights/");
        leaguesAndLinksMap.put(" Europa League", "https://ourmatch.net/videos/europe/uefa-europa-league-highlights/");
    }


    /**
     * This method validate the main leagues and the match to their countries
     *
     * @param leaguesAndLinks - The leagues <--> country hashmap
     * @return - True is all match, false if not
     */
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

    /**
     * This methods search in the search field at the ourmatch site
     *
     * @param searchTerm - The term to search
     * @return - True if the search was done (validated by searching the search term at the URL), false if not
     */
    public boolean search(String searchTerm) {
        By searchInput = By.cssSelector("input.search-text");
        By searchBtn = By.className("search-submit");
        if (searchTerm.equals("randomSearchTerm")) {
            searchTerm = ActionBot.generateRandomString(10);
        }
        ActionBot.writeToElement(searchInput, searchTerm);
        ActionBot.clickOnElement(searchBtn, "searchBtn");
        return validateSearchInURL(searchTerm);
    }

    public String getErrorMsg() {
        By errorMsgDiv = By.cssSelector("div.error-msg");
        String msg = ActionBot.getElementText(errorMsgDiv);
        report.log("Error msg: " + msg);
        return msg;
    }

    /**
     * This function validates that a certain term is found at the current page url
     *
     * @param searchTerm - The term to search
     * @return - True if found in url, false if not
     */
    private boolean validateSearchInURL(String searchTerm) {

        boolean isValid = false;

        String url = driver.getCurrentUrl();
        report.log("Current Url: " + url);
        if (url.contains("s= + " + searchTerm)) {
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

    /**
     * This method select a random match
     *
     * @return - The selected match page
     */
    public GamePage selectRandomItem() {
        By videosOnPage = By.cssSelector(".vidthumb");
        ActionBot.selectRandomItem(videosOnPage);
        return new GamePage(driver);
    }

    /**
     * This method check the views (watched) data
     */
    //TODO: finish this
    public void getViewsData() {
        By viewsLocator = By.cssSelector("span.views i.count");
        List<String> viewsValues = ActionBot.getTextFromElementList(viewsLocator);

        try {
            for (String val : viewsValues) {
                if (val.contains("K")) {
                    val.replace("k", "");
                    val = val.replace("k", "");
                    long num = Long.parseLong(val);
                    System.out.println("num: " + num);
                }
                System.out.println("val: " + val);
            }
        } catch (java.lang.NumberFormatException ex) {
            System.out.println("NumberFormatException");
            report.log(ex.getMessage());
        }
    }

    /**
     * This method clicks on the twitter icon
     *
     * @return - New TwitterPopUpPage
     */
    public TwitterPopUpPage followOnTwitter() {
        ActionBot.clickOnElementInIFrame(By.cssSelector("div#header iframe[src*='twitter']"), By.cssSelector("a#follow-button"), " Twitter button");
        String mainPage = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(mainPage)) {
                driver.switchTo().window(window);
                break;
            }
        }
        report.log("iframe src: " + driver.getCurrentUrl());

        return new TwitterPopUpPage(driver);
    }


}
