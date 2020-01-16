package se.andersson.selenium;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class BasePO {

    private static final Logger LOG = LogManager.getLogger(BasePO.class);

    private static WebDriver driver;
    private final WebDriverWait WAIT;
    private WebElement previous = null;
    private final static boolean DEBUG = true;

    private static final int TIME_TO_WAIT = 10;
    public static final int SLEEP = 1;
    private static final String XPATH_ROOT_START = "//*[contains(text(),'";
    private static final String XPATH_END = "')]";

    public BasePO() {
        WAIT = new WebDriverWait(driver, TIME_TO_WAIT);
    }

    public BasePO(final String location) {
        DriverFactory factory = new DriverFactory();
        driver = factory.getDriver();
        driver.get(location);

        WAIT = new WebDriverWait(driver, TIME_TO_WAIT);
    }
    
    public BasePO(final String location, final int latency) {
        DriverFactory factory = new DriverFactory();
        factory.addProxy(latency);
        driver = factory.getDriver();
        driver.get(location);

        WAIT = new WebDriverWait(driver, TIME_TO_WAIT);
    }

    public static final WebDriver getDriver() {
        return driver;
    }

    private By createLocator(final String text) {
        return By.xpath(XPATH_ROOT_START + text + XPATH_END);
    }

    public final WebElement findElement(final String locator) {
        By by = createLocator(locator);
        return element(by, TIME_TO_WAIT);
    }

    public final WebElement findElement(final String locator, final int seconds) {
        By by = createLocator(locator);
        return element(by, seconds);
    }

    public final WebElement findElement(final By by) {
        return element(by, TIME_TO_WAIT);
    }

    public final WebElement findElement(final By by, final int seconds) {
        return element(by, seconds);
    }

    public final WebElement findParent(final String locator) {
        By by = createLocator(locator);
        return parent(by, TIME_TO_WAIT);
    }

    public final WebElement findParent(final By by) {
        return parent(by, TIME_TO_WAIT);
    }

    public final List<WebElement> findElements(final By by) {
        Instant start = Instant.now();
        LOG.info("Wait for: " + by.toString() + "...");

        WAIT.until(d -> driver.findElements(by).size() > 0);

        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        LOG.info("Found after: " + (elapsed / 1000) + " seconds.");

        return driver.findElements(by);
    }

    public final void type(final By by, final String value) {
        WebElement element = findElement(by);
        element.clear();
        element.sendKeys(value);
    }

    // Because with an element of type number clear field does not work.
    // Therefore a Backspace must be used to delete value in field.
    public void typeNumber(final By by, final String value) {
        WebElement element = findElement(by);
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    public final void click(final By by) {
        findElement(by).click();
    }

    public void javascriptClick(final WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public final void cleanUp() {
        DriverFactory factory = new DriverFactory();
        factory.close();
    }

    public void sleep() {
        sleep(SLEEP);
    }

    public void sleep(long l) {
        if (l == 0) {
            return;
        }

        try {
            Thread.sleep(l * 1000);
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
    }

    public void setAttribute(WebElement element, String key, String value) {
        String attribute = MessageFormat.format("arguments[0].setAttribute(\"{0}\", \"{1};\");", key, value);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(attribute, element);
    }

    public void highLight(WebElement element) {
        if (DEBUG) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            if (previous != null) {

                try {
                    js.executeScript("arguments[0].setAttribute('style','');", previous);
                } catch (StaleElementReferenceException e) {
                }
            }

            js.executeScript("arguments[0].setAttribute('style','border: 2px solid red;');", element);

            previous = element;
        }
    }

    public final boolean elementExists(final By by) {
        return elementExists(by, TIME_TO_WAIT);
    }

    public final boolean elementExists(final By by, final int seconds) {
        return element(by, seconds) != null;
    }
    
    public boolean waitForAttribute(final WebElement element, final String value) {
        return WAIT.until(ExpectedConditions.attributeContains(element, "class", value));
    }

    public boolean waitForTextToBePresent(final String value) {
        By by = createLocator(value);
        return WAIT.until(ExpectedConditions.textToBePresentInElementLocated(by, value));
    }

    public boolean waitForTextToBePresent(final By by, final String value) {
        return WAIT.until(ExpectedConditions.textToBePresentInElementLocated(by, value));
    }

    public void waitForNumberOfElements(final By by, final int length) {
        WAIT.until(ExpectedConditions.numberOfElementsToBe(by, length));
    }

    public void waitForElementToBeClickable(final By by) {
        WAIT.until(ExpectedConditions.elementToBeClickable(by));
    }

    public <T> Boolean waitForInvisibilityOf(final By by) {
        return waitForInvisibilityOf(by, TIME_TO_WAIT);
    }

    public <T> Boolean waitForInvisibilityOf(final By by, final int seconds) {
        WebElement element = findElement(by);
        return new WebDriverWait(driver, seconds).until(ExpectedConditions.invisibilityOf(element));
    }

    @SuppressWarnings("finally")
	private WebElement element(final By by, final int seconds) {
        WebDriverWait w = new WebDriverWait(driver, seconds);

        Instant start = Instant.now();
        String message = "";
        
        WebElement element = null;
        try {
            LOG.info("Wait for: " + by.toString() + "...");
            element = w.until(ExpectedConditions.visibilityOfElementLocated(by));
            message = "Found after: ";
            highLight(element);
        } catch (TimeoutException cause) {
            message = "Did NOT found element! Waited for: ";
            throw new NoSuchElementException("", cause);
        } finally {
            Instant finish = Instant.now();
            long elapsed = Duration.between(start, finish).toMillis();
            LOG.info(message + (elapsed / 1000) + " seconds.");
            return element;
        }
    }

    @SuppressWarnings("finally")
	private final WebElement parent(final By by, final int seconds) {
        WebElement element = findElement(by);
        WebDriverWait w = new WebDriverWait(driver, TIME_TO_WAIT);

        Instant start = Instant.now();
        String message = "";

        WebElement parent = null;
        try {
            LOG.info("Search for parent to: " + by.toString() + "...");
            parent = w.until(ExpectedConditions.visibilityOf(element.findElement(By.xpath(".."))));
            message = "Found after: ";
            highLight(parent);
        } catch (TimeoutException cause) {
            message = "Did NOT found element! Waited for: ";
            throw new NoSuchElementException("", cause);
        } finally {
            Instant finish = Instant.now();
            long elapsed = Duration.between(start, finish).toMillis();
            LOG.info(message + (elapsed / 1000) + " seconds.");
            return parent;
        }
    }

}
