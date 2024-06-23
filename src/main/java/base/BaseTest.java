package base;

import java.io.File;
import java.nio.file.Paths;
import static utils.ExtentReporter.extentLogWithScreenshot;
import static base.PlaywrightFactory.takeScreenshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;

import pages.ForgotPasswordPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.RegisterPage;
import utils.ExtentReporter;
import utils.TestProperties;

public class BaseTest {

    protected Page page;
    protected SoftAssert softAssert = new SoftAssert();
    protected ExtentTest extentTest, testNode;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected ProductPage productPage;
    protected RegisterPage registerPage;
    protected ForgotPasswordPage forgotPasswordPage;
    protected static ExtentReports extreporter;
    protected static TestProperties testproperties;
    private static Logger log;
    /**
	 * BeforeSuite method is used to do cleanup of test-results and initialization of
	 * logger, TestProperties and extentreports 
	 * @throws Exception
	 */
    @BeforeSuite
    public void setUpBeforeTestSuite() throws Exception {
        File f = new File("test-results");
        if (f.exists() && !deleteDirectory(f)) {
            throw new Exception("Exception occurred while deleting the test-results directory");
        }

        log = LogManager.getLogger();
        testproperties = new TestProperties();
        testproperties.updateTestProperties();
        extreporter = ExtentReporter.getExtentReporter(testproperties);
    }
    /**
	 * AfterSuite method is used to assert all the soft assertions and flush the extent report
	 */
    @AfterSuite
    public void tearDownAfterSuite() {
        try {
            softAssert.assertAll();
            extreporter.flush();
        } catch (Exception ex) {
            log.error("Error in AfterSuite Method", ex);
        }
    }
    /**
	 * BeforeMetho to start the playwright server, create a page and navigate to the URL 
	 * 
	 */
    @BeforeMethod
    public void launchPlaywrightAndUrl(ITestResult result) {
        PlaywrightFactory pf = new PlaywrightFactory(testproperties);
        page = pf.createPage();
        page.navigate(testproperties.getProperty("url"));
        //fetch Test Method name dynamically
        
        String testName = result.getMethod().getMethodName(); // Initialize testNode here or in each test method
        extentTest = extreporter.createTest(testName); // Initialize extentTest here
        // Initialize homePage
        homePage = new HomePage(page,extentTest);
        loginPage = new LoginPage(page,extentTest);
        registerPage = new RegisterPage(page,extentTest);
        forgotPasswordPage=new ForgotPasswordPage(page,extentTest);
    }

    @AfterMethod
    public void closePageAndPwServer(ITestResult result) {
        if (extentTest != null) {
            String testName = extentTest.getModel().getName();
            if (Boolean.parseBoolean(testproperties.getProperty("enableTracing"))) {
                String fileName = testproperties.getProperty("tracingDirectory") + " Trace_" + testName + ".zip";
                page.context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get(fileName)));
            }
            if (!result.isSuccess()) {
                extentLogWithScreenshot(extentTest, Status.WARNING, "The test is not passed. Please refer to the previous step", takeScreenshot(page));
            }
        } else {
            log.warn("extentTest is null. Skipping tracing and logging steps.");
        }
        page.context().browser().close();
        extreporter.flush();
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allFolders = directoryToBeDeleted.listFiles();

        if (allFolders != null) {
            for (File folder : allFolders) {
                deleteDirectory(folder);
            }
        }

        return directoryToBeDeleted.delete();
    }
}
