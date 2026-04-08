package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class RegisterPageTest extends BaseTest {

	@BeforeTest
	public void registerSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
//	@DataProvider
//	public Object[][] getUserRegTestData(){
//		return new Object [][] {
//			{"vishal2","mehta","vishaltest2@open.com","9876543211","Vishal@123","yes"},
//			{"jyothi","sharma","jyothitest@open.com","9876543212","Jyoti@123","no"},
//			{"Archana","sharma","archanatest@open.com","9876543213","Archana@123","no"}
//		};
//	}
	
	@DataProvider
	public Object[][] getUserRegTestData(){
		return new Object [][] {
			{"vishal3","mehta","9876543214","Vishal@123","yes"},
			{"jyothi1","sharma","9876543215","Jyoti@123","no"},
			{"Archana1","sharma","9876543216","Archana@123","no"}
		};
	}
	
//	@Test
//	public void userRegisterTest() {
//		Assert.assertTrue(registerPage
//				.userRegisteration("vishal1", "mehta1", "vishaltesting1@open.com", "9856325652", "Vishal@123", "yes"));
//	}
	
//	@Test(dataProvider = "getUserRegTestData")
//	public void userRegisterTest(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
//		Assert.assertTrue(registerPage
//				.userRegisteration(firstName, lastName, email, telephone, password, subscribe));
//	}
	
	@Test(dataProvider = "getUserRegTestData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage
				.userRegisteration(firstName, lastName, telephone, password, subscribe));
	}
}
