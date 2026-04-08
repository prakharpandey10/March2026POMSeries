package com.qa.opencart.pages;

//this is imported to avoid writting AppConstants.HOME_PAGE_TITLE
import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.MEDIUM_DEFAULT_TIMEOUT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	//creating reference of ElementUtil
	private ElementUtil eleUtil;
	
	//1. private By locators
	// Locators are made private because if it's public anyone can create object of LoginPage and manipulate it.
	// Locators are made final so that no one can access them within the class and manipulate them.
	// We are using encapsulation here as locators are private and actions are public.
	private final By email = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By registerLink = By.linkText("Register");
	
	//2. public page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		//initializing it
		eleUtil = new ElementUtil(driver);
	}
	
	//3. public page actions/methods
	@Step("getting login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIMEOUT);
		//String title = driver.getTitle();
		System.out.println("Login Page title :"+title);
		return title;
	}
	
	@Step("getting login page url")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(LOGIN_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		//String url = driver.getCurrentUrl();
		System.out.println("Login Page url :"+url);
		return url;
	}
	
	@Step("checking forgot pwd link exists")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
		//return driver.findElement(forgotPwdLink).isDisplayed();
	}
	
	@Step("login with valid username: {0} and password: {1}")
	//public String doLogin(String username, String pwd)
	public AccountsPage doLogin(String username, String pwd){
		System.out.println("user credentials :"+username+" : "+pwd);
		eleUtil.waitForElementVisible(email, MEDIUM_DEFAULT_TIMEOUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		//driver.findElement(email).sendKeys(username);
		//driver.findElement(password).sendKeys(pwd);
		//driver.findElement(loginBtn).click();
//		String title = eleUtil.waitForTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
//		System.out.println("accounts page title :"+title);
		//return driver.getTitle();
		//return title;
		//When ever we are landing on a new page then it's method responsibility to return the next landing page class object.
		//When ever we create the object of page class we have to supply the driver
		return new AccountsPage(driver);
	}

	@Step("navigating to registration page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.clickWhenReady(registerLink, DEFAULT_TIMEOUT);
		return new RegisterPage(driver);
	}
}
