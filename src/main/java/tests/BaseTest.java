package tests;


import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import infra.pages.Browsers;
import infra.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.WebDriverFacrory;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public abstract class BaseTest {

    protected ReportDispatcher report = ReportManager.getInstance();

    protected WebDriver driver;
    protected String siteUrl = "https://ourmatch.net/videos/";

    // Run before ALL tests!
    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/obroshi/Documents/Automation_course/ourmatch/src/main/java/utils/webdrivers/chromedriver");
        if (driver == null) {
            driver = WebDriverFacrory.getDriver(Browsers.ChROME);
        }
        driver.manage().window().maximize();
    }


    // Run after ALL tests!
    @AfterClass
    public void afterAllTests() {
//        driver.close();
    }

    public HomePage navigateToHomePage(){
        driver.get(siteUrl);
        return  new HomePage(driver);
    }


}
