package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.SecureRandom;

public class LoginTest {
    WebDriver driver;
    @BeforeClass public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }
    @Test
    public void testLoginFunctionlity() {
        //Kiểm tra tiêu đề trang
        String expectedTile = "Swag Labs";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTile, actualTitle, "Page title is incorrect!");

        //Kiểm tra URL
        String expectedURL = "https://www.saucedemo.com/";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(expectedURL, actualURL, "Page URL is incorrect!");

        //Thực hiện login bằng Action
        LoginAction.performLogin(driver, "standard_user", "secret_sauce");

        //Kiểm tra URL sau khi login thành công
        String expectedHomePageURL = "https://www.saucedemo.com/inventory.html";
        String actualHomePageURL = driver.getCurrentUrl();
        Assert.assertEquals(expectedHomePageURL, actualHomePageURL, "Login failed");

        //Kiểm tra logo trên trang Home
        WebElement appLogo = driver.findElement(By.className("app_logo"));
        Assert.assertTrue(appLogo.isDisplayed(), "App logo is displayed on invertor page!");

        //Kiểm tra có its nhất 1 sản phẩm hiển thị
        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No product displayed on inventory page!");
    }
        @AfterClass
        public void afterClass() {
            System.out.println("=== @AfterClass: Đóng Driver - Chạy sau tất cả các method trong class này ===");
            if (driver != null) {
                driver.quit();
            }
        }

}
