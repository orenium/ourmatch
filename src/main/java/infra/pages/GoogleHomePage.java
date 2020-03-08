package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import infra.pages.utils.ActionBot;

public class GoogleHomePage extends BasePage {


    private static final String GOOGLE_HOMEPAGE_URL = "https://www.google.com/";
    private static final By searchBoxSelector = By.cssSelector("input.gLFyf.gsfi");

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * This method navigates to gGoogle's home page
     */
     public void openGoogleHomePage(){
         ActionBot.navigateToURL(GOOGLE_HOMEPAGE_URL);
     }

    /**
     * This method run google search
     *
     * @param searchTerm - The term to search
     * @return - A new GoogleSearchResults Page
     */
    public GoogleSearchResultsPage runGoogleSearch(String searchTerm) {

        ActionBot.clickOnElement(searchBoxSelector, "Search Box");
        ActionBot.writeToElement(searchBoxSelector, searchTerm);
        ActionBot.clickEnterToSearch(searchBoxSelector);

        return new GoogleSearchResultsPage(driver);
    }


}
