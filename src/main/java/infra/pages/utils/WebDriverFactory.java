package infra.pages.utils;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import infra.pages.Browsers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static infra.pages.utils.MainConfig.webDriverType;

public class WebDriverFactory {

    public static WebDriver driver;


    public WebDriverFactory() {
        ReportDispatcher report = ReportManager.getInstance();
    }

    private static final String OS = System.getProperty("os.name");

    public static WebDriver getDriver(Browsers browser) {

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
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


