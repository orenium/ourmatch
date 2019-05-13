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

    private static final String OS = System.getProperty("os.name");

    public static WebDriver getDriver(Browsers browser) {

        switch (browser) {
            case CHROME:
                if (OS.equals("Mac OS X")) {
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/webdrivers/macOS/chromedriver");
                } else if (OS.equals("Windows 10")) {
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/webdrivers/winOS/chromedriver.exe");
                }
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                if (OS.equals("Mac OS X")) {
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/webdrivers/macOS/geckodriver");
                } else if (OS.equals("Windows 10")) {
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/webdrivers/winOS/geckodriver.exe");
                }
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


