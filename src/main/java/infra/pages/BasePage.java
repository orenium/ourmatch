package infra.pages;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import infra.pages.utils.ActionBot;

import static infra.pages.utils.ActionBot.*;


@Listeners(il.co.topq.difido.ReportManagerHook.class)
public abstract class BasePage {

    protected WebDriver driver;
    protected static ReportDispatcher report;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        report = ReportManager.getInstance();
    }


    /**
     * This method closes the "Accept cookies popup
     *
     * @return - True if close, false if not
     */
    public boolean closeAcceptCookiesDialog() throws InterruptedException {
        By container = By.cssSelector("div.cc-window.cc-banner.cc-type-info.cc-theme-edgeless.cc-bottom.cc-color-override-1861914146");
        By acceptAndCloseBtn = By.cssSelector("div.cc-window.cc-banner.cc-type-info.cc-theme-edgeless.cc-bottom.cc-color-override-1861914146 div.cc-compliance a");

        boolean isClosed = false;

        try {
            if (isElementDisplayed(container,true)) {
                clickOnElement(acceptAndCloseBtn, "accept and close button");
            } else {
                waitForElementToBeDisplayed(container);
                clickOnElement(acceptAndCloseBtn, "accept and close button");
            }
        } catch (Exception ex) {
            report.log("Unable to find closeAcceptCookiesDialog: " + ex.getMessage());
        }

        Thread.sleep(2000);
        if (!isElementDisplayed(container,true)) {
            isClosed = true;
        }
        return isClosed;
    }


    /**
     * This method clicks on the GoalOfTheMonthLink
     *
     * @return - A new GoalOfTheMonthPage
     */
    public GoalOfTheMonthPage openGoalOfTheMonthLink() {
        By goalOfTheMonthBtn = By.cssSelector("div[id=main-navigation] li a[href='http://ourmatch.net/goal-of-the-month/']");
        clickOnElement(goalOfTheMonthBtn, "Goal of the month link");
        return new GoalOfTheMonthPage(driver);
    }


}







