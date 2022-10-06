package com.crm.qa.TestCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.crm.qa.BaseClass.TestBase;
import com.crm.qa.Pages.ContactsPage;
import com.crm.qa.Pages.DealsPage;
import com.crm.qa.Pages.HomePage;
import com.crm.qa.Pages.LoginPage;
import com.crm.qa.Utilities.TestUtility;

import java.io.IOException;

public class ContactsPageTest extends TestBase
{
	LoginPage loginPage;
	HomePage homePage;
	TestUtility testUtil;
	ContactsPage contactsPage;
	DealsPage dealsPage;
	
	String sheetName = "Contacts"; 
	
	public ContactsPageTest()
	{
		super();
	}

	@BeforeMethod(alwaysRun=true)
	@Parameters("browser")
	public void setUp(String browser) throws IOException {
		//super.setup(String browser);
		initialization(browser);
		testUtil = new TestUtility();
		Log.info("Application Launched Successfully");
		
		loginPage = new LoginPage();
		contactsPage = new ContactsPage();
		dealsPage = new DealsPage();
		homePage = loginPage.login(property.getProperty("Username"),property.getProperty("Password"));
	}
	
	@Test(priority=1, enabled=false)
	public void verifyContactsPageLabelTest()
	{
		testUtil.switchToFrame("mainpanel");
		contactsPage = homePage.clickOnContactsLink();
		Assert.assertTrue(contactsPage.verifyContactsLabel(), "Contacts Label is Missing in the Page");
		Log.info("Verified Contacts Page Label");
	}
	
	@Test(priority=2, enabled=true)
	public void selectSingleContactsTest()
	{
		testUtil.switchToFrame("mainpanel");
		contactsPage = homePage.clickOnContactsLink();
		contactsPage.selectContactByName("Hello Test");
		Log.info("Verified Single Contacts");
	}
	
	@Test(priority=3, enabled=false)
	public void selectMultipleContactsTest()
	{
		testUtil.switchToFrame("mainpanel");
		contactsPage = homePage.clickOnContactsLink();
		contactsPage.selectContactByName("Ram Kumar");
		contactsPage.selectContactByName("Sanjay Kumar");
		Log.info("Verified Multiple Contacts");
	}
	
	@DataProvider
	public Object[][] getCRMContactsTestData()
	{
		Object data [][] = TestUtility.getTestData(sheetName);
		return data;
	}
	
	@Test(priority=4, enabled=false, dataProvider="getCRMContactsTestData")
	public void validateCreateNewContactTest(String Title, String FirstName, String LastName, String Company)
	{
		testUtil.switchToFrame("mainpanel");
		homePage.clickOnNewContactLink();
		contactsPage.createNewContact(Title, FirstName, LastName, Company);
		Log.info("New Contacts Created Successfully");
	}
}
