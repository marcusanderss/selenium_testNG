package se.andersson.selenium.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.andersson.selenium.po.LoginPO;
import se.andersson.selenium.po.MenuPO;
import se.andersson.selenium.po.RegisterPO;

/**
 *
 * @date 2 jan. 2020
 * @author Marcus Andersson
 */
public class HeaderTest {

    private static final Logger LOG = LogManager.getLogger(HeaderTest.class);
    private MenuPO menu;

    @Test
    public void register() {
        RegisterPO register = menu.clickRegister();

        register.clickMale()
                .typeFirstName("Konrad")
                .typeLastName("Jönsson")
                .typeMail("konradJ@mail.com")
                .typePassword("python")
                .typeConfirmPassword("python")
                .clickRegister();
        
//        WebElement e = register.findElement("The specified email already exists");
    }
    
    @Test
    public void login() {
        LoginPO login = menu.clickLogin();
        
        login.typeMail("konradJ@mail.com");
        login.typePassword("python");
        login.clickLogin();
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
