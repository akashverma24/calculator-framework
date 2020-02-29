package testrunner;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WebDriver driver;


    @Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    public void openBrowser() {
        System.out.println("Called openBrowser");
        String Browser = System.getProperty("Browser");

        if (Browser.equals("Chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                // reduced dimension so that height and width does not changes
                Dimension d = new Dimension(800,480);
                driver.manage().window().setSize(d);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
                driver.manage().deleteAllCookies();
        } else if (Browser.equals("Firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                Dimension d = new Dimension(800,480);
                driver.manage().window().setSize(d);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
                WebDriverWait wait = new WebDriverWait(driver, 15);
                driver.manage().deleteAllCookies();
            }
        }



    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) {

        if(scenario.isFailed()) {
            try {
                scenario.write("Current Page URL is " + driver.getCurrentUrl());
//            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
                byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }

        }

        closeDriver();

    }

    public void closeDriver() {
        if (driver != null) {
            try {
                driver.close();
                driver.quit();
            } catch (NoSuchMethodError nsme) { // in case quit fails
            } catch (NoSuchSessionException nsse) { // in case close fails
            } catch (SessionNotCreatedException snce) {} // in case close fails
            driver = null;
        }
    }

}
