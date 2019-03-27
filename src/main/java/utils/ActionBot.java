package utils;


import com.esotericsoftware.minlog.Log;
import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import java.util.List;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class ActionBot {

    private WebDriver driver;
    private WebDriverWait wait;
    private static ActionBot instance;
    private ReportDispatcher report = ReportManager.getInstance();

    private ActionBot(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);

    }


    public static ActionBot getInstance(WebDriver driver) {
        if (instance == null){
            instance = new ActionBot(driver);
        }
        return instance;
    }

    public void writeToElement(By elementLocator, String text) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.sendKeys(text);
            report.log("Text entered: " + text);
        } else {
            report.log("element isn't display");
        }
    }

    public void clickOnElement(By elementLocator) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.click();
            report.log("element " + elementLocator.toString() + " was clicked");
        } else {
            report.log("element isn't display");
        }
    }

    public void waitForElementToBeDisplayed(By elementLocator) {
        WebElement element = driver.findElement(elementLocator);
        try {

            wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception ex) {
            report.log(ex.getMessage());
        }


    }

    public boolean isElementDisplayed(By elementLocator) {
        boolean isDisplayed = false;
        WebElement element = driver.findElement(elementLocator);

        try {
            if (element.isDisplayed()) {
                isDisplayed = true;
            } else {
                waitForElementToBeDisplayed(elementLocator);
                isDisplayed = true;
            }
        } catch (Exception ex) {
            Log.error(ex.getMessage());
        }
        return isDisplayed;
    }

    public List<WebElement> getAllElements(By elementLocator) {

        List<WebElement> elementsList = null;

        try {
            elementsList = driver.findElements(elementLocator);
        } catch (Exception ex){
            Log.error(ex.getMessage());
        }

        return elementsList;
    }

}
