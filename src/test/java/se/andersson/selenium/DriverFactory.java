package se.andersson.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class DriverFactory {

    private WebDriver driver;
    private BrowserMobProxy proxy;

    private final ChromeOptions options;

    public DriverFactory() {
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
        proxy.setReadBandwidthLimit(1000000);
        proxy.setWriteBandwidthLimit(1000000);

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
