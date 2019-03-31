package infra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ActionBot;

public class GoalOfTheMonthPage extends BasePage {

    By goalOptions = By.cssSelector("span.css-answer-input.pds-answer-input input");
    By goallabels = By.cssSelector("span.css-answer-span.pds-answer-span");


    public GoalOfTheMonthPage(WebDriver driver) {
        super(driver);
    }


    public int vote() {

        int selected = -1;
        ActionBot.moveToElement(By.cssSelector("#PDI_form10257564"));
        selected = ActionBot.selectRandomItem(goalOptions);
        report.log(ActionBot.getTextFromElementList(goallabels).get(selected) + " was selected");
        return selected;
    }
}
