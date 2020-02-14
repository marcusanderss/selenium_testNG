package se.andersson.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class DriverFactory {

    private static final Logger LOG = LogManager.getLogger(DriverFactory.class);

    private WebDriver driver;
    private BrowserMobProxy proxy;

    private final ChromeOptions options;

    private static DriverFactory instance = null;

    public static DriverFactory getInstance() {
        if (instance == null) {
            instance = new DriverFactory();
        }

        return instance;
    }

    private DriverFactory() {
        options = new ChromeOptions();
        options.addArguments("incognito");
        options.addArguments("start-maximized");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    }

    public void headlessMode() {
        options.addArguments("headless");
    }

    public void addProxy(final int latency) {
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        proxy.setLatency(latency, TimeUnit.MILLISECONDS);

        long limit = 100000;
        proxy.setReadBandwidthLimit(limit);
        proxy.setWriteBandwidthLimit(limit);

        Proxy seleniumProy = ClientUtil.createSeleniumProxy(proxy);

        options.setCapability(CapabilityType.PROXY, Platform.LINUX);
        options.setProxy(seleniumProy);
    }

    public WebDriver getDriver() {

        if (driver == null) {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    System.setProperty("webdriver.chrome.driver", "drivers" + File.separator + "chromedriver.exe");
                } else {
                    System.setProperty("webdriver.chrome.driver", "drivers" + File.separator + "chromedriver");
                }
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

                System.out.println("Create new Driver " + driver);

            } catch (WebDriverException e) {
                System.out.println("WebDriverException: " + e);
            }
        }

        return driver;
    }

    public void close() {
        System.out.println("Close Driver " + driver);

        if (proxy != null) {
            proxy.stop();
        }

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
