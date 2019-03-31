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



    public HomePage clickFirstLink() {

        List<WebElement> links = ActionBot.getAllElements(resultsLinks);
        if (links.size() > 0) {
            ActionBot.clickOnElement(links.get(0), "first link");
        }
        return new HomePage(driver);
    }


    public String getFirstTitle() {

        By firstResTitle = By.cssSelector("div[id=res] a h3");
        String title = ActionBot.getElementText(firstResTitle);
        report.log("First title: " + title);
        return title;
    }

    public String getFirstLink() {

        String link = ActionBot.getAllElements(resultsLinks).get(0).getText();
        report.log("First link: " + link);
        return link;
    }


}
