package se.andersson.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import se.andersson.selenium.BasePO;
import se.andersson.selenium.utils.Table;

/**
 *
 * @date 10 jan. 2020
 * @author Marcus Andersson
 */
public class ShoppingCartPO extends BasePO {

    private Table table;

    public ShoppingCartPO() {
        if(elementExists(By.cssSelector("table.cart"), 5)){
        this.table = new Table(getDriver(), By.cssSelector("table.cart"));}
    }

    public final void checkPost(final int row) {
        WebElement box = table.getColumn(row + 1, 0);
        box.findElement(By.cssSelector("input[type=checkbox]")).click();
    }

    public final void clickDeletePost() {
        WebElement delete = findElement(By.xpath("//input[@type='submit' and @name='updatecart' and @value='Update shopping cart']"));
        delete.click();
        this.table = new Table(getDriver(), By.cssSelector("table.cart"));
    }

    public final CheckoutPO clickCheckOut() {
        findElement(By.id("termsofservice")).click();
        findElement(By.id("checkout")).click();
        waitForTextToBePresent(By.tagName("h1"), "Checkout");

        return new CheckoutPO();
    }

    public final Table getTable() {
        return table;
    }

    public final int numberOfPosts() {
        return table.getNumberOfRows() - 1;
    }
    
}
