package se.andersson.selenium.tests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import se.andersson.selenium.po.MenuPO;
import se.andersson.selenium.po.ProductsPO;

/**
 *
 * @date 12 jan. 2020
 * @author Marcus Andersson
 */
public class ProxyTest {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(ProxyTest.class);
    
    String driverPath = "drivers" + File.separator + "chromedriver";
    String sFileName = "logs/SeleniumEasy.har";

    public WebDriver driver;
    public BrowserMobProxy server;

    @BeforeMethod
    public void setUp() {

        // start the proxy
        server = new BrowserMobProxyServer();
        server.start(0);
        server.setLatency(3, TimeUnit.MILLISECONDS);
        int port = server.getPort(); // get the JVM-assigned port
        
        //get the Selenium proxy object - org.openqa.selenium.Proxy;
        Proxy client = ClientUtil.createSeleniumProxy(server);

//        proxy = self.server.create_proxy()
//proxy.limits({'upstream_kbps': 50 ,'downstream_kbps': 50})
        


        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.PROXY, Platform.LINUX);
        options.setProxy(client);
        
        if (System.getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", "drivers" + File.separator + "chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", "drivers" + File.separator + "chromedriver");
        }
        
        driver = new ChromeDriver(options);

        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        server.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        // create a new HAR with the label "seleniumeasy.com"
        // HAR captures the traffic.
        server.newHar("seleniumeasy.com");

        // open seleniumeasy.com
//        driver.get("http://demowebshop.tricentis.com/");


    }

    @Test
    public void testCaseOne() {
//        System.out.println("Navigate to selenium tutorials page");
//        driver.findElement(new ByChained(By.cssSelector("div.header-links"), By.cssSelector("a[href='/register']"))).click();
        
        MenuPO menu = new MenuPO("http://demowebshop.tricentis.com/");
menu.clickApparelShoes();
ProductsPO products = new ProductsPO();

        products.sortBy("Created on");
        products.itemsToDisplay(4);
menu.cleanUp();
    }

    @AfterMethod
    public void tearDown() {

        try {
            // get the HAR data
            Har har = server.getHar();

            // Write HAR Data in a File
            // Denna fil kan visas i en HAR-viewer, exempelvis:http://www.softwareishard.com/har/viewer/
            File harFile = new File(sFileName);

            har.writeTo(harFile);

            if (driver != null) {
                server.stop();
                driver.quit();
            }
        } catch (IOException e) {
            LOG.info(e);
        }
    }
}
