package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;

public class RegisterPage {
	private Page page;
	private ExtentTest extentTest;
	private Logger log = LogManager.getLogger(RegisterPage.class);

	public RegisterPage(Page page, ExtentTest extentTest) {
		this.page = page;
		this.extentTest = extentTest;
	}

	private String registerAccountHeader = "#content h1";
	private String loginPageLink = "a:text('login page')";
	private String firstNameEditbox = "id=input-firstname";
	private String lastNameEditbox = "id=input-lastname";
	private String emailEditbox = "#input-email";
	private String telephoneEditbox = "#input-telephone";
	private String passwordEditbox = "#input-password";
	private String confirmPasswordEditbox = "#input-confirm";
	private String accountCreatedHeader ="div#content h1";
	private String accountCreatedSucMsg ="div#content p";
	private String accountCreatedContinueBtn="a.btn.btn-primary";
	private String accountCreatedBreadCrumbSuccess="#common-success > ul > li:nth-child(3) > a";
	private String accountCreatedContactUsLink = "a:text('contact us')";
	private String registerHomeIcon = "//*[@class='fa fa-home']";
	
	public void clickHomeIcon() {
		page.locator(registerHomeIcon).click();
	}
	
	 public String getRegisterPageTitle() {
	        return page.title();
	    }
	public String getAccountCreatedHeader() {
		return page.locator(accountCreatedHeader).textContent().trim();
	}
	
	
	public String getAccountCreatedSucMsg() {
		return page.locator(accountCreatedSucMsg).first().textContent().trim();
	}
	
	public boolean isAccountCreatedSucMsgExists() {
		return page.locator(accountCreatedSucMsg).first().isVisible();
	}
	
	public boolean isAccountCreatedBreadCrumbSucExists() {
		return page.locator(accountCreatedBreadCrumbSuccess).isVisible();
	}
	
	public void clickOnContactUsLink() {
		page.locator(accountCreatedContactUsLink).click();
	}
	
	public void clickOnAccountCreatedContinueBtn() {
		page.locator(accountCreatedContinueBtn).click();
	}
	
	public String getTelephoneEditbox() {
		return telephoneEditbox;
	}

	public void setTelephoneEditbox(String telephone) {
		page.locator(telephoneEditbox).fill(telephone);
	}

	private String subScribeYesRadioBtn = "//*[@id=\"content\"]/form/fieldset[3]/div/div/label[1]/input";
	private String subScribeNoRadioBtn = "//*[@id=\"content\"]/form/fieldset[3]/div/div/label[2]/input";
	private String privacyCheckbox = "input[name='agree']";
	private String continueBtn = "input[value='Continue']";

	public String getLoginPageLink() {
		return loginPageLink;
	}

	public void setLoginPageLink(String loginPageLink) {
		this.loginPageLink = loginPageLink;
	}

	public String getFirstNameEditbox() {
		return firstNameEditbox;
	}

	public void setFirstNameEditbox(String firstName) {
		page.locator(firstNameEditbox).fill(firstName);
	}

	public String getLastNameEditbox() {
		return lastNameEditbox;
	}

	public void setLastNameEditbox(String lastName) {
		page.locator(lastNameEditbox).fill(lastName);
	}

	public String getEmailEditbox() {
		return emailEditbox;
	}

	public void setEmailEditbox(String email) {
		page.locator(emailEditbox).fill(email);
	}

	public String getPasswordEditbox() {
		return passwordEditbox;
	}

	public void setPasswordEditbox(String password) {
		page.locator(passwordEditbox).fill(password);
	}

	public String getConfirmPasswordEditbox() {
		return confirmPasswordEditbox;
	}

	public void setConfirmPasswordEditbox(String confirmPassword) {
		page.locator(confirmPasswordEditbox).fill(confirmPassword);
	}

	public void setPersonalDetails(String fname, String lname, String email, String telephone) {
		setFirstNameEditbox(fname);
		setLastNameEditbox(lname);
		setEmailEditbox(email);
		setTelephoneEditbox(telephone);
	}

	public void setPasswordDetails(String pwd, String confirmPwd) {
		setPasswordEditbox(pwd);
		setConfirmPasswordEditbox(confirmPwd);
	}

	public void selectSubScribe(String subscribe) {
		log.info("select newsletter subscription value");
		if (subscribe.equalsIgnoreCase("Yes")) {
			log.info("select newsletter subscription value -Yes");
			page.locator(subScribeYesRadioBtn).click();
		} else {
			log.info("select newsletter subscription value -No");
			page.click(subScribeNoRadioBtn);
		}
	}
	
	public void checkAgreeCheckbox() {
		log.info("check agree checkbox");
		page.locator(privacyCheckbox).check();
	}
	
	public void clickContinueBtn() {
		log.info("Click on Continue button");
		page.click(continueBtn);
	}
	
}
