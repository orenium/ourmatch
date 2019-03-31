package tests;


import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import infra.pages.Browsers;
import infra.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.ActionBot;
import utils.WebDriverFacrory;

import java.util.Random;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public abstract class BaseTest {

    protected ReportDispatcher report;

    protected static WebDriver driver;
    protected static String siteUrl = "https://ourmatch.net/videos/";

    // Run before ALL tests!
    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/java/utils/webdrivers/chromedriver");
        if (driver == null) {
            driver = WebDriverFacrory.getDriver(Browsers.CHROME);
            driver.manage().window().maximize();
        }
        report = ReportManager.getInstance();
    }


    // Run after ALL tests!
    @AfterClass
    public void afterAllTests() {
//        driver.close();
    }

    public static HomePage navigateToHomePage(){
        ActionBot.navigateToURL(siteUrl);
        return new HomePage(driver);
    }

    public static WebDriver getDriver() {
        return driver;
    }


}
