package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

public class TwitterPopUpPage extends BasePage {

    private final String PROFILE_NAME = "Ourmatch.net";


    public TwitterPopUpPage(WebDriver driver) {
        super(driver);
    }

    /**
     * This method checks if the twitter popup is shown (ourmatch profile)
     * @return - True is shown, false if not
     */
    public boolean isPopupShown() {
        By mainDiv = By.cssSelector("div#bd");
        return ActionBot.isElementDisplayed(mainDiv);
    }

    public boolean isValidProfile(){
        By profileName = By.cssSelector("div.profile.summary h2");

        String profile = ActionBot.getElementText(profileName);
        report.log("Popup profile name: "+ profile);

        return profile.equals(PROFILE_NAME);

    }
}
