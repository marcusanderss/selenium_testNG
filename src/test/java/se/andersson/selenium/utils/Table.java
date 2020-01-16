package se.andersson.selenium.utils;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Table {
    
    private final WebDriver DRIVER;
    
    private final WebElement TABLE;
    private final By TR = By.tagName("tr");
    private final By TD = By.xpath("*");

    public Table(final WebDriver driver, final By by) {
        this.DRIVER = driver;
        this.TABLE = driver.findElement(by);
    }

    public int getNumberOfRows() {
        return TABLE.findElements(TR).size();
    }
    
    public int getNumberOfColumns() {
        WebElement row = TABLE.findElement(TR);
        return row.findElements(TD).size();
    }

    public List<WebElement> getRows() {
        return TABLE.findElements(TR);
    }

    public List<WebElement> getColumns(WebElement tr) {
        return tr.findElements(TD);
    }

    public WebElement getColumn(int tr, int td) {
        return TABLE.findElements(TR).get(tr).findElements(TD).get(td);
    }

}
