import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.applitools.eyes.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class ValidateRestHomePage extends BaseClass{


    @DataProvider(name="HomePage")
    public Object[][] getData() {

        //ExcelReader excel = new ExcelReader("C:\\Automation\\demoForRest.com.au\\src\\main\\resources\\TestData\\ObjectRepository.xlsx");
        ExcelReader excel = new ExcelReader(".\\src\\main\\resources\\TestData\\ObjectRepository.xlsx");
        String sheetName = "HomePageObj";

        int rowNum = excel.getRowCount(sheetName);
        int colNum = excel.getColumnCount(sheetName);

        System.out.println("Total number of rows: " + rowNum + "Total number of columns: " + colNum);

        Object[][] data = new Object[rowNum - 1][colNum];

        for (int rows = 2; rows <= rowNum; rows++) {

            for (int cols = 0; cols < colNum; cols++) {
                data[rows-2][cols] = excel.getCellData(sheetName, cols, rows);
                //System.out.println(rows);
                //System.out.println(cols);
                //System.out.println(data);
            }

        }

        return data;

    }

    @Test(dataProvider = "HomePage", priority = 1)
    public void validateObjectsArePresentOnHomePage(String Sno,String Website,String Page,String ElementName,String Identifier,String Value) throws InterruptedException {

        //System.out.println(Sno + Website + Page + ElementName + Identifier + Value);
        //WebElement element = driver.findElement(By.xpath(Value));
        //JavascriptExecutor executor = (JavascriptExecutor) driver;
        // executor.executeScript("arguments[0].click();", element);
        WebDriverWait wait = new WebDriverWait(driver,30);
        driver.get("https://www.rest.com.au");

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("/html/body/header/nav/div/ul/li[1]"))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Value)));
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Thread.sleep(2000);
        actions.moveToElement(driver.findElement(By.xpath(Value))).perform();
        actions.build().perform();

        driver.switchTo().activeElement().findElement(By.xpath(Value));

        System.out.println(ElementName + " is Displayed : " + driver.findElement(By.xpath(Value)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(Value)).isDisplayed());
        System.out.println(driver.findElement(By.xpath(Value)).getText());
    }

    @Test
    public void getAllLinksOfHomePage(){

        driver.get("https://www.rest.com.au");
        //Get list of web-elements with tagName  - a
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        //Traversing through the list and printing its text along with link address
        for(WebElement link:allLinks){
            System.out.println(link.getText() + " - " + link.getAttribute("href"));
        }

    }

    @Test
    public void validateHomePageLoading(){
        System.out.println("Opening Rest.com.au");

  //  Opening ################################ Using AppliTools ######################################

        // Set AUT's name, test name and viewport size (width X height)
        // We have set it to 800 x 600 to accommodate various screens. Feel free to
        // change it.
        //eyes.open(driver, "Rest", "Smoke Test", new RectangleSize(800, 800));
        eyes.open(driver, "Rest", "Homepage");

        // Navigate the browser to the "ACME" demo app.
        //driver.get("https://demo.applitools.com");
        driver.get("https://rest.com.au/super/super-products");
        driver.get("https://www.rest.com.au");
        driver.manage().window().maximize();

        // To see visual bugs after the first run, use the commented line below instead.
        //driver.get("https://demo.applitools.com/index_v2.html");

        // Visual checkpoint #1 - Check the login page.
        //eyes.checkWindow("Login Window");
        //eyes.checkWindow("Home Page", eyes.getForceFullPageScreenshot());
        eyes.checkWindow("Home Page",true);

        // This will create a test with two test steps.
       // driver.findElement(By.id("log-in")).click();

        // Visual checkpoint #2 - Check the app page.
       // eyes.checkWindow("App Window");

        // End the test.
        eyes.closeAsync();




  //  Closing ################################ Using AppliTools ######################################



    }




}
