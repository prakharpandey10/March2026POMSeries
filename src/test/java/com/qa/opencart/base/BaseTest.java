package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	//this will open browser for me, I will call initDriver from DriverFactory by creating it's object
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory();
		//initializing properties
		prop = df.initProp();
		//browser name is passed from xml file
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
		}
		//calling the method by passing the prop reference
		driver = df.initDriver(prop);
		
		//driver = df.initDriver("chrome");
		//Initializing the driver of LoginPage
		loginPage = new LoginPage(driver);
	}
	
	@AfterMethod
	public void attachScreenshot(ITestResult result) {
	//	if(!result.isSuccess()) {
	//		ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
	//	}
		ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
