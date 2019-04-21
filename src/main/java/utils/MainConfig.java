package utils;
import infra.pages.Browsers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainConfig {

    public static Browsers webDriverType;
    public static boolean closeBrowserAtClassTestEnd;
    public static boolean closeBrowserAtTestsEnd;
    public static int webDriverImplicitWaitInSeconds;
    public static String baseUrl;
    public static String email;
    public static String comment;


    public static void initFromFile(String filePath) throws IOException {

        Properties prop = new Properties();
        InputStream input = new FileInputStream("/Users/obroshi/Documents/Automation_course/ourmatch/src/main/resources/ourmatch.properties");
        prop.load(input);

        webDriverType = Browsers.valueOf(prop.getProperty("webDriverType"));
//        closeBrowserAtClassTestEnd = Boolean.parseBoolean(prop.getProperty("closeBrowserAtClassTestEnd"));
        closeBrowserAtTestsEnd = Boolean.parseBoolean(prop.getProperty("closeBrowserAtTestsEnd"));
        webDriverImplicitWaitInSeconds = Integer.parseInt(prop.getProperty("webDriverImplicitWaitInSeconds"));
        baseUrl = prop.getProperty("siteURL");
        email = prop.getProperty("email");
        comment = prop.getProperty("comment");

        input.close();
    }
}