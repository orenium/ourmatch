package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public abstract class AbstractTest {

    protected WebDriver driver;


    // Run before ALL tests!
    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/obroshi/Documents/Automation_course/dominos/src/main/java/utils/webdrivers/chromedriver");
        if (driver == null) {
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
    }


    // Run after ALL tests!
    @AfterClass
    public void afterAllTests() {
//        driver.close();
    }


}
