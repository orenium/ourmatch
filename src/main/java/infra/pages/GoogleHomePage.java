package infra.pages;

import com.esotericsoftware.minlog.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleHomePage extends BasePage {


    private static final String HOMEPAGE_URL = "https://www.google.com/";
    private final By searchBoxSelector = By.xpath("//*[@id='tsf']/div[2]/div/div[1]/div/div[1]/input");

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    public GoogleSearchResultsPage runGoogleSearch(String searchTerm) {
        driver.get(HOMEPAGE_URL);
        try {
            WebElement searchBox = driver.findElement(searchBoxSelector);
            searchBox.sendKeys(searchTerm);
            searchBox.sendKeys(Keys.ENTER);

        } catch (Exception ex){
            report.log(ex.getMessage());
        }

        return new GoogleSearchResultsPage(driver);
    }



}
