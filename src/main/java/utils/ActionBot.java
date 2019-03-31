package utils;


import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import infra.pages.Browsers;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import tests.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class ActionBot {

    private static ReportDispatcher report = ReportManager.getInstance();
    private static Random random = new Random();
    private static WebDriver driver = BaseTest.getDriver();
    public static Actions action = new Actions(driver);
    private static WebDriverWait wait = new WebDriverWait(driver, 10);

    public ActionBot() {
    }


    public static void navigateToURL(String url) {
        driver.get(url);
        report.log("Navigating to: " + url);
    }

    public static void writeToElement(By elementLocator, String text) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.sendKeys(text);
            report.log("Text entered: " + text);
        } else {
            report.log("Element isn't display");
        }
    }

    public static void clickOnElement(By elementLocator, String elementName) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.click();
            report.log("Element " + elementName + " was clicked");
        } else {
            report.log("Element isn't display");
        }
    }

    public static void clickEnterToSearch(By elementLocator) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.sendKeys(Keys.ENTER);
        } else {
            report.log("Element isn't display");
        }
    }

    public static void clickOnElement(WebElement element, String elementName) {
        moveToElement(element);
        if (element.isDisplayed()) {
            element.click();
            report.log("Element " + elementName + " was clicked");
        } else {
            report.log("Element isn't display");
        }
    }

    public static void waitForElementToBeDisplayed(By elementLocator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
    }

    public static void clickOnIFrameElement(By iFrameLocator, By elementInIframe, String elementName) {

        driver.switchTo().frame(driver.findElement(iFrameLocator));
        WebElement element = getElement(elementInIframe);

        if (element.isDisplayed()) {
            element.click();
            report.log("Element " + elementName + " was clicked");
        } else {
            report.log("Element isn't display");
        }

        driver.switchTo().defaultContent();
    }


    public static boolean isElementDisplayed(By elementLocator) {
        boolean isDisplayed = false;

        try {
            WebElement element = driver.findElement(elementLocator);
            if (element.isDisplayed()) {
                isDisplayed = true;
            } else {
                waitForElementToBeDisplayed(elementLocator);
                isDisplayed = true;
            }

        } catch (org.openqa.selenium.NoSuchElementException ex) {
            waitForElementToBeDisplayed(elementLocator);
        } catch (Exception ex) {
            report.log(ex.getMessage());
        }
        return isDisplayed;
    }

    public static List<WebElement> getAllElements(By elementLocator) {

        List<WebElement> elementsList = null;

        try {
            elementsList = driver.findElements(elementLocator);
        } catch (Exception ex) {
            report.log(ex.getMessage());
        }

        return elementsList;
    }

    public static void moveToElement(By byLocator) {
        WebElement webElement = driver.findElement(byLocator);

        try {
            action.moveToElement(webElement).perform();
            wait.until(ExpectedConditions.visibilityOf(webElement));
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            report.log(ex.getMessage());
        }
    }

    public static void moveToElement(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
        action.moveToElement(webElement).perform();
    }

    public void executeJavaScript(String javaScript, WebElement element) {

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        if (element != null) {
            jsExecutor.executeScript(javaScript, element);
        } else {
            jsExecutor.executeScript(javaScript);
        }
    }

    public static String getElementText(By byLocator) {
        String text = "";
        try {
            if (isElementDisplayed(byLocator)) {
                text = driver.findElement(byLocator).getText();
            }
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            report.log("Couldn't find element element text, returning empty string..");
        }
        return text;
    }

    public static List<String> getTextFromElementList(By listLocator){
        List<WebElement> elements = driver.findElements(listLocator);
        List<String> list = null;
        if (elements.size() > 0){
            list = new ArrayList();
            for (WebElement element: elements){
                list.add(element.getText());
                report.log(element.getText());
            }
        }
        return list;
    }

    public static int selectRandomItem(By listLocator) {
        int index = -1;

        List<WebElement> list = getAllElements(listLocator);
        if (list.size() > 0) {
            index = random.nextInt(list.size());
            clickOnElement(list.get(index), "item # " + (index + 1));
        }
        return index;
    }

    public static String getElementAttribute(By byLocator, String attribute) {

        return driver.findElement(byLocator).getAttribute(attribute);
    }

    public static WebElement getElement(By byLocator) {

        return driver.findElement(byLocator);
    }

    public static void doubleClick(By byLocator) {
        WebElement element = driver.findElement(byLocator);
        action.doubleClick(element).perform();
    }

}