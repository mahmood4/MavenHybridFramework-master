package com.crm.qa.BaseClass;

import com.crm.qa.Constants.Constants;
import com.crm.qa.Utilities.WebEventListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseC {
    public static WebDriver driver;
    public static Properties property;
    public static ChromeOptions chromeOptions;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;
    public static Logger Log;


    //  public WebDriver driver; //declared outside, so each  method in class can access

    @Parameters("browser") //parameter is a tag with attribute name = "browser" in testng.xml
    @BeforeTest  //BeforeTest will execute before every Test(method) executes
    public void setup(String browser) throws Exception {

        if (browser.equals("Chrome")) {
            chromeOptions = new ChromeOptions();

            chromeOptions.setExperimentalOption("useAutomationExtension", false);
            chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            //System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(chromeOptions);
        } else if (browser.equals("IE")) {
            System.setProperty("webdriver.ie.driver", Constants.INTERNET_EXPLORER_DRIVER_PATH);
            driver = new InternetExplorerDriver();
        } else if (browser.equals("Firefox")) {
            System.setProperty("webdriver.gecko.driver", Constants.FIREFOX_DRIVER_PATH);
            driver = new FirefoxDriver();
        } else {
            System.out.println("Path of Driver Executable is not Set for any Browser");
        }

        e_driver = new EventFiringWebDriver(driver);

        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);

        driver.get(property.getProperty("Url"));
    }

}


