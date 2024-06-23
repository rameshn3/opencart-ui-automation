package pages;

import static base.PlaywrightFactory.takeScreenshot;
import static utils.ExtentReporter.extentLogWithScreenshot;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static utils.ExtentReporter.extentLog;

public class ProductPage {

	private Page page;
	private ExtentTest extentTest;

	public ProductPage(Page page, ExtentTest extentTest) {
		this.page = page;
		this.extentTest = extentTest;
	}

	private String productItem = "div.product-thumb  div.caption";
	private String priceList = "p.price";
	private String priceExTaxList = "span.price-tax";
	private String sortInputDrpdown = "#input-sort";
	private String myAccountMenu = "//span[contains(.,'My Account')]";
	private String errorMsg = "//div[contains(@class,'alert alert-danger')]";

	/**
	 * This method fetches productname and price from products page
	 * 
	 * @return
	 */
	public Map<String, Double> getProductsAndPrice() {

		Map<String, Double> mapOfProductsAndPrice = new HashMap<>();
		Locator productItems = page.locator(productItem);

		for (int i = 0; i < productItems.count(); i++) {
			String productName = productItems.nth(i).locator("a").textContent();
			// Get the price for each product
			Locator priceLocator = productItems.nth(i).locator(priceList);
			String priceString = priceLocator.textContent().replaceAll("[^0-9.]", "");
			Double price = Double.parseDouble(priceString);
			mapOfProductsAndPrice.put(productName, price);
		}
		return mapOfProductsAndPrice;
	}

	
	public boolean sortProductByPrice() {
		Map<String,Double> mapSortedFromCode=sortMapByValue(getProductsAndPrice());
		//select price low to high
		page.selectOption(sortInputDrpdown, page.locator("text = 'Price (Low > High)'").elementHandle());
		//fetch the prices from ui
		Map<String,Double> mapOfProductsPricesSortedFromUI=getProductsAndPrice();
	
		if(mapOfProductsPricesSortedFromUI.equals(mapSortedFromCode)) {
			extentLog(extentTest,Status.PASS,"The products are sorted by prices in UI");
			return true;
		}else {
			extentLogWithScreenshot(extentTest,Status.FAIL,"The products are sorted by prices in UI",takeScreenshot(page));
			return false;	
		}
	}
	
	/**
	 * this method is used to srot the map based on value
	 * @param mapOfProdctAndPrice
	 * @return
	 */
	private Map<String,Double> sortMapByValue(Map<String,Double>mapOfProdctAndPrice){
		
		 return mapOfProdctAndPrice.entrySet().stream().sorted(Map.Entry.comparingByValue())
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue,newValue)->oldValue,LinkedHashMap::new));
		
	}
	
}
