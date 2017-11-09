package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PagesFactory {
    @FindBy(css = "li.item-110")
    private WebElement eventItem;

    public HomePage(WebDriver driver) {
        super(driver);
    }


    public EventPages clickOnEventItemLink() {
        System.out.println("Navigate to main page");
        eventItem.click();
        return new EventPages(driver);
    }

    public boolean isEventItemDisplayed(){

        return eventItem.isDisplayed();
    }


}
