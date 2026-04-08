package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	//creating reference of ElementUtil
	private ElementUtil eleUtil;
	
	private final By resultsProduct = By.cssSelector("div.product-thumb");
	
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public int getResultsProductCount() {
		int searchCount = eleUtil.waitForAllElementsVisible(resultsProduct,AppConstants.MEDIUM_DEFAULT_TIMEOUT ).size();
		System.out.println("total number of search products :"+searchCount);
		return searchCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("product name :"+productName);
		//We are not maintaining the locator as the product name is dynamic
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}
	    
}
