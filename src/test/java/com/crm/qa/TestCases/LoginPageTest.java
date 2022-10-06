package com.crm.qa.TestCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.crm.qa.BaseClass.TestBase;
import com.crm.qa.Constants.Constants;
import com.crm.qa.Pages.HomePage;
import com.crm.qa.Pages.LoginPage;

import java.io.IOException;

public class LoginPageTest extends TestBase
{	
	LoginPage loginPage;
	HomePage homePage;

	//@BeforeTest
	//@Override
	//public void setup(String browser) throws Exception {
	//	super.setup(browser);
	//}

	public LoginPageTest()
	{
		super();
	}

	@BeforeMethod(alwaysRun=true)
	@Parameters("browser")
	public void setUp(String browser) throws IOException {
		//super.setup(String browser);
		initialization(browser);
		//setup();
		Log.info("Application Launched Successfully");

		
  		loginPage = new LoginPage();
	}
	
	@Test(priority=1, enabled=true)
	public void loginPageTitleTest()
	{
		String title = loginPage.validateLoginPageTitle();
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE, "Login Page Title is not Matched");
		Log.info("Login Page Title Verified");
	}
	
	@Test(priority=2, enabled=false)
	public void crmLogoImageTest()
	{
		boolean flag = loginPage.validateCRMImage();
		Assert.assertTrue(flag);
		Log.info("CRM Logo Verified");
	}
	
	@Test(priority=3, enabled=false, invocationCount=1)
	public void loginTest()
	{
		homePage = loginPage.login(property.getProperty("Username"),property.getProperty("Password"));
		Log.info("Successfully Logged into CRM Application");
	}
}
