package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginAction {
    public static void performLogin(WebDriver driver, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userInp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passInp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));

        userInp.sendKeys(username);
        passInp.sendKeys(password);
        loginBtn.click();
    }
}
