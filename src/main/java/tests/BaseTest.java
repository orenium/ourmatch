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
import infra.pages.utils.MainConfig;
import infra.pages.utils.WebDriverFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import static infra.pages.utils.ActionBot.navigateToURL;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public abstract class BaseTest {

    protected static ReportDispatcher report;

    protected static WebDriver driver;
    private static final String siteUrl = "https://ourmatch.net/videos/";
    private static final String REPORTING_FILE_PATH = "/Documents/Automation_course/ourmatch/test-output/difido/current/index.html";

    public static WebDriver getDriver() {
        return driver;
    }

    // Run before ALL tests!
    @BeforeClass
    public void setup() throws IOException {
        MainConfig.initFromFile("src/main/resources/ourmatch.properties");

        if (driver == null) {
            driver = WebDriverFactory.getDriver(MainConfig.webDriverType);
        }
        report = ReportManager.getInstance();

        System.getProperty("os.name");
    }

    public static HomePage navigateToHomePage() {
        navigateToURL(siteUrl);
        return new HomePage(driver);
    }



    /**
     * This methods take a screenshot
     *
     * @param description - The description to add to the screenshot
     * @throws Exception
     */
    public static void takeScreenShot(String description) throws Exception {

        if (driver != null) {
            File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            report.addImage(screenShotFile, description);
        }
    }

    // Run after ALL tests classes!
    @AfterClass
    public void afterAllTestsClasses() throws Exception {
        takeScreenShot("Browser state when test ends");
    }


    // Run after All tests!
    @AfterSuite
    public void afterAllTests() {
        openReportHtmlFile();
//        if (driver != null) {
//            if (MainConfig.closeBrowserAtTestsEnd) {
//                int openTabs = driver.getWindowHandles().size();
//                if (openTabs > 1) {
//                    for (String handle : driver.getWindowHandles()) {
//                        driver.switchTo().window(handle);
//                        report.log("Closing browser");
//                        driver.close();
//                    }
//                } else if (openTabs == 1) {
//                    report.log("Closing browser");
//                    driver.close();
//                } else {
//                    report.log("All tabs are already closed");
//                }
//            }
//        }
    }

    private static void openReportHtmlFile() {

        try {
            String machineName = InetAddress.getLocalHost().getHostName();
            String currentUser = machineName.substring(0, machineName.indexOf("-"));
            File htmlFile = new File("/Users/" + currentUser + REPORTING_FILE_PATH);

            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (Exception ex) {
            report.log("BaseTest.openReportHtmlFile: " + ex.getCause());
        }
    }
}



