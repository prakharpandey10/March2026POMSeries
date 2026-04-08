package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest {
	
	@BeforeClass
	public void searchSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void searchTest() {
		searchResultsPage = accPage.doSearch("iMac");
		int actResultsCount = searchResultsPage.getResultsProductCount();
		Assert.assertEquals(actResultsCount, 1);
	}

}
