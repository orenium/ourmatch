package utils;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import tests.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class ActionBot {

    private static ReportDispatcher report = ReportManager.getInstance();
    private static Random random = new Random();
    private static WebDriver driver = BaseTest.getDriver();
    public static Actions action = new Actions(driver);
    private static WebDriverWait wait = new WebDriverWait(driver, MainConfig.webDriverImplicitWaitInSeconds);

    public ActionBot() {
    }


    /**
     * This method receive a url and open a broswer with that given url
     *
     * @param url - page to open
     */
    public static void navigateToURL(String url) {
        driver.get(url);
        report.log("Navigating to: " + url);
    }


    /**
     * This method inserts a text to a textual webelement
     *
     * @param elementLocator - The locator for that webelement
     * @param text           - The text to insert
     */
    public static void writeToElement(By elementLocator, String text) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.sendKeys(text);
            report.log("Text entered: " + text);
        } else {
            report.log("Element isn't display");
        }
    }


    /**
     * This method click on a webelement
     *
     * @param elementLocator - By locator for the webelement to click on
     * @param elementName    - Element's name (for logging)
     */
    public static void clickOnElement(By elementLocator, String elementName) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.click();
            report.log("Element " + elementName + " was clicked");
        } else {
            report.log("Element isn't display");
        }
    }

    /**
     * This method click on a webelement
     *
     * @param element     - webelement to click on
     * @param elementName - Element's name (for logging)
     */
    public static void clickOnElement(WebElement element, String elementName) {
        moveToElement(element);
        if (element.isDisplayed()) {
            element.click();
            report.log("Element " + elementName + " was clicked");
        } else {
            report.log("Element isn't display");
        }
    }

    /**
     * This method clicks on ENTER for start searching after validating some element is visible
     *
     * @param elementLocator - The element to verify with
     */
    public static void clickEnterToSearch(By elementLocator) {
        WebElement element = driver.findElement(elementLocator);

        if (element.isDisplayed()) {
            element.sendKeys(Keys.ENTER);
        } else {
            report.log("Element isn't display");
        }
    }


    /**
     * This method wait for an element to be display
     *
     * @param elementLocator - The element By locator
     */
    public static void waitForElementToBeDisplayed(By elementLocator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
    }

    /**
     * This method checks if a certain element is displayed on not
     *
     * @param elementLocator - The element to check
     * @param waitForElement - True if waiting can help, false for quicker reply
     * @return - True if displayed, false if not
     */
    public static boolean isElementDisplayed(By elementLocator, boolean waitForElement) {
        boolean isDisplayed = false;

        try {
            if (driver.findElement(elementLocator).isDisplayed()) {
                isDisplayed = true;
            }
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            if (waitForElement) {
                waitForElementToBeDisplayed(elementLocator);
            }
        }
        return isDisplayed;
    }

    /**
     * This method checks if a certain element is located inside a givem iframe
     *
     * @param iFrameLocator  - The iframe By locator
     * @param elementLocator - The element inside the iframe By locator
     * @return - True if found, false if not
     */
    public static boolean isElementDisplayedInIframe(By iFrameLocator, By elementLocator) {
        boolean isDisplayed;
        switchToIFrameDriver(iFrameLocator);
        isDisplayed = isElementDisplayed(elementLocator, true);
        switchToDefaultContent();
        return isDisplayed;
    }

    /**
     * This method return a list of webelements
     *
     * @param elementLocator - The elements By locator
     * @return - A list of webelements
     */
    public static List<WebElement> getAllElements(By elementLocator) {

        List<WebElement> elementsList = null;

        try {
            elementsList = driver.findElements(elementLocator);
        } catch (Exception ex) {
            report.log(ex.getMessage());
        }

        return elementsList;
    }


    /**
     * This method moves to a given webelement
     *
     * @param byLocator - The element's By locator
     */
    public static void moveToElement(By byLocator) {
        WebElement webElement = driver.findElement(byLocator);
        try {
            action.moveToElement(webElement).perform();
            wait.until(ExpectedConditions.visibilityOf(webElement));
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            report.log(ex.getMessage());
        }
    }

    /**
     * This method moves to a given webelement
     *
     * @param webElement - The element's to move to
     */
    public static void moveToElement(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
        action.moveToElement(webElement).perform();
    }

    /**
     * This method runs a JavaScript script
     *
     * @param javaScript - The JS command to run
     */
    public static void executeJavaScript(String javaScript) {

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(javaScript);
    }


    /**
     * This method gets the child element and returns the parent element
     *
     * @param childLocator - The By child's element locator
     * @return - The parent (as webelement)
     */
    public static WebElement getParentElement(By childLocator) {
        WebElement childElement = driver.findElement(childLocator);
        WebElement parent = childElement.findElement(By.xpath(".."));
        return parent;
    }


    /**
     * This method get the text from a webelement
     *
     * @param byLocator - The webelement By locator
     * @return - The element's inner text
     */
    public static String getElementText(By byLocator) {
        String text = "";
        try {
            if (isElementDisplayed(byLocator,true)) {
                text = driver.findElement(byLocator).getText();
            }
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            report.log("Couldn't find element element text, returning empty string..");
        }
        return text;
    }

    /**
     * This method get the text from a webelement
     *
     * @param element - The webelement
     * @return - The element's inner text
     */
    public static String getElementText(WebElement element) {
        String text = "";
        try {
            if (element.isDisplayed()) {
                text = element.getText();
            }
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            report.log("Couldn't find element element text, returning empty string..");
        }
        return text;
    }

    public static String getElementInIframeText(By iFrameLocator, By elementLocator) {
        switchToIFrameDriver(iFrameLocator);
        String text = "";
        try {
            if (isElementDisplayedInIframe(iFrameLocator, elementLocator)) {
                WebElement element = driver.findElement(elementLocator);
                text = element.getText();
            }
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            report.log("Couldn't find element element text, returning empty string..");
        }
        switchToDefaultContent();
        return text;
    }


    /**
     * This method returns a list of strings from a list of webelements
     *
     * @param listLocator - The element's list By locator
     * @return - List of strings from the given webelements list
     */
    public static List<String> getTextFromElementList(By listLocator) {
        List<WebElement> elements = driver.findElements(listLocator);
        List<String> list = null;
        if (elements.size() > 0) {
            list = new ArrayList();
            for (WebElement element : elements) {
                list.add(element.getText());
//                report.log(element.getText());
            }
        }
        return list;
    }

    /**
     * This method returns a random number from a range
     *
     * @param listLocator - The list of elements By locator (the list's size is the range)
     * @return - a random number (by list size)
     */
    public static int selectRandomItem(By listLocator) {
        int index = -1;

        List<WebElement> list = getAllElements(listLocator);
        if (list.size() > 0) {
            index = random.nextInt(list.size());
            clickOnElement(list.get(index), "item # " + (index + 1));
        }
        return index;
    }

    //Switch focus to new tab
    public static void switchToNewTab() {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        report.log("Switching to the new tab");
    }


    /**
     * This method get an attribute value from a webelement
     *
     * @param byLocator - The element's By locator
     * @param attribute - The requested attribute's name
     * @return - The attribute's value
     */
    public static String getElementAttribute(By byLocator, String attribute) {

        return driver.findElement(byLocator).getAttribute(attribute);
    }

    /**
     * This method search for a webelement
     *
     * @param byLocator - The element's By locator
     * @return - Webelement
     */
    public static WebElement getElement(By byLocator) {

        return driver.findElement(byLocator);
    }

    /**
     * This method double clicks on a webelement
     *
     * @param byLocator - The element's By locator
     */
    public static void doubleClick(By byLocator) {
        WebElement element = driver.findElement(byLocator);
        action.doubleClick(element).perform();
    }

    /**
     * This method switch the webdriver to iframe so we can find and do things on element inside
     *
     * @param iFrameLocator - The iframe's By locator
     */
    public static void switchToIFrameDriver(By iFrameLocator) {
        WebElement iFrame = driver.findElement(iFrameLocator);
        driver.switchTo().frame(iFrame);
    }

    /**
     * This methods 'release the webdriver from the iframe boundaries and changed back to the default DOM driver
     */
    public static void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    /**
     * This method moves to an element in an iframe
     *
     * @param iFrameLocator   - The iframe tag to move to
     * @param elementInIFrame - The element to move inside the iframe
     */
    public static void moveToElementInIFrame(By iFrameLocator, By elementInIFrame) {
        switchToIFrameDriver(iFrameLocator);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementInIFrame)));
        action.moveToElement(driver.findElement(elementInIFrame)).perform();
        switchToDefaultContent();
    }

    /**
     * This method click on a webelemnt which wrapped in an iframe tag
     *
     * @param iFrameLocator   - The iframe tag to search in
     * @param elementInIFrame - The element to click inside the iframe
     * @param elementName     - Element's name (for logging)
     */
    public static void clickOnElementInIFrame(By iFrameLocator, By elementInIFrame, String elementName) {
        switchToIFrameDriver(iFrameLocator);
//        WebElement element = driver.findElement(elementInIFrame);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(elementInIFrame)).click();
        wait.until(ExpectedConditions.elementToBeClickable(elementInIFrame)).click();
        report.log("element " + elementName + " (inside iframe) was clicked");
        switchToDefaultContent();
    }

    /**
     * This method click on a webelemnt which wrapped in an iframe tag
     *
     * @param waitInSeconds   - The new webdriverWait requested in seconds
     * @param iFrameLocator   - The iframe tag to search in
     * @param elementInIFrame - The element to click inside the iframe
     * @param elementName     - Element's name (for logging)
     */
    public static void clickOnElementInIFrame(int waitInSeconds, By iFrameLocator, By elementInIFrame, String elementName) {
        switchToIFrameDriver(iFrameLocator);
        WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementInIFrame)).click();
        report.log("element " + elementName + " (inside iframe) was clicked");
        switchToDefaultContent();
    }


    /**
     * This method get the text from an element located in an iframe element
     *
     * @param iFrameLocator   - The iframe By locator
     * @param elementInIFrame - The element's By locator
     * @return - The element's text
     */
    public static String getTextFromElementInIFrame(By iFrameLocator, By elementInIFrame) {
        switchToIFrameDriver(iFrameLocator);
        String text = wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementInIFrame))).getText();
        switchToDefaultContent();
        return text;
    }

    /**
     * This method return an index as a number
     *
     * @param listSize - The list (for the random limits)
     * @return - A random number
     */
    public static int getRandomIndex(int listSize) {
        return random.nextInt(listSize);
    }


    public static String generateRandomString(int stringLength) {
        String generatedString = "";
        Random random = new Random();
        while (generatedString.length() < stringLength) {
            generatedString += (char) (97 + random.nextInt(25));
        }
        return generatedString;
    }

    public static void switchDriverToWindow(String window) {
        driver.switchTo().window(window);
    }

}