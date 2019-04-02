package utils;

import infra.pages.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFacrory {

    public static WebDriver driver;

    public WebDriverFacrory() {
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
        return driver;
    }

}


