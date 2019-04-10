package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ActionBot;

import java.util.List;

public class GoogleSearchResultsPage extends BasePage {

    private By resultsLinks = By.cssSelector(".iUh30 span");



    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
    }


    /**
     * This method clicks on the first result link
     *
     * @return - A ourmatch new Homepage
     */
    public HomePage clickFirstLink() {

        List<WebElement> links = ActionBot.getAllElements(resultsLinks);
        if (links.size() > 0) {
            ActionBot.clickOnElement(links.get(0), "first link");
        }
        return new HomePage(driver);
    }

    /**
     * Thie method get the first title from a google search result
     *
     * @return - The title
     */
    public String getFirstTitle() {

        By firstResTitle = By.cssSelector("div[id=res] a h3");
        String title = ActionBot.getElementText(firstResTitle);
        report.log("First title: " + title);
        return title;
    }

    /**
     * Thie method get the first link from a google search result
     *
     * @return - The link (url)
     */
    public String getFirstLink() {

        String link = ActionBot.getAllElements(resultsLinks).get(0).getText();
        report.log("First link: " + link);
        return link;
    }


}
