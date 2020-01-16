package se.andersson.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

import se.andersson.selenium.BasePO;

/**
 *
 * @date 10 jan. 2020
 * @author Marcus Andersson
 */
public class LoginPO extends BasePO {

    private static final By EMAIL = By.id("Email");
    private static final By PASSWORD = By.id("Password");
    
    private String email;
    
    public final void typeMail(final String value) {
        type(EMAIL, value);
        this.email = value;
    }
    public final void typePassword(final String value) {
        type(PASSWORD, value);
    }

    public final void clickLogin() {
        By chained = new ByChained(By.cssSelector("div.buttons"), By.cssSelector("input[value='Log in']"));
        click(chained);
        waitForTextToBePresent(email);
    }
}