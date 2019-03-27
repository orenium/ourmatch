package utils;

import infra.pages.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFacrory {

    public static WebDriver driver;

    public WebDriverFacrory() {
    }

    public static WebDriver getDriver(Browsers browser) {

        switch (browser) {
            case ChROME:
                System.setProperty("webdriver.chrome.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/java/utils/webdrivers/chromedriver");
                driver = new ChromeDriver();
                break;
            case FIREFOXX:
                System.setProperty("webdriver.gecko.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/java/utils/webdrivers/geckodriver");
                driver = new FirefoxDriver();
                break;
            case SAFARI:
                driver = new SafariDriver();
                break;

        }
        return driver;
    }

}


