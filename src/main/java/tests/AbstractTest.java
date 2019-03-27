package tests;

import infra.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;


public abstract class AbstractTest {

    protected WebDriver driver;

    protected String siteUrl = "https://ourmatch.net/videos/";


    // Run before ALL tests!
    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/java/utils/webdrivers/chromedriver");
        if (driver == null) {
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
    }


    // Run after ALL tests!
    @AfterClass
    public void afterAllTests() {
        driver.close();
    }

    public HomePage navigateToHomePage(){
        driver.get(siteUrl);
        return  new HomePage(driver);
    }


}
