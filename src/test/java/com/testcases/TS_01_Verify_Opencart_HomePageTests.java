package com.testcases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.AppConstants;

public class TS_01_Verify_Opencart_HomePageTests extends BaseTest{
	
	
	@Test
	public void homePageTitleTest() {
	String actTitle = homePage.getHomePageTitle();
	Assert.assertEquals(actTitle, AppConstants.HOME_PAGE_TITLE);
	}
	
	

	@Test
	public void homePageURLTest() {
	String actUrl = homePage.getHomePageURL();
	Assert.assertEquals(actUrl, testproperties.getProperty("url"));
	}
	
	
	

	@Test
	public void verifyOpenCartLogoTest() {
	boolean isLogoExists = homePage.isOpenCartLogoExists();
	Assert.assertTrue(isLogoExists);
	}
	
	

	@Test
	public void featuredSectioncardsCountTest() {
	int  actCardCount = homePage.getFeaturedSectionCardsCount();
	Assert.assertEquals(actCardCount, AppConstants.FEATURED_SECTION_CARD_COUNT);
	}
	
	

	@Test
	public void navigateToLoginPageTest() {
	loginPage = homePage.navigateToLoginPage();
	String loginPageTitle = loginPage.getLoginPageTitle();
	Assert.assertEquals(loginPageTitle, AppConstants.LOGIN_PAGE_TITLE,"The Title of the login page should match the expected title");
	loginPage.clickLoginHomeIcon();
	
	}
	

	@Test
	public void navigateToRegisterPageTest() {
	registerPage = homePage.navigateToRegisterPage();
	String registerPageTitle = registerPage.getRegisterPageTitle();
	Assert.assertEquals(registerPageTitle, AppConstants.REGISTER_PAGE_TITLE,"The Title of the register page should match the expected title");
	registerPage.clickHomeIcon();
	
	}
	
	@DataProvider
	public Object[][] productData(){
		Object[][] data = new Object[3][1];
		//first row
		data[0][0] ="Macbook";
		//second row
		data[1][0]= "iMac";
		//third row
		data[2][0] = "Samsung";
		return data;
	}
	
	@Test(dataProvider = "productData",dependsOnMethods="navigateToRegisterPageTest")
	public void searchTest(String productName) {
		boolean actSearchHeader = homePage.searchProduct(productName);
		Assert.assertTrue(actSearchHeader,"The product search should be successful for:"+productName);
	}
	
}