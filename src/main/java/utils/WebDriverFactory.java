package utils;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import infra.pages.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static utils.MainConfig.webDriverType;

public class WebDriverFactory {

    public static WebDriver driver;


    public WebDriverFactory() {
        ReportDispatcher report = ReportManager.getInstance();
    }

    public static WebDriver getDriver(Browsers browser) {

        switch (browser) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/resources/webdrivers/chromedriver");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/resources/webdrivers/geckodriver");
                driver = new FirefoxDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(MainConfig.webDriverImplicitWaitInSeconds, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        ReportDispatcher report = ReportManager.getInstance();
        report.log("Opened new " + webDriverType + " browser window");


        return driver;
    }

}


