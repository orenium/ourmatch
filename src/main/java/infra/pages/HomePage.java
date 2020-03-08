package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import infra.pages.utils.ActionBot;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class HomePage extends BasePage {

    public static HashMap<String, String> leaguesAndLinksMap;
    private static final By homeTeam = By.cssSelector("div.match-thumb-info div.match-thumb-teamnameh");
    private static final By awayTeam = By.cssSelector("div.match-thumb-info div.match-thumb-teamnamea");

    private static final By leaguesTitles = By.cssSelector("li.popular-leagues-list ul li.hover-tp a");
    private static final By leaguesLinks = By.cssSelector("li.popular-leagues-list ul li a");
    private static final By searchInput = By.cssSelector("input.search-text");
    private static final By searchBtn = By.className("search-submit");
    private static final By viewsLocator = By.cssSelector("span.views i.count");

    private static By videosOnPage;

    public HomePage(WebDriver driver) {
        super(driver);
        videosOnPage = By.cssSelector(".vidthumb");
    }

    public void setLeaguesMap() {
        leaguesAndLinksMap = new HashMap();

        leaguesAndLinksMap.put(" Premier League", "https://ourmatch.net/videos/england/premier-league-highlights/");
        leaguesAndLinksMap.put(" La Liga", "https://ourmatch.net/videos/spain/la-liga-highlights/");
        leaguesAndLinksMap.put(" Serie A", "https://ourmatch.net/videos/italy/serie-a-highlights/");
        leaguesAndLinksMap.put(" Bundesliga", "https://ourmatch.net/videos/germany/bundesliga-highlights/");
        leaguesAndLinksMap.put(" Ligue 1", "https://ourmatch.net/videos/france/ligue-1-highlights/");
        leaguesAndLinksMap.put(" Champions League", "https://ourmatch.net/videos/europe/uefa-champions-league-highlights/");
        leaguesAndLinksMap.put(" Europa League", "https://ourmatch.net/videos/europe/uefa-europa-league-highlights/");
        report.log("main leagues buttons <--> links hash was set");
    }

    /**
     * This methods check if the the search button is displayed on page as an indication of page has loaded
     * @return - True if found, false if not
     */
    public boolean isElementInPage(){
        if (ActionBot.isElementDisplayed(searchBtn, false)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method validate the main leagues and the match to their countries
     *
     * @param leaguesAndLinks - The leagues <--> country hashmap
     * @return - True is all match, false if not
     */
    public boolean mainLeaguesLinksValidation(HashMap<String, String> leaguesAndLinks) {

        List<WebElement> leaguesTitlesList;
        List<WebElement> leaguesLinksList;

        boolean isValid = false;
        int validLinks = 0;

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

        return isValid;
    }

    /**
     * This methods search in the search field at the ourmatch site
     *
     * @param searchTerm - The term to search
     * @return - True if the search was done (validated by searching the search term at the URL), false if not
     */
    public boolean search(String searchTerm) {
        boolean isSearched;

        if (searchTerm.equals("randomSearchTerm")) {
            searchTerm = ActionBot.generateRandomString(10);
        }
        ActionBot.writeToElement(searchInput, searchTerm);
        ActionBot.clickOnElement(searchBtn, "searchBtn");
        isSearched = true;
        return isSearched;
    }

    /**
     * This method return as error msg
     *
     * @return - The error msg
     */
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
    public boolean validateSearchInURL(String searchTerm) {
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
     * This method checks if a search term is shown at the search results
     *
     * @param searchTerm - The searched team
     * @return - True if found (at least at 3 results), false if not
     */
    public boolean validateSearchByResults(String searchTerm) {
        int validResultsFound = 0;
        boolean isShownInResults = false;
        List<String> homeTeams = ActionBot.getTextFromElementList(homeTeam);
        List<String> awayTeams = ActionBot.getTextFromElementList(awayTeam);
        for (int i = 0; i < homeTeams.size(); i++) {
            if (homeTeams.get(i).equalsIgnoreCase(searchTerm) || awayTeams.get(i).equalsIgnoreCase(searchTerm)) {
                validResultsFound++;
                report.log(validResultsFound + ". Found in result: " + homeTeams.get(i) + " VS " + awayTeams.get(i));
                if (validResultsFound == 3) {
                    isShownInResults = true;
                    break;
                }
            }
        }
        return isShownInResults;
    }

    /**
     * This method selects (clicks) on a specific match by given index
     *
     * @param index - The index number
     * @return - New GamePage
     */
    public GamePage selectMatchByIndex(int index) {
        List<WebElement> matches = ActionBot.getAllElements(videosOnPage);
        if (matches.size() > 0) {
            ActionBot.clickOnElement(matches.get(index), "match #" + index);
        }
        return new GamePage(driver);
    }

    /**
     * This method select a random match
     *
     * @return - The selected match page
     */
    public GamePage selectRandomItem() {
        ActionBot.selectRandomItem(videosOnPage);
        return new GamePage(driver);
    }

    /**
     * This method check the views (watched) data
     *
     * @return - The index of most viewed match (per page)
     */
    public int getViewsData() {

        long formattedValue;
        int mostViews = -1;
        int mostViewsIndex = -1;
        int index = 0;
        List<String> viewsValues = ActionBot.getTextFromElementList(viewsLocator);
        List<String> homeTeams = ActionBot.getTextFromElementList(homeTeam);
        List<String> awayTeams = ActionBot.getTextFromElementList(awayTeam);

        try {
            for (String val : viewsValues) {
                if (val.contains("K")) {
                    String[] splitted = val.split(Pattern.quote("."), 2);
                    splitted[1] = splitted[1].replace("K", "");
                    formattedValue = Long.parseLong(splitted[0]) * 1000 + Long.parseLong(splitted[1]) * 10;
                } else {
                    formattedValue = Long.parseLong(val);
                }
                report.log("Match: " + homeTeams.get(index) + " VS " + awayTeams.get(index) + ": " + formattedValue + " views");
                if (formattedValue > mostViews) {
                    mostViews = (int) formattedValue;
                    mostViewsIndex = index;
                }
                index++;
            }
            report.log("++++++++++++++++++++++++++++++++++++++++");
            report.log("The most recently viewed match: " + homeTeams.get(mostViewsIndex) + " VS " + awayTeams.get(mostViewsIndex) + ": " + viewsValues.get(mostViewsIndex) + " views");
        } catch (java.lang.NumberFormatException ex) {
            System.out.println("NumberFormatException");
            report.log(ex.getMessage());
        }
        return mostViewsIndex;
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
