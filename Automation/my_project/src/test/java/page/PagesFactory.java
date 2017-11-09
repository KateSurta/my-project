package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PagesFactory {
    protected WebDriver driver;

    protected PagesFactory (WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public String assertCurrentURL(){
        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
    }
}
