
import static com.google.common.base.Strings.isNullOrEmpty;

import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    //  Opening ################################ Using AppliTools ######################################
    private EyesRunner runner;
    //private Eyes eyes;
    public Eyes eyes;
    private static BatchInfo batch;
    //private WebDriver driver;
    public WebDriver driver;

    @BeforeSuite
    public static void setBatch() {
        // Must be before ALL tests (at Class-level)
        batch = new BatchInfo("Rest Testing");
    }

    @BeforeMethod
    public void beforeEach() {
        // Initialize the Runner for your test.
        runner = new ClassicRunner();

        // Initialize the eyes SDK
        eyes = new Eyes(runner);



        // Raise an error if no API Key has been found.
        if(isNullOrEmpty(System.getenv("APPLITOOLS_API_KEY"))) {
            throw new RuntimeException("No API Key found; Please set environment variable 'APPLITOOLS_API_KEY'.");
        }

        // Set your personal Applitols API Key from your environment variables.
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        // set batch name
        eyes.setBatch(batch);

        // Use Chrome browser
        //driver = new ChromeDriver();

    }
//  Closing ################################ Using AppliTools ######################################

//   String baseUrl = "https://rest.com.au/";

    @BeforeTest
    public void openBrowser(){
        //System.setProperty("webdriver.chrome.driver", "C:\\Automation\\demoForRest.com.au\\src\\main\\resources\\Driver\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", ".\\src\\main\\resources\\Driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.get(baseUrl);
    }

    @AfterTest
    public void closeBrowser(){

//  Opening ################################ Using AppliTools ######################################
        // If the test was aborted before eyes.close was called, ends the test as
        // aborted.
        eyes.abortIfNotClosed();

        // Wait and collect all test results
        TestResultsSummary allTestResults = runner.getAllTestResults();

        // Print results
        System.out.println(allTestResults);
//  Closing ################################ Using AppliTools ######################################

        driver.close();
        driver.quit();
    }


}
