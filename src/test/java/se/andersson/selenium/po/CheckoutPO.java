package se.andersson.selenium.po;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import se.andersson.selenium.BasePO;
/**
 *
 * @date 3 jan. 2020
 * @author Marcus Andersson
 */
public class CheckoutPO extends BasePO {

    private static final int TIME_TO_WAIT = 5;

    public final void checkOut() {
        List<WebElement> billingSteps = findElements(By.cssSelector("ol#checkout-steps li"));

        // Billing Address        
        waitForAttribute(billingSteps.get(0), "active");
        click(By.cssSelector("#billing-buttons-container input"));

        // Shipping Address
        waitForAttribute(billingSteps.get(1), "active");
        findElement(By.cssSelector("#shipping-buttons-container input")).click();

        // Shipping Method
        waitForAttribute(billingSteps.get(2), "active");
        findElement(By.cssSelector("#shipping-method-buttons-container input")).click();

        // Payment Method
        waitForAttribute(billingSteps.get(3), "active");
        findElement(By.cssSelector("#payment-method-buttons-container input")).click();

        // Payment Information
        waitForAttribute(billingSteps.get(4), "active");
        findElement(By.cssSelector("#payment-info-buttons-container input")).click();

        // Confirm Order
        waitForAttribute(billingSteps.get(5), "active");
        findElement(By.cssSelector("#confirm-order-buttons-container input")).click();
    }

}