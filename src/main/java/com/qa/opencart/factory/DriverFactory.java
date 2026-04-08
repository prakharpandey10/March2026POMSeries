package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	
	WebDriver driver;
	//this will help me to load the properties from .properties file
	Properties prop;
	OptionsManager optionsManager;
	FileInputStream ip;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
//	public static String highlight;
	
	//For Log
	public static final Logger log = LogManager.getLogger(DriverFactory.class);
	//warn, info, error, fatal
	
	/**
	 * This method is used to initialize th driver on the basis of given browser name
	 * @param browserName
	 * @return
	 */
//	public WebDriver initDriver(Properties prop) {
//		
//		String browserName = prop.getProperty("browser");
//		System.out.println("Browser name :"+browserName);
//		optionsManager = new OptionsManager(prop);
//		
//		switch(browserName.toLowerCase().trim()) {
//		case "chrome":
//			driver = new ChromeDriver(optionsManager.getChromeOptions());
//			break;
//		case "firefox":
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
//			break;
//		case "edge":
//			driver = new EdgeDriver();
//			break;
//		case "safari":
//			driver = new SafariDriver();
//			break;
//		default:
//			System.out.println("Plz pass the valid browser name...."+browserName);
//			throw new BrowserException("====INVALID BROWSER=====");
//		}
//		driver.get(prop.getProperty("url"));
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//		return driver;
//	}
	
	//changes for taking screenshot
    public WebDriver initDriver(Properties prop) {
		log.info("properties :"+prop);
    	
		String browserName = prop.getProperty("browser");
	//	System.out.println("Browser name :"+browserName);
		log.info("browser name :"+browserName);
		optionsManager = new OptionsManager(prop);
		
		switch(browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			tlDriver.set(new EdgeDriver());
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println("Plz pass the valid browser name...."+browserName);
			log.error("plz pass the valid browser name ..."+browserName);
			throw new BrowserException("====INVALID BROWSER=====");
		}
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
	}
    
    //return the local thread copy of the driver
    public static WebDriver getDriver() {
    	return tlDriver.get();
    }
	
//	public WebDriver initDriver(String browserName) {
//		System.out.println("Browser name :"+browserName);
//		
//		switch(browserName.toLowerCase().trim()) {
//		case "chrome":
//			driver = new ChromeDriver();
//			break;
//		case "firefox":
//			driver = new FirefoxDriver();
//			break;
//		case "edge":
//			driver = new EdgeDriver();
//			break;
//		case "safari":
//			driver = new SafariDriver();
//			break;
//		default:
//			System.out.println("Plz pass the valid browser name...."+browserName);
//			throw new BrowserException("====INVALID BROWSER=====");
//		}
//		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//		return driver;
//	}
	
	/**
	 * this is used to initialize config properties
	 * @return
	 */
//	public Properties initProp() {
//		prop = new Properties();
//		//to make connection with the config.properties
//		try {
//			FileInputStream ip = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");
//				prop.load(ip);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		 catch (IOException e) {
//			e.printStackTrace();
//		}
//		return prop;
//	}

	//mvn clean install -Denv="qa"
	public Properties initProp() {
		String envName = System.getProperty("env");
		FileInputStream ip = null;
		prop = new Properties();
		try {
		if(envName==null) {
		//	System.out.println("env is null, hence running the testcases on qa env..");
			log.warn("env is null, hence running the testcases on qa env..");

			ip = new FileInputStream(".\\src\\test\\resources\\config\\qa.config.properties");
		}else {
			log.info("runing testcases on env :"+envName);
		//	System.out.println("runing testcases on env :"+envName);
			switch(envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream(".\\src\\test\\resources\\config\\qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream(".\\src\\test\\resources\\config\\dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream(".\\src\\test\\resources\\config\\stage.config.properties");
				break;
			default:
				log.error("====INVALID ENV NAME====="+envName);
				throw new FrameworkException("====INVALID ENV NAME=====");
			}
		}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
		prop.load(ip);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	//take Screenshot
	
	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}
	
	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);
	}
	
	public static String getScreenshotBase64() {
		return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64);
	}
}
