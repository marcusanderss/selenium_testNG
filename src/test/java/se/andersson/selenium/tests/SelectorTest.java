package se.andersson.selenium.tests;

import java.text.MessageFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.andersson.selenium.DriverFactory;

/**
 *
 * @date 6 jan. 2020
 * @author Marcus Andersson
 */
public class SelectorTest {

    private WebElement previous = null;
    private static WebDriver driver;

    @Test
    public void selectCSS() {
        // First Child Element
        findElement(By.cssSelector(".list > .inactive"));

        // Second Child Element
        findElement(By.cssSelector(".list .inactive:nth-child(2)"));

        // Sibling
        findElement(By.cssSelector(".header-logo + .header-links-wrapper"));

        // Equals
        findElement(By.cssSelector("input[id='small-searchterms']"));

        // Starts with
        findElement(By.cssSelector("input[id^='small-searc']"), "blue");

        // Ends with
        findElement(By.cssSelector("input[id*='l-searchterms']"), "green");
    }

    @Test
    public void selectXpath() {
        // More than one Attribute
        findElement(By.xpath("//input[@type='text' and @name='q' and @value='Search store']"));
    }

    private WebElement findElement(final By by) {
        return findElement(by, "red");
    }

    private WebElement findElement(final By by, final String color) {
        WebElement element = driver.findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (previous != null) {

            try {
                js.executeScript("arguments[0].setAttribute('style','');", previous);
            } catch (StaleElementReferenceException e) {
            }
        }
        String attribute = MessageFormat.format("arguments[0].setAttribute(\"style\", \"border: 2px solid {0};\");", color);
        js.executeScript(attribute, element);

        previous = element;
        sleep(1);

        return element;
    }

    private void sleep(long l) {
        try {
            Thread.sleep(l * 1000);
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
    }

    @BeforeMethod
    public static void beforeEach() {
        DriverFactory factory = DriverFactory.getInstance();
        driver = factory.getDriver();
        driver.get("http://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public static void afterEach() {
        driver.close();
    }
}
