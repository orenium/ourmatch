package infra.pages;

import com.esotericsoftware.minlog.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleSearchResultsPage extends BasePage {

    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
    }
    private By resultsLinks = By.cssSelector(".iUh30 span");


    public HomePage clickFirstLink() {

        try {
            driver.findElements(resultsLinks).get(0).click();

        } catch (Exception ex){
            Log.error(ex.getMessage());
        }

        return new HomePage(driver);

    }


    public String getFirstTitle() {

        By firstResTitle = By.cssSelector("div[id=res] a h3");

        String title = driver.findElement(firstResTitle).getText();

        Log.info("First title: " + title);

        return title;
    }

    public String getFirstLink() {

        String link = driver.findElements(resultsLinks).get(0).getText();

        Log.info("First link: " + link);

        return link;
    }


}
