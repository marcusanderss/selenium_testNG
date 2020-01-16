package se.andersson.selenium.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static int parseInteger(final String value) {
        int valueToString = 0;

        Pattern p = Pattern.compile("\\d+");

        Matcher m = p.matcher(value);
        if (m.find()) {
            valueToString = Integer.parseInt(m.group());
        }
        
        return valueToString;
    }
}