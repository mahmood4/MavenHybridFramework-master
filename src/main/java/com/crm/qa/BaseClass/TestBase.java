package com.crm.qa.BaseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import io.github.bonigarcia.wdm.WebDriverManager;



import com.crm.qa.Constants.Constants;
import com.crm.qa.Utilities.TestUtility;
import com.crm.qa.Utilities.WebEventListener;
import org.testng.annotations.Parameters;

public class TestBase
{
	public static WebDriver driver; 
	public static Properties property;
	public static ChromeOptions chromeOptions;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static Logger Log;
		
	public TestBase()
	{
		Log = Logger.getLogger(this.getClass());
		try 
		{
			property = new Properties();
			FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/crm/qa/Configuration/Configuration.properties");
			property.load(inputStream);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@BeforeTest
	public void setLog4j()
	{
		TestUtility.setDateForLog4j();
	}




	public static void initialization(String browser1) throws IOException {
	//	String broswerName = property.getProperty("Browser");
		Properties prop = new Properties();
		prop.load(TestBase.class.getClassLoader().getResourceAsStream("Mavenbrowser.properties"));

		String browserName=prop.getProperty("browserVal");
		System.out.println(browserName);
	//	String broswerName = System.getProperty("Browser");
			//	String broswerName = "Chrome";
		if(browserName.equals("Chrome"))
		{
			chromeOptions = new ChromeOptions();

			chromeOptions.setExperimentalOption("useAutomationExtension", false);
			chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			//System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
		}
		else if(browserName.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver", Constants.INTERNET_EXPLORER_DRIVER_PATH);
			driver = new InternetExplorerDriver();
		}
		else if(browserName.equals("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", Constants.FIREFOX_DRIVER_PATH);
			driver = new FirefoxDriver();
		}
		else
		{
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
	
	@AfterTest
	public void endReport()
	{
		
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() throws IOException
	{
		driver.quit();
		Log.info("Browser Terminated");
		Log.info("-----------------------------------------------");
	}
}
