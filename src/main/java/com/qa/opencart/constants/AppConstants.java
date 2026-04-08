package com.qa.opencart.constants;

import java.util.*;

public class AppConstants {
	
	//Here variables have been made as public and static since i don't wan't to create object of the class to access it.
	//Instead it could be accessed via class name directly.
	//final is there so that no one can modify it.
	public static final int DEFAULT_TIMEOUT = 5;
	public static final int MEDIUM_DEFAULT_TIMEOUT = 10;
	public static final int LONG_DEFAULT_TIMEOUT = 15;
	
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String HOME_PAGE_TITLE = "My Account";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	public static final String HOME_PAGE_FRACTION_URL = "route=account/account";
	
	public static final List<String> expectedAccPageHeadersList = new ArrayList<String>(Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter"));
	
	public static final String REGISTER_SUCCESS_MESSG = "Your Account Has Been Created!";
	
	

}
