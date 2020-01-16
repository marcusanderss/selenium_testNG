package se.andersson.selenium.po;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import se.andersson.selenium.BasePO;

/**
 *
 * @date 10 jan. 2020
 * @author Marcus Andersson
 */
public class ProductsPO extends BasePO {

    public final void sortBy(final String sort) {
        By by = By.id("products-orderby");
        select(by, sort);
    }

    public final void itemsToDisplay(final int items) {
        By by = By.id("products-pagesize");
        select(by, Integer.toString(items));

        waitForNumberOfElements(By.cssSelector("div.product-grid div.item-box"), items);
    }

    public final void clickNextPage() {
        click(By.cssSelector("li.next-page"));
    }

    public final void clickItem(final int position) {
        List<WebElement> items = findElements(By.cssSelector("div.item-box div img"));
        highLight(items.get(position));
        items.get(position).click();
        
        waitForElementToBeClickable(By.cssSelector("div.product-essential input[value='Add to cart']"));
    }

    public final void addToCart() {
        click(By.cssSelector("div.product-essential input[value='Add to cart']"));
        waitForTextToBePresent("The product has been added to your");
    }

    private void select(By by, String value) {
        Select select = new Select(findElement(by));
        select.selectByVisibleText(value);
    }

}
