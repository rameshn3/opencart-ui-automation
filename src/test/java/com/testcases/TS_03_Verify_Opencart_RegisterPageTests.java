package com.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import base.BaseTest;
import utils.AppConstants;

public class TS_03_Verify_Opencart_RegisterPageTests extends BaseTest{
	
@BeforeMethod
public void setUp() {
	registerPage = homePage.navigateToRegisterPage();
	String registerPageTitle = registerPage.getRegisterPageTitle();
	Assert.assertEquals(registerPageTitle, AppConstants.REGISTER_PAGE_TITLE,"The Title of the register page should match the expected title");
	
}
	
	
	@Test
	public void registerNewAccountTest() {
	//create Object for Faker class
		Faker faker = new Faker();
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String email = faker.internet().emailAddress();
		String telephone = faker.phoneNumber().phoneNumber();
		String password = faker.internet().password();
		
		registerPage.setPersonalDetails(firstName, lastName, email, telephone);
		registerPage.setPasswordDetails(password,password);
		registerPage.selectSubScribe("Yes");
		registerPage.checkAgreeCheckbox();
		registerPage.clickContinueBtn();
		Assert.assertTrue(registerPage.isAccountCreatedSucMsgExists(),"Account Creation success message should be displayed");
		Assert.assertTrue(registerPage.isAccountCreatedBreadCrumbSucExists(),"Account Creation breadcrumb should be displayed");
		registerPage.clickOnAccountCreatedContinueBtn();
	}
	
	
	
	
}