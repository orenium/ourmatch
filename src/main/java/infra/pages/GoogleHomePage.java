package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

public class GoogleHomePage extends BasePage {


    private static final String HOMEPAGE_URL = "https://www.google.com/";
    private final By searchBoxSelector = By.xpath("//*[@id='tsf']/div[2]/div/div[1]/div/div[1]/input");

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    public GoogleSearchResultsPage runGoogleSearch(String searchTerm) {
        ActionBot.navigateToURL(HOMEPAGE_URL);

        ActionBot.clickOnElement(searchBoxSelector, "Search Box");
        ActionBot.writeToElement(searchBoxSelector, searchTerm);
        ActionBot.clickEnterToSearch(searchBoxSelector);

        return new GoogleSearchResultsPage(driver);
    }



}
