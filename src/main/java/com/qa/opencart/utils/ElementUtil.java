package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.chaintest.plugins.ChainTestListener;

import io.qameta.allure.Step;

public class ElementUtil {
	
	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	//public ElementUtil(WebDriver driver) {
	//	this.driver = driver;
	//}
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	
	private void nullCheck(CharSequence... value) {
		if(value == null) {
			throw new RuntimeException("====Value can not be null====");
		}
	}
	
	@Step("clicking on element using : {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}
	
	@Step("entring value : {1} into element {0}")
	public void doSendKeys(By locator, String value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys(String locatorType, String locatorValue, String value) {
		nullCheck(value);
		getElement(locatorType, locatorValue).clear();
		getElement(locatorType, locatorValue).sendKeys(value);
	}
	
	public void doSendKeys(By locator, CharSequence... value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys(String locatorType, String locatorValue, CharSequence... value) {
		nullCheck(value);
		getElement(locatorType, locatorValue).clear();
		getElement(locatorType, locatorValue).sendKeys(value);
	}
	
	@Step("fetching the element test using : {0}")
	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element Text :"+eleText);
		return eleText;
	}
	
	public String getElementDomAttributeValue(By locator, String attrName) {
		nullCheck(attrName);
		return getElement(locator).getDomAttribute(attrName);
	}
	
	public String getElementDomPropertyValue(By locator, String propName) {
		nullCheck(propName);
		return getElement(locator).getDomAttribute(propName);
	}
	
	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		}catch(NoSuchElementException e) {
			System.out.println("element is not present on the page :"+locator);
			return false;
		}
	}
	
	//***********************findElementsUtils***********************//
	
	public List<String> getElementTextList(By locator){
 	   List<WebElement>eleList = getElements(locator);
 	   List<String>eleTextList = new ArrayList<String>();
 	   for(WebElement e : eleList) {
 		   String text = e.getText();
 		   if(text.length() != 0) {
 			 System.out.println(text);  
 			 eleTextList.add(text);
 		   }
 	   }
 	   return eleTextList;
    }

    public int getElementsCount(By locator) {
 	   int eleCount = getElements(locator).size();
 	   System.out.println("element count ===>"+eleCount);
 	   return eleCount;
    }
    
    public boolean checkElementDisplayed(By locator) {
		if(getElements(locator).size() == 1) {
			System.out.println("element :"+ locator+ " is displayed one time");
			return true;
		}
		return false;
	}
	
	public boolean checkElementDisplayed(By locator, int expElementCount) {
		if(getElements(locator).size() == expElementCount) {
			System.out.println("element :"+ locator+ " is displayed on the page time "+expElementCount);
			return true;
		}
		return false;
	}
	
	public void clickElement(By locator, String value) {
		List<WebElement>eleList = driver.findElements(locator);
		System.out.println("total number of elements :"+eleList.size());
		for(WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if(text.contains(value)) {
				e.click();
				break;
			}
		}
	}
    
    public List <WebElement>getElements(By locator){
 	   return driver.findElements(locator);
    }
    
    public WebElement getElement(String locatorType, String locatorValue) {
		return driver.findElement(getBy(locatorType, locatorValue));
	}
    
    @Step("finding the ele using :{0}")
    public WebElement getElement(By locator) {
    	ChainTestListener.log("locator :"+locator.toString());
		return driver.findElement(locator);
	}
    
//    public WebElement getElement(By locator) {
//    	WebElement element = driver.findElement(locator);
//    	highlightElement(element);
//		return element;
//	}
    
//    private void highlightElement(WebElement element) {
//    	if(Boolean.parseBoolean(DriverFactory.highlight)) {
//    		jsUtil.flash(element);
//    	}
//    }
    
    public WebElement getElementWithWait(By locator, int timeout) {
		return waitForElementVisible(locator, timeout);
	}
	
	public By getBy(String locatorType, String locatorValue) {
		By locator  = null;
		
		switch(locatorType.toUpperCase()){
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASS":
			locator = By.className(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;
		default:
			System.out.println("Please pass the right locator type "+locatorType);
			break;
		}
		return locator;
	}
	

    
    //*******************************drop down utils -- non select based*********************************//
    /**
	 * this method is used to select the choices with three different use cases:
	 * 1. single selection : selectChoice(choice, choicesList, "choice 3")
	 * 2. multi selection : selectChoice(choice, choicesList, "choice 1", "choice 2", "choice 6 2 1", "choice 7", "choice 2 3")
	 * 3. all selection : selectChoice(choice, choicesList, "ALL")
	 * @param choice
	 * @param choicesList
	 * @param choiceValue
	 * @throws InterruptedException
	 */
	public void selectChoice(By choice, By choicesList, String... choiceValue) throws InterruptedException {
		driver.findElement(choice).click();
		
		Thread.sleep(2000);
		
		List<WebElement>choices = driver.findElements(choicesList);
		System.out.println(choices.size());
		
		if(choiceValue[0].equalsIgnoreCase("all")) {
			//logic to select all the choices
			for(WebElement e : choices) {
				e.click();
			}
		}else {
		
		for(WebElement e : choices) {
			String text = e.getText();
			System.out.println(text);
			
			for(String value : choiceValue) {
				if(text.trim().equals(value)) {
					e.click();
					break;
				}
			}
		}
		}
	}
	
//*****************************************Action Utils***********************************//
	
	public void doMoveToElement(By locator) throws InterruptedException {
		//Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
		Thread.sleep(2000);
	}
	
	public void handleParentSubMenu(By parentMenu, By subMenu) throws InterruptedException {
		doMoveToElement(parentMenu);
		doClick(subMenu);
	}
	
	public void handle4LevelMenuHandle(By level1Menu, By level2Menu, By level3Menu, By level4Menu ) throws InterruptedException {
		doClick(level1Menu);
		Thread.sleep(2000);
		doMoveToElement(level2Menu);
		Thread.sleep(2000);
		doMoveToElement(level3Menu);
		Thread.sleep(2000);
		doClick(level4Menu);
	}
	
	public void doActionsSendKeys(By locator, String value) {
		//Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}
	
	public void doActionsClick(By locator) {
		//Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	public void doSendKeysWithPause(By locator, String value, long pauseTime) {
		//Actions act = new Actions(driver);
		
		char val[] = value.toCharArray();
		for(char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
		}
	}
	
	//=============================Wait Utils==============================================//
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForAllElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}catch(TimeoutException e) {
			return Collections.EMPTY_LIST;//[]-0
		}
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("waiting for element and clicking on it using : {0} and timeout : {1}")
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	//	highlightElement(element);
		return element;
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeout
	 */
	public void clickWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public void clickWithWait(By locator, int timeout) {
		waitForElementVisible(locator, timeout).click();
	}
	
	public void sendKeysWithWait(By locator, int timeout, CharSequence...value) {
		waitForElementVisible(locator, timeout).sendKeys(value);
	}
	
	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void acceptAlert(int timeout) {
		waitForAlert(timeout).accept();
	}
	
	public void dismissAlert(int timeout) {
		waitForAlert(timeout).dismiss();
	}

	public void getTextAlert(int timeout) {
		waitForAlert(timeout).getText();
	}
	
	public void sendKeysAlert(int timeout, String value) {
		waitForAlert(timeout).sendKeys(value);
	}
	
	//=======================Wait For Title=================//
	
	public String waitForTitleContains(String fractionTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitle));
			return driver.getTitle();
		}catch(TimeoutException e) {
			return null;
		}
	}
	
	public String waitForTitleIs(String title, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleContains(title));
			return driver.getTitle();
		}catch(TimeoutException e) {
			return null;
		}
	}
	
	//==============================Wait For URL=============================//
	
	public String waitForURLContains(String fractionURL, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlContains(fractionURL));
			return driver.getCurrentUrl();
		}catch(TimeoutException e) {
			return null;
		}
	}
	
	public String waitForURLIs(String url, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlToBe(url));
			return driver.getCurrentUrl();
		}catch(TimeoutException e) {
			return null;
		}
	}
	
	//==========================Wait For Frame==============================//
	
	public void waitForFrameAndSwitchToIt(By frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameAndSwitchToIt(String frameNameOrID, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
	}
	
	public void waitForFrameAndSwitchToIt(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
		
	public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));	
	}

	//================================Wait For Frame===============================//
	
	public boolean waitForWindow(int timeout, int expectedNumberOfWindows) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
		}catch(Exception e) {
			System.out.println("Expected number of windows are not correct");
			return false;
		}
	}

}

