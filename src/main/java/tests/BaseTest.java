package tests;


import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import infra.pages.HomePage;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.ActionBot;
import utils.MainConfig;
import utils.WebDriverFacrory;

import java.io.File;
import java.io.IOException;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public abstract class BaseTest {

    protected static ReportDispatcher report;

    protected static WebDriver driver;
    protected static String siteUrl = "https://ourmatch.net/videos/";

    // Run before ALL tests!
    @BeforeClass
    public void setup() throws IOException {
        MainConfig.initFromFile("src/main/resources/config/MainConfig.properties");

        System.setProperty("webdriver.chrome.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/java/utils/webdrivers/chromedriver");
        if (driver == null) {
            driver = WebDriverFacrory.getDriver(MainConfig.webDriverType);
            driver.manage().window().maximize();
        }
        report = ReportManager.getInstance();
    }


    // Run after ALL tests!
    @AfterClass
    public void afterAllTests() throws Exception {
        takeScreenShot("Browser state when test ends");

        if (MainConfig.closeBrowserAtTestEnd) {
            driver.close();
        }
    }

    public static HomePage navigateToHomePage(){
        ActionBot.navigateToURL(siteUrl);
        return new HomePage(driver);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void takeScreenShot(String description) throws Exception{

        if (driver != null){
            File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            report.addImage(screenShotFile,description);
        }
    }


}
