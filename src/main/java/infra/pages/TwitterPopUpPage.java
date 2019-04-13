package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

import java.util.Iterator;
import java.util.Set;

public class TwitterPopUpPage extends BasePage {

    private final String PROFILE_NAME = "Ourmatch.net";
    private static String parentWindowHandler;
    private static String subWindowHandler;


    public TwitterPopUpPage(WebDriver driver) {
        super(driver);
        parentWindowHandler = driver.getWindowHandle(); // Store your parent window
        subWindowHandler = null;
    }

    /**
     * This method checks if the twitter popup is shown (ourmatch profile)
     *
     * @return - True is shown, false if not
     */
    public boolean isPopupShown() {
        By mainDiv = By.cssSelector("div#bd");
        boolean isDisplayed = false;

        Set<String> handles = driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
        }
        ActionBot.switchDriverToWindow(subWindowHandler); // switch to popup window

        isDisplayed = ActionBot.isElementDisplayed(mainDiv,true);

        ActionBot.switchDriverToWindow(parentWindowHandler);  // switch back to parent window

        return isDisplayed;
    }

    public boolean isValidProfile() {
        By profileName = By.cssSelector("div.profile.summary h2");
        ActionBot.switchDriverToWindow(subWindowHandler); // switch to popup window
        String profile = ActionBot.getElementText(profileName);
        report.log("Popup profile name: " + profile);
        ActionBot.switchDriverToWindow(parentWindowHandler);  // switch back to parent window
        return profile.equals(PROFILE_NAME);

    }
}
