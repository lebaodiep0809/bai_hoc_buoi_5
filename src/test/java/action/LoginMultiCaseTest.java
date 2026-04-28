package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginMultiCaseTest {
    WebDriver driver;
    @BeforeClass
    public void setUpClass(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @BeforeMethod
    public void setUp(){
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testLoginWithValidCredentials(){
        //Điền thông tin đúng bằng action
        LoginAction.performLogin(driver, "standard_user", "secret_sauce");

        //Xác nhận vào trang inventory
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");

        //Kiểm tra logo & sản phẩm
        WebElement appLogo = driver.findElement(By.className("app_logo"));
        Assert.assertTrue(appLogo.isDisplayed(), "App logo is displayed on invertor page!");

        //Kiểm tra có its nhất 1 sản phẩm hiển thị
        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No product displayed on inventory page!");

    }

    @Test
    public void testLoginWithInvalidCredentials(){
        //Điền thông tin sai bằng Action
        LoginAction.performLogin(driver, "standard_user1", "secret_sauce1");

        //Kiểm tra thông báo lỗi
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test
    public void testLoginWithInvaliUsername(){
        //Điền thông tin sai bằng Action
        LoginAction.performLogin(driver, "standard_user", "secret_sauce1");


        //Kiểm tra thông báo lỗi
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test
    public void testLoginWithInvaliPassword(){

        LoginAction.performLogin(driver, "standard_user1", "secret_sauce");

        //
        //Kiểm tra thông báo lỗi
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test
    public void testLoginWithEmptyUsername(){

        LoginAction.performLogin(driver, "", "secret_sauce1");


        //Kiểm tra thông báo lỗi
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username is required"));

    }

    @Test
    public void testLoginWithEmptyPassword(){

        LoginAction.performLogin(driver, "standard_user", "");


        //Kiểm tra thông báo lỗi
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not displayed");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Password is required"));
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("=== @AfterMethod: Chạy sau mỗi test case (@Test) ===");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("=== @AfterClass: Đóng Driver - Chạy sau tất cả các method trong class này ===");
        if (driver != null) {
            driver.quit();
        }
    }
}
