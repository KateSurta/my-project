package design;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import static locator.locatorPages.GREEN_PANEL;
import static locator.locatorPages.START_URL;

/**
     * Created by Tatiana_Sauchanka on 10/11/2017.
     */
    public class BasicTest {
        protected static WebDriver driver;

//    private static WebDriver driver;
// use getDriver() for children

        public static WebDriver getDriver() {

            if (driver == null) {
                setChromeDriver();
//            setUpFireFox();
            }
            return driver;
        }

        public static void setChromeDriver()
        {
//        String exePath = "C:\\Chromedriver\\chromedriver.exe";


            System.setProperty("webdriver.chrome.driver", "d:\\kate\\IBA institude\\lection\\Automation Testing\\Chromedriver\\chromedriver.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setJavascriptEnabled(true);
            driver = new ChromeDriver(capabilities);


        }

        public static void setUpFireFox() {
//        appropriate System Variables have already added

//        String exePath =  "C:\\geckodriver-v0.14.0-win64\\geckodriver.exe";
//        System.setProperty("webdriver.gecko.driver", exePath);

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            driver = new FirefoxDriver(capabilities);
            capabilities.setJavascriptEnabled(true);

        }

        @BeforeClass(description = "Start browser")
        public void setUp() {
            getDriver().get(START_URL);
            WebElement expectedElement = (new WebDriverWait(driver, 5)).
                    until(new ExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver webDriver) {
                            return webDriver.findElement(GREEN_PANEL);

                        }
                    });
        }


        @BeforeClass (dependsOnMethods = "setUp" )
        public void enableJavaScript(){

        }

        @BeforeClass(dependsOnMethods = "setUp", description = "Add implicite wait and maximize window")
        public void addImplicitly() {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            //driver.manage().window().maximize();
        }



//    @\AfterClass
//    public void afterClass() throws Exception {
//        driver.quit();
//    }



    }


