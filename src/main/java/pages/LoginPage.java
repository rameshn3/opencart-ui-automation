package pages;

import static base.PlaywrightFactory.takeScreenshot;
import static utils.ExtentReporter.extentLogWithScreenshot;

import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import static utils.ExtentReporter.extentLog;

public class LoginPage {
private Page page;
private ExtentTest extentTest;
private Logger log = LogManager.getLogger(LoginPage.class);
	public LoginPage(Page page, ExtentTest extentTest) {
		this.page = page;
		this.extentTest = extentTest;
	}

	private String emailEditbox = "input#input-email";
	private String passwordEditbox = "input#input-password";
	private String loginBtn = "//input[@value='Login']";
	private String newCustomerHeader = "//h2[normalize-space()='New Customer']";
	private String newCustomerContinueBtn = "//a[@class='btn btn-primary']";
	private String returningCustomerHeader = "//h2[normalize-space()='Returning Customer']";
	private String forgotPasswordLink = "a:text('Forgotten Password')";
	private String logoutLink = "//a[normalize-space()='Logout']";
	private String myAccountMenu = "//span[contains(.,'My Account')]";
	private String errorMsg = "//div[contains(@class,'alert alert-danger')]";
	private String loginBreadCrumb = "//ul[@class='breadcrumb']//a[contains(text(),'Login')]";
	private String loginHomeIcon = "//*[@class='fa fa-home']";
	
	
	public void clickLoginHomeIcon() {
		log.info("click on Login home icon in breadcrumb");
		page.locator(loginHomeIcon).click();
	}
	
	/**
	 * this method fetches the current page title
	 */
	public String getLoginPageTitle() {
		page.waitForLoadState();
		return page.title();
	}
	
	public void navigateToRegisterPage() {
		log.info("click on continue button in login page under newCustomer section");
		page.locator(newCustomerContinueBtn).click();
		
	}
	
	public void navigateToForgotPasswordPage() {
		log.info("click on forgotPasswordLink in login page ");
		page.locator(forgotPasswordLink).first().click();
		
	}
	
	private boolean isLogoutLinkVisible() {
		page.click(myAccountMenu);
		return page.locator(logoutLink).isVisible();
	}
	
	public boolean doLogin(String username,String password) {
		extentLog(extentTest,Status.INFO,"Login to the application using username"+username);
		page.fill(emailEditbox, username);
		page.fill(passwordEditbox, new String(Base64.getDecoder().decode(password)));
		page.click(loginBtn);
		if(isLogoutLinkVisible()) {
			extentLog(extentTest,Status.PASS,"Login to the application is Successful");
			return true;
		}
		
		boolean isErrorMsgDisplayed = page.textContent(errorMsg).contains(" Warning: No match for E-Mail Address and/or Password.");
		extentLogWithScreenshot(extentTest, Status.FAIL, "User login to the application is unsuccessful", takeScreenshot(page));
		return !isErrorMsgDisplayed;
	}
	
	public boolean newCustomerHeaderExists() {
		return page.locator(newCustomerHeader).isVisible();
	}
	
	public boolean returningCustomerHeaderExists() {
		return page.locator(returningCustomerHeader).isVisible();
	}
	
	public boolean loginBreadCrumbExists() {
		return page.locator(loginBreadCrumb).isVisible();
	}
}
