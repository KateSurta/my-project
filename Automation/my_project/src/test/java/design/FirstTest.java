package design;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.EventPages;
import page.HomePage;

import static locator.locatorPages.EVENT_PAGE_URL;

public class FirstTest extends BasicTest{

    @Test
    public void EventItemIsDisplayed() {
        boolean b = new HomePage(driver).isEventItemDisplayed();
        Assert.assertEquals(b, true);
    }

    @Test (dependsOnMethods = {"EventItemIsDisplayed"})
    public void clickOnEventItemLink() {
        new HomePage(driver).clickOnEventItemLink();

    }

    @Test(dependsOnMethods = {"clickOnEventItemLink"})
    public void inspectEventPagesOpened(){
        String registerURL = new EventPages(driver).assertCurrentURL();
        Assert.assertEquals(registerURL,EVENT_PAGE_URL);

    }

    @Test (dependsOnMethods = {"inspectEventPagesOpened"})
    public void assertEventPagesIsDisplayed() {
        boolean b = new EventPages(driver).isEventPositionDisplayed();
        Assert.assertEquals(b, true);
    }






}
