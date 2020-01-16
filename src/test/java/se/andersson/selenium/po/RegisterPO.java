package se.andersson.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import se.andersson.selenium.BasePO;

/**
 *
 * @date 10 jan. 2020
 * @author Marcus Andersson
 */
public class RegisterPO extends BasePO {

    private static final By MALE_RADIO = By.xpath("//input[@type='radio' and @name='Gender' and @value='M']");
    private static final By FEMALE_RADIO = By.xpath("//input[@type='radio' and @name='Gender' and @value='F']");
    private static final By FIRST_NAME = By.id("FirstName");
    private static final By LAST_NAME = By.id("LastName");
    private static final By EMAIL = By.id("Email");

    public final RegisterPO clickMale() {
        click(MALE_RADIO);

        return this;
    }

    public final RegisterPO clickFemale() {
        click(FEMALE_RADIO);
        
        return this;
    }

    public final RegisterPO typeFirstName(final String value) {
        type(FIRST_NAME, value);
        
        return this;
    }

    public final RegisterPO typeLastName(final String value) {
        type(LAST_NAME, value);
        
        return this;
    }

    public final RegisterPO typeMail(final String value) {
        type(EMAIL, value);
        
        return this;
    }
    
    public final RegisterPO typePassword(final String value) {
        WebElement parent = findParent("Password:");
        WebElement input = parent.findElement(By.tagName("input"));
        input.clear();
        input.sendKeys(value);

        return this;
    }
    
    public final RegisterPO typeConfirmPassword(final String value) {
        WebElement parent = findParent("Confirm password:");
        WebElement input = parent.findElement(By.tagName("input"));
        input.clear();
        input.sendKeys(value);
        
        return this;
    }
    
    public final RegisterPO clickRegister() {
        click(By.xpath("//input[@type='submit' and @value='Register']"));
        waitForTextToBePresent("Your registration completed");
        click(By.xpath("//input[@type='button' and @value='Continue']"));
        waitForTextToBePresent(By.tagName("h2"), "Welcome to our store");
        
        return this;
    }
    
}
