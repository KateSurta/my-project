package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class EventPages  extends PagesFactory{
    @FindBy(css = "div.position-2c.breadcrumb")
    private WebElement eventPosition;


    public EventPages(WebDriver driver) {
        super(driver);
    }

    public boolean isEventPositionDisplayed(){
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        return eventPosition.isDisplayed();
    }


}
