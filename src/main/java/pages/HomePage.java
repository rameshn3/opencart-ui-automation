package pages;
import static base.PlaywrightFactory.takeScreenshot;
import static utils.ExtentReporter.extentLogWithScreenshot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static utils.ExtentReporter.extentLog;
public class HomePage {

	private Page page;
	private ExtentTest extentTest;
	private Logger log = LogManager.getLogger(HomePage.class);
	/**
	 * Constructor to initialize the page objects with the page instance and ExtentTest instacne
	 * 
	 */
	public HomePage(Page page,ExtentTest extentTest) {
		this.page=page;
		this.extentTest=extentTest;
	}
	private String openCartLogo = "#logo";
	private String searchEditbox = "input[name='search']";
	private String searchIcon = "div#search button";
	private String searchPageHeader = "div#content h1";
	private String loginLink = "a:text('Login')";
	private String registerLink = "a:text('Register')";
	private String myAccountLink = "a[title='My Account']";
	private String productSearchResult = "div.product-thumb";
	private String featuredproductList = "//*[@id='content']/div[2]/div";
	private String addTocartSelector = "text='Add to Cart'";
	private String productCaptionName = "div.caption h4 a";
	private String shoppingCartLink ="//a[contains(text(),'Shopping Cart')]";
	private String shoppingCartIcon = "div#cart";
	private String productHeader = "div#content h2";
	
	/**
	 * this method fetches the current page title
	 */
	public String getHomePageTitle() {
		page.waitForLoadState();
		return page.title();
	}
	public String getHomePageURL() {
		String url =  page.url();
		System.out.println("page url : " + url);
		return url;
	}
	public boolean isOpenCartLogoExists() {
		log.info("checking the logo exists or not");
		return page.locator(openCartLogo).isVisible();
	}
	
	public int getFeaturedSectionCardsCount() {
		log.info("checking featured section cards count");
		return page.locator(featuredproductList).count();
	}
	
	/**
	 * This method navigates to login page
	 * @return
	 */
	public LoginPage navigateToLoginPage() {
		page.click(myAccountLink);
		page.click(loginLink);
		return new LoginPage(page,extentTest);
	}
	
	/**
	 * This method navigates to Register page
	 * @return
	 */
	public RegisterPage navigateToRegisterPage() {
		page.click(myAccountLink);
		page.click(registerLink);
		return new RegisterPage(page,extentTest);
	}
	
	
	/**
	 * this method performs the product search
	 * @param productName
	 * @return
	 */
	public boolean searchProduct(String productName) {
		page.fill(searchEditbox, productName);
		page.click(searchIcon);
		String searchheader = page.textContent(searchPageHeader);
		extentLog(extentTest, Status.PASS, "search of "+searchheader+" product is sucessful");
		if(page.locator(productSearchResult).count()>0) {
			extentLog(extentTest, Status.PASS, "search of "+productName+" product is sucessful");	
		return true;
		}
		extentLogWithScreenshot(extentTest, Status.FAIL, "No Product is available for the search"+productName,takeScreenshot(page));	
		return false;
	}
	
	/**
	 * 
	 */
	public String addProductToCart() {
	Locator productLocator =	page.locator(productSearchResult).nth(0);
	productLocator.locator(addTocartSelector).click();
	String product = productLocator.locator(productCaptionName).textContent();
	extentLogWithScreenshot(extentTest, Status.PASS, product+" is added to cart.",takeScreenshot(page));	
	
	return product;
	}
}
