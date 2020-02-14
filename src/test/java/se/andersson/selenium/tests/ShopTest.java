package se.andersson.selenium.tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.andersson.selenium.po.CheckoutPO;
import se.andersson.selenium.po.LoginPO;
import se.andersson.selenium.po.MenuPO;
import se.andersson.selenium.po.ProductsPO;
import se.andersson.selenium.po.ShoppingCartPO;

/**
 *
 * @date 2 jan. 2020
 * @author Marcus Andersson
 */
public class ShopTest {

    private static final Logger LOG = LogManager.getLogger(ShopTest.class);
    private MenuPO menu;
    private Instant timerStart;

    @Test
    public void buyStuff() {

        assertTrue(menu.getNumberOfBoughtItems() == 0);

        menu.clickApparelShoes();

        ProductsPO products = new ProductsPO();

        products.sortBy("Created on");
        products.itemsToDisplay(4);
        products.itemsToDisplay(8);
        products.itemsToDisplay(12);
        products.itemsToDisplay(4);
        products.clickNextPage();
        products.clickItem(1);
        products.addToCart();

        assertTrue(menu.getNumberOfBoughtItems() == 1);

        menu.clickApparelShoes();
        products.clickItem(5);
        products.addToCart();

        assertTrue(menu.getNumberOfBoughtItems() == 2);

        menu.clickNotebooks();
        products.clickItem(0);
        products.addToCart();

        assertTrue(menu.getNumberOfBoughtItems() == 3);

        menu.clickSubMenu("Electronics", "Cell phones");
        products.clickItem(2);
        products.addToCart();

        assertTrue(menu.getNumberOfBoughtItems() == 4);

        menu.clickDigitalDownloads();
        products.clickItem(1);
        products.addToCart();

        assertTrue(menu.getNumberOfBoughtItems() == 5);

        ShoppingCartPO shoppingCart = menu.clickShoppingCart();
        assertTrue(shoppingCart.numberOfPosts() == 5);

        shoppingCart.checkPost(1);
        shoppingCart.checkPost(3);
        shoppingCart.clickDeletePost();
        assertTrue(shoppingCart.numberOfPosts() == 3);
        assertTrue(menu.getNumberOfBoughtItems() == 3);

        menu.clickLogin();
        LoginPO login = new LoginPO();
        login.typeMail("konradJ@mail.com");
        login.typePassword("python");
        login.clickLogin();

        menu.clickShoppingCart();
        CheckoutPO checkout = shoppingCart.clickCheckOut();
        checkout.checkOut();

        menu.clickLogout();
    }

    @BeforeMethod
    public void beforeEach() {
        timerStart = Instant.now();
        menu = new MenuPO("http://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public void afterEach() {
        menu.cleanUp();
        
        Instant timerStop = Instant.now();
        long elapsedTime = Duration.between(timerStart, timerStop).toMillis();
        LOG.info("Test did run for " + (elapsedTime / 1000) + " seconds.");
    }
}
