package se.andersson.selenium.po;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;

import se.andersson.selenium.BasePO;
import se.andersson.selenium.utils.StringUtil;

/**
 *
 * @date 10 jan. 2020
 * @author Marcus Andersson
 */
public class MenuPO extends BasePO {

    private static final By TOP_MENU = By.cssSelector("ul.top-menu");
    private static final By SUB_MENU = By.cssSelector("div.block.block-category-navigation");
    private static final By HEADER_MENU = By.cssSelector("div.header-links");

    public MenuPO(final String location) {
        super(location);
    }
    
    public MenuPO(final String location, final int latency) {
        super(location, latency);
    }

    public void clickBooks() {
        click(new ByChained(TOP_MENU, By.linkText("BOOKS")));
        waitForTextToBePresent(By.tagName("h1"), "Books");
    }

    public void clickComputers() {
        click(new ByChained(TOP_MENU, By.linkText("COMPUTERS")));
        waitForTextToBePresent(By.tagName("h1"), "Computers");
    }

    public void clickElectronics() {
        click(new ByChained(TOP_MENU, By.linkText("ELECTRONICS")));
        waitForTextToBePresent(By.tagName("h1"), "Electronics");
    }

    public void clickApparelShoes() {
        click(new ByChained(TOP_MENU, By.linkText("APPAREL & SHOES")));
        waitForTextToBePresent(By.tagName("h1"), "Apparel & Shoes");
    }

    public void clickDigitalDownloads() {
        click(new ByChained(TOP_MENU, By.linkText("DIGITAL DOWNLOADS")));
        waitForTextToBePresent(By.tagName("h1"), "Digital downloads");
    }

    public void clickJewelry() {
        click(new ByChained(TOP_MENU, By.linkText("JEWELRY")));
        waitForTextToBePresent(By.tagName("h1"), "Jewelry");
    }

    public void clickGiftscards() {
        click(new ByChained(TOP_MENU, By.linkText("GIFT CARDS")));
        waitForTextToBePresent(By.tagName("h1"), "Gift Cards");
    }

    public void clickDesktops() {
        mouseover(TOP_MENU, By.linkText("COMPUTERS"));

        click(new ByChained(TOP_MENU, By.linkText("Desktops")));
        waitForTextToBePresent(By.tagName("h1"), "Desktops");
    }

    public void clickNotebooks() {
        mouseover(TOP_MENU, By.linkText("COMPUTERS"));

        click(new ByChained(TOP_MENU, By.linkText("Notebooks")));
        waitForTextToBePresent(By.tagName("h1"), "Notebooks");
    }

    public final void clickAccessories() {
        mouseover(TOP_MENU, By.linkText("COMPUTERS"));

        click(new ByChained(TOP_MENU, By.linkText("Accessories")));
        waitForTextToBePresent(By.tagName("h1"), "Accessories");
    }

    public final void clickCameraPhoto() {
        mouseover(TOP_MENU, By.linkText("ELECTRONICS"));

        click(new ByChained(TOP_MENU, By.linkText("Camera, photo")));
        waitForTextToBePresent(By.tagName("h1"), "Camera, photo");
    }

    public final void clickCellPhones() {
        mouseover(TOP_MENU, By.linkText("ELECTRONICS"));

        click(new ByChained(TOP_MENU, By.linkText("Cell phones")));
        waitForTextToBePresent(By.tagName("h1"), "Cell phones");
    }

    public final RegisterPO clickRegister() {
        click(new ByChained(HEADER_MENU, By.cssSelector("a[href='/register']")));
        waitForTextToBePresent(By.tagName("h1"), "Register");

        return new RegisterPO();
    }

    public final LoginPO clickLogin() {
        click(new ByChained(HEADER_MENU, By.cssSelector("a[href='/login']")));
        waitForTextToBePresent(By.tagName("h1"), "Welcome, Please Sign In!");

        return new LoginPO();
    }

    public final void clickLogout() {
        click(new ByChained(HEADER_MENU, By.cssSelector("a[href='/logout']")));
    }

    public final ShoppingCartPO clickShoppingCart() {
        click(new ByChained(HEADER_MENU, By.cssSelector("a[href='/cart']")));
        waitForTextToBePresent(By.tagName("h1"), "Shopping cart");

        return new ShoppingCartPO();
    }

    public final void clickWishlist() {
        click(new ByChained(HEADER_MENU, By.cssSelector("a[href='/wishlist']")));
        waitForTextToBePresent(By.tagName("h1"), "Wishlist");
    }

    public final void clickSubMenu(final String menu) {
        List<WebElement> elements = findElements(new ByChained(SUB_MENU, By.tagName("a")));
        for (WebElement element : elements) {
            if (menu.equals(element.getText())) {
                element.click();
                waitForTextToBePresent(By.tagName("h1"), menu);
                return;
            }
        }
    }

    public final void clickSubMenu(final String menu, final String sub) {
        clickSubMenu(menu);
        List<WebElement> elements = findElements(new ByChained(SUB_MENU, By.cssSelector("ul.sublist"), By.tagName("a")));
        for (WebElement element : elements) {
            if (sub.equals(element.getText())) {
                element.click();
                waitForTextToBePresent(By.tagName("h1"), sub);
                return;
            }
        }
    }

    public final int getNumberOfBoughtItems() {
        String bought = findElement(By.id("topcartlink")).getText();
        return StringUtil.parseInteger(bought);
    }

    private void mouseover(final By... by) {
        By chained = new ByChained(by);
        WebElement element = findElement(chained);
        Actions builder = new Actions(getDriver());
        builder.moveToElement(element).perform();
    }

}
