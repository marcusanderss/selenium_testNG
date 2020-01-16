package se.andersson.selenium.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class ScreenShotUtil {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(ScreenShotUtil.class);
    private static final ResourceBundle RB = ResourceBundle.getBundle("config");
    private static final String SNAPSHOT = RB.getString("SNAPSHOT");

    public static void captureScreenshot(String fileName, WebDriver driver) {
        if (Boolean.parseBoolean(SNAPSHOT)) {
            try {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd.hh.mm");

                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destFile = new File("screenshots/" + fileName + "_" + formater.format(calendar.getTime()) + ".png");

                FileUtils.copyFile(scrFile, destFile);
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }
}
