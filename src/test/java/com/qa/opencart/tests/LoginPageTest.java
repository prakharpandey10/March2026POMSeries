package com.qa.opencart.tests;

//this is imported to avoid writting AppConstants.HOME_PAGE_TITLE
import static com.qa.opencart.constants.AppConstants.HOME_PAGE_TITLE;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
//import com.qa.opencart.constants.AppConstants;

//the parent of all the test class is BaseTest
@Feature("F 50: Open Cart - Login Feature")
@Epic("Epic 100: design pages for opencart app")
@Story("US 101: implement login page for opencart application")
public class LoginPageTest extends BaseTest {
	
	//to call the methods of LoginPage we need to create it's object which is already created inside BaseTest and we are inheriting it here.
	//Since access modifier of LoginPage reference is default it will work within the package only
	//Here, will change the access modifier to protected
	
	@Description("checking opencart login page title...")
	@Severity(SeverityLevel.MINOR)
	@Owner("Prakhar")
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		//Assert.assertEquals(actTitle, "Account Login");
		ChainTestListener.log("Checking logging Page title :"+actTitle);
		Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
	}
	
	@Description("checking opencart login page url...")
	@Severity(SeverityLevel.MINOR)
	@Owner("Prakhar")
	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		//Assert.assertTrue(actURL.contains("route=account/login"));
		Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTION_URL));
	}
	
	@Description("checking opencart login page has forgot pwd link...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Prakhar")
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
//	@Test(priority = Short.MAX_VALUE)
//	public void doLoginTest() {
//		String actAccPageTitle = loginPage.doLogin("testseleniumuser@open.com", "Selenium@123");
//		Assert.assertEquals(actAccPageTitle, "My Account");
//	}
	
	//Here we will have to change the access modifier of prop reference to protected to access it
	@Description("check user is able to login with valid user credentials...")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Prakhar")
	@Test(priority = Short.MAX_VALUE)
	public void doLoginTest() {
		//String actAccPageTitle = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		//Assert.assertEquals(actAccPageTitle, "My Account");
		//Assert.assertEquals(actAccPageTitle, HOME_PAGE_TITLE);
		Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
	}

}
