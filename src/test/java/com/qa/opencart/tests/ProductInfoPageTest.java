package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtil;

public class ProductInfoPageTest extends BaseTest{

	@BeforeClass
	public void productInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductTestData(){
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
		};
	}
	
	//AAA - Arrange, Act, Assert(1)
//	@Test
//	public void productHeaderTest() {
//		searchResultsPage = accPage.doSearch("macbook");
//		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
//		String actHeader = productInfoPage.getProductHeader();
//		Assert.assertEquals(actHeader, "MacBook Pro");
//	}
	
	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		String actHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData(){
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"macbook", "MacBook Air", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7},
		};
	}
	
//	@Test
//	public void productImageCountTest() {
//		searchResultsPage = accPage.doSearch("macbook");
//		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
//		int actImageCount = productInfoPage.getProductImagesCount();
//		Assert.assertEquals(actImageCount, 4);
//	}
	@DataProvider
	public Object[][] getProductCSVData(){
		return CSVUtil.csvData("product");
	}
	
	@Test(dataProvider = "getProductCSVData")
	public void productImageCountTest(String searchKey, String productName, String expectedImageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actImageCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(String.valueOf(actImageCount), expectedImageCount);
	}
	
	@Test
	public void productInfoTest() {
			searchResultsPage = accPage.doSearch("macbook");
			productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
			
			Map<String, String>actualProductDetailsMap = productInfoPage.getProductDetailsMap();
			// when we have multiple assertions in a single method, use soft assertions.
			
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertEquals(actualProductDetailsMap.get("productheader"), "MacBook Pro");
			softAssert.assertEquals(actualProductDetailsMap.get("productimages"), "4");
			softAssert.assertEquals(actualProductDetailsMap.get("Brand"), " Apple");
			softAssert.assertEquals(actualProductDetailsMap.get("Product Code"), " Product 18");
			softAssert.assertEquals(actualProductDetailsMap.get("Availability"), " Out Of Stock");
			softAssert.assertEquals(actualProductDetailsMap.get("productprice"), "$2000.00");
			softAssert.assertEquals(actualProductDetailsMap.get("extraxprice"), "$2000.00");
			softAssert.assertAll();
	}
}
