package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

import java.util.Iterator;
import java.util.Set;

public class TwitterPopUpPage extends BasePage {

    private static final String PROFILE_NAME = "Ourmatch.net";
    private static String parentWindowHandler;
    private static String subWindowHandler;
    private static final By mainDiv = By.cssSelector("div#bd");
    private static final By profileName = By.cssSelector("div.profile.summary h2");


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

        boolean isDisplayed;
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

    /**
     * This method validate that the twitter popup account is actually a ourmatch acount
     * @return - True if profile is valid, false if not.
     */
    public boolean isValidProfile() {

        ActionBot.switchDriverToWindow(subWindowHandler); // switch to popup window
        String profile = ActionBot.getElementText(profileName);
        report.log("Popup profile name: " + profile);
        ActionBot.switchDriverToWindow(parentWindowHandler);  // switch back to parent window
        return profile.equals(PROFILE_NAME);

    }
}
