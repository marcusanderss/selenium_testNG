package se.andersson.selenium.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.andersson.selenium.po.MenuPO;

/**
 *
 * @date 1 jan. 2020
 * @author Marcus Andersson
 */
public class MenuTest {

    private MenuPO menu;

    @Test
    public void mainMenu() {
        menu.clickDesktops();
        menu.clickElectronics();
        menu.clickGiftscards();
        menu.clickCellPhones();
        menu.clickJewelry();
        menu.clickNotebooks();
        menu.clickApparelShoes();
        menu.clickAccessories();
        menu.clickCameraPhoto();
        menu.clickDigitalDownloads();
        menu.clickComputers();
        menu.clickBooks();
    }

    @Test
    public void leftMenu() {
        List<String> locations = Arrays.asList("Books", "Computers", "Electronics", "Apparel & Shoes", "Digital downloads", "Jewelry", "Gift Cards");
        for (String location : locations) {
            menu.clickSubMenu(location);
        }

        menu.clickSubMenu("Computers", "Desktops");
        menu.clickSubMenu("Computers", "Notebooks");
        menu.clickSubMenu("Computers", "Accessories");
        menu.clickSubMenu("Electronics", "Camera, photo");
        menu.clickSubMenu("Electronics", "Cell phones");
    }

    @Test
    public void headerLinks() {
        menu.clickRegister();
        menu.clickLogin();
        menu.clickShoppingCart();
        menu.clickWishlist();
    }

    @BeforeMethod
    public void beforeEach() {
        menu = new MenuPO("http://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public void afterEach() {
        menu.cleanUp();
    }
}
