package tests;


import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import infra.pages.HomePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.ActionBot;
import utils.MainConfig;
import utils.WebDriverFactory;

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

        if (driver == null) {
            driver = WebDriverFactory.getDriver(MainConfig.webDriverType);
        }
        report = ReportManager.getInstance();
    }


    // Run after ALL tests classes!
    @AfterClass
    public void afterAllTestsClasses() throws Exception {
        takeScreenShot("Browser state when test ends");

        if (MainConfig.closeBrowserAtClassTestEnd) {
//            driver.close();
        }
    }

    public static HomePage navigateToHomePage(){
        ActionBot.navigateToURL(siteUrl);
        return new HomePage(driver);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * This methods take a screenshot
     * @param description - The description to add to the screenshot
     * @throws Exception
     */
    public static void takeScreenShot(String description) throws Exception{

        if (driver != null){
            File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            report.addImage(screenShotFile,description);
        }
    }


    // Run after ALL tests!
    @AfterSuite
    public void afterAllTests() {

        if (MainConfig.closeBrowserAtSuiteTestEnd) {
            driver.close();
        }
    }


}
