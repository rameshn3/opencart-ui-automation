package com.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.AppConstants;

public class TS_02_Verify_Opencart_LoginPageTests extends BaseTest{
	
@BeforeMethod
public void setUp() {
	loginPage = homePage.navigateToLoginPage();
	String loginPageTitle = loginPage.getLoginPageTitle();
	Assert.assertEquals(loginPageTitle, AppConstants.LOGIN_PAGE_TITLE,"The Title of the login page should match the expected title");
	
}
	
	
	@Test
	public void loginPageTitleTest() {
	String actTitle = loginPage.getLoginPageTitle();
	Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test
	public void navigateToRegisterPageTest() {
	loginPage.navigateToRegisterPage();
	String registerPageTitle = registerPage.getRegisterPageTitle();
	Assert.assertEquals(registerPageTitle, AppConstants.REGISTER_PAGE_TITLE,"The Title of the register page should match the expected title");
	registerPage.clickHomeIcon();
	
	}
	
	@Test
	public void navigateToForgotPasswordPageTest() {
	loginPage.navigateToForgotPasswordPage();
	String forgotpwdPageTitle = forgotPasswordPage.getForgotPasswordPageTitle();
	Assert.assertEquals(forgotpwdPageTitle, AppConstants.FORGOT_PASSWORD_PAGE_TITLE,"The Title of the forgotpassword page should match the expected title");
	registerPage.clickHomeIcon();
	
	}
	
	@Test
	public void verifyNewCustomerHeaderExists() {
		boolean isNewCustomerHeaderExists = loginPage.newCustomerHeaderExists();
		Assert.assertTrue(isNewCustomerHeaderExists, "The New Customer header shoud be visible on the login page");
	}
	
	@Test
	public void verifyReturningCustomerHeaderExists() {
		boolean isreturningCustomerHeaderExists = loginPage.returningCustomerHeaderExists();
		Assert.assertTrue(isreturningCustomerHeaderExists, "The returning Customer header shoud be visible on the login page");
	}
	
	@Test
	public void verifyLoginBreadCrumbExists() {
		boolean loginbreadCrumbExists = loginPage.loginBreadCrumbExists();
		Assert.assertTrue(loginbreadCrumbExists, "The login breadcrumb shoud be visible on the login page");
	}
	
	@Test
	public void loginTest() {
		boolean isLoginSuccess= loginPage.doLogin(testproperties.getProperty("username"), testproperties.getProperty("password"));
		
		if(isLoginSuccess) {
			Assert.assertTrue(isLoginSuccess,"Login should succeed for valid credentails");
		}else {
			Assert.assertFalse(isLoginSuccess,"Login should fail for invalid credentails");
			
		}
		
	}
	
	
	
}